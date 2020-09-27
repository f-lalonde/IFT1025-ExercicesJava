// Francis Lalonde - 801363 - exercice noté 3 - 29 mars 2020 (VERSION COVID-19)

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//tiré de http://www.avajava.com/tutorials/lessons/how-do-i-alphabetically-sort-the-lines-of-a-file.html

public class TrierAlphabetiquement {

    /**
     * Prend un fichier et en fait le tri lexicographique, ligne par ligne.
     * 
     * Exporte le fichier trié dans un fichier et renvoie le nom de ce fichier.
     * 
     * Si le fichier existe déjà, renvoie le nom du fichier et évite de refaire 
     * le travail.
     * 
     * @param fichier
     * @return
     * @throws Exception
     */
    public static String Trier(String fichier) throws Exception {

        String prefixe = fichier.split("\\.")[0];

        String outputFile = "fichiers/"+prefixe+"Lexico.txt";

        // Si le fichier existe déjà, on ne le refait pas. Ici également,
        // idéalement, on comparerait les MD5.
        File dejaFait = new File(outputFile);
    
        if(dejaFait.exists()){
            // System.out.println("HUGE SUCCESS");
            return outputFile;
        } else {
            dejaFait.createNewFile();
        }
        
        FileReader fileReader = new FileReader(fichier);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String inputLine;
        List<String> lineList = new ArrayList<String>();
        while ((inputLine = bufferedReader.readLine()) != null) {
            lineList.add(inputLine);
        }
        fileReader.close();

        Collections.sort(lineList, String.CASE_INSENSITIVE_ORDER);

        FileWriter fileWriter = new FileWriter(outputFile);
        PrintWriter out = new PrintWriter(fileWriter);
        for (String outputLine : lineList) {
            out.println(outputLine);
        }
        // System.out.println("Dictionnaire lexicographique créé.\n");
        out.flush();
        out.close();
        fileWriter.close();
        lineList.clear();// libère la mémoire

        return outputFile;

    }
}
