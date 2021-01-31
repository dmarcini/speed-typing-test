package com.dmarcini.app.controllers;

import java.util.stream.IntStream;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import com.dmarcini.app.sentencegenerator.SentenceGenerator;


public final class SpeedTypingTestController {
    @FXML
    private Label sentencesLabel;
    @FXML
    private TextField sentencesTextField;
    @FXML
    private VBox resultsVBox;
    @FXML
    private Label timeLabel;
    @FXML
    private Label accuracyLabel;
    @FXML
    private Label wpmLabel;
    @FXML
    private Button resetButton;

    private final SentenceGenerator sentenceGenerator;

    private long startTime;

    private boolean isTypingStarted;

    public SpeedTypingTestController() {
        this.sentenceGenerator = new SentenceGenerator("com/dmarcini/app/files/sentences.txt");

        this.isTypingStarted = false;
    }

    @FXML
    private void initialize() {
        sentencesLabel.setWrapText(true);

        resultsVBox.setVisible(false);

        sentencesLabel.setText(sentenceGenerator.getRandomSentence());

        sentencesTextField.textProperty().addListener(e -> startTyping());
        sentencesTextField.setOnKeyPressed(this::getResults);
    }

    @FXML
    private void startTyping() {
        if (!isTypingStarted) {
            startTime = System.currentTimeMillis();
        }

        isTypingStarted = true;
    }

    @FXML
    private void getResults(KeyEvent e) {
        if (!e.getCode().equals(KeyCode.ENTER)) {
            return;
        }

        long timeInSeconds = (System.currentTimeMillis() - startTime) / 1000;

        timeLabel.setText("time: " + timeInSeconds + "s");
        accuracyLabel.setText("accuracy: " + calculateAccuracy() + "%");
        wpmLabel.setText("WPM: " + calculateWPM(timeInSeconds));

        resultsVBox.setVisible(true);
    }

    private int calculateAccuracy() {
        int sentencesLength = sentencesLabel.getText().length();
        int inputSentencesLength = sentencesTextField.getLength();

        int minSentencesLength = Math.min(sentencesLength, inputSentencesLength);
        int maxSentencesLength = Math.max(sentencesLength, inputSentencesLength);

        String sentences = sentencesLabel.getText();
        String inputSentences = sentencesTextField.getText();

        int accuracy = (int) IntStream.range(0, minSentencesLength)
                                      .filter(i -> sentences.charAt(i) ==
                                                   inputSentences.charAt(i))
                                      .count();
        accuracy = (int) (accuracy / (double) maxSentencesLength * 100);

        return accuracy;
    }

    private int calculateWPM(long timeInSeconds) {
        int inputSentencesLength = sentencesTextField.getLength();

        return (int) (inputSentencesLength * 60 / (5 * timeInSeconds));
    }
}
