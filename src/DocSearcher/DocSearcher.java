package DocSearcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DocSearcher {
    private String route;
    private String keyWord;
    private List<File> filesInFolder;
    private File file;
    public DocSearcher(String route, String keyWord) throws IOException {
        this.route = route;
        this.keyWord = keyWord;
    }
    public File docSearcher() throws IOException {
        this.filesInFolder = Files.walk(Paths.get(this.route))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        List<File> negatives = new ArrayList<>();
        for (File file : filesInFolder){
            if(file.getName().indexOf(keyWord) == -1)
                negatives.add(file);
        }
        filesInFolder.removeAll(negatives);
        if(!filesInFolder.isEmpty()) {
            System.out.println("---Select the file (PDF)---");
            for (int i = 0; i < filesInFolder.size(); i++) {
                System.out.print((i + 1) + ") ");
                System.out.println(filesInFolder.get(i).getName());
            }
            Scanner in = new Scanner(System.in);
            String selection;
            int selectionInt;
            do {
                System.out.print("...");
                selection = in.nextLine();
                selectionInt = Integer.parseInt(selection);
                if (selectionInt <= 0 && selectionInt > filesInFolder.size()) {
                    System.out.println("Invalid Selection");
                }
            } while (selectionInt <= 0 && selectionInt > filesInFolder.size());
            this.file = filesInFolder.get(selectionInt - 1);
            return this.file;
        }
        else{
            System.out.println("Keyword not found in the directory.");
            return null;
        }
    }
}
