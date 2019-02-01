package modele;

import modele.graph.Edge;
import modele.graph.Graph;
import modele.graph.GraphArrayList;
import modele.graph.Test;

import java.awt.*;
import java.io.*;
import java.util.*;

public class SeamCarving
{

   public static int[][] readpgm(String fn)
	 {
        try {
            BufferedReader d = new BufferedReader(new FileReader(fn));
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
		   d.close();
		   return im;
        }catch (NullPointerException e){
            return null;
        }catch (FileNotFoundException e) {
            System.out.println("\nImage cible introuvable.");
            System.exit(1);
            return null;
        }catch(Throwable t) {
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
     *
     * @param image tableau des valeurs des pixels de l'image
     * @return tableau contenant les facteurs d'intérêt
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
     * Méthode qui permet de créer un graphe via un tableau.
     *
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

    public static Graph tograph_energie_avant(int[][] img){
        // Récupération du nombre de sommets
        int nbSommets = img.length * img[0].length;
        // Instanciation du graph avec les deux sommets supplémentaires
        GraphArrayList graphe = new GraphArrayList(nbSommets+2);
        // Hauteur du tableau
        int hauteur = img.length;
        // Initialisation du numéro de sommets
        int numSommet = 1;
        int gauche;
        int droite;
        int largeur = img[0].length;
        // Initialisation des arêtes partant de la base
        for (int i = 1; i <= largeur ; i++){
            graphe.addEdge(new Edge(0,i,0));
        }
        // On parcourt le tableau
        for(int i = 0 ; i < hauteur-1 ; i++){
            for(int j = 0 ; j < largeur ; j++){
                // Valeur du sommet gauche
                if(j == 0){
                    gauche = 0;
                }else{
                    gauche = img[i][j-1];
                }
                // Valeur du sommet droit
                if(j == largeur - 1){
                    droite = 0;
                }else{
                    droite = img[i][j+1];
                }
                // Ajout des arêtes
                graphe.addEdge(new Edge(numSommet,numSommet+largeur,Math.abs(droite-gauche)));
                if(j > 0){
                    graphe.addEdge(new Edge(numSommet,numSommet+largeur-1,Math.abs(gauche-img[i+1][j])));
                }
                if(j < largeur - 1){
                    graphe.addEdge(new Edge(numSommet,numSommet+largeur+1,Math.abs(droite-img[i+1][j])));
                }

                // Incrémentation du numéro du sommet parcouru
                numSommet++;
            }
        }
        // On lie ensuite les sommets au sommet de fin
        for(int i = 0 ; i < largeur ; i++){
            if(i == 0){
                graphe.addEdge(new Edge(numSommet, nbSommets + 1, img[hauteur-1][i+1]));
            }else if(i == largeur - 1){
                graphe.addEdge(new Edge(numSommet, nbSommets + 1, img[hauteur-1][i-1]));
            }else{
                graphe.addEdge(new Edge(numSommet, nbSommets + 1,
                        Math.abs(img[hauteur-1][i-1]-img[hauteur-1][i+1])));
            }
            numSommet++;
        }
        return graphe;
    }

    /**
     * Tri topologique, on utilise la méthode de la classe Test, avec un paramètre supplémentaire qui
     * récupère les sommets dans l'ordre inverse de l'ordre suffixe
     * @param g le graphe dont on veut trouver un tri topologique
     * @return une liste contenant les arêtes qui forment le tri topologique
     */
   public static ArrayList<Integer> tritopo(Graph g){
       // Initialisation de la liste des sommets, et du tableau de booléen utilisé par la fonction dfs
       ArrayList<Integer> topo = new ArrayList<Integer>();
       Test.initialiserVisite(g.vertices());
       // Lancement du parcours en profondeur
       // Le graphe est forcement connexe, donc pas besoin de mettre
       // le dfs dans un boucle
       Test.dfs(g,0,topo);
       // Ajout du premier sommet
       topo.add(0);
       // On prend l'ordre inverse de l'ordre suffixe
       Collections.reverse(topo);
       return topo;
   }

    /**
     * Méthode qui permet de calculer le chemin de coût minimal entre le sommet s et t sur un graphe dont un tri
     * topologique est donné par le paramètre order
     * @param g graphe dans lequel trouver le CCM
     * @param s sommet de début
     * @param t sommet de fin
     * @param order tri topologique (liste des sommets dans l'ordre)
     * @return le CCM
     */
   public static ArrayList<Integer> bellman(Graph g, int s, int t, ArrayList<Integer> order){
       ArrayList<Integer> res = new ArrayList<Integer>();
       // Initialisation du tableau qui va contenir les valeurs
       int [] T = new int[g.vertices()];
       // On commence par donner les valeurs des CCM pour chaque sommet dans le sens du tri topologique
       int min;
       int value;
       Iterator<Edge> i;
       Edge next;
       // On parcourt tous les sommets dans le tri topologique
       for(Integer vertices : order){
            min = 0;
            // Pour chaque sommet, on trouve la plus petite valeur pour le sommet précédent et le coût de l'arête.
            i = g.prev(vertices).iterator();
            // Donc on parcourt tous les sommets précédents

           // Gestion du premier sommet précédent pour initialiser min
            if(i.hasNext()){
                next = i.next();
                // on cherche min(T[u] + coût(u,v))
                min = T[next.getFrom()] + next.getCost();
                // Parcours des autres
                while(i.hasNext()){
                    next = i.next();
                    value = T[next.getFrom()] + next.getCost();
                    if(value < min){
                        min = value;
                    }
                }
            }
            // Ensuite, on met à jour T
            T[vertices] = min;
       }
       // On récupère ensuite les sommets du CCM entre s et t
       res.add(t);
       // Donc pour ça, on part de la fin, et on s'arrête quand on arrive au début
       while(t != s){
           i = g.prev(t).iterator();
           // Puis on regarde tous les sommets précédents
           if(i.hasNext()){
               // On commence par initialiser min avec le premier
               next = i.next();
               t = next.getFrom();
               min = T[next.getFrom()]+ next.getCost();
               // Puis pour les autres
               while (i.hasNext()){
                   // On regarde min(T[u]+coût(u,v))
                   next = i.next();
                   value = T[next.getFrom()]+next.getCost();
                   if(value < min){
                       min = value;
                       t = next.getFrom();
                   }
               }
           }
           // Puis on ajoute en tête de la liste le sommet précédent
           res.add(0,t);
       }
       return res;
   }

    /**
     * Méthode qui permet de récupérer la nouvelle image en enlevant une colonne.
     *
     * Les sommets enlevés sont ceux qui se trouvent dans le ccm.
     * @param img l'image cible
     * @param ccm le chemin de coût minimal issu de l'algorithme de bellman
     * @return la nouvelle image avec une colonne en moins
     */
   public static int[][] recup_nouvelleImage(int[][] img, ArrayList<Integer> ccm){
       int nbSommet = 1;
       int hauteur = img.length;
       int largeur = img[0].length;
       int[][] nouvelleImg = new int[hauteur][largeur - 1];
       int decalage = 0;
       int numeroSommetCCM = 1;
       // Pour chaque pixel de la première image
       for (int i = 0; i < hauteur; i++) {
           for (int j = 0; j < largeur; j++) {
               // On regarde si le numéro du sommet est contenu dans le CCM
               if (ccm.get(numeroSommetCCM) != nbSommet) {
                   // Si ce n'est pas le cas, cela signifie qu'on peut garder le pixel
                   nouvelleImg[i][j - decalage] = img[i][j];
               } else {
                   // Sinon, on ne le garde pas, et on décale les suivants
                   if( numeroSommetCCM < ccm.size()-1) {
                       numeroSommetCCM ++;
                   }
                   if (decalage == 0) {
                       decalage++;
                   }
               }
               nbSommet++;
           }
           decalage--;
       }
       return nouvelleImg;
   }
}
