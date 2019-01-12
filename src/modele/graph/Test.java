package modele.graph;

import java.util.ArrayList;

public class Test
{
   static boolean visite[];
   public static void dfs(Graph g, int u, ArrayList<Edge> topo)
	 {
		visite[u] = true;
		System.out.println("Je visite " + u);
		for (Edge e: g.next(u))
		  if (!visite[e.to]) {
			  dfs(g, e.to, topo);
			  topo.add(0,e);
		  }
	 }

	public static void initialiserVisite(int taille){
   		visite = new boolean[taille];
	}

   public static void testGraph()
	 {
		int n = 5;
		int i,j;
		GraphArrayList g = new GraphArrayList(n*n+2);
		
		for (i = 0; i < n-1; i++)
		  for (j = 0; j < n ; j++)
			g.addEdge(new Edge(n*i+j, n*(i+1)+j, 1664 - (i+j)));

		for (j = 0; j < n ; j++)		  
		  g.addEdge(new Edge(n*(n-1)+j, n*n, 666));
		
		for (j = 0; j < n ; j++)					
		  g.addEdge(new Edge(n*n+1, j, 0));
		
		g.addEdge(new Edge(13,17,1337));
		g.writeFile("test.dot");
		// dfs à partir du sommet 3
		visite = new boolean[n*n+2];

		ArrayList<Edge> fin = new ArrayList<Edge>();
		dfs(g, 3, fin);
	 }
   
   public static void main(String[] args)
	 {
		testGraph();
	 }
}
