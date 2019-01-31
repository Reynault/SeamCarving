package modele.algo;

import modele.graph.Graph;

/**
 * Classe qui représente un type de seamcarving
 */
public abstract class Algo {
    /**
     * Méthode d'exécution du seam carving
     * @param img tableau qui contient l'image
     * @return graphe généré
     */
    public abstract Graph executer(int[][] img);
}
