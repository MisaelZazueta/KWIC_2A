package AndSearcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.StringTokenizer;

public class And {
    private ArrayList<String> words;
    private HashSet<Integer> pages;
    public And(String line){
        pages = new HashSet<>();
        words = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(line);
        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            word = word.toUpperCase();
            this.words.add(word);
        }
    }
    public ArrayList<String> getWords() { return words; }
    public HashSet<Integer> getPages() { return pages; }
    @Override
    public String toString() {
        ArrayList<Integer> pagesA = new ArrayList<>();
        pagesA.addAll(pages);
        Collections.sort(pagesA);
        String line = "AND: ";
        for (String word : words){
            line += word + " ";
        }
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
