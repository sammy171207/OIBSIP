package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Random;

public class HelloController {
    @FXML
    private TextField guessField;
    @FXML
    private Button submitButton;
    @FXML
    private Label feedbackLabel;
    @FXML
    private Label attemptsLabel;

    private int targetNumber;
    private int attempts;

    public void initialize() {
        Random random = new Random();
        targetNumber = random.nextInt(100) + 1;
        attempts = 0;
    }

    @FXML
    protected void handleSubmitButtonAction() {
        String guessText = guessField.getText();
        try {
            int guess = Integer.parseInt(guessText);
            attempts++;
            if (guess < targetNumber) {
                feedbackLabel.setText("Too low!");
            } else if (guess > targetNumber) {
                feedbackLabel.setText("Too high!");
            } else {
                feedbackLabel.setText("Correct! The number was " + targetNumber);
                submitButton.setDisable(true);
            }
            attemptsLabel.setText("Attempts: " + attempts);
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter a valid number.");
        }
    }
}
