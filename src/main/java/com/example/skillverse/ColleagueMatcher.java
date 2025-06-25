package com.example.skillverse;

import java.sql.*;
import java.util.*;

public class ColleagueMatcher {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/SkillVerse";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1216";

    public static class MatchResult {
        public int personalInfoId;  // personal_info.id
        public String fullName;
        public int score;

        public MatchResult(int personalInfoId, String fullName, int score) {
            this.personalInfoId = personalInfoId;
            this.fullName = fullName;
            this.score = score;
        }
    }

    public static List<MatchResult> findTopMatches(int currentUserId) {
        List<MatchResult> matches = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 1. Get personal_info.id by user_id (users.id)
            PreparedStatement piStmt = conn.prepareStatement(
                    "SELECT id FROM personal_info WHERE user_id = ?"
            );
            piStmt.setInt(1, currentUserId);
            ResultSet piRs = piStmt.executeQuery();

            if (!piRs.next()) {
                System.err.println("No personal_info record for user_id: " + currentUserId);
                return matches;
            }
            int currentPersonalInfoId = piRs.getInt("id");

            // 2. Fetch current user's personal_info + work_info by personal_info.id
            PreparedStatement currStmt = conn.prepareStatement(
                    "SELECT wi.*, pi.first_name, pi.last_name, pi.location_country, pi.languages, pi.user_id " +
                            "FROM work_info wi " +
                            "JOIN personal_info pi ON wi.id = pi.id " +
                            "WHERE pi.id = ?"
            );
            currStmt.setInt(1, currentPersonalInfoId);
            ResultSet currentRs = currStmt.executeQuery();

            if (!currentRs.next()) {
                System.err.println("No work_info/personal_info found for personal_info.id: " + currentPersonalInfoId);
                return matches;
            }

            // Extract current user fields
            String role = currentRs.getString("primary_role");
            String[] skills = split(currentRs.getString("skills"));
            int experience = currentRs.getInt("experience_years");
            String projects = currentRs.getString("project_types");
            String country = currentRs.getString("location_country");
            String[] languages = split(currentRs.getString("languages"));
            String workStyle = currentRs.getString("work_style");

            // 3. Fetch all other users (exclude current user by personal_info.user_id)
            PreparedStatement otherStmt = conn.prepareStatement(
                    "SELECT wi.*, pi.first_name, pi.last_name, pi.id, pi.location_country, pi.languages, pi.user_id " +
                            "FROM work_info wi " +
                            "JOIN personal_info pi ON wi.id = pi.id " +
                            "WHERE pi.user_id != ?"
            );
            otherStmt.setInt(1, currentUserId);
            ResultSet rs = otherStmt.executeQuery();

            while (rs.next()) {
                int score = 0;

                if (safeEquals(role, rs.getString("primary_role"))) score += 3;
                score += overlapCount(skills, split(rs.getString("skills")), 4);

                int otherExp = rs.getInt("experience_years");
                if (Math.abs(otherExp - experience) <= 2) score += 2;

                if (safeEquals(projects, rs.getString("project_types"))) score += 2;
                if (safeEquals(country, rs.getString("location_country"))) score += 2;

                score += overlapCount(languages, split(rs.getString("languages")), 2);

                if (safeEquals(workStyle, rs.getString("work_style"))) score += 1;

                matches.add(new MatchResult(
                        rs.getInt("id"),
                        rs.getString("first_name") + " " + rs.getString("last_name"),
                        score
                ));
            }

            matches.sort((a, b) -> Integer.compare(b.score, a.score));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matches.subList(0, Math.min(matches.size(), 3));
    }

    private static String[] split(String s) {
        return s != null ? s.toLowerCase().split(",\\s*") : new String[0];
    }

    private static int overlapCount(String[] a, String[] b, int maxPoints) {
        int count = 0;
        for (String x : a)
            for (String y : b)
                if (x.equalsIgnoreCase(y)) count++;
        return Math.min(count, maxPoints);
    }

    private static boolean safeEquals(String a, String b) {
        return a != null && b != null && a.trim().equalsIgnoreCase(b.trim());
    }
}
