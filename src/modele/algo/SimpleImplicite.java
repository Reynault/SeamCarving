package modele.algo;

import modele.SeamCarving;
import modele.graph.Graph;
import modele.graph.GraphImplicit;

public class SimpleImplicite extends Algo{
    @Override
    public Graph executer(int[][] img) {
        int[][] itr = SeamCarving.interest(img);
        Graph g = new GraphImplicit(itr,itr[0].length,itr.length);
        return g;
    }
}
