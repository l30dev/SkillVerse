package com.example.skillverse;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class WelcomePage {

    public static void show(Stage stage) {
        Label welcomeLabel = new Label("Welcome to SkillVerse");
        welcomeLabel.getStyleClass().add("welcome-label");

        // Logo ImageView
        ImageView logoView = new ImageView(new Image(WelcomePage.class.getResourceAsStream("/logo.png")));
        logoView.setFitHeight(250);
        logoView.setPreserveRatio(true);
        logoView.getStyleClass().add("image-view");

        // Continue Button
        Button continueButton = new Button("Continue");
        continueButton.getStyleClass().add("button");
        continueButton.setOnAction(e -> LoginApp.show(stage));

        // Feedback Label (initially empty)
        Label feedbackLabel = new Label();
        feedbackLabel.getStyleClass().add("feedback-bubble");

        // VBox layout: logo, welcome text, button, feedback message
        VBox mainContent = new VBox(20, logoView, welcomeLabel, continueButton, feedbackLabel);
        mainContent.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(mainContent);
        root.getStyleClass().add("welcome-root");

        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(WelcomePage.class.getResource("welcomeStyle.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("SkillVerse");
        stage.show();

        // Feedback messages list
        List<Feedback> feedbacks = List.of(
                new Feedback("Anna T.", "SkillVerse helped me find my ideal tech teammate!"),
                new Feedback("Jake M.", "I improved my resume after the AI matched me with a mentor."),
                new Feedback("Lela G.", "Great tool for discovering new collaboration opportunities."),
                new Feedback("Rahul K.", "A smart app that actually understands your skills!")
        );

        // Start feedback animation loop
        playFeedbackLoop(feedbacks, feedbackLabel);
    }

    private static void playFeedbackLoop(List<Feedback> feedbacks, Label feedbackLabel) {
        final int[] index = {0};

        Runnable showNextFeedback = new Runnable() {
            @Override
            public void run() {
                Feedback fb = feedbacks.get(index[0]);
                feedbackLabel.setText("💬 " + fb.text + " - " + fb.name);

                // Reset opacity and position for smooth animation
                feedbackLabel.setOpacity(1);
                feedbackLabel.setTranslateX(600);

                TranslateTransition slideIn = new TranslateTransition(Duration.seconds(1), feedbackLabel);
                slideIn.setToX(0);

                PauseTransition stay = new PauseTransition(Duration.seconds(5));

                FadeTransition fade = new FadeTransition(Duration.seconds(1), feedbackLabel);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);

                SequentialTransition seq = new SequentialTransition(slideIn, stay, fade);

                seq.setOnFinished(event -> {
                    index[0] = (index[0] + 1) % feedbacks.size();
                    run();
                });

                seq.play();
            }
        };

        showNextFeedback.run();
    }

    private static class Feedback {
        String name;
        String text;

        public Feedback(String name, String text) {
            this.name = name;
            this.text = text;
        }
    }
}
