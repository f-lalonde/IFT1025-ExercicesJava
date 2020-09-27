/*
Exercice noté 2
Francis Lalonde (801363)
IFT-1025, 26 janvier 2020
*/
public class Matrice {

    private double[][] matrice;

    /**
     * Constructeur : crée une matrice de taille (N lignes) x (M colonnes).
     * La matrice est peuplée par des zéros.
     * @param lignes
     * @param cols
     */
    public Matrice(int lignes, int cols){
        this.matrice = new double[lignes][cols];
        for(int i=0; i<lignes; ++i){
            for(int j=0; j<cols; ++j){
                this.matrice[i][j] = 0.0;
            }
        }
    }


    /**
     * Additionne la valeur n dans toutes les cellules de la matrice
     * @param n
     */
    public void additionnerScalaire(double n){
        for(int i=0; i<this.matrice.length; ++i){
            for(int j=0; j<this.matrice[0].length; ++j){
                this.matrice[i][j] += n;
            }
        }
    }

    /**
     * Multiplie toutes les cellules d'une matrice par un scalaire n
     * @param n
     */
    public void multiplierScalaire(double n){
        for(int i=0; i<this.matrice.length; ++i){
            for(int j=0; j<this.matrice[0].length; ++j){
                this.matrice[i][j] *= n;
            }
        }
    }

    /**
     * Fait le produit matriciel entre deux matrices, sans les modifier
     * @param m
     * @return nouvel objet de type Matrice
     */
    public Matrice dotProduct(Matrice m){
        int nbLignes = this.matrice.length;
        int nbColonnes = m.matrice[0].length;
        Matrice produitMatriciel = new Matrice(nbLignes, nbColonnes);
        Vecteur ligneM1;
        Vecteur colonneM2;

        if(this.matrice[0].length != m.matrice.length){
            System.err.println("Erreur dans les dimensions des matrices");
            return null;
        }

        for(int i=0; i<nbLignes; ++i){
            ligneM1 = this.getLine(i);
            for(int j=0; j<nbColonnes; ++j){
                colonneM2 = m.getCol(j);
                produitMatriciel.matrice[i][j] = ligneM1.dotProduct(colonneM2);
            }
        }
        return produitMatriciel;

    }

    /**
     * Accesseur pour la cellule à une ligne/colonne donnée
     * @param ligne
     * @param col
     * @return
     */
    public double getCell(int ligne, int col){
        return this.matrice[ligne][col];
    }

    /**
     * Mutateur pour la cellule à une ligne/colonne donnée
     * @param ligne 
     * @param col
     * @param valeur
     */
    public void setCell(int ligne, int col, double valeur){
        this.matrice[ligne][col] = valeur;
    }
    
    /**
     * Retourne un nouveau vecteur contenant la Nième ligne
     * @param ligne
     * @return nouvel objet de type Vecteur
     */
    public Vecteur getLine(int ligne){
        Vecteur ligneRetournee = new Vecteur(this.matrice[ligne]);
        return ligneRetournee;
    }

     /**
      * Retourne un nouveau vecteur contenant la Nième colonne
      * @param col 
      * @return nouvel objet de type Vecteur
      */
    public Vecteur getCol(int col){
        Matrice inverse = this.transpose();
        Vecteur colonneRetournee = new Vecteur(inverse.matrice[col]);
        return colonneRetournee;
    }

    /**
     * Affiche la matrice sur la console, chaque ligne entre crochets [ ]
     */
    public void afficher(){
        String matriceAfficher = "";

        for(int i=0; i<this.matrice.length; ++i){
            matriceAfficher += "[ ";
            for(int j=0; j<this.matrice[0].length; ++j){
                matriceAfficher += Double.toString(this.getCell(i, j))+ " ";
            }
            matriceAfficher += "]\n";
        }
        System.out.println(matriceAfficher);
    }

    /**
     * Execute une transposition de la matrice, sans la modifier.
     * @return un objet Matrice, transposée de la matrice initiale.
     */
    public Matrice transpose(){
        int ligneMatrice = this.matrice.length;
        int colMatrice = this.matrice[0].length;
        Matrice matriceTransposee = new Matrice(colMatrice, ligneMatrice);

        for(int i=0; i<ligneMatrice; ++i){
            for(int j=0; j<colMatrice; ++j){
                matriceTransposee.matrice[j][i] = this.matrice[i][j];
            }
        }
        return matriceTransposee;
    }

    /**
     * Retourne une matrice identité de taille n x n
     * @param n
     * @return objet Matrice de taille n x n
     */
    public static Matrice identite(int n){
        Matrice matriceIdentite = new Matrice(n, n);
        
        for(int i = 0; i<n; ++i){
            matriceIdentite.matrice[i][i]=1;   
        }
        return matriceIdentite;
    }
}