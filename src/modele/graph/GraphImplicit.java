package modele.graph;

import java.util.ArrayList;

/**
 * Classe qui représente un graphe implicite
 */
public class GraphImplicit extends Graph {

	// Tableau des facteurs d'intérêt
    int[][] interest;
    // Largeur
    int w;
    // Hauteur
    int h;

	/**
	 * Constructeur à trois paramètres
	 * @param interest le tableau des intérêts
	 * @param w la largeur
	 * @param h la hauteur
	 */
	public GraphImplicit(int[][] interest, int w, int h) {
		this.interest = interest;
		this.w = w;
		this.h = h;
	}

	public int vertices(){
		return w*h+2;
    }

    public Iterable<Edge> next(int v){
		// Variables
		ArrayList<Edge> edges = new ArrayList();
		int ligne, colonne, valeur;
		// Position dans le tableau
		ligne = (v/w) - 1;
		colonne = (v%w);

		// Récupération des arêtes

		// Dans le cas où v == 0
		if(v == 0){
			for (int i = 1; i <= w ; i++){
				edges.add(new Edge(0,i,0));
			}
		// Dans le cas où v != dernier sommet
		}else if(v != w*h){
			valeur = interest[ligne][colonne];
			edges.add(new Edge(v, v + w, valeur));
			if (colonne == 0) {
				edges.add(new Edge(v, v + w + 1, valeur));
			} else if (colonne == w - 1) {
				edges.add(new Edge(v, v + w - 1, valeur));
			} else {
				edges.add(new Edge(v, v + w + 1, valeur));
				edges.add(new Edge(v, v + w - 1, valeur));
			}

		}
	    return edges;
	}

   	public Iterable<Edge> prev(int v){
		// Variables
	    ArrayList<Edge> edges = new ArrayList();
		int ligne, colonne, valeur;
		// Position dans le tableau
		ligne = v/w-1;
		colonne = v%w;

		// Récupération des arêtes

		// Dans le cas où v est le dernier sommet
		if(v == w*h){
			for(int i = 0 ; i < w ; i++){
				// On lie ensuite les sommets au sommet de fin
				edges.add(new Edge(v, w*h+1, interest[h-1][i]));
			}
		// Pour les autres sommets
		}else if(v != w*h){
			valeur = interest[ligne][colonne];;
			edges.add(new Edge(v, v - w, valeur));
			if (colonne == 0) {
				edges.add(new Edge(v, v - w + 1, valeur));
			} else if (colonne == w - 1) {
				edges.add(new Edge(v, v - w - 1, valeur));
			} else {
				edges.add(new Edge(v, v - w + 1, valeur));
				edges.add(new Edge(v, v - w - 1, valeur));
			}

		}
	    return edges;
	}
}
