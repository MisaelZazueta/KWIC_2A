package FullIndexGenerator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

public class WordsToIgnore implements PropertyChangeListener {
    private HashSet<String> wordsToIgnore;

    public WordsToIgnore(){
        wordsToIgnore = new HashSet<>();
    }

    private void wordsToIgnoreSetCreator(String line){
        String lineA;
        lineA = line.toUpperCase();
        wordsToIgnore.add(lineA);
    }

    public HashSet<String> getWordsToIgnore() {
        return wordsToIgnore;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String line = (String) evt.getNewValue();
        if (line != null)
            this.wordsToIgnoreSetCreator(line);
    }
}
