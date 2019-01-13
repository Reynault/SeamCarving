package modele.graph;

public class Edge
{
	protected int from;
	protected int to;
	protected int cost;
   public Edge(int x, int y, int cost)
	 {
		this.from = x;
		this.to = y;
		this.cost = cost;
	 }

	@Override
	public String toString() {
		return "Edge{" +
				"from=" + from +
				", to=" + to +
				", cost=" + cost +
				'}';
	}

	/**
	 * Getteur de from
	 * @return sommet de départ
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * Getteur de to
	 * @return sommet de fin
	 */
	public int getTo() {
		return to;
	}

	/**
	 * Getteur de la valeur de l'arête
	 * @return valeur de l'arête
	 */
	public int getCost() {
		return cost;
	}
}
