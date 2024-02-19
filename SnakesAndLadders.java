package comp2402a5;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class SnakesAndLadders {

    public static void doIt(BufferedReader r, PrintWriter w) throws IOException {
        int N = Integer.parseInt(r.readLine());
        int finalSquare = N * N;

        int[] gameBoard = new int[finalSquare + 1];
        for (int i = 1; i <= finalSquare; i++) {
            gameBoard[i] = i;
        }

        for (String line = r.readLine(); line != null; line = r.readLine()) {
            String[] splitted = line.split(" ");
            int start = Integer.parseInt(splitted[0]);
            int end = Integer.parseInt(splitted[1]);
            gameBoard[start] = end;
        }

        int minDiceRolls = findMinDiceRolls(gameBoard, finalSquare);
        w.println(minDiceRolls);
    }

    private static int findMinDiceRolls(int[] board, int finalSquare) {
        boolean[] visited = new boolean[finalSquare + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        visited[1] = true;

        int rolls = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int currentSquare = queue.poll();

                for (int j = 1; j <= 6; j++) {
                    int nextSquare = board[currentSquare + j];
                    if (nextSquare == finalSquare) {
                        return rolls + 1;
                    }

                    if (!visited[nextSquare]) {
                        visited[nextSquare] = true;
                        queue.offer(nextSquare);
                    }
                }
            }

            rolls++;
        }

        return -1; 
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
