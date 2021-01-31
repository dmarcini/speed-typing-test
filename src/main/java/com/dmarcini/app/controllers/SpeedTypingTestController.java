package com.dmarcini.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public final class SpeedTypingTestController {
    @FXML
    private Label sentencesLabel;
    @FXML
    private TextField sentencesTextField;
    @FXML
    private Label resultsLabel;
    @FXML
    private Button resetButton;

    public SpeedTypingTestController() {

    }

    @FXML
    private void initialize() {
        resultsLabel.setVisible(false);
    }
}
