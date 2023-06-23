package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;
    private TreeMap<Character, Integer> frequency_map;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        // TODO: Fill in this method.
        TreeMap<Character, Integer> map = new TreeMap<>();

        for ( String element : words) {
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
        this.frequency_map = map;
        return map;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        // TODO: Fill in this method.
        Map<Character, Integer> map = getFrequencyMap();

        Object[] sorted_values = map.values().toArray();
        Arrays.sort(sorted_values, Collections.reverseOrder());

        // TODO: thelma, you need to get sorted values array and make a loop in it, and find the key
        //  in map with every value and see if the key is inside GUESSES, if it is, pass, if it's not, you should add to GUESSES
        //  and return the key.
        //  if frequency map is empty return "?"
        //  deal with value TIES (IDK HOW YET) / Find out how TreeMaps deal with ties.

        Set<Map.Entry<Character, Integer>> entries_set = map.entrySet();


        int max_value = (int) sorted_values[0];
        System.out.println(map);
        System.out.println(entries_set);
        System.out.println(guesses);
        System.out.println(Arrays.toString(sorted_values));

        for(Object sorted_value : sorted_values) {
            for (Map.Entry<Character, Integer> entry : entries_set) {
                if (entry.getValue().equals(sorted_value)) {
                    System.out.println(sorted_value);

                    if (guesses.contains(entry.getKey())) {
                        continue;
                    }

                    return entry.getKey();
                }
            }
        }



        return '?';
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("proj0/data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
