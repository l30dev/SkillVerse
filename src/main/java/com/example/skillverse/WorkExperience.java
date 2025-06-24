package com.example.skillverse;

public class WorkExperience {
    private String education;
    private String primaryRole;
    private String skills;
    private int experienceYears;
    private String projectTypes;
    private String availability;
    private String languagesSpoken;
    private String workStyle;
    private String bio;

    public WorkExperience(String education, String primaryRole, String skills, int experienceYears,
                          String projectTypes, String availability, String languagesSpoken,
                          String workStyle, String bio) {
        this.education = education;
        this.primaryRole = primaryRole;
        this.skills = skills;
        this.experienceYears = experienceYears;
        this.projectTypes = projectTypes;
        this.availability = availability;
        this.languagesSpoken = languagesSpoken;
        this.workStyle = workStyle;
        this.bio = bio;
    }

    public String getEducation() { return education; }
    public String getPrimaryRole() { return primaryRole; }
    public String getSkills() { return skills; }
    public int getExperienceYears() { return experienceYears; }
    public String getProjectTypes() { return projectTypes; }
    public String getAvailability() { return availability; }
    public String getLanguagesSpoken() { return languagesSpoken; }
    public String getWorkStyle() { return workStyle; }
    public String getBio() { return bio; }
}
