package com.example.skillverse;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApp extends Application {

    @Override
    public void start(Stage stage) {
        WelcomePage.show(stage); // Start from welcome screen
    }

    public static void show(Stage stage) {
        LoginPage loginPage = new LoginPage(stage); // You already have this class
        Scene scene = new Scene(loginPage.getLayout(), 350, 400);
        scene.getStylesheets().add(LoginApp.class.getResource("loginStyle.css").toExternalForm());
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}