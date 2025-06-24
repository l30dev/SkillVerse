package com.example.skillverse;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SuccessPage {

    public static void show(Stage stage, String username) {
        Label welcomeLabel = new Label("Welcome, " + username + "!");
        Button profileBtn = new Button("Check Profile Information");
        Button searchBtn = new Button("Search Compatible Colleagues");
        Button logoutBtn = new Button("Logout");

        profileBtn.setOnAction(e -> {
            int userId = Database.getId(username);
            System.out.println("User ID: " + userId); 

            PersonalInfo info = Account_Info_Database.getPersonalInfoByUserId(userId);

            if (info == null) {
                System.out.println("No personal info found!");
                return;
            }

            
            Label infoLabel = new Label(
                    "Name: " + info.getFirstName() + " " + info.getLastName() + "\n" +
                            "Birthday: " + info.getBirthday() + "\n" +
                            "Gender: " + info.getGender() + "\n" +
                            "Email: " + info.getEmail() + "\n" +
                            "Location: " + info.getCountry() + ", " + info.getCity() + "\n" +
                            "Phone: " + info.getPhone() + "\n" +
                            "Bio: " + info.getBio()
            );

            Button workInfoBtn = new Button("Show Work Info");
            Button backBtn = new Button("Back");

            VBox profileLayout = new VBox(10, infoLabel, workInfoBtn, backBtn);
            profileLayout.setAlignment(Pos.CENTER);
            profileLayout.setPadding(new Insets(20));
            profileLayout.getStyleClass().add("login-box");

            Scene profileScene = new Scene(profileLayout, 400, 500);
            profileScene.getStylesheets().add(SuccessPage.class.getResource("loginStyle.css").toExternalForm());
            stage.setScene(profileScene);

           
            workInfoBtn.setOnAction(ev -> {
                int personalInfoId = Account_Info_Database.getPersonalInfoIdByUserId(userId);
                WorkExperience work = Account_Info_Database.getWorkInfoByPersonalInfoId(personalInfoId);
                if (work == null) {
                    System.out.println("No work info found!");
                    return;
                }

                Label workLabel = new Label(
                        "Education: " + work.getEducation() + "\n" +
                                "Primary Role: " + work.getPrimaryRole() + "\n" +
                                "Skills: " + work.getSkills() + "\n" +
                                "Experience: " + work.getExperienceYears() + " years\n" +
                                "Project Types: " + work.getProjectTypes() + "\n" +
                                "Availability: " + work.getAvailability() + "\n" +
                                "Languages: " + work.getLanguagesSpoken() + "\n" +
                                "Work Style: " + work.getWorkStyle() + "\n" +
                                "Bio: " + work.getBio()
                );

                Button backToProfileBtn = new Button("Back to Profile Info");

                VBox workLayout = new VBox(10, workLabel, backToProfileBtn);
                workLayout.setAlignment(Pos.CENTER);
                workLayout.setPadding(new Insets(20));
                workLayout.getStyleClass().add("login-box");

                Scene workScene = new Scene(workLayout, 400, 500);
                workScene.getStylesheets().add(SuccessPage.class.getResource("loginStyle.css").toExternalForm());
                stage.setScene(workScene);

                backToProfileBtn.setOnAction(b -> stage.setScene(profileScene));
            });

           
            backBtn.setOnAction(backEvent -> show(stage, username));
        });

        searchBtn.setOnAction(e -> {
            System.out.println("Search feature coming soon!");
        });

        logoutBtn.setOnAction(e -> {
            // Navigate back to login page
            LoginPage loginPage = new LoginPage(stage);
            Scene loginScene = new Scene(loginPage.getLayout(), 350, 400);
            loginScene.getStylesheets().add(SuccessPage.class.getResource("loginStyle.css").toExternalForm());
            stage.setScene(loginScene);
        });

        VBox layout = new VBox(15, welcomeLabel, profileBtn, searchBtn, logoutBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("login-box");

        Scene scene = new Scene(layout, 350, 450);
        scene.getStylesheets().add(SuccessPage.class.getResource("loginStyle.css").toExternalForm());
        stage.setScene(scene);
    }
}
