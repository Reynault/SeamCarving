import modele.SeamCarving;
import modele.algo.Algo;
import modele.algo.EnergieAvant;
import modele.algo.Simple;
import modele.algo.SimpleImplicite;
import modele.graph.Graph;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe principale de test
 */
public class Principale {

    public static void principale(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Veuillez choisir une option : \nSeam carving simple : 1\nSeam carving avec énergie " +
                    "avant : 2\nSeam carving simple avec graphe implicite : 3\nAutre : Seam carving simple");
            int option = sc.nextInt();
            sc.nextLine();

            Algo typeSeamCarving;
            // En fonction du type de seam carving choisi, on change de type
            switch (option){
                case 1:
                    typeSeamCarving = new Simple();
                    break;
                case 2:
                    typeSeamCarving = new EnergieAvant();
                    break;
                case 3:
                    typeSeamCarving = new SimpleImplicite();
                    break;
                default:
                    typeSeamCarving = new Simple();
                    break;
            }

            System.out.println("Quel type de suppression souhaitez-vous réaliser ?\nColonnes : 1\nLignes : 2\nAutre : Colonnes");
            option = sc.nextInt();
            sc.nextLine();

            int[][] img;
            Graph g;
            ArrayList<Integer> topo;
            ArrayList<Integer> ccm;

            // Image cible
            System.out.println("Quelle image souhaitez-vous choisir ?");
            String image = sc.nextLine();

            // Nom du fichier final
            System.out.println("Nom du fichier final ?");
            String imagedestination = sc.nextLine();

            // Nombre de fois qu'on utilise le seam carving
            System.out.println("Combien de fois voulez-vous utiliser le seam carving ?");
            int nb = sc.nextInt();
            sc.nextLine();

            System.out.println("Application du seam carving en cours ...");

            // On lit l'image
            img = SeamCarving.readpgm(image);

            if(option==2){
                img = SeamCarving.rotationAvant(img);
            }

            // Utilisation du seam carving nb fois
            for(int k = 0; k < nb ; k++) {
                // On exécute le seam carving
                g = typeSeamCarving.executer(img);

                // Puis on réalise un tri topologique
                topo = SeamCarving.tritopo(g);

                // On applique l'algorithme de bellman pour récupérer le chemin de coût minimal entre s et t
                ccm = SeamCarving.bellman(g, 0, g.vertices() - 1, topo);

                // Puis on recreér la nouvelle image avec une colonne en moins
                img = SeamCarving.recup_nouvelleImage(img,ccm);
            }

            if(option==2){
                img = SeamCarving.rotationAvant(img);
            }

            // On écrit ensuite la nouvelle image
            SeamCarving.writepgm(img, imagedestination);

        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("L'image finale est vide.");
        }catch (InputMismatchException e){
            System.out.println("Mauvaises options");
        }catch(Exception e){
            System.out.println("Une erreur est survenue lors de l'exécution de l'application.");
        }
    }

    /**
     * Méthode main
     * @param args
     */
    public static void main(String[] args){
        principale();
    }
}
