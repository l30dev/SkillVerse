package com.example.skillverse;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class WorkInfoEditPage {
    private Stage stage;
    private int personalInfoId;
    private WorkExperience work;
    private Scene backScene;

    public WorkInfoEditPage(Stage stage, int personalInfoId, WorkExperience work, Scene backScene) {
        this.stage = stage;
        this.personalInfoId = personalInfoId;
        this.work = work;
        this.backScene = backScene;

        buildLayout();
    }

    private void buildLayout() {
        ComboBox<String> education = new ComboBox<>();
        education.getItems().addAll(
                "High School", "Associate Degree", "Bachelor's Degree", "Master's Degree", "PhD",
                "Online Course Graduate", "Vocational Training", "Diploma Holder", "Certificate Program",
                "Postdoc", "Self-taught", "Technical School", "Bootcamp Graduate", "Short-term Workshop", "Other"
        );
        education.setValue(work.getEducation());

        ComboBox<String> role = new ComboBox<>();
        role.getItems().addAll(
                "Software Developer", "Data Scientist", "Project Manager", "UX/UI Designer", "QA Engineer",
                "DevOps Engineer", "AI/ML Engineer", "Business Analyst", "IT Consultant", "System Administrator",
                "Product Manager", "Mobile Developer", "Cloud Architect", "Technical Writer", "Other"
        );
        role.setValue(work.getPrimaryRole());

        TextArea skills = new TextArea(work.getSkills());

        Spinner<Integer> experienceYears = new Spinner<>(0, 50, work.getExperienceYears());
        experienceYears.setEditable(true);

        ComboBox<String> projectTypes = new ComboBox<>();
        projectTypes.getItems().addAll(
                "Web Applications", "Mobile Applications", "AI Projects", "Data Analysis", "Embedded Systems",
                "Cloud Solutions", "Blockchain Projects", "AR/VR Solutions", "Networking", "Game Development",
                "IoT Projects", "Cybersecurity Solutions", "Machine Learning Models", "DevOps Infrastructure", "Other"
        );
        projectTypes.setValue(work.getProjectTypes());

        ComboBox<String> availability = new ComboBox<>();
        availability.getItems().addAll(
                "Full-time", "Part-time", "Freelance", "Internship", "Contract",
                "Remote", "On-site", "Hybrid", "Project-based", "Consultant",
                "Temporary", "Volunteer", "Co-op", "Seasonal", "Other"
        );
        availability.setValue(work.getAvailability());

        ListView<String> languagesList = new ListView<>();
        languagesList.setItems(FXCollections.observableArrayList(
                "English", "Georgian", "Mandarin", "German", "French",
                "Russian", "Japanese", "Korean", "Hindi", "Italian",
                "Arabic", "Portuguese", "Turkish", "Polish", "Other"
        ));
        languagesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        languagesList.setPrefHeight(180);

        // Select existing languages
        for (String lang : work.getLanguagesSpoken().split(",\\s*")) {
            languagesList.getSelectionModel().select(lang);
        }

        ComboBox<String> workStyle = new ComboBox<>();
        workStyle.getItems().addAll(
                "Independent", "Team-oriented", "Detail-oriented", "Creative", "Analytical",
                "Problem-solver", "Flexible", "Strategic", "Proactive", "Organized",
                "Leader", "Communicator", "Fast learner", "Technical", "Innovative"
        );
        workStyle.setValue(work.getWorkStyle());

        TextArea bio = new TextArea(work.getBio());

        Button saveBtn = new Button("✅ Save Changes");
        Button cancelBtn = new Button("❌ Cancel");

        VBox layout = new VBox(10, education, role, skills, experienceYears, projectTypes,
                availability, new Label("Select Languages (Ctrl+Click):"), languagesList,
                workStyle, bio, saveBtn, cancelBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("login-box");

        Scene scene = new Scene(layout, 500, 650);
        scene.getStylesheets().add(WorkInfoEditPage.class.getResource("workStyle.css").toExternalForm());
        stage.setScene(scene);

        saveBtn.setOnAction(e -> {
            work.setEducation(education.getValue());
            work.setPrimaryRole(role.getValue());
            work.setSkills(skills.getText());
            work.setExperienceYears(experienceYears.getValue());
            work.setProjectTypes(projectTypes.getValue());
            work.setAvailability(availability.getValue());

            List<String> selectedLangs = languagesList.getSelectionModel().getSelectedItems();
            String languagesCsv = selectedLangs.stream().collect(Collectors.joining(", "));
            work.setLanguagesSpoken(languagesCsv);

            work.setWorkStyle(workStyle.getValue());
            work.setBio(bio.getText());

            Account_Info_Database.updateWorkInfo(personalInfoId, work);
            SuccessPage.showAlert("Work info updated!");
            stage.setScene(backScene);
        });

        cancelBtn.setOnAction(e -> stage.setScene(backScene));
    }
}

