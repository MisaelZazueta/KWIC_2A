package PhraseSearcher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class PhraseSearcher implements PropertyChangeListener {
    ArrayList<Phrase> phraseIndex;
    String lineAux;
    int pageAux;
    public void setPhraseIndex(ArrayList<Phrase> phraseIndex) {
        this.phraseIndex = phraseIndex;
    }
    public ArrayList<Phrase> getPhraseIndex() { return phraseIndex; }
    public void phrasesSearcher(String line, int page){
        String actualLine = cleanLine(line);
        if (!actualLine.equals("")) {
            String doubleLine = lineAux + " " + actualLine;
            doubleLine = doubleLine.replaceAll("[\\s]{2,}", " ");
            for (Phrase phrase : phraseIndex) {
                if (actualLine.indexOf(phrase.getPhrase()) != -1 || (doubleLine.indexOf(phrase.getPhrase()) != -1 && page == pageAux)) {
                    phrase.getPages().add(page);
                } else if (doubleLine.indexOf(phrase.getPhrase()) != -1 && page != pageAux) {
                    phrase.getPages().add(pageAux);
                    phrase.getPages().add(page);
                }
            }
            lineAux = actualLine;
            pageAux = page;
        }
    }
    private static String cleanLine(String line){
        line = line.replaceAll("[^A-Za-záéíúóÁÉÍÓÚñÑ]", " ");
        line = line.replaceAll("[\\s]{2,}", " ");
        line = line.toUpperCase();
        line = line.trim();
        return line;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String line = (String) evt.getNewValue();
        int page = (Integer) evt.getOldValue();
        this.phrasesSearcher(line, page);
    }
}
