package KWIC;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Principal {
    public static void main(String[] args) throws IOException {
        File file = null;
        while(file == null){
            JFileChooser fileChooser = new JFileChooser();
            int seleccion = fileChooser.showOpenDialog(null);
            if (seleccion == JFileChooser.APPROVE_OPTION)
            {
                file = fileChooser.getSelectedFile();
            }
        }
        KWICLector reader = new KWICLector();

        ObservadorNuevaLinea shiftObserver = new ObservadorNuevaLinea();
        reader.agregadorEscuchador(shiftObserver);

        ObservadorAlfabetizador alphabetizer = new ObservadorAlfabetizador();
        shiftObserver.agregadorEscuchador(alphabetizer);

        reader.leerArchivo(file);

        List<String> lines= alphabetizer.getLines();
        System.out.println("Resultado:");
        lines.stream().forEach(System.out::println);

    }
}
