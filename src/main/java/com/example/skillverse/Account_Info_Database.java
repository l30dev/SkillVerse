package com.example.skillverse;

import java.sql.*;

public class Account_Info_Database {

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/SkillVerse";
        String user = "postgres";
        String password = "1216";
        return DriverManager.getConnection(url, user, password);
    }

    // Insert personal info and return generated personal_info.id (profileId)
    public static int insertPersonalInfoReturnId(String firstName, String lastName, Date birthday, String gender,
                                                 String email, String country, String city, String phone,
                                                 String bio, Timestamp createdAt, int user_id) {
        String sql = "INSERT INTO personal_info (first_name, last_name, birthday, gender, email, " +
                "location_country, location_city, phone_number, bio, last_info_update, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setDate(3, birthday);
            stmt.setString(4, gender);
            stmt.setString(5, email);
            stmt.setString(6, country);
            stmt.setString(7, city);
            stmt.setString(8, phone);
            stmt.setString(9, bio);
            stmt.setTimestamp(10, createdAt);
            stmt.setInt(11, user_id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // return profileId (personal_info.id)
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;  // error case
    }

    // Fetch personal_info.id by user_id — this is the "profile ID"
    public static int getPersonalInfoIdByUserId(int userId) {
        String sql = "SELECT id FROM personal_info WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");  // personal_info.id = profileId
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // Get personal info by user_id (for display)
    public static PersonalInfo getPersonalInfoByUserId(int userId) {
        String sql = "SELECT * FROM personal_info WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new PersonalInfo(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("birthday"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("location_country"),
                        rs.getString("location_city"),
                        rs.getString("phone_number"),
                        rs.getString("bio")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Insert work info linked to personal_info.id (profileId)
    // Note: in work_info table, the FK column is named 'id' (refers to personal_info.id)
    public static boolean insertWorkInfo(int personalInfoId, String education, String primary_role, String skills,
                                         int experience_years, String project_types, String availability,
                                         String languages_spoken, String work_style, Timestamp last_info_update) {
        String sql = "INSERT INTO work_info (id, education, primary_role, skills, experience_years, " +
                "project_types, availability, languages, work_style, last_info_update) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, personalInfoId);  // FK column 'id' refers to personal_info.id
            stmt.setString(2, education);
            stmt.setString(3, primary_role);
            stmt.setString(4, skills);
            stmt.setInt(5, experience_years);
            stmt.setString(6, project_types);
            stmt.setString(7, availability);
            stmt.setString(8, languages_spoken);
            stmt.setString(9, work_style);
            stmt.setTimestamp(10, last_info_update);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get work experience by personal_info.id (profileId)
    // Note: in work_info table, the FK column is named 'id' (refers to personal_info.id)
    public static WorkExperience getWorkInfoByPersonalInfoId(int personalInfoId) {
        String sql = "SELECT * FROM work_info WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, personalInfoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new WorkExperience(
                        rs.getString("education"),
                        rs.getString("primary_role"),
                        rs.getString("skills"),
                        rs.getInt("experience_years"),
                        rs.getString("project_types"),
                        rs.getString("availability"),
                        rs.getString("languages"),
                        rs.getString("work_style"),
                        ""  // bio or extra info if any
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void updateWorkInfo(int personalInfoId, WorkExperience work) {
        String sql = "UPDATE work_info SET education = ?, primary_role = ?, skills = ?, experience_years = ?, " +
                "project_types = ?, availability = ?, languages = ?, work_style = ?, last_info_update = CURRENT_TIMESTAMP " +
                "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, work.getEducation());
            pstmt.setString(2, work.getPrimaryRole());
            pstmt.setString(3, work.getSkills());
            pstmt.setInt(4, work.getExperienceYears());
            pstmt.setString(5, work.getProjectTypes());
            pstmt.setString(6, work.getAvailability());
            pstmt.setString(7, work.getLanguagesSpoken());
            pstmt.setString(8, work.getWorkStyle());
            pstmt.setInt(9, personalInfoId);

            int updatedRows = pstmt.executeUpdate();
            System.out.println(updatedRows > 0 ? "✅ Work info updated." : "❌ No work info updated.");
        } catch (SQLException e) {
            System.out.println("❌ Failed to update work info: " + e.getMessage());
        }
    }

    public static void updatePersonalInfo(int userId, PersonalInfo info) {
        String sql = "UPDATE personal_info SET " +
                "first_name = ?, last_name = ?, birthday = ?, gender = ?, " +
                "email = ?, location_country = ?, location_city = ?, phone_number = ?, bio = ?, last_info_update = CURRENT_TIMESTAMP " +
                "WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, info.getFirstName());
            pstmt.setString(2, info.getLastName());
            pstmt.setDate(3, info.getBirthday()); // java.sql.Date
            pstmt.setString(4, info.getGender());
            pstmt.setString(5, info.getEmail());
            pstmt.setString(6, info.getCountry());  // maps to location_country
            pstmt.setString(7, info.getCity());     // maps to location_city
            pstmt.setString(8, info.getPhone());    // maps to phone_number
            pstmt.setString(9, info.getBio());
            pstmt.setInt(10, userId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Personal info updated successfully.");
            } else {
                System.out.println("⚠️ No personal info record updated - check user_id.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to update personal info: " + e.getMessage());
        }
    }


}
