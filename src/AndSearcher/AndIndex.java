package AndSearcher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class AndIndex implements PropertyChangeListener {
    private ArrayList<And> andIndex;
    private HashSet<String> andList = new HashSet<>();
    private PropertyChangeSupport changeSupport;
    public AndIndex(){
        andIndex = new ArrayList<>();
        changeSupport = new PropertyChangeSupport(this);
    }
    public void setListener(PropertyChangeListener pcl){ this.changeSupport.addPropertyChangeListener(pcl); }
    public void andIndexCreator(List<String> ands){
        for (int i = 0; i < ands.size(); i++) {
            And and = new And(ands.get(i));
            this.andIndex.add(and);
        }
    }
    private void andListCreator(String line){
        String lineA = line.toUpperCase();
        StringTokenizer st = new StringTokenizer(lineA);
        HashSet<String> aux = new HashSet<>();
        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            aux.add(word);
        }
        if(aux.size() >= 2){
            String lineB = "";
            for (String word : aux){
                lineB += word + " ";
            }
            lineB = lineB.trim();
            if(andList.add(lineB))
                this.changeSupport.firePropertyChange("new_word", andList, lineB);
        }
    }
    public ArrayList<And> getAnds() { return andIndex; }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String line = (String) evt.getNewValue();
        if (line != null)
            this.andListCreator(line);
    }
}
