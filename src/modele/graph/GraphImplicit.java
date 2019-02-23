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
		ArrayList<Edge> edges = new ArrayList<Edge>();
		int ligne, colonne, valeur;
		// Position dans le tableau
		if(v != 0) {
			ligne = (v-1)/w;
			colonne = v - (ligne * w + 1);
		}else{
			ligne = 0;
			colonne = 0;
		}
		// Récupération des arêtes

		// Cas dans lequel le sommet ne fait pas parti des derniers sommets qui pointent
		// vers le sommet final
		if(ligne < h-1){
			// Dans le cas où v == 0
			if(v == 0){
				for (int i = 1; i <= w ; i++){
					edges.add(new Edge(0,i,0));
				}
			// Dans le cas où v != dernier sommet
			}else{
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
		// Cas dans lequel on se trouve dans la dernière ligne
		}else if(ligne == h-1){
			edges.add(new Edge(v,w*h+1,interest[ligne][colonne]));
		}
	    return edges;
	}

   	public Iterable<Edge> prev(int v){
		// Variables
	    ArrayList<Edge> edges = new ArrayList<Edge>();
		int ligne, colonne;
		// Position dans le tableau
		if(v != 0) {
			ligne = (v-1)/w;
			colonne = v - (ligne * w + 1);
		}else{
			ligne = 0;
			colonne = 0;
		}
		// Récupération des arêtes si le sommet ne fait pas parti de la première ligne
		if(ligne > 0) {
			// Dans le cas où v est le dernier sommet
			if (v == w*h+1) {
				for (int i = 0; i < w; i++) {
					// On lie ensuite les sommets au sommet de fin
					edges.add(new Edge(v-(w-i), v, interest[ligne - 1][i]));
				}
				// Pour les autres sommets
			} else{
				edges.add(new Edge(v - w, v, interest[ligne-1][colonne]));
				if (colonne == 0) {
					edges.add(new Edge(v - w + 1, v, interest[ligne-1][colonne+1]));
				} else if (colonne == w - 1) {
					edges.add(new Edge(v - w - 1, v, interest[ligne-1][colonne-1]));
				} else {
					edges.add(new Edge(v - w + 1, v, interest[ligne-1][colonne+1]));
					edges.add(new Edge(v - w - 1, v, interest[ligne-1][colonne-1]));
				}
			}
		// Dans le cas de la première ligne
		}else if(ligne == 0 && v != 0){
			edges.add(new Edge(0, v,0));
		}
	    return edges;
	}
}
