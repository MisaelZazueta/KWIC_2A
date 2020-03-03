package KWIC;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ObservadorNuevaLinea implements PropertyChangeListener {
    private List<String> lineas;
    private PropertyChangeSupport changeSupport;
    public ObservadorNuevaLinea(){
        this.lineas = new ArrayList<>();
        changeSupport = new PropertyChangeSupport(this);
    }
    public void agregadorEscuchador(PropertyChangeListener pcl){
        this.changeSupport.addPropertyChangeListener(pcl);
    }
    public void onShift(String linea) {
        if (linea != null) {
            ArrayList<ArrayList<String>> lineas = new ArrayList<>();
            ArrayList<String> palabras = new ArrayList<>();
            ArrayList<String> lineasImp = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(linea);
            while (st.hasMoreTokens()) {
                palabras.add(st.nextToken());
            }
            lineas.add(palabras);
            int x = 0;
            while (x != (palabras.size() - 1)) {
                ArrayList<String> palabras1 = new ArrayList<>();
                palabras1.addAll(lineas.get(lineas.size() - 1));
                palabras1.add(palabras1.get(0));
                palabras1.remove(0);
                lineas.add(palabras1);
                String lineaAux = "";
                for (int i = 0; i < palabras1.size(); i++) {
                    if (i != (palabras1.size() - 1))
                        lineaAux += palabras1.get(i) + " ";
                    else
                        lineaAux += palabras1.get(i);
                }
                lineasImp.add(lineaAux);
                this.changeSupport.firePropertyChange("line_shifted",lineasImp,lineaAux);
                this.lineas.add(lineaAux);
                x++;
            }
        }
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String linea = (String)evt.getNewValue();
        this.onShift(linea);
    }
}
