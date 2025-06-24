package com.example.skillverse;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterSuccessPage {
    private VBox layout;

    public RegisterSuccessPage(Stage stage) {
        Label successMsg = new Label("Successfully Registered!");
        Button backToLogin = new Button("Back to Login");

        layout = new VBox(15, successMsg, backToLogin);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("login-box");

        backToLogin.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(stage);
            Scene loginScene = new Scene(loginPage.getLayout(), 350, 400);
            loginScene.getStylesheets().add(getClass().getResource("loginStyle.css").toExternalForm());
            stage.setScene(loginScene);
        });
    }

    public VBox getLayout() {
        return layout;
    }
}