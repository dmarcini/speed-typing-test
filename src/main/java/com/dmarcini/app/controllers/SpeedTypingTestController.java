package com.dmarcini.app.controllers;

import com.dmarcini.app.sentencegenerator.SentenceGenerator;
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

    private final SentenceGenerator sentenceGenerator;

    public SpeedTypingTestController() {
        this.sentenceGenerator = new SentenceGenerator("com/dmarcini/app/files/sentences.txt");
    }

    @FXML
    private void initialize() {
        sentencesLabel.setWrapText(true);
        resultsLabel.setVisible(false);

        sentencesLabel.setText(sentenceGenerator.getRandomSentence());
    }
}
