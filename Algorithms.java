package comp2402a5;
// Thanks to Pat Morin for the skeleton of this file!

import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class Algorithms {
	
	/**
	 * Perform a bread-first search of g starting at vertex i
	 * @param g
	 * @param i
	 */
	public static void bfs(Graph g, int r) {
		boolean[] explored = new boolean[g.nVertices()];
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(r);
		explored[r] = true;
		while (!q.isEmpty()) {
			int i = q.remove();
			for (Integer j : g.outEdges(i)) {
				if (!explored[j]) {
					q.add(j);
					explored[j] = true;
				}
			}
		}
	}

	// bfs but also prints out the "edge" formed in the BFS tree.
	public static void bfsZ(Graph g, int r) {
		boolean[] explored = new boolean[g.nVertices()];
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(r);
		explored[r] = true;
		while (!q.isEmpty()) {
			int i = q.remove();
			for (Integer j : g.outEdges(i)) {
				if (!explored[j]) {
					System.out.println(i + " => " + j);
					q.add(j);
					explored[j] = true;
				}
			}
		}
	}

	/**
	 * Perform a bread-first search of g starting at vertex s, ending at t
	 * * Return the minimum distance between s and t, found via bfs.
	 * @param g
	 * @param s
	 * @param t 
	 */
	public static int bfs(Graph g, int s, int t) {
    if( s == t )  return 0; 

	boolean[] explored = new boolean[g.nVertices()];
	Queue<Integer> q = new LinkedList<Integer>();
	q.add(s);
	explored[s] = true;
    // Keep track of the distance from s to j in d[j]
    Queue<Integer> d = new LinkedList<Integer>();
    d.add(0);
	while (!q.isEmpty()) {
		int i = q.remove();
		int dist = d.remove();
		for (Integer j : g.outEdges(i)) {
			if( j == t ) { // we found t!
				return dist+1;
			}
			if (!explored[j]) {
				q.add(j);
				d.add(dist+1);
				explored[j] = true;
			}
		}
	}
    return -1;
	}

	/**
	 * Perform a bread-first search of g starting at vertex s, ending at t
	 * Return an array of "predecessors" along the path, that is, for a
	 * vertex i, if pred[i]=j then bfs travelled along the edge (j,i).
	 * pred[i] can be thought of as i's parent in the tree rooted at s
	 * that is formed by the BFS.
	 * @param g
	 * @param s
	 */
	public static int[] bfsPred(Graph g, int s) {
		boolean[] explored = new boolean[g.nVertices()];
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(s);
		explored[s] = true;

		int[] pred = new int[g.nVertices()];
		for( int i=0; i < g.nVertices(); i++ ) {
			pred[i] = -1;
		}
		while (!q.isEmpty()) {
			int i = q.remove();
			for (Integer j : g.outEdges(i)) {
				if (!explored[j]) {
					pred[j]=i;
					q.add(j);
					explored[j] = true;
				}
			}
		}
		return pred;
	}


	protected static byte white = 0, grey = 1, black = 2;

	/** 
	 * Recursive implementation of DFS
	 * @param g
	 * @param i
	 */
	public static void dfs(Graph g, int r) {
		byte[] c = new byte[g.nVertices()];
		dfs(g, r, c);
	}

	public static void dfs(Graph g, int i, byte[] c) {
		c[i] = grey;  // currently visiting i
		for (Integer j : g.outEdges(i)) {
			if (c[j] == white) {
				c[j] = grey;
				dfs(g, j, c);
			} 
		}
		c[i] = black; // done visiting i
	}

	public static void dfsZ(Graph g, int r) {
		byte[] c = new byte[g.nVertices()];
		dfsZ(g, r, c);
	}

	public static void dfsZ(Graph g, int i, byte[] c) {
		c[i] = grey;  // currently visiting i
		for (Integer j : g.outEdges(i)) {
			if (c[j] == white) {
				System.out.println(i + " => " + j);
				c[j] = grey;
				dfsZ(g, j, c);
			} 
		}
		c[i] = black; // done visiting i
	}

	/**
	 * A non-recursive implementation of dfs
	 * Note, this doesn't give exactly the same traversal as dfs(g,r)
	 * @param g
	 * @param r
	 */
	public static void dfs2(Graph g, int r) {
		byte[] c = new byte[g.nVertices()];
		Stack<Integer> s = new Stack<Integer>();
		s.push(r);
		while (!s.isEmpty()) {
			int i = s.pop();
			if (c[i] == white) {
				c[i] = grey;
				for (int j : g.outEdges(i))
					s.push(j);
			}
		}
	}

	public static void dfs2Z(Graph g, int r) {
		byte[] c = new byte[g.nVertices()];
		Stack<Integer> s = new Stack<Integer>();
		s.push(r);
		while (!s.isEmpty()) {
			int i = s.pop();
			if (c[i] == white) {
				c[i] = grey;
				System.out.println(i);
				for (int j : g.outEdges(i))
					s.push(j);
			}
		}
	}

	public static int[] dfs2Pred(Graph g, int r) {
		byte[] c = new byte[g.nVertices()];
		int[] pred = new int[g.nVertices()];
		for( int i=0; i < g.nVertices(); i++ ) {
			pred[i] = -1;
		}
		Stack<Integer> s = new Stack<Integer>();
		s.push(r);
		while (!s.isEmpty()) {
			int i = s.pop();
			if (c[i] == white) {
				c[i] = grey;
				for (int j : g.outEdges(i)) {
					pred[j] = i;
					s.push(j);
				}
			}
		}
		return pred;
	}

	public static List<Integer> topologicalSort(Graph g) {
		List<Integer> l = new ArrayList<>();
		Queue<Integer> q = new LinkedList<Integer>();
		int[] inDegrees = new int[g.nVertices()];
		for( int i=0; i < inDegrees.length; i++ ) {
			inDegrees[i] = g.inDegree(i);
			if( inDegrees[i] == 0 )
				q.add(i);
		}
		boolean[] seen = new boolean[g.nVertices()];
		while (!q.isEmpty()) {
			int i = q.remove();
			seen[i] = true;
			l.add(i);
			for (Integer j : g.outEdges(i)) {
				inDegrees[j]--;
				if (!seen[j]) {
					q.add(j);
					seen[j] = true;
				}
			}
		}
		if( l.size() != g.nVertices() )
			return null;
		return l;
	}
}
