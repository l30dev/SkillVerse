package com.example.skillverse;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPage {
    private VBox layout;

    public LoginPage(Stage stage,String prevUsername, String prevPassword) {
        Label title = new Label("Login");
        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setText(prevPassword);
        username.setText(prevUsername);
        Button loginBtn = new Button("Login");
        Button toRegisterBtn = new Button("Register");

        layout = new VBox(10, title, username, password, loginBtn, toRegisterBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("login-box");

        Platform.runLater(() -> {
            deselect(username);
            deselect(password);
        });

        loginBtn.setOnAction(e -> {
            if (Database.validateUser(username.getText(), password.getText())) {
                SuccessPage.show(stage, username.getText());
            } else {
                showAlert("Invalid credentials.");
            }
        });

        toRegisterBtn.setOnAction(e -> {
            RegisterPage registerPage = new RegisterPage(stage, username.getText(), password.getText());
            Scene registerScene = new Scene(registerPage.getLayout(), 350, 400);
            registerScene.getStylesheets().add(getClass().getResource("loginStyle.css").toExternalForm());
            stage.setScene(registerScene);
        });
    }
    public LoginPage(Stage stage) {
        this(stage, "", "");
    }

    public VBox getLayout() {
        return layout;
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
    private void deselect(TextField textField) {
        Platform.runLater(() -> {
            int caretPos = textField.getCaretPosition();
            textField.selectRange(caretPos, caretPos);
        });
    }
}