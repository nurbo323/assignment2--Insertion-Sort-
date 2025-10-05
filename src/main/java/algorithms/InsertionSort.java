package algorithms;

import metrics.PerformanceTracker;

public class InsertionSort {

    public static void sort(int[] arr, PerformanceTracker tracker) {
        if (arr == null) return;
        int n = arr.length;
        tracker.start();
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0) {
                tracker.incrementComparisons();
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    tracker.incrementSwaps();
                    j--;
                } else break;
            }
            arr[j + 1] = key;
        }
        tracker.stop();
    }
}
