package modele.algo;

import modele.SeamCarving;
import modele.graph.Graph;

/**
 * Classe qui modélise le seam carving simple
 */
public class Simple extends Algo {
    @Override
    public Graph executer(int[][] img) {
        return SeamCarving.tograph_energie_avant(img);
    }
}
