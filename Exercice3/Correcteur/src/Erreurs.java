// Francis Lalonde - 801363 - exercice noté 3 - 29 mars 2020 (VERSION COVID-19)

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Erreurs {

    /**
     * Génère la liste associative de mots tronqués depuis les mots du
     * dictionnaire fourni. Crée au passage un index du dictionnaire et une
     * liste des clés pour cet index, composée des différents premiers
     * caractère de tous les mots du dictionnaire.
     * 
     * Exporte la liste associative dans de multiples fichiers, chaque liste
     * ne contenant que les mots commençant par un caractère précis.
     * 
     * Si certains fichiers existent déjà, on assume que le travail a déjà
     * été fait, et on évite de le refaire.
     * 
     * @param dictionnaire
     * @return
     * @throws IOException
     */
    //@SuppressWarnings("unchecked")
    public static void GenererErreurs(String dictionnaire) throws IOException {
        String prefix = dictionnaire.split("\\.")[0];
        File tmp = new File(prefix+"Index.dat");
        File tmp2 = new File(prefix+"Cles.dat");
        

        /* vérifie l'existence des fichiers. Si tout est beau, retourne à la 
        fonction appelante. Idéalement, on compare à leur MD5, mais je n'ai 
        pas trouvé comment importer manuellement la libraire fast-md5, bien que
        ça se faisait facilement par Gradle dans IntelliJ. */
        if(tmp.exists() && tmp2.exists()){
            // System.out.println("Pas de fichier à créer. HUGE SUCCESS \\o/");
            return;
        }
        
        RandomAccessFile dict = new RandomAccessFile(dictionnaire, "r");
        String mot;
        Map<Character, Map<String, Set<String>>> splitErreurs = new HashMap<Character, Map<String, Set<String>>>();
        Map<Character, Long> index = new HashMap<Character, Long>();
        
        // le caractère # ne devrait pas se retrouver dans un dictionnaire utilisé.
        // utilisé afin d'éviter une exception NullPointer
        Character charMotPrecedent = '#'; 
        Character charMotActuel;
        List<Character> listeCles = new ArrayList<Character>();

        long position = dict.getFilePointer();

        while ((mot = dict.readLine()) != null) {

            // RandomAccessFile ne lit pas nativement en UTF-8
            String motUTF = new String(mot.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            String motMin = motUTF.toLowerCase();
            charMotActuel = motMin.charAt(0);

            // création de l'index du dictionnaire
            if (charMotActuel.compareTo(charMotPrecedent) != 0) {
                index.put(charMotActuel, position);
                listeCles.add(charMotActuel);
                charMotPrecedent = charMotActuel;
            }

            position = dict.getFilePointer();
            // création du multi HashMap de mots du dictionnaire tronqués et 
            // leur(s) correction(s), par leurs premières lettres.
            Set<String> erreurs= Tronquer(motMin);

            for(String x: erreurs){
                Map<String, Set<String>> erreursMap = new HashMap<String, Set<String>>();
                Set<String> tempErreurs = new HashSet<String>();
                Character tempCar = x.charAt(0);
                if(splitErreurs.containsKey(tempCar)){
                    erreursMap = splitErreurs.get(tempCar);
                    if(erreursMap.containsKey(x)){
                        tempErreurs = erreursMap.get(x);
                        tempErreurs.add(motUTF);
                        erreursMap.replace(x, tempErreurs);
                        
                    } else {
                        tempErreurs.add(motUTF);
                        erreursMap.put(x, tempErreurs);
                    }
                    splitErreurs.replace(tempCar, erreursMap);
                } else {
                    tempErreurs.add(motUTF);
                    erreursMap.put(x, tempErreurs);
                    splitErreurs.put(tempCar, erreursMap);
                }
            }
        }

        // Trie de la liste des clés dans le même ordre que le dictionnaire.
        Collections.sort(listeCles);
        
        ObjectOutputStream exporterFichier = new ObjectOutputStream(
            new FileOutputStream(prefix+"Index.dat"));
        exporterFichier.writeObject(index);
        exporterFichier.flush();
        exporterFichier.close();
        index.clear(); // libère la mémoire
        // System.out.println("Index du dictionnaire lexicographique créé.\n");

        exporterFichier = new ObjectOutputStream(new FileOutputStream(prefix+"Cles.dat"));
        exporterFichier.writeObject(listeCles);
        exporterFichier.flush();
        exporterFichier.close();
        listeCles.clear();// libère la mémoire
        // System.out.println("Liste des clés du dictionnaire lexicographique créé.\n");

        dict.close();
        
        splitErreurs.forEach((car,map) -> {
            try{
                final int c = Integer.valueOf((Character)car);
                final ObjectOutputStream exporterFichier2 = new ObjectOutputStream(
                    new FileOutputStream(prefix+"Map"+c+".dat"));
                exporterFichier2.writeObject(map);
                exporterFichier2.flush();
                exporterFichier2.close();
                map.clear(); // libère la mémoire au fur et à mesure.
                // System.out.println("Map '"+c+"' créé.\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Prend un mot et renvoie un ensemble des mots tronqués créés à partir de celui-ci.
     * @param mot
     * @return
     */
    public static Set<String> Tronquer(String mot){

        Set<String> motsTronques = new HashSet<String>();
        String temp;
        
        if (mot.length() > 2) { // inutile d'enlever une lettre s'il n'y en a qu'une!
            for (int j = 0; j < mot.length(); ++j) {
                // il manque la première lettre
                if (j == 0) {
                    temp = mot.substring(1);

                    // il manque la dernière lettre
                } else if (j == mot.length() - 1) {
                    temp = mot.substring(0, j);

                    // il manque une lettre entre la première et la dernière
                } else {
                    temp = mot.substring(0, j).concat(mot.substring(j + 1));
                }

                motsTronques.add(temp);
            }
        }
        return motsTronques;
    }
}