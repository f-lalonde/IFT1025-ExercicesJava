// Francis Lalonde - 801363 - exercice noté 3 - 29 mars 2020 (VERSION COVID-19)

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chercher {

    /**
     * Prend un mot et vérifie s'il est dans le fichier fourni.
     * On assume que le fichier est trié de manière lexicographique.
     * Prend également un index du fichier contenant l'emplacement des 
     * différents premiers caractères des mots du fichier, ainsi qu'une
     * liste de ces caractères triés de manière lexicographique, afin de 
     * déplacer le pointeur de lecture et éviter de lire le fichier dans
     * son entièreté chaque fois.
     * 
     * Retourne un booléen.
     * 
     * @param mot
     * @param nomFichier
     * @param index
     * @param listeCles
     * @return
     * @throws IOException
     */
    public static Boolean DansFichier(String mot, String nomFichier, Map<Character, Long> index, List<Character> listeCles) throws IOException {
        RandomAccessFile fichier = new RandomAccessFile(nomFichier, "r");
        Character motChar = mot.charAt(0);

        if (!index.containsKey(motChar)){
            fichier.close();
            return false;
        }

        Long indexChar = index.get(motChar);
        fichier.seek(indexChar);
        String motFichier;

        if(listeCles.indexOf(motChar) == listeCles.size()-1){
            try {
                while((motFichier = fichier.readLine()) != null){
                    motFichier = new String(motFichier.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                        
                    if(motFichier.equalsIgnoreCase(mot)){
                        fichier.close();
                        return true;
                    }
                }

            } catch (EOFException f) {
                f.printStackTrace();
            }

        } else {
            Character next = listeCles.get(listeCles.indexOf(motChar)+1);
            Long indexNext = index.get(next);

            while(fichier.getFilePointer() < indexNext){
                motFichier = new String(fichier.readLine().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                if(motFichier.equalsIgnoreCase(mot)){
                    fichier.close();
                    return true;
                }      
            }
        }
        fichier.close();
        return false;
    }
}
