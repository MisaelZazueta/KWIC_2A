package FullIndexGenerator;

import WordSearcher.Word;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class FullIndexCreator implements PropertyChangeListener {
    private HashSet<String> wordsToIgnore;
    private ArrayList<String> wordsNotToIgnore = new ArrayList<>();
    private ArrayList<Word> fullIndex;
    public FullIndexCreator(HashSet<String> wordsToIgnore){
        this.wordsToIgnore = wordsToIgnore;
        this.fullIndex = new ArrayList<>();
    }

    private void fullIndex (String line, int page){
        HashSet<String> lines;
        lines = cleanLine(line);
        for (String word : lines){
            if (!wordsToIgnore.contains(word) && !wordsNotToIgnore.contains(word)){
                Word word1 = new Word(word);
                word1.getPages().add(page);
                wordsNotToIgnore.add(word);
                fullIndex.add(word1);
            }
            else if (!wordsToIgnore.contains(word) && wordsNotToIgnore.contains(word)){
                for (Word word1 : fullIndex){
                    if(word1.getWord().equals(word))
                        word1.getPages().add(page);
                }
            }
        }
    }

    public ArrayList<Word> getFullIndex() {
        Collections.sort(fullIndex);
        return fullIndex;
    }

    private static HashSet<String> cleanLine(String line){
        HashSet<String> lines =  new HashSet<>();
        line = line.replaceAll("[^A-Za-záéíúóÁÉÍÓÚñÑ]", " ");
        StringTokenizer st = new StringTokenizer(line);
        while (st.hasMoreTokens()) {
            String lineA = st.nextToken();
            lineA = lineA.toUpperCase();
            lines.add(lineA);
        }
        return lines;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String line = (String) evt.getNewValue();
        int page = (int) evt.getOldValue();
        this.fullIndex(line, page);
    }
}
