package modele.algo;

import modele.SeamCarving;
import modele.graph.Graph;

/**
 * Classe qui modélise le seam carving avec la méthode énergie avant
 */
public class EnergieAvant extends Algo {
    @Override
    public Graph executer(int[][] img) {
        return SeamCarving.tograph_energie_avant(img);
    }
}
