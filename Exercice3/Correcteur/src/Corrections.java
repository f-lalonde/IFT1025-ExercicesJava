// Francis Lalonde - 801363 - exercice noté 3 - 29 mars 2020 (VERSION COVID-19)

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Corrections {

    /**
     * Prend un texte et effectue une vérification des corrections qui pourraient
     * y être apportées à l'aide d'un dictionnaire trié de façon lexicographique. 
     * 
     * La méthode assume l'existence d'une liste associative et d'un index du
     * dictionnaire et d'une liste des clés de cet index créés avant son appel.
     * 
     * Retourne un texte mettant en relief les corrections possibles. 
     * @param texte
     * @param dictionnaire
     * @param listeClesDict
     * @return
     */

    
    public static String Correcteur(String texte, String dictionnaire) {
        boolean pasla = true;
        String texteImp = "";
        String prefixe = dictionnaire.split("\\.")[0];
        Map<String, Set<String>> erreurMap = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(texte));
            
            // restoration de l'index du dictionnaire.
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(prefixe + "Index.dat"));
            Map<Character, Long> indexDict = (HashMap<Character, Long>) ois.readObject();
            ois.close();

            // restoration des clés du dictionnaire
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(prefixe + "Cles.dat")));
            List<Character> clesDict = (List<Character>) ois.readObject();
            ois.close();

            // Tiré de exemple.java
            // Défini ce qu'est un mot et en fait la lecture dans le texte.
            String mot;
            String separateur;

            Pattern patternMot = Pattern.compile("[a-zA-Z\\u00C0-\\u017F]+");
            Pattern patternSeparateur = Pattern.compile("[^a-zA-Z\\u00C0-\\u017F]+");

            Scanner scanner = new Scanner(br);
            scanner.useDelimiter("\\b");

            while (scanner.hasNext(patternMot)) {
                pasla = true;
                mot = scanner.next(patternMot);
                String motMin = mot.toLowerCase();

                boolean motTrouve;
                motTrouve = Chercher.DansFichier(motMin, dictionnaire, indexDict, clesDict);
                
                // C'est ici que la magie opère!
                if(!motTrouve){
                    
                    Set<String> corrections = new HashSet<>();
                    boolean corrVide = true;
                    Character premierChar = motMin.charAt(0);
                    int c = Integer.valueOf((Character)premierChar);
                    // on vérifie si le mot est dans la liste des mots tronqués 
                    // prégénérée (cas A)
                    File tempF = new File(prefixe+"Map"+c+".dat");
                    if(!tempF.exists()){
                        pasla = true;

                    } else {
                        pasla = false;
                        ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(prefixe+"Map"+c+".dat")));
                        erreurMap = (HashMap<String, Set<String>>) ois.readObject();
                        ois.close();
                    }
                    
                    if(!pasla){
                        if(erreurMap.containsKey(motMin)){
                            corrections = erreurMap.get(motMin);
                            corrVide = false;
                        }
                    }
                    
                    // on crée une liste de mots tronqués depuis le mot actuel
                    Set<String> motTronques = Erreurs.Tronquer(motMin);

                    for(String x: motTronques){
                        // on vérifie s'ils sont dans dictionnaire (Cas B1)
                        boolean dansDict = false;
                        dansDict = Chercher.DansFichier(x, dictionnaire, indexDict, clesDict);

                        if(dansDict) {
                            corrections.add(x);
                            corrVide = false;
                        }

                        // on vérifie s'ils sont dans la liste de mots tronqués 
                        // prégénérée (cas B2)
                        Character temp = x.charAt(0);
                        int ctemp = Integer.valueOf((Character)temp);
                        if(!(ctemp == c)){
                            c = ctemp;
                            tempF = new File(prefixe+"Map"+c+".dat");
                            if(!tempF.exists()){
                                pasla = true;
                            } else {
                                pasla = false;
                                ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(prefixe+"Map"+c+".dat")));
                                erreurMap = (HashMap<String, Set<String>>) ois.readObject();
                                ois.close();
                            }
                            
                        }
                        if(!pasla){
                            if(erreurMap.containsKey(x)){
                                Set<String> corrTemp;
                                corrTemp = erreurMap.get(x);
                                corrections.addAll(corrTemp);
                                corrVide = false;
                            }
                        }
                        
                    
                    }
                    // Si on ne trouve pas le mot ni de correction potentielle
                    if(corrVide){
                        corrections.add("(?)");
                    }
                    
                    separateur = scanner.next(patternSeparateur);

                    // Création du texte renvoyé (avec suggestions de corrections)
                    texteImp = texteImp.concat("["+ mot + " => ");

                    for(String x : corrections){
                        texteImp = texteImp.concat(x+", ");
                    }
                    int beautiful = texteImp.lastIndexOf(",");
                    texteImp = texteImp.substring(0,beautiful);
                    texteImp = texteImp.concat("]"+separateur);

                // On recopie le mot s'il n'y a pas de suggestion de correction.
                } else {
                    separateur = scanner.next(patternSeparateur);
                    texteImp = texteImp.concat(mot).concat(separateur);
                }
            }
            scanner.close();
            br.close();
            

        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return texteImp;
    }
}
