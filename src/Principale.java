import modele.SeamCarving;
import modele.graph.Edge;
import modele.graph.Graph;
import modele.graph.GraphArrayList;

import java.util.ArrayList;

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
        int[][] image = SeamCarving.readpgm("test.pgm");
        // Écriture de l'image
        SeamCarving.writepgm(image,"test2.pgm");
    }

    /**
     * Méthode de test de la méthode interest, on calcul l'intérêt d'une image, et on l'affiche
     */
    public static void test_interest(){
        // Récupération du tableau d'intérêts
        int[][] image = SeamCarving.readpgm("test.pgm");
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
        int[][] image = SeamCarving.readpgm("test.pgm");
        int[][] interet = SeamCarving.interest(image);
        // Création du graphe et affichage dans un fichier
        Graph g = SeamCarving.tograph(interet);
        g.writeFile("graphe");
    }

    /**
     * Méthode de test de la méthode tritopo, on lit une image pour ensuite créer le tableau d'intérêts
     * qu'on va ensuite transformer en graphe, puis parcourir en profondeur pour faire un
     * tri topologique
     */
    public static void test_tritopo(){
        // Récupération du graphe
        int[][] image = SeamCarving.readpgm("test.pgm");
        int[][] itr = SeamCarving.interest(image);
        Graph g = SeamCarving.tograph(itr);
        // Tri topologique et affichage des sommets dans le sens inverse de l'ordre suffixe
        ArrayList<Edge> topo = SeamCarving.tritopo(g);
        for(Edge e : topo){
            System.out.println(e);
        }
    }

    /**
     * Méthode main
     * @param args
     */
    public static void main(String[] args){
        test_writepgm();
        test_interest();
        test_tograph();
        test_tritopo();
    }
}
