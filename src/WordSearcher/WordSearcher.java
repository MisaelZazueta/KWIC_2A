package WordSearcher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class WordSearcher implements PropertyChangeListener {
    private ArrayList<Word> wordIndex;
    public void setWordIndex(ArrayList<Word> wordIndex) {
        this.wordIndex = wordIndex;
    }
    private void wordSearcher(String line, int page) {
        HashSet<String> lines;
        lines = cleanLine(line);
        for (String word : lines){
            for (int i = 0; i < wordIndex.size(); i++) {
                if(word.equals(wordIndex.get(i).getWord())){
                    wordIndex.get(i).getPages().add(page);
                }
            }
        }
    }
    public ArrayList<Word> getWordIndex() {
        return wordIndex;
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
        int page = (Integer) evt.getOldValue();
        this.wordSearcher(line, page);
    }
}