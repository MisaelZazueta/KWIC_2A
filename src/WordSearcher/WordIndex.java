package WordSearcher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WordIndex implements PropertyChangeListener{
    private ArrayList<Word> words;
    private HashSet<String> wordsList = new HashSet<>();
    private PropertyChangeSupport changeSupport;
    public WordIndex() {
        this.words = new ArrayList<>();
        changeSupport = new PropertyChangeSupport(this);
    }
    public void setListener(PropertyChangeListener pcl){
        this.changeSupport.addPropertyChangeListener(pcl);
    }
    private void wordsListCreator(String line) {
        String lineA = line.toUpperCase();
        if(wordsList.add(lineA))
            this.changeSupport.firePropertyChange("new_word", words, lineA);
    }
    public ArrayList<Word> getWords() {
        return words;
    }
    public void wordsIndexCreator(List<String> words){
        for (int i = 0; i < words.size(); i++) {
            Word word = new Word(words.get(i));
            this.words.add(word);
        }
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String line = (String) evt.getNewValue();
        if (line != null)
            this.wordsListCreator(line);
    }
}
