package modele.algo;

import modele.graph.Graph;
import modele.graph.GraphImplicit;

public class SimpleImplicite extends Algo{
    @Override
    public Graph executer(int[][] img) {
        return new GraphImplicit(img,img[0].length,img.length);
    }
}
