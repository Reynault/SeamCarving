package modele;

import modele.graph.Edge;
import modele.graph.Graph;
import modele.graph.GraphArrayList;
import modele.graph.Test;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
public class SeamCarving
{

   public static int[][] readpgm(String fn)
	 {
        try {
            InputStream f = ClassLoader.getSystemClassLoader().getResourceAsStream(fn);
            BufferedReader d = new BufferedReader(new InputStreamReader(f));
            String magic = d.readLine();
            String line = d.readLine();
		   while (line.startsWith("#")) {
			  line = d.readLine();
		   }
		   Scanner s = new Scanner(line);
		   int width = s.nextInt();
		   int height = s.nextInt();
		   line = d.readLine();
		   s = new Scanner(line);
		   int maxVal = s.nextInt();
		   int[][] im = new int[height][width];
		   s = new Scanner(d);
		   int count = 0;
		   while (count < height*width) {
			  im[count / width][count % width] = s.nextInt();
			  count++;
		   }
		   return im;
        }

        catch(Throwable t) {
            t.printStackTrace(System.err) ;
            return null;
        }
    }

	/**
	 * Méthode qui permet d'écrire un fichier pgm
	 *
	 * @param image image à écrire
	 * @param filename nom de l'image en pgm
	 */
   public static void writepgm(int[][] image, String filename){
	   try {
		   // Ouverture du flux d'écriture
		   BufferedWriter bf = new BufferedWriter(new FileWriter(filename));
		   // Ligne de départ d'un fichier pgm
		   bf.write("P2");
		   bf.newLine();
		   // Récupération de la largeur/hauteur
		   int hauteur = image.length;
		   if(hauteur > 1) {
               int largeur = image[0].length;
               // Ecriture de la largeur et hauteur
               bf.write(largeur + " " + hauteur);
               bf.newLine();
               // Ecriture de la valeur maximale
               bf.write("255");
               bf.newLine();
               // On parcourt la matrice
               for (int i = 0; i < hauteur; i++) {
                   for (int j = 0; j < largeur; j++) {
                       // Ecriture de la valeur d'un pixel
                       bf.write(image[i][j] + " ");
                   }
                   // Création de la nouvelle ligne pour que le
                   // Fichier soit plus visible
                   bf.newLine();
               }
           }
		   // Fermeture du flux
		   bf.close();
		   // Gestion de l'erreur
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
   }

    /**
     * Méthode interest qui permet de récupérer le tableau contenant les intérêts des pixels de l'image en fonction des
     * voisins.
     * @param image
     * @return
     */
   public static int[][] interest(int[][] image){
       // Récupération de la hauteur et de la largeur
       int hauteur = image.length;
       int largeur = image[0].length;
       int[][] interet = new int[hauteur][largeur];
       int moyenne;
       // On parcourt l'image
       for(int i = 0 ; i < hauteur ; i++){
           for(int j = 0 ; j < largeur ; j++){
               // Pour chaque pixel, on regarde s'il se trouve tout à droite, tout à gauche, ou s'il possède
               // deux voisins
               if(j == 0){
                   // S'il est tout à gauche, on soustrait par rapport à l'unique voisin
                   interet[i][j] = Math.abs(image[i][j]-image[i][j+1]);
               }else if(j == (largeur - 1) ){
                   // De même avec le voisin de gauche s'il se trouve à droite
                   interet[i][j] = Math.abs(image[i][j]-image[i][j-1]);
               }else{
                   // S'il possède deux voisins, on fait la moyenne des deux
                   moyenne = (image[i][j - 1] + image[i][j + 1]) / 2;
                   // Puis on fait la différence
                   interet[i][j] = Math.abs(image[i][j] - moyenne);
               }
           }
       }
       return interet;
   }

    /**
     * Méthode qui permet de créer un graphe via un tableau (celui qui correspond aux intérêts)
     * @param itr le tableau à utiliser
     * @return Le graphe
     */
   public static Graph tograph(int[][] itr){
       // Récupération du nombre de sommets
       int nbSommets = itr.length * itr[0].length;
       // Instanciation du graph avec les deux sommets supplémentaires
       GraphArrayList graphe = new GraphArrayList(nbSommets+2);
       // Hauteur du tableau
       int hauteur = itr.length;
       // Initialisation du numéro de sommets
       int numSommet = 1;
       int valeur;
       int largeur = itr[0].length;
       // Initialisation des arêtes partant de la base
       for (int i = 1; i <= largeur ; i++){
           graphe.addEdge(new Edge(0,i,0));
       }
       // On parcourt le tableau
       for(int i = 0 ; i < hauteur-1 ; i++){
           for(int j = 0 ; j < largeur ; j++){
               valeur = itr[i][j];
               // On lie le sommet aux suivants (les trois/deux du dessous)
               graphe.addEdge(new Edge(numSommet, numSommet + largeur, valeur));
               if (j == 0) {
                   graphe.addEdge(new Edge(numSommet, numSommet + largeur + 1, valeur));
               } else if (j == largeur - 1) {
                   graphe.addEdge(new Edge(numSommet, numSommet + largeur - 1, valeur));
               } else {
                   graphe.addEdge(new Edge(numSommet, numSommet + largeur + 1, valeur));
                   graphe.addEdge(new Edge(numSommet, numSommet + largeur - 1, valeur));
               }
               // Incrémentation du numéro du sommet parcouru
               numSommet++;
           }
       }
       for(int i = 0 ; i < largeur ; i++){
           // On lie ensuite les sommets au sommet de fin
           graphe.addEdge(new Edge(numSommet, nbSommets + 1, itr[hauteur-1][i]));
           numSommet++;
       }
       return graphe;
   }

    /**
     * Tri topologique, on utilise la méthode de la classe Test, avec un paramètre supplémentaire qui
     * récupère les sommets dans l'ordre inverse de l'ordre suffixe
     * @param g
     * @return
     */
   public static ArrayList<Edge> tritopo(Graph g){
       // Initialisation de la liste des sommets, et du tableau de booléen utilisé par la fonction dfs
       ArrayList<Edge> topo = new ArrayList<Edge>();
       Test.initialiserVisite(g.vertices());
       // Lancement du parcours en profondeur
       Test.dfs(g,0,topo);
       return topo;
   }

    /**
     * Méthode qui permet de calculer le chemin de coût minimal entre le sommet s et t sur un graphe dont un tri
     * topologique est donné par le paramètre order
     * @param g graphe dans lequel trouver le CCM
     * @param s sommet de début
     * @param t sommet de fin
     * @param order tri topologique
     * @return le CCM
     */
   public static ArrayList<Edge> bellman(Graph g, int s, int t, ArrayList<Edge> order){

       return null;
   }
}
