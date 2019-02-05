import modele.SeamCarving;
import modele.algo.Algo;
import modele.algo.EnergieAvant;
import modele.algo.Simple;
import modele.algo.SimpleImplicite;
import modele.graph.DFS;
import modele.graph.Graph;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe principale de test
 */
public class Principale {

    /**
     * Méthode de test de la méthode writepgm, on lit une image pgm puis on la réécrit
     * et on l'affiche pour voir si le résultat est équivalent
     */
    public static void test_writepgm(){
        // Lecture de l'image de test
        int[][] image = SeamCarving.readpgm("images/test.pgm");
        // Écriture de l'image
        SeamCarving.writepgm(image,"test2.pgm");
    }

    /**
     * Méthode de test de la méthode interest, on calcul l'intérêt d'une image, et on l'affiche
     */
    public static void test_interest(){
        // Récupération du tableau d'intérêts
        int[][] image = SeamCarving.readpgm("images/test.pgm");
        int[][] interet = SeamCarving.interest(image);
        int hauteur = interet.length;
        if(hauteur > 0) {
            int largeur = interet[0].length;
            // Affichage du tableau
            for (int i = 0; i < hauteur; i++) {
                for (int j = 0; j < largeur; j++) {
                    System.out.print(interet[i][j] + "    ");
                }
                System.out.println("");
            }
        }
    }

    /**
     * Méthode de test de la méthode toGraph, on lit une image, on crée le tableau correspondant aux intérêts
     * puis on crée le graphe et on affiche les arêtes
     */
    public static void test_tograph(){
        // Récupération du tableau d'intérêts
        int[][] image = SeamCarving.readpgm("images/test.pgm");
        int[][] interet = SeamCarving.interest(image);
        // Création du graphe et affichage dans un fichier
        Graph g = SeamCarving.tograph(interet);
        g.writeFile("graphe.dot");
    }

    /**
     * Méthode de test de la méthode tritopo, on lit une image pour ensuite créer le tableau d'intérêts
     * qu'on va ensuite transformer en graphe, puis parcourir en profondeur pour faire un
     * tri topologique
     */
    public static void test_tritopo(){
        // Récupération du graphe
        int[][] image = SeamCarving.readpgm("images/test.pgm");
        int[][] itr = SeamCarving.interest(image);
        Graph g = SeamCarving.tograph(itr);
        // Tri topologique et affichage des sommets dans le sens inverse de l'ordre suffixe
        ArrayList<Integer> topo = SeamCarving.tritopo(g);
        for(Integer e : topo){
            System.out.println(e);
        }
    }

    /**
     * Méthode de test de la méthode bellman
     */
    public static void test_bellman(){
        // Récupération du tri topo
        int[][] image = SeamCarving.readpgm("images/test.pgm");
        int[][] itr = SeamCarving.interest(image);
        Graph g = SeamCarving.tograph(itr);
        ArrayList<Integer> topo = SeamCarving.tritopo(g);
        // Algo Bellman
        ArrayList<Integer> res = SeamCarving.bellman(g,0,g.vertices()-1,topo);
        for(Integer e : res){
            System.out.println(e);
        }
    }

    /**
     * Méthode de test de la méthode tograph_energie_avant
     */
    public static void test_tograph_energie_avant(){
        // Récupération de l'image
        int[][] image = SeamCarving.readpgm("images/test.pgm");
        // Création du graphe et affichage dans un fichier
        Graph g = SeamCarving.tograph_energie_avant(image);
        g.writeFile("graphe.dot");
    }

    /**
     * Méthode main
     * @param args
     */
    public static void main(String[] args){
//        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Veuillez choisir une option : \nSeam carving simple : 1\nSeam carving avec énergie " +
                    "avant : 2\nSeam carving simple avec graphe implicite : 3");
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

            // On écrit ensuite la nouvelle image
            SeamCarving.writepgm(img, imagedestination);
//        }catch(ArrayIndexOutOfBoundsException e){
//            System.out.println("L'image finale est vide.");
//        }catch (InputMismatchException e){
//            System.out.println("Mauvaises options");
//        }catch(Exception e){
//            System.out.println("Une erreur est survenue lors de l'exécution de l'application.");
//        }
    }
}
