/*
Francis Lalonde (801363)
IFT-1025, 19 janvier 2020
*/

import java.util.Scanner;

public class Exercice1 {

    public static String[] agrandirTab(String[] tab) {
        String[] tabPlusUn = new String[tab.length + 1];
        for (int i = 0; i < tab.length; ++i) {
            tabPlusUn[i] = tab[i];
        }
        return tabPlusUn;
    }

    public static String[] lireMots() {
        Scanner scanner;
        String userInput = "";
        String[] tab = new String[0];

        System.out.print(
                "Entrez une séquence de caractères." +
                        " Entrez \"stop\" pour terminer l'entrée de données.\n>");
        
        while (!userInput.equals("stop")) { // arrête sur "stop".
            scanner = new Scanner(System.in);

            userInput = scanner.nextLine();
            
            // on cesse d'accepter des entrées lorsque l'utilisateur entre stop.
            if (userInput.equals("stop")) {
                continue; // force l'évaluation du while. 
            }
            String[] temp = agrandirTab(tab); // tableau avec une case de plus
            temp[temp.length - 1] = userInput;// pour y insérer le nouveau mot
            tab = temp;
        }
        return tab;
    }

    public static String[] trier(String[] mots) {
        int minimum;
        String temp; // afin de permettre une permutation dans le tableau
        
        // trouve l'élément "minimum" et le met en position [i]
        // ne réévalue pas les éléments placés avant [i]
        for (int i = 0; i < mots.length - 1; ++i) { 
            minimum = i; 
            for (int j = i + 1; j < mots.length; ++j) {
                if (mots[j].compareTo(mots[minimum]) < 0) {
                    minimum = j;
                }
            }
            if (minimum != i) {
                temp = mots[i];
                mots[i] = mots[minimum];
                mots[minimum] = temp;
            }
        }
        return mots;
    }

    public static String[] retirerDoublons(String[] mots) {
        int eliminer = 0;
        int compteur = 0;

        // trouve le nombre de doublons.
        for (int i = 0; i < mots.length - 1; ++i) {
            if (mots[i].equals(mots[i + 1])) {
                eliminer++;
            }
        }

        String[] unique = new String[mots.length - eliminer];

        // puisque le tableau est classé, on ne transfert que les mots dont le
        // le mot qui suit est différent pour éliminer les doublons.
        for (int i = 0; i < mots.length; ++i) {
            if (i < (mots.length - 1) && mots[i].equals(mots[i + 1])) {
                continue;
            } else {
                unique[compteur] = mots[i];
                compteur++; // puisque [i] passe sur les doublons.
                continue;
            }
        }
        return unique;
    }

    public static void main(String[] args) {
        String[] mots = lireMots();
        mots = trier(mots);
        mots = retirerDoublons(mots);
        for (int i = 0; i < mots.length; ++i) {
            System.out.println(mots[i]);
        }
    }
}