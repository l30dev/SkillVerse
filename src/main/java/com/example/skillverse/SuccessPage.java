package com.example.skillverse;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.List;

public class SuccessPage {

    public static void show(Stage stage, String username) {
        Label welcomeLabel = new Label("Welcome, " + username + "!");
        welcomeLabel.setFont(new Font("Arial", 22));

        Button profileBtn = new Button("👤 View Profile Info");
        Button searchBtn = new Button("🔍 Search Compatible Colleagues");
        Button logoutBtn = new Button("🚪 Logout");

        profileBtn.setOnAction(e -> {
            int userId = Database.getId(username);
            PersonalInfo info = Account_Info_Database.getPersonalInfoByUserId(userId);

            if (info == null) {
                showAlert("No personal info found!");
                return;
            }

            Label infoLabel = new Label(formatPersonalInfo(info));
            infoLabel.setWrapText(true);

            Button editProfileBtn = new Button("Edit Profile Info");
            Button workInfoBtn = new Button(" View Work Info");
            Button backBtn = new Button(" Back");

            VBox profileLayout = new VBox(15, infoLabel, editProfileBtn, workInfoBtn, backBtn);
            profileLayout.setAlignment(Pos.CENTER);
            profileLayout.setPadding(new Insets(25));
            profileLayout.getStyleClass().add("login-box");

            Scene profileScene = new Scene(profileLayout, 450, 550);
            profileScene.getStylesheets().add(SuccessPage.class.getResource("profileStyle.css").toExternalForm());
            stage.setScene(profileScene);

            editProfileBtn.setOnAction(ev -> showEditProfilePage(stage, username, info, profileScene));

            workInfoBtn.setOnAction(ev -> {
                int personalInfoId = Account_Info_Database.getPersonalInfoIdByUserId(userId);
                WorkExperience work = Account_Info_Database.getWorkInfoByPersonalInfoId(personalInfoId);
                if (work == null) {
                    showAlert("No work info found!");
                    return;
                }

                Label workLabel = new Label(formatWorkInfo(work));
                workLabel.setWrapText(true);

                Button editWorkBtn = new Button("Edit Work Info");
                Button backToProfileBtn = new Button("Back to Profile");

                VBox workLayout = new VBox(15, workLabel, editWorkBtn, backToProfileBtn);
                workLayout.setAlignment(Pos.CENTER);
                workLayout.setPadding(new Insets(25));
                workLayout.getStyleClass().add("login-box");

                Scene workScene = new Scene(workLayout, 450, 550);
                workScene.getStylesheets().add(SuccessPage.class.getResource("workStyle.css").toExternalForm());
                stage.setScene(workScene);

                editWorkBtn.setOnAction(ev2 -> {
                    WorkInfoEditPage editPage = new WorkInfoEditPage(stage, personalInfoId, work, workScene);
                });

                backToProfileBtn.setOnAction(ev2 -> stage.setScene(profileScene));
            });

            backBtn.setOnAction(backEvent -> show(stage, username));
        });


        searchBtn.setOnAction(e -> {
            int currentUserId = Database.getId(username);
            List<ColleagueMatcher.MatchResult> matches = ColleagueMatcher.findTopMatches(currentUserId);

            if (matches.isEmpty()) {
                showAlert("No compatible partners found.");
            } else {
                StringBuilder sb = new StringBuilder("Top Compatible Partners:\n\n");
                for (ColleagueMatcher.MatchResult match : matches) {
                    sb.append(String.format("%s (Score: %d)\n", match.fullName, match.score));
                }
                showInfoDialog("Search Results", sb.toString());
            }
        });

        logoutBtn.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(stage);
            Scene loginScene = new Scene(loginPage.getLayout(), 350, 400);
            loginScene.getStylesheets().add(SuccessPage.class.getResource("loginStyle.css").toExternalForm());
            stage.setScene(loginScene);
        });

        VBox layout = new VBox(20, welcomeLabel, profileBtn, searchBtn, logoutBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.getStyleClass().add("login-box");

        Scene scene = new Scene(layout, 400, 500);
        scene.getStylesheets().add(SuccessPage.class.getResource("loginStyle.css").toExternalForm());
        stage.setScene(scene);
    }

    private static void showEditProfilePage(Stage stage, String username, PersonalInfo info, Scene backScene) {
        TextField firstName = new TextField(info.getFirstName());
        TextField lastName = new TextField(info.getLastName());
        DatePicker birthday = new DatePicker();
        birthday.setValue(info.getBirthday().toLocalDate());
        TextField gender = new TextField(info.getGender());
        TextField email = new TextField(info.getEmail());
        TextField phone = new TextField(info.getPhone());
        TextField country = new TextField(info.getCountry());
        TextField city = new TextField(info.getCity());
        TextArea bio = new TextArea(info.getBio());

        Button saveBtn = new Button("✅ Save Changes");
        Button cancelBtn = new Button("❌ Cancel");

        VBox layout = new VBox(10, firstName, lastName, birthday, gender, email, phone, country, city, bio, saveBtn, cancelBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
        layout.getStyleClass().add("login-box");

        Scene scene = new Scene(layout, 450, 600);
        scene.getStylesheets().add(SuccessPage.class.getResource("profileStyle.css").toExternalForm());
        stage.setScene(scene);

        saveBtn.setOnAction(e -> {
            info.setFirstName(firstName.getText());
            info.setLastName(lastName.getText());
            info.setBirthday(Date.valueOf(birthday.getValue()));
            info.setGender(gender.getText());
            info.setEmail(email.getText());
            info.setPhone(phone.getText());
            info.setCountry(country.getText());
            info.setCity(city.getText());
            info.setBio(bio.getText());

            int userId = Database.getId(username);
            Account_Info_Database.updatePersonalInfo(userId, info);
            showAlert("Profile updated!");
            stage.setScene(backScene);
        });

        cancelBtn.setOnAction(e -> stage.setScene(backScene));
    }

    private static String formatPersonalInfo(PersonalInfo info) {
        return "👤 Name: " + info.getFirstName() + " " + info.getLastName() + "\n" +
                "🎂 Birthday: " + info.getBirthday() + "\n" +
                "⚧ Gender: " + info.getGender() + "\n" +
                "📧 Email: " + info.getEmail() + "\n" +
                "🌍 Location: " + info.getCountry() + ", " + info.getCity() + "\n" +
                "📞 Phone: " + info.getPhone() + "\n" +
                "📝 Bio: " + info.getBio();
    }

    private static String formatWorkInfo(WorkExperience work) {
        return "🎓 Education: " + work.getEducation() + "\n" +
                "💼 Primary Role: " + work.getPrimaryRole() + "\n" +
                "💼 Skills: " + work.getSkills() + "\n" +
                "📅 Experience: " + work.getExperienceYears() + " years\n" +
                "📁 Project Types: " + work.getProjectTypes() + "\n" +
                "📆 Availability: " + work.getAvailability() + "\n" +
                "🌐 Languages: " + work.getLanguagesSpoken() + "\n" +
                "🔧 Work Style: " + work.getWorkStyle();
    }

    static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    static void showInfoDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
