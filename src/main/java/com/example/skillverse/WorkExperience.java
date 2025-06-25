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
    public void setEducation(String education) { this.education = education; }
    public void setPrimaryRole(String primaryRole) { this.primaryRole = primaryRole; }
    public void setSkills(String skills) { this.skills = skills; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    public void setProjectTypes(String projectTypes) { this.projectTypes = projectTypes; }
    public void setAvailability(String availability) { this.availability = availability; }
    public void setLanguagesSpoken(String languagesSpoken) { this.languagesSpoken = languagesSpoken; }
    public void setWorkStyle(String workStyle) { this.workStyle = workStyle; }
    public void setBio(String bio) { this.bio = bio; }

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
