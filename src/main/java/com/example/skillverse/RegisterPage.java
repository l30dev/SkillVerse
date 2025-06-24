package com.example.skillverse;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterPage {
    private VBox layout;

    public RegisterPage(Stage stage, String prevUser, String prevPassword) {
        Label title = new Label("Register");
        TextField username = new TextField();
        username.setPromptText("New Username");
        PasswordField password = new PasswordField();
        password.setPromptText("New Password");

        Button registerBtn = new Button("Register");
        Button backBtn = new Button("Back to Login");

        layout = new VBox(10, title, username, password, registerBtn, backBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("login-box");

        registerBtn.setOnAction(e -> {
            boolean success = Database.insertUser(username.getText(), password.getText());
            if (success) {
                // Pass the newly registered username to PersonalInfoPage
                PersonalInfoPage personalInfoPage = new PersonalInfoPage(stage, username.getText());
                Scene personalScene = new Scene(personalInfoPage.getLayout(), 400, 600);
                personalScene.getStylesheets().add(getClass().getResource("loginStyle.css").toExternalForm());
                stage.setScene(personalScene);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Registration failed.");
                alert.show();
            }
        });

        backBtn.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(stage, prevUser, prevPassword);
            Scene loginScene = new Scene(loginPage.getLayout(), 350, 400);
            loginScene.getStylesheets().add(getClass().getResource("loginStyle.css").toExternalForm());
            stage.setScene(loginScene);
        });
    }

    public VBox getLayout() {
        return layout;
    }
}
