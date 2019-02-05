package modele.graph;

import java.util.Stack;

public class DFS
{

	public static void botched_dfs1(Graph g, int s){
	    // Pile dans laquelle on place les sommets en cours de visite
        Stack<Integer> stack = new Stack<Integer>();
        // On initialise le tableau de booléens, qui indique
        // pour chaque sommet s'il est déjà visité ou non
        boolean visited[] = new boolean[g.vertices()];
        // On commence par le premier, donc on l'ajoute à la
        // pile
        stack.push(s);
        // On le visite, donc on met à jour le tableau
        visited[s] = true;
        // Ensuite, on continue la visite, tant que
        // la pile n'est pas vide
        while (!stack.empty()){
            // On récupère le sommet courant
            int u = stack.pop();
            System.out.println(u);
            // Puis pour chacun de ses voisins
            for (Edge e: g.next(u))
                // On regarde s'il a déjà été visité
                if (!visited[e.to]) {
                    // Si ce n'est pas le cas, on le visite,
                    // Puis on l'ajoute en tant que sommet en cours de visite
                    visited[e.to] = true;
                    stack.push(e.to);
                }
        }
    }

    public static void botched_dfs2(Graph g, int s){
		// Pile vide
		Stack<Integer> stack = new Stack<Integer>();
		// Tableau qui indique les sommets visités
		boolean visited[] = new boolean[g.vertices()];
		// On est en train de parcourir le premier
		stack.push(s);
		System.out.println(s);
		// On l'indique comme visité
		visited[s] = true;
		// Ensuite tant qu'on a encore un sommet à parcourir
		while (!stack.empty()){
			// On l'enlève
			int u = stack.pop();
			// Puis pour chaque voisin
			for (Edge e: g.next(u)) {
				// Si celui-ci n'est pas déjà visité
				if (!visited[e.to]) {
					System.out.println(e.to);
					// On le met à true
					visited[e.to] = true;
					// et on va le visité
					stack.push(e.to);
				}
			}
		}
    }
    
    public static void botched_dfs3(Graph g, int s){
		// Initialisation de la pile
		Stack<Integer> stack = new Stack<Integer>();
		// Init du tableau des visites
		boolean visited[] = new boolean[g.vertices()];
		// On ajoute le premier
		stack.push(s);
		// Tant qu'il y a des sommets en cours de visite
		while (!stack.empty()) {
			// On enlève celui parcouru
			int u = stack.pop();
			// Puis on regarde s'il n'a pas été encore visité
			if (!visited[u]) {
				// Si c'est le cas, on le visite
				visited[u] = true;
				System.out.println(u);
				// Puis on ajoute chaque voisin non visité
				for (Edge e : g.next(u)) {
					if (!visited[e.to]) {
						stack.push(e.to);
					}
				}
			}
		}
		System.out.println("Taille de la pile : "+stack.capacity());
    }

    
    public static void botched_dfs4(Graph g, int s){
	Stack<Integer> stack = new Stack<Integer>();
	boolean visited[] = new boolean[g.vertices()];
	stack.push(s);
	visited[s] = true;
	System.out.println(s);
	while (!stack.empty()){
	    boolean end = true;
	    /* (a) Soit u le sommet en haut de la pile */
	    /* (b) Si u a un voisin non visité, alors */
	    /*     (c) on le visite et on l'ajoute sur la pile */
	    /* Sinon */
	    /*     (d) on enlève u de la pile */
	   
	    /* (a) */
	    int u = stack.peek();
	    for (Edge e: g.next(u))
		if (!visited[e.to]) /* (b) */
		    {
			visited[e.to] = true;
			System.out.println(e.to);			
			stack.push(e.to); /*(c) */
			end = false;
			break;
		    }
	    if (end) /*(d)*/
		stack.pop();
	}
	System.out.println(stack.capacity());
    }

    public static void testGraph(){
		int n = 5;
		int i,j;
		// Graph de test simple
		GraphArrayList g = new GraphArrayList(6);
		g.addEdge(new Edge(0, 1, 1));
		g.addEdge(new Edge(0, 2, 1));
		g.addEdge(new Edge(0, 3, 1));
		g.addEdge(new Edge(1, 4, 1));
		g.addEdge(new Edge(4, 3, 1));
		g.addEdge(new Edge(3, 5, 1));
		g.addEdge(new Edge(5, 1, 1));
		g.writeFile("test.dot");
		System.out.println("Test simple :");
		System.out.println("Print df1 :");
		botched_dfs1(g, 0);
		System.out.println("Print df2 :");
		botched_dfs2(g, 0);
		System.out.println("Print df3 :");
		botched_dfs3(g, 0);
		System.out.println("Print df4 :");
		botched_dfs4(g, 0);

		System.out.println("Test graph 100 :");
		// Graph à 100 sommets, test pour le troisième algo
		GraphArrayList g2 = new GraphArrayList(100);
		for(i = 0 ; i < 100 ; i++){
			for( j = 0 ; j < 100 ; j++){
				if(j != i){
					g2.addEdge(new Edge(i,j,1));
				}
			}
		}
		g2.writeFile("test2.dot");
		System.out.println("Print df3 :");
		botched_dfs3(g2, 0);
    }
    
    public static void main(String[] args)
    {
	testGraph();
    }
}
