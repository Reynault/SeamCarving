import modele.SeamCarving;
import modele.graph.Graph;

import java.io.IOException;
import java.util.ArrayList;
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
        int[][] image = SeamCarving.readpgm("assets/test.pgm");
        // Écriture de l'image
        SeamCarving.writepgm(image,"test2.pgm");
    }

    /**
     * Méthode de test de la méthode interest, on calcul l'intérêt d'une image, et on l'affiche
     */
    public static void test_interest(){
        // Récupération du tableau d'intérêts
        int[][] image = SeamCarving.readpgm("assets/test.pgm");
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
        int[][] image = SeamCarving.readpgm("assets/test.pgm");
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
        int[][] image = SeamCarving.readpgm("assets/test.pgm");
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
        int[][] image = SeamCarving.readpgm("assets/test.pgm");
        int[][] itr = SeamCarving.interest(image);
        Graph g = SeamCarving.tograph(itr);
        ArrayList<Integer> topo = SeamCarving.tritopo(g);
        // Algo Bellman
        ArrayList<Integer> res = SeamCarving.bellman(g,0,g.vertices()-1,topo);
        for(Integer e : res){
            System.out.println(e);
        }
    }

    public static void main_premiere_partie(){
            // On demande à l'utilisateur le nom de l'image de base
            Scanner sc = new Scanner(System.in);
            long chrono;
            long chrono2;
            long div = 1000;
            System.out.println("Quelle image souhaitez-vous choisir ? (sans le pgm)");
            // On récupère la chaîne de l'utilisateur
            String image = sc.nextLine();

            chrono = java.lang.System.currentTimeMillis();

            // On lit l'image
            int[][] img = SeamCarving.readpgm(image + ".pgm");

            chrono2 = java.lang.System.currentTimeMillis();
            System.out.println("Temps pour readpgm : " + (chrono2 - chrono) + " millisecondes");
            chrono = java.lang.System.currentTimeMillis();

            // On calcule les facteurs d'intêret
            int[][] itr = SeamCarving.interest(img);

            chrono2 = java.lang.System.currentTimeMillis();
            System.out.println("Temps pour interest : " + (chrono2 - chrono) + " millisecondes");
            chrono = java.lang.System.currentTimeMillis();

            // On réalise un graphe sur les facteurs d'intérêt
            Graph g = SeamCarving.tograph(itr);

            chrono2 = java.lang.System.currentTimeMillis();
            System.out.println("Temps pour tograph : " + (chrono2 - chrono) + " millisecondes");
            chrono = java.lang.System.currentTimeMillis();

            // Puis on réalise un tri topologique
            ArrayList<Integer> topo = SeamCarving.tritopo(g);

            chrono2 = java.lang.System.currentTimeMillis();
            System.out.println("Temps pour topo : " + (chrono2 - chrono) + " millisecondes");
            chrono = java.lang.System.currentTimeMillis();

            // On applique l'algorithme de bellman pour récupérer le chemin de coût minimal entre s et t
            ArrayList<Integer> ccm = SeamCarving.bellman(g, 0, g.vertices() - 1, topo);

            chrono2 = java.lang.System.currentTimeMillis();
            System.out.println("Temps pour bellman : " + (chrono2 - chrono) + " millisecondes");
            chrono = java.lang.System.currentTimeMillis();

            // Puis on recreér la nouvelle image avec une colonne en moins
            int nbSommet = 1;
            int hauteur = img.length;
            int largeur = img[0].length;
            int[][] nouvelleImg = new int[hauteur][largeur - 1];
            int decalage = 0;
            // Pour chaque pixel de la première image
            for (int i = 0; i < hauteur; i++) {
                for (int j = 0; j < largeur; j++) {
                    // On regarde si le numéro du sommet est contenu dans le CCM
                    if (!ccm.contains(nbSommet)) {
                        // Si ce n'est pas le cas, cela signifie qu'on peut garder le pixel
                        nouvelleImg[i][j - decalage] = img[i][j];
                    } else {
                        // Sinon, on ne le garde pas, et on décale les suivants
                        if (decalage == 0) {
                            decalage++;
                        }
                    }
                    nbSommet++;
                }
                decalage--;
            }

            chrono2 = java.lang.System.currentTimeMillis();
            System.out.println("Temps pour nouvelle image : " + (chrono2 - chrono) + " millisecondes");

            System.out.println("Nom du fichier final ? (sans le pgm)");
            // On récupère la chaîne de l'utilisateur qui indique le nom du fichier résultant
            image = sc.nextLine();

            chrono = java.lang.System.currentTimeMillis();

            // On écrit ensuite la nouvelle image
            SeamCarving.writepgm(nouvelleImg, image + ".pgm");

            chrono2 = java.lang.System.currentTimeMillis();
            System.out.println("Temps pour writepgm : " + (chrono2 - chrono) + " millisecondes");
    }

    /**
     * Méthode main
     * @param args
     */
    public static void main(String[] args){
        try {
            main_premiere_partie();
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("L'image finale est vide, la largeur de l'image initiale est de 1.");
        }catch (NullPointerException e){
            System.out.println("Image cible inttrouvable.");
        }catch(Exception e){
            System.out.println("Une erreur est survenue lors du lancement de l'application.");
        }
    }
}
