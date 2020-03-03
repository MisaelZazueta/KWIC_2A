package PhraseSearcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Phrase {
    private String phrase;
    private HashSet<Integer> pages;
    public Phrase(String phrase){
        this.phrase = phrase;
        pages = new HashSet<>();
    }
    public HashSet<Integer> getPages() {
        return pages;
    }
    public String getPhrase() {
        return phrase;
    }
    @Override
    public String toString() {
        ArrayList<Integer> pagesA = new ArrayList<>();
        pagesA.addAll(pages);
        Collections.sort(pagesA);
        String line = "PHRASE: " + phrase + " ";
        int x = line.length();
        for (int i = 0; i < (50 - x); i++) {
            line += ".";
        }
        line += " PAGES: ";
        for (int i = 0; i < pagesA.size(); i++) {
            if (i < (pagesA.size() - 1))
                line += pagesA.get(i) + ", ";
            else
                line += pagesA.get(i);
        }
        return line;
    }
}
