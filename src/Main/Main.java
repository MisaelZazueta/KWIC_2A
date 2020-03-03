package Main;

import AndSearcher.And;
import AndSearcher.AndIndex;
import AndSearcher.AndSearcher;
import DocSearcher.DocSearcher;
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

        String routeOfTheFile = "C:\\Users\\Usuari0\\Desktop\\Misael Zazueta\\Maestria en Ciencias";
        String keyWordOfTheFile = "Principito";

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
    }
}