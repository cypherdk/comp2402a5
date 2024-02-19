package comp2402a5;
// Thanks to Pat Morin for the skeleton of this file!

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class AdjacencyLists implements Graph {
	int n;    // num nodes / vertices
	List<List<Integer>> adj; // adj(i) is list of i's out-nbrs

	@SuppressWarnings("unchecked")
	public AdjacencyLists(int n0) {
		n = n0;
		adj = new ArrayList<>(n);
		for (int i = 0; i < n; i++) 
			adj.add(new ArrayList<Integer>());
	}
	
	public int nVertices() {
		return n;
	}

	public int addVertex() {
		adj.add(new ArrayList<Integer>());
		n++;
		return (n-1); // This is the index of the new vertex
	}

	public void addEdge(int i, int j) {
		// Should do a OOB check for i, j
		adj.get(i).add(j);
	}

	public void removeEdge(int i, int j) {
    	adj.get(i).remove(j);
	}

	public boolean hasEdge(int i, int j) {
		return adj.get(i).contains(j);
	}

	public List<Integer> outEdges(int i) {
		return adj.get(i);
	}

	public int outDegree(int i) {
		return outEdges(i).size();
	}

	public List<Integer> inEdges(int i) {
		List<Integer> edges = new ArrayList<Integer>();
		for (int j = 0; j < n; j++)
			if (adj.get(j).contains(i))	edges.add(j);
		return edges;
	}

	public int inDegree(int i) {
    	return inEdges(i).size();
	}

	public static Graph mesh(int n) {
		Graph g = new AdjacencyLists(n*n);
		for (int k = 0; k < n*n; k++) {
			if (k % n > 0) 
				g.addEdge(k, k-1);
			if (k >= n)
				g.addEdge(k, k-n);
			if (k % n != n-1)
				g.addEdge(k, k+1);
			if (k < n*(n-1))
				g.addEdge(k, k+n);
		}
		return g;
	}

	public static Graph cycle(int n) {
		Graph g = new AdjacencyLists(0);
		HashMap<String,Integer> name2Vertex = new HashMap<>();
		name2Vertex.put("a", g.addVertex() );
		name2Vertex.put("b", g.addVertex() );
		name2Vertex.put("c", g.addVertex() );
		name2Vertex.put("d", g.addVertex() );
		name2Vertex.put("e", g.addVertex() );
		g.addEdge( name2Vertex.get("a"), name2Vertex.get("b") );
		g.addEdge( name2Vertex.get("b"), name2Vertex.get("c") );
		g.addEdge( name2Vertex.get("c"), name2Vertex.get("d") );
		g.addEdge( name2Vertex.get("d"), name2Vertex.get("e") );
		g.addEdge( name2Vertex.get("e"), name2Vertex.get("a") );
		return g;
	}
	
	public static void main(String[] args) {
		Graph g = mesh(4);
		Algorithms.bfsZ(g,0);
		Algorithms.dfsZ(g,0);
		Algorithms.dfs2Z(g,0);
	}
}
