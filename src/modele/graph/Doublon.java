package modele.graph;

import java.util.Iterator;

/**
 * Classe qui modélise un doublon entre un numéro de sommet et un itérateur qui permet de parcourir ses voisins
 */
public class Doublon {
    // Numéro du sommet
    private int numeroSommet;
    // Itérateur sur les voisins
    private Iterator<Edge> voisins;

    /**
     * Constructeur qui prend deux paramètres
     * @param numeroSommet le numéro du sommet
     * @param voisins la liste des voisins
     */
    public Doublon(int numeroSommet, Iterator<Edge> voisins) {
        this.numeroSommet = numeroSommet;
        this.voisins = voisins;
    }

    /**
     * Getteur qui retourne le numéro
     * @return numéro du sommet
     */
    public int getNumeroSommet() {
        return numeroSommet;
    }

    /**
     * Getteur qui retourne l'itérateur sur les voisins
     * @return itérateur
     */
    public Iterator<Edge> getVoisins() {
        return voisins;
    }
}
