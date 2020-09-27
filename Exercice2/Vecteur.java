/*
Exercice noté 2
Francis Lalonde (801363)
IFT-1025, 26 janvier 2020
*/

public class Vecteur {

    private double[] vecteur;

    /**
     * Constructeur avec en paramètre un tableau d'éléments de type double.
     * @param elements
     */
    public Vecteur(double[] elements){
        this.vecteur = new double[elements.length];

        for(int i=0; i<elements.length; ++i){
            this.vecteur[i] = elements[i];
        }
    }   

    /**
     * Permet de changer la valeur à l'index donné.
     * ATTENTION : le premier élément a 0 pour index.
     * @param index
     * @param valeur
     */
    public void setElement(int index, double valeur){
        this.vecteur[index] = valeur;
    }

    /**
     * Retourne la valeur à l'index demandé
     * @param index
     * @return valeur de type double
     */
    public double getElement(int index){
        return this.vecteur[index];
    }

    /**
     * Calcule le produit scalaire avec un autre vecteur
     * @param v
     * @return valeur de type double
     */
    public double dotProduct(Vecteur v){
        double produitScalaire = 0.0;
        double valeurVecteur1;
        double valeurVecteur2;
        
        if(this.vecteur.length != v.vecteur.length){
            System.err.println("Erreur dans les dimensions des vecteurs");
            return Double.NaN;
        }

        for(int i=0; i<this.vecteur.length; ++i){
            valeurVecteur1 = this.vecteur[i];
            valeurVecteur2 = v.vecteur[i];
            produitScalaire += valeurVecteur1*valeurVecteur2;
        }
        return produitScalaire;
    }

    /**
     * Affiche le contenu du vecteur entres accolades sur la console.
     */
    public void afficher(){
        String afficherVecteur = "{";
        for(int i=0; i<this.vecteur.length-1; ++i){
            afficherVecteur += Double.toString(vecteur[i]) +", ";
        }
        afficherVecteur += Double.toString(this.vecteur[vecteur.length-1]) + "}";
        System.out.println(afficherVecteur);
    }
}