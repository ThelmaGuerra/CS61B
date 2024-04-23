package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngm;
    public HistoryTextHandler(NGramMap temp) {
        ngm = temp;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";

        for(String word : words) {
            response += word + ": " + ngm.weightHistory(word, startYear, endYear) + "\n" + "\n";
        }

        return response;
    }
}
