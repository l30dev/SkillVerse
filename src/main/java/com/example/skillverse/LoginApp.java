package com.example.skillverse;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApp extends Application {

    @Override
    public void start(Stage stage) {
        LoginPage loginPage = new LoginPage(stage);
        Scene scene = new Scene(loginPage.getLayout(), 350, 400);
        scene.getStylesheets().add((getClass().getResource("loginStyle.css")).toExternalForm());
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}