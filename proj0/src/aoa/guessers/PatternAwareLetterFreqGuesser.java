package aoa.guessers;

import aoa.utils.FileUtils;
import com.google.common.collect.Lists;
import edu.princeton.cs.algs4.In;

import java.util.*;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.

        // get which words match the pattern.
        List<String> wordsThatMatchPattern = keepOnlyWordsThatMatchPattern(pattern);

        // get frequency map of these words.
        Map<Character, Integer> matchedFrequencyMap = getFreqMapThatMatchesPattern(wordsThatMatchPattern);


        // get sorted list of values.
        Object[] sorted_values = matchedFrequencyMap.values().toArray();
        Arrays.sort(sorted_values, Collections.reverseOrder());

        // get set from map.
        Set<Map.Entry<Character, Integer>> entries_set = matchedFrequencyMap.entrySet();

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

    public Map<Character, Integer> getFreqMapThatMatchesPattern(List<String> wordsThatMatchPattern) {

        TreeMap<Character, Integer> map = new TreeMap<>();

        for ( String element : wordsThatMatchPattern) {
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

    public List<String> keepOnlyWordsThatMatchPattern(String pattern) {
        // words must be the same size as patterns.
        // hidden letters can be all possible letters.
        // loop of 'WORDS' and each WORD must be the same SIZE as the PATTERN.
        // then, Inner for loop of the pattern length, compare each character of WORD and PATTERN, to see if they match.
        // if pattern_character = '-' then word_character can be any letter.
        // if pattern_character != '-' then word_character should be = pattern_character.
        // which means, if pattern_character != '-' and word_character != pattern_character, then is NOT A MATCH.

        List<String> wordsThatMatchPattern = new ArrayList<String>();

        for(String word: words) {
            if (word.length() == pattern.length()) {
                boolean isMatched = true;

                for (int i = 0; i < pattern.length(); i++) {

                    if(pattern.charAt(i) != '-' && word.charAt(i) != pattern.charAt(i)) {
                        isMatched = false;
                    }
                }

                if(isMatched) {
                    wordsThatMatchPattern.add(word);
                }
            }
        }

        return wordsThatMatchPattern;
    }

    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("proj0/data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}