package comp2402a5;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MixAndBoom {

    static ArrayList<Integer>[] graph;

    public static void doIt(BufferedReader r, PrintWriter w) throws IOException {
        int N = Integer.parseInt(r.readLine());

        
        graph = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            String[] splitted = line.split(" ");
            int u = Integer.parseInt(splitted[0]), v = Integer.parseInt(splitted[1]);
            graph[u].add(v);
            graph[v].add(u);
        }

        
        if (isBipartite(N)) {
            w.println("yes");
        } else {
            w.println("no");
        }
    }

    private static boolean isBipartite(int N) {
        int[] color = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            if (color[i] == 0) {
                if (!bfs(i, color)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean bfs(int start, int[] color) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        color[start] = 1;

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (int neighbor : graph[node]) {
                if (color[neighbor] == 0) {
                    color[neighbor] = -color[node];
                    queue.add(neighbor);
                } else if (color[neighbor] == color[node]) {
                    return false; 
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        try {
            BufferedReader r;
            PrintWriter w;
            if (args.length == 0) {
                r = new BufferedReader(new InputStreamReader(System.in));
                w = new PrintWriter(System.out);
            } else if (args.length == 1) {
                r = new BufferedReader(new FileReader(args[0]));
                w = new PrintWriter(System.out);
            } else {
                r = new BufferedReader(new FileReader(args[0]));
                w = new PrintWriter(new FileWriter(args[1]));
            }
            long start = System.nanoTime();
            doIt(r, w);
            w.flush();
            long stop = System.nanoTime();
            System.out.println("Execution time: " + 1e-9 * (stop - start));
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
    }
}
