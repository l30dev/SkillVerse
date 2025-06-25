package com.example.skillverse;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PersonalInfoPage {
    private VBox layout;
    private String username;


    public PersonalInfoPage(Stage stage, String username) {
        this.username = username;

        Label title = new Label("Personal Information");

        TextField firstName = new TextField();
        firstName.setPromptText("First Name");

        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");

        DatePicker birthday = new DatePicker();

        TextField gender = new TextField();
        gender.setPromptText("Gender");

        TextField email = new TextField();
        email.setPromptText("Email");

        TextField country = new TextField();
        country.setPromptText("Country");

        TextField city = new TextField();
        city.setPromptText("City");

        TextField phone = new TextField();
        phone.setPromptText("Phone (Optional)");

        TextArea bio = new TextArea();
        bio.setPromptText("Short Bio");

        Button nextBtn = new Button("Next");

        layout = new VBox(10, title, firstName, lastName, birthday, gender, email, country, city, phone, bio, nextBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("login-box");

        nextBtn.setOnAction(e -> {
            if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || birthday.getValue() == null ||
                    gender.getText().isEmpty() || email.getText().isEmpty() || country.getText().isEmpty() ||
                    city.getText().isEmpty()) {
                showAlert("Please fill out all required fields.");
                return;
            }


            int userId = Database.getId(username);
            if (userId == -1) {
                showAlert("User not found. Please login again.");
                return;
            }


            int personalId = Account_Info_Database.insertPersonalInfoReturnId(
                    firstName.getText(),
                    lastName.getText(),
                    Date.valueOf(birthday.getValue()),
                    gender.getText(),
                    email.getText(),
                    country.getText(),
                    city.getText(),
                    phone.getText(),
                    bio.getText(),
                    Timestamp.valueOf(LocalDateTime.now()),
                    userId
            );

            if (personalId != -1) {
                WorkInfoPage workPage = new WorkInfoPage(stage, personalId);
                Scene workScene = new Scene(workPage.getLayout(), 400, 600);
                workScene.getStylesheets().add(getClass().getResource("profileStyle.css").toExternalForm());
                stage.setScene(workScene);
            } else {
                showAlert("Failed to save personal info.");
            }
        });
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
