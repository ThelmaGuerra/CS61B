package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.

        // get list of words that match the pattern and that have not being guessed.
        List<String> wordsThatMatchPatternAndNotGuessed = keepOnlyWordsThatMatchPatternAndNotGuessed(pattern, guesses);


        // get frequency map of words that match pattern and are not guessed.

        Map<Character, Integer> matchedFrequencyMapAndNotGuessed = getFreqMapThatMatchesPatternAndNotGuessed(wordsThatMatchPatternAndNotGuessed);


        // get sorted list of values.
        Object[] sorted_values = matchedFrequencyMapAndNotGuessed.values().toArray();
        Arrays.sort(sorted_values, Collections.reverseOrder());

        // get set from map.
        Set<Map.Entry<Character, Integer>> entries_set = matchedFrequencyMapAndNotGuessed.entrySet();

        for(Object sorted_value : sorted_values) {
            for (Map.Entry<Character, Integer> entry : entries_set) {
                if (entry.getValue().equals(sorted_value)) {

                    if (guesses.contains(entry.getKey())) {
                        continue;
                    }

                    return entry.getKey();
                }
            }
        }

        return '?';
    }

    public List<String> keepOnlyWordsThatMatchPatternAndNotGuessed(String pattern, List<Character> guesses) {

        List<String> wordsThatMatchPatternAndNotGuessed = new ArrayList<String>();


        for(
                String word: words) {
            if (word.length() == pattern.length()) {
                boolean isMatched = true;

                for (int i = 0; i < word.length(); i++) {
                    if(guesses.contains(word.charAt(i)) && !guesses.contains(pattern.charAt(i))) {
                        isMatched = false;

                    }

                    if(pattern.charAt(i) != '-' && word.charAt(i) != pattern.charAt(i)) {
                        isMatched = false;
                    }
                }

                if(isMatched) {
                    wordsThatMatchPatternAndNotGuessed.add(word);
                }
            }
        }

        return wordsThatMatchPatternAndNotGuessed;
    }

    public Map<Character, Integer> getFreqMapThatMatchesPatternAndNotGuessed(List<String> wordsThatMatchPatternAndNotGuessed) {

        TreeMap<Character, Integer> map = new TreeMap<>();

        for ( String element : wordsThatMatchPatternAndNotGuessed) {
            for (int i = 0; i < element.length(); i++) {
                char c = element.charAt(i);
                if (map.containsKey(c)) {
                    int value = map.get(c);
                    map.replace(c, value + 1);
                } else {
                    map.put(c, 1);
                }
            }
        }

        return map;
    }

    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("proj0/data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
