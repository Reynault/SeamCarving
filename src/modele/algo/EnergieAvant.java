package modele.algo;

import modele.SeamCarving;
import modele.graph.Graph;

/**
 * Classe qui modélise le seam carving avec la méthode énergie avant
 */
public class EnergieAvant extends Algo {
    @Override
    public Graph executer(int[][] img) {
        int[][] itr = SeamCarving.interest(img);

        return SeamCarving.tograph(itr);
    }
}
