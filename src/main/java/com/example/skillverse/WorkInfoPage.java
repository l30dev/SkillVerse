package com.example.skillverse;


import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class WorkInfoPage {
    private VBox layout;
    private int personalInfoId;

    public WorkInfoPage(Stage stage, int personalInfoId) {
        this.personalInfoId = personalInfoId;

        Label title = new Label("Work & Skill Information");

        ComboBox<String> education = new ComboBox<>();
        education.getItems().addAll(
                "High School", "Associate Degree", "Bachelor's Degree", "Master's Degree", "PhD",
                "Online Course Graduate", "Vocational Training", "Diploma Holder", "Certificate Program",
                "Postdoc", "Self-taught", "Technical School", "Bootcamp Graduate", "Short-term Workshop", "Other"
        );
        education.setPromptText("Select Education");

        TextArea skills = new TextArea();
        skills.setPromptText("List of Skills");

        ComboBox<String> role = new ComboBox<>();
        role.getItems().addAll(
                "Software Developer", "Data Scientist", "Project Manager", "UX/UI Designer", "QA Engineer",
                "DevOps Engineer", "AI/ML Engineer", "Business Analyst", "IT Consultant", "System Administrator",
                "Product Manager", "Mobile Developer", "Cloud Architect", "Technical Writer", "Other"
        );
        role.setPromptText("Select Primary Role");

        Label experienceYearsText = new Label("Years of Experience:");
        Spinner<Integer> experienceYears = new Spinner<>(0, 50, 0);
        experienceYears.setEditable(true);

        ComboBox<String> projectTypes = new ComboBox<>();
        projectTypes.getItems().addAll(
                "Web Applications", "Mobile Applications", "AI Projects", "Data Analysis", "Embedded Systems",
                "Cloud Solutions", "Blockchain Projects", "AR/VR Solutions", "Networking", "Game Development",
                "IoT Projects", "Cybersecurity Solutions", "Machine Learning Models", "DevOps Infrastructure", "Other"
        );
        projectTypes.setPromptText("Select Project Type");

        ComboBox<String> availability = new ComboBox<>();
        availability.getItems().addAll(
                "Full-time", "Part-time", "Freelance", "Internship", "Contract",
                "Remote", "On-site", "Hybrid", "Project-based", "Consultant",
                "Temporary", "Volunteer", "Co-op", "Seasonal", "Other"
        );
        availability.setPromptText("Select Availability");

        ListView<String> languagesList = new ListView<>();
        languagesList.setItems(FXCollections.observableArrayList(
                "English", "Georgian", "Mandarin", "German", "French",
                "Russian", "Japanese", "Korean", "Hindi", "Italian",
                "Arabic", "Portuguese", "Turkish", "Polish", "Other"
        ));
        languagesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        languagesList.setPrefHeight(180);

        ComboBox<String> workStyle = new ComboBox<>();
        workStyle.getItems().addAll(
                "Independent", "Team-oriented", "Detail-oriented", "Creative", "Analytical",
                "Problem-solver", "Flexible", "Strategic", "Proactive", "Organized",
                "Leader", "Communicator", "Fast learner", "Technical", "Innovative"
        );
        workStyle.setPromptText("Select Work Style");

        TextArea bio = new TextArea();
        bio.setPromptText("Short Bio / Info");

        Button submitBtn = new Button("Submit Work Experience");

        submitBtn.setOnAction(e -> {
            if (education.getValue() == null || role.getValue() == null || skills.getText().trim().isEmpty()) {
                showAlert("Please fill out Education, Role, and Skills.");
                return;
            }

            List<String> selectedLangs = languagesList.getSelectionModel().getSelectedItems();
            String languagesCsv = selectedLangs.stream().collect(Collectors.joining(", "));

            boolean inserted = Account_Info_Database.insertWorkInfo(
                    personalInfoId,
                    education.getValue(),
                    role.getValue(),
                    skills.getText().trim(),
                    experienceYears.getValue(),
                    projectTypes.getValue(),
                    availability.getValue(),
                    languagesCsv,
                    workStyle.getValue(),
                    Timestamp.valueOf(LocalDateTime.now())
            );

            if (inserted) {
                RegisterSuccessPage successPage = new RegisterSuccessPage(stage);
                Scene successScene = new Scene(successPage.getLayout(), 300, 200);
                successScene.getStylesheets().add(getClass().getResource("loginStyle.css").toExternalForm());
                stage.setScene(successScene);
            } else {
                showAlert("Failed to save work info.");
            }
        });

        layout = new VBox(10, title, education, skills, role, experienceYearsText, experienceYears,
                projectTypes, availability, new Label("Select Languages (Ctrl+Click):"), languagesList,
                workStyle, bio, submitBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("login-box");
    }

    public VBox getLayout() {
        return layout;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
