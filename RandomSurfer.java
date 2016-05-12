import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

public class RandomSurfer {
    public static void main(String[] args) {
        int T = Integer.parseInt(args[0]);    // number of moves
        int M = StdIn.readInt();              // number of pages  - ignore since M = N
        int N = StdIn.readInt();              // number of pages
        if (M != N) throw new RuntimeException("M does not equal N");

        // Read transition matrix.
        double[][] p = new double[N][N];     // p[i][j] = prob. that surfer moves from page i to page j
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                p[i][j] = StdIn.readDouble();


        int[] freq = new int[N];            // freq[i] = # times surfer hits page i

        // Start at page 0.
        int page = 0;

        for (int t = 0; t < T; t++) {

            // Make one random move.
            double r = Math.random();
            double sum = 0.0;
            for (int j = 0; j < N; j++) {
                // Find interval containing r.
                sum += p[page][j];
                if (r < sum) {
                    page = j;
                    break;
                }
            }
            freq[page]++;
        }


        // Print page ranks.
        for (int i = 0; i < N; i++) {
            StdOut.printf("%8.5f", (double) freq[i] / T);
        }
        StdOut.println();
    }
}
