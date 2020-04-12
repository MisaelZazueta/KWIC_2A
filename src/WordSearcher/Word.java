package WordSearcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Word implements Comparable<Word>{
    private String word;
    private HashSet<Integer> pages;
    public Word(String word){
        this.word = word;
        pages = new HashSet<>();
    }
    public HashSet<Integer> getPages() {
        return pages;
    }
    public String getWord() {
        return word;
    }
    @Override
    public String toString() {
        ArrayList<Integer> pagesA = new ArrayList<>();
        pagesA.addAll(pages);
        Collections.sort(pagesA);
        String line = "WORD: " + word;
        line += " ";
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

    @Override
    public int compareTo(Word o) {
        return this.word.compareTo(o.word);
    }
}
