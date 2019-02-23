package modele.algo;

import modele.SeamCarving;
import modele.graph.Graph;

/**
 * Classe qui modélise le seam carving simple
 */
public class Simple extends Algo {
    @Override
    public Graph executer(int[][] img) {
        int[][] itr = SeamCarving.interest(img);
        Graph g = SeamCarving.tograph(itr);
        return g;
    }
}
