// Francis Lalonde - 801363 - exercice noté 3 - 29 mars 2020 (VERSION COVID-19)
import java.util.*;
import java.io.*;

public class Correcteur {

    public static void main(String[] args) {
        
        /* depuis le fichier exemple.java. S'assure qu'on fournit deux fichiers. 
        On assume que le premier est le texte à corriger et le deuxième le 
        dictionnaire contre lequel on va le corriger. */
        if (args.length != 2) {

            System.out.println("Attention: 2 arguments sont attendus");
            System.exit(-1);
        }

        String texte = args[0];
        String dictionnaire = args[1];
        String texteCorrige = "";

        File dir = new File("fichiers/");
        if(!dir.exists()){
            dir.mkdir();
        }
    
        try{

            String dictTri = TrierAlphabetiquement.Trier(dictionnaire);
            Erreurs.GenererErreurs(dictTri);
            texteCorrige = Corrections.Correcteur(texte, dictTri);

        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(texteCorrige);
    }
}

