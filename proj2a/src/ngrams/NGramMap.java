package ngrams;

import java.util.Collection;
import java.util.HashMap;

import edu.princeton.cs.algs4.In;


import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    HashMap<String, TimeSeries> wordsMap = new HashMap<>();
    TimeSeries countsTS = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        In wordsIn = new In(wordsFilename);
        In countsIn = new In(countsFilename);

        while(!wordsIn.isEmpty()) {
            String nextLine = wordsIn.readLine();

            String[] splitLine = nextLine.split("\t");
            String word = splitLine[0];
            int year = Integer.parseInt(splitLine[1]);
            Double number = Double.valueOf(splitLine[2]);

            if(wordsMap.containsKey(word)) {
                TimeSeries ts = wordsMap.get(word);
                ts.put(year, number);
                // test if needed to .replace the TimeSeries.
            } else {
                TimeSeries ts = new TimeSeries();
                ts.put(year, number);
                wordsMap.put(word, ts);
            }
        }

        while(!countsIn.isEmpty()) {
            String nextLine = countsIn.readLine();

            String[] splitLine = nextLine.split(",");
            int year = Integer.parseInt(splitLine[0]);
            Double number = Double.valueOf(splitLine[1]);
            countsTS.put(year, number);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if(!wordsMap.containsKey(word)) {
            return new TimeSeries();
        }


        return new TimeSeries(wordsMap.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        if(!wordsMap.containsKey(word)) {
            return new TimeSeries();
        }

        TimeSeries ts = wordsMap.get(word);

        return new TimeSeries(ts, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return new TimeSeries(countsTS, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if(!wordsMap.containsKey(word)) return new TimeSeries();

        TimeSeries slicedWordTS = new TimeSeries(wordsMap.get(word), startYear, endYear);

        slicedWordTS.forEach((year, number) -> {
            slicedWordTS.put(year, number / countsTS.get(year));
        });


        return slicedWordTS;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        if(!wordsMap.containsKey(word)) return new TimeSeries();

        TimeSeries wordTS = new TimeSeries(wordsMap.get(word), MIN_YEAR, MAX_YEAR);

        wordTS.forEach((year, number) -> {
            wordTS.put(year, number / countsTS.get(year));
        });

        return wordTS;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries ts = new TimeSeries();

        for (String word : words) {
            if(wordsMap.containsKey(word)) {
                TimeSeries slicedWeightTS = this.weightHistory(word, startYear, endYear);
                ts = ts.plus(slicedWeightTS);
            }
        }
        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries ts = new TimeSeries();

        for (String word : words) {
            if(wordsMap.containsKey(word)) {
                TimeSeries slicedWeightTS = this.weightHistory(word);
                ts = ts.plus(slicedWeightTS);
            }
        }
        return ts;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.

    public static void main(String[] args) {
        NGramMap ngm = new NGramMap("./data/ngrams/very_short.csv", "./data/ngrams/total_counts.csv");
    }
}
