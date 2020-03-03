package KWIC;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObservadorAlfabetizador implements PropertyChangeListener {
    private ArrayList<String> lineas = new ArrayList<>();
    public void alfabetizar(String linea) {
        lineas.add(linea);
        Collections.sort(lineas, String.CASE_INSENSITIVE_ORDER);
    }
    public List<String> getLines(){
        return this.lineas;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String linea = (String)evt.getNewValue();
        this.alfabetizar(linea);
    }
}