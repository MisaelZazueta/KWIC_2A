/*Sistema KWIC v2.1
* Autor: Ing. Jose Misael Burruel Zazueta
*
* En la carpeta SRC se encuentran los directorios que componen al sistema KWIC v2.1
* Estos directorios se nombran segun su proposito en el sistema:
* Main: Se ecuentra la clase main, que es la aplicacion del sistema.
* KWIC: Se encuentran los directorios del KWIC v1, que son utilizados para la v2.1
* FullIndexGenerator: Se ecuentran las clases que se encargan de generar el index completo de palabras
* PhraseSearcher: Se encuentran las clases que se encargan de generar el indice de frases a buscar
* WordSearcher: Se encuentran las clases que se enccargan de generar el indice de palabras especificas a buscar
* DocSeacher: Se encuentran las clases que se encargan de buscar un archivo en un directorio
* AndSeacher: Se ecuentran las clases que se encargan de buscar palabras aleatorias (no frases) en una misma pagina
*/
package Main;

import AndSearcher.And;
import AndSearcher.AndIndex;
import AndSearcher.AndSearcher;
import DocSearcher.DocSearcher;
import FullIndexGenerator.FullIndexCreator;
import FullIndexGenerator.WordsToIgnore;
import GeneralSearcher.Searcher;
import KWIC.KWICLector;
import KWIC.ObservadorAlfabetizador;
import PhraseSearcher.Phrase;
import PhraseSearcher.PhraseIndex;
import PhraseSearcher.PhraseSearcher;
import WordSearcher.Word;
import WordSearcher.WordIndex;
import WordSearcher.WordSearcher;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main{
    public static void main(String args[]) throws IOException {

        KWICLector readerFullIndex = new KWICLector(); // Se crea un lector de archivos txt
        WordsToIgnore wordsToIgnore = new WordsToIgnore(); // Se crea un indice de palabras a ignorar
        readerFullIndex.agregadorEscuchador(wordsToIgnore); // Se agrega un observador al lector del FullIndex
        File fileWordsToIgnore = new File("Ignore.txt");
        readerFullIndex.leerArchivo(fileWordsToIgnore); // Se envia el archivo txt al lector para generar el indice
        FullIndexCreator fullIndexCreator = new FullIndexCreator(wordsToIgnore.getWordsToIgnore()); // se envia el indice de palabras a ignorar al generador de fullIndex

        /*El proceso anterior explicado en los comentarios se repite similarmente para cada uno de los buscadores
        * Se utilizaron tanto el lector del KWIC original para leer el archivo pdf,
        * como el alfabetizador para ordenar los indices.
        */

        KWICLector readerWordsIndex = new KWICLector();
        WordIndex wordIndex = new WordIndex();
        ObservadorAlfabetizador alfabetizadorWords = new ObservadorAlfabetizador();
        wordIndex.setListener(alfabetizadorWords);
        readerWordsIndex.agregadorEscuchador(wordIndex);
        File fileWords = new File("Words.txt");
        readerWordsIndex.leerArchivo(fileWords);
        List<String> words = alfabetizadorWords.getLines();
        wordIndex.wordsIndexCreator(words);

        KWICLector readerPhrasesIndex = new KWICLector();
        PhraseIndex phraseIndex = new PhraseIndex();
        ObservadorAlfabetizador alfabetizadorPhrases = new ObservadorAlfabetizador();
        phraseIndex.setListener(alfabetizadorPhrases);
        readerPhrasesIndex.agregadorEscuchador(phraseIndex);
        File filePhrases = new File("Phrases.txt");
        readerPhrasesIndex.leerArchivo(filePhrases);
        List<String> phrases = alfabetizadorPhrases.getLines();
        phraseIndex.phraseIndexCreator(phrases);

        KWICLector readerAndIndex = new KWICLector();
        AndIndex andIndex = new AndIndex();
        ObservadorAlfabetizador alfabetizadorAnd = new ObservadorAlfabetizador();
        andIndex.setListener(alfabetizadorAnd);
        readerAndIndex.agregadorEscuchador(andIndex);
        File fileAnd = new File("And.txt");
        readerAndIndex.leerArchivo(fileAnd);
        List<String> ands = alfabetizadorAnd.getLines();
        andIndex.andIndexCreator(ands);

        WordSearcher wordSearcher = new WordSearcher();
        wordSearcher.setWordIndex(wordIndex.getWords());

        PhraseSearcher phraseSearcher = new PhraseSearcher();
        phraseSearcher.setPhraseIndex(phraseIndex.getPhrases());

        AndSearcher andSearcher = new AndSearcher();
        andSearcher.setAndIndex(andIndex.getAnds());

        KWICLector reader = new KWICLector();
        Searcher searcher = new Searcher();
        reader.agregadorEscuchador(searcher);

        searcher.setChangeSupporters(phraseSearcher);
        searcher.setChangeSupporters(wordSearcher);
        searcher.setChangeSupporters(andSearcher);
        searcher.setChangeSupporters(fullIndexCreator);

        String routeOfTheFile = "C:\\Users\\Usuari0\\Desktop\\Misael Zazueta\\Maestria en Ciencias";
        String keyWordOfTheFile = ".pdf";

        DocSearcher docSearcher = new DocSearcher(routeOfTheFile, keyWordOfTheFile);
        File fileA = docSearcher.docSearcher();

        try {
            PdfReader pdfReader = new PdfReader(fileA.getAbsolutePath());
            for(int i=1; i <= pdfReader.getNumberOfPages(); i++) {
                searcher.setPage(i);
                String pageContent = PdfTextExtractor.getTextFromPage(pdfReader, i);
                try {
                    File file = new File("src/text.txt");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(pageContent);
                    bw.close();
                    reader.leerArchivo(file);
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pdfReader.close();
        }
        catch (Exception e) {
            System.out.println("Formato Invalido");
        }

        System.out.println("----WORD INDEX----");
        for (Word word : wordSearcher.getWordIndex())
            System.out.println(word);
        System.out.println();

        System.out.println("----PHRASE INDEX----");
        for (Phrase phrase : phraseSearcher.getPhraseIndex())
            System.out.println(phrase);
        System.out.println();

        System.out.println("----AND INDEX----");
        for (And and : andSearcher.getAndIndex())
            System.out.println(and);
        System.out.println();

        System.out.println("----FULL INDEX----");
        for(Word word : fullIndexCreator.getFullIndex())
            System.out.println(word);
    }
}