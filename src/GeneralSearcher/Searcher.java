package GeneralSearcher;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Searcher implements PropertyChangeListener {
    ArrayList<String> words;
    private String line;
    private int page;
    private PropertyChangeSupport changeSupport;
    public Searcher() {
        this.page = 0;
        this.line = null;
        changeSupport = new PropertyChangeSupport(this);
        words = new ArrayList<>();
    }
    public void setChangeSupporters(PropertyChangeListener pcs) {
        this.changeSupport.addPropertyChangeListener(pcs);
    }
    public void setPage(int page) {
        this.page = page;
    }
    public void setLine(String line) {
        this.line = line;
        words.add(line);
        this.changeSupport.firePropertyChange("new_line",page, line);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String line = (String)evt.getNewValue();
        if(line != null) {
            line = line.trim();
            if (!line.equals(""))
                this.setLine(line);
        }
    }
}