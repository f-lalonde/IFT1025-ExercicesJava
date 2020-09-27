/*
Exercice noté 2
Francis Lalonde (801363)
IFT-1025, 26 janvier 2020
*/
public class Exercice2 {

    public static void main(String[] args) {

        double[] elements = new double[]{1, 2, 3};
        Vecteur v1 = new Vecteur(elements);
        Vecteur v2 = new Vecteur(elements);
        v2.setElement(0, 10);

        System.out.println("--- Vecteurs ---");
        v1.afficher();
        v2.afficher();
        System.out.println("\nle 1er élément du 2e vecteur est " + 
            v2.getElement(0));
        
        double dotV1V2 = v1.dotProduct(v2);
        System.out.println("\nle produit scalaire des vecteurs 1 et 2 est " + 
            dotV1V2+"\n");

        System.out.println("--- Matrices ---");
        Matrice m1 = Matrice.identite(2);
        m1.multiplierScalaire(3);
        m1.setCell(0, 1, 5);
        m1.setCell(1, 0, -2);
        System.out.println("Matrice 1 :");
        m1.afficher();

        Matrice m2 = new Matrice(2, 1);
        m2.additionnerScalaire(10);
        System.out.println("Matrice 2 :");
        m2.afficher();

        Matrice m3 = m1.dotProduct(m2);
        System.out.println("Matrice 3 :");
        m3.afficher();

        Matrice m4 = Matrice.identite(5);
        System.out.println("Matrice 4 :");
        m4.setCell(0, 0, 1);
        m4.setCell(0, 1, 2);
        m4.setCell(0, 2, 3);
        m4.setCell(0, 3, 4);
        m4.setCell(0, 4, 5);
        m4.afficher();
        Vecteur ligneM4 = m4.getLine(0);
        Vecteur colM4 = m4.getCol(2);

        System.out.println("Première ligne de la matrice 4:");
        ligneM4.afficher();
        System.out.println("\n3e colonne de la matrice 4:");
        colM4.afficher();
        System.out.println("\nMatrice 4 transposée");
        Matrice m4T = m4.transpose();
        m4T.afficher();

        System.out.println("Matrice 4 avec multiplication scalaire de 10");
        m4.multiplierScalaire(10);
        m4.afficher();


        System.out.println("--- Tests d'erreurs : ---");
        System.out.println("\n--> Matrice 5x5 * Matrice 2x2\n");
        m4.dotProduct(m2);
        Vecteur erreur = m4.getLine(1);
        Vecteur erreur2 = m3.getCol(0);
        System.out.println("\n--> Vecteur de 5 éléments * Vecteur de 2 éléments\n");
        erreur.dotProduct(erreur2);

    }
}