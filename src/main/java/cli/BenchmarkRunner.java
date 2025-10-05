package cli;

import algorithms.InsertionSort;
import metrics.PerformanceTracker;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) throws IOException {
        int[] sizes = {100, 1000, 10000}; // сначала небольшие, потом увеличишь
        String[] dists = {"random","sorted","reverse","nearly"};
        Path csv = Paths.get("docs/performance-plots/results.csv");
        if (!Files.exists(csv.getParent())) Files.createDirectories(csv.getParent());
        if (!Files.exists(csv)) Files.write(csv, "algorithm,n,distribution,trial,time_ns,comparisons,swaps,arrayAccesses\n".getBytes(), StandardOpenOption.CREATE);
        Random rnd = new Random(123);
        for (int n : sizes) {
            for (String dist : dists) {
                int[] base = generate(n, dist, rnd);
                int trials = 3;
                for (int t = 0; t < trials; t++) {
                    int[] arr = Arrays.copyOf(base, base.length);
                    PerformanceTracker tracker = new PerformanceTracker();
                    tracker.startTimer();
                    InsertionSort.sort(arr, tracker); // использует трекер
                    tracker.stopTimer();
                    String line = String.format("insertion,%d,%s,%d,%d,%d,%d,%d\n",
                            n, dist, t, tracker.elapsedNs(), tracker.getComparisons(), tracker.getSwaps(), tracker.getArrayAccesses());
                    Files.write(csv, line.getBytes(), StandardOpenOption.APPEND);
                    System.out.println("Wrote: " + line.trim());
                }
            }
        }
    }

    private static int[] generate(int n, String dist, Random rnd) {
        int[] a = new int[n];
        switch (dist) {
            case "sorted": for (int i=0;i<n;i++) a[i]=i; break;
            case "reverse": for (int i=0;i<n;i++) a[i]=n-i; break;
            case "nearly": for (int i=0;i<n;i++) a[i]=i; for (int k=0;k<Math.max(1,n/100);k++){int i=rnd.nextInt(n),j=rnd.nextInt(n);int tmp=a[i];a[i]=a[j];a[j]=tmp;} break;
            default: for (int i=0;i<n;i++) a[i]=rnd.nextInt(); break;
        }
        return a;
    }
}
