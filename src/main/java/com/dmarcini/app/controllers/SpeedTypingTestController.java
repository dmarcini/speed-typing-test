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

    private long startTypingTime;
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

        resetButton.setOnAction(e -> reset());
    }

    @FXML
    private void startTyping() {
        if (!isTypingStarted) {
            startTypingTime = System.currentTimeMillis();
        }

        isTypingStarted = true;
    }

    @FXML
    private void getResults(KeyEvent e) {
        if (!e.getCode().equals(KeyCode.ENTER)) {
            return;
        }

        long timeInSeconds = (System.currentTimeMillis() - startTypingTime) / 1000;

        timeLabel.setText("time: " + timeInSeconds + "s");
        accuracyLabel.setText("accuracy: " + calculateAccuracy() + "%");
        wpmLabel.setText("WPM: " + calculateWPM(timeInSeconds));

        resultsVBox.setVisible(true);
    }

    @FXML
    private void reset() {
        isTypingStarted = false;

        sentencesLabel.setText(sentenceGenerator.getRandomSentence());
        sentencesTextField.clear();
        resultsVBox.setVisible(false);
    }

    private int calculateAccuracy() {
        var sentenceWords = sentencesLabel.getText().split(" ");
        var inputSentenceWords = sentencesTextField.getText().split(" ");

        int minSentenceWordsLength = Math.min(sentenceWords.length,
                                              inputSentenceWords.length);
        int maxSentenceWordsLength = Math.max(sentenceWords.length,
                                              inputSentenceWords.length);

        int accuracy = (int) IntStream.range(0, minSentenceWordsLength)
                                      .filter(i -> sentenceWords[i].equals(inputSentenceWords[i]))
                                      .count();
        accuracy = (int) (accuracy / (double) maxSentenceWordsLength * 100);

        return accuracy;
    }

    private int calculateWPM(long timeInSeconds) {
        int inputSentencesLength = sentencesTextField.getLength();

        return (int) (inputSentencesLength * 60 / (5 * timeInSeconds));
    }
}
