package PhraseSearcher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PhraseIndex implements PropertyChangeListener{
    private ArrayList<Phrase> phrases;
    private PropertyChangeSupport changeSupport;
    private HashSet<String> phrasesList = new HashSet<>();
    public PhraseIndex(){
        this.phrases = new ArrayList<>();
        this.changeSupport =new PropertyChangeSupport(this);
    }
    public void setListener(PropertyChangeListener pcl){
        this.changeSupport.addPropertyChangeListener(pcl);
    }
    public void phraseIndexCreator(List<String> phrases){
        ArrayList<Phrase> phraseIndex = new ArrayList<>();
        for (int i = 0; i < phrases.size(); i++) {
            Phrase phrase = new Phrase(phrases.get(i));
            phraseIndex.add(phrase);
        }
        this.phrases = phraseIndex;
    }
    private void phrasesListCreator(String line){
        String lineA = line.toUpperCase();
        if(phrasesList.add(lineA))
            this.changeSupport.firePropertyChange("new_word", phrasesList, lineA);
    }
    public ArrayList<Phrase> getPhrases() {
        return phrases;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String line = (String) evt.getNewValue();
        if (line != null)
            this.phrasesListCreator(line);
    }
}