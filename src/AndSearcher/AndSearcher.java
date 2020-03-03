package AndSearcher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class AndSearcher implements PropertyChangeListener {
    private ArrayList<And> andIndex;
    private HashSet<String> auxWords = new HashSet<>();
    private int page = 0;
    public void setAndIndex(ArrayList<And> andIndex) { this.andIndex = andIndex; }
    private void andSearcher(String line, int actualPage){
        HashSet<String> words;
        words = cleanLine(line);
        for (String word : words){
            for (int i = 0; i < andIndex.size(); i++) {
                for (String indexWord : andIndex.get(i).getWords()) {
                    if (word.equals(indexWord)) {
                        auxWords.add(word);
                    }
                }
            }
        }
        this.page = actualPage;
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
    public ArrayList<And> getAndIndex() { return andIndex; }
    private void generateHashWordsAux(){
        if(!auxWords.isEmpty()){
            for(And and: andIndex){
                boolean add = true;
                for(String word : and.getWords()){
                    if(!auxWords.contains(word)){
                        add = false;
                    }
                }
                if(add)
                    and.getPages().add(this.page);
            }
        }
        this.auxWords = new HashSet<>();
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String line = (String) evt.getNewValue();
        int page = (Integer) evt.getOldValue();
        if(page != this.page)
            generateHashWordsAux();
        this.andSearcher(line, page);
    }
}
