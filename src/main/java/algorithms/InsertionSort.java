package algorithms;

public class InsertionSort {

    public static void sort(int[] arr) {
        if (arr == null) return; // простая валидация
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            // Сдвигаем элементы, большие key, вправо
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}
