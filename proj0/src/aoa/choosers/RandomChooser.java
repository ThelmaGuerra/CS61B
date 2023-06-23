package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.List;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in/change this constructor.
        List<String> words = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if(wordLength < 1) {throw new IllegalArgumentException("The length of the word is too small.");}

        if(words.size() == 0) {throw new IllegalStateException("dictionary file is empty.");}


        int words_size = words.size();
        int random_number = StdRandom.uniform(words_size);
        chosenWord = words.get(random_number);

        String first_pattern = "";
        for(int i = 0; i < chosenWord.length(); i++) {
            first_pattern += "-";
        }

        pattern = first_pattern;
    }

    @Override
    public int makeGuess(char letter) {
        // TODO: Fill in this method.
        int number_of_occurrences = 0;
        StringBuilder new_pattern = new StringBuilder(pattern);

        for(int i = 0; i < chosenWord.length(); i++) {
            if(chosenWord.charAt(i) == letter) {
                number_of_occurrences++;

                new_pattern.setCharAt(i, letter);

            }
        }

        pattern = String.valueOf(new_pattern);

        return number_of_occurrences;
    }

    @Override
    public String getPattern() {
        // TODO: Fill in this method.
        return pattern;
    }

    @Override
    public String getWord() {
        // TODO: Fill in this method.
        return chosenWord;
    }
}
