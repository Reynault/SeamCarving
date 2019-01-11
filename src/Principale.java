import modele.SeamCarving;

/**
 * Classe principale de test
 */
public class Principale {
    /**
     * Premier test de la méthode writepgm, on lire une image pgm puis on la réécrit
     * et on l'affiche pour voir si le résultat est équivalent
     */
    public static void premierTest(){
        // Lecture de l'image de test
        SeamCarving.readpgm("../../assets/test.pgm");
    }

    /**
     * Méthode main
     * @param args
     */
    public static void main(String[] args){
        // Execution du premier test
        premierTest();
    }
}
