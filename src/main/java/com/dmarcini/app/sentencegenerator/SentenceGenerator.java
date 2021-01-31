package com.dmarcini.app.sentencegenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class SentenceGenerator {
    private List<String> sentences;

    private final Random random;

    public SentenceGenerator(String sentencesPath) {
        this.random = new Random();

        try {
            Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                                                                   .getResource(sentencesPath))
                                                                   .toURI());

            this.sentences = Files.lines(path).collect(Collectors.toList());
        } catch (URISyntaxException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getRandomSentence() {
        return sentences.get(random.nextInt(sentences.size()));
    }
}
