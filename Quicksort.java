import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;

public class Quicksort {

    //a (Algorithmus Dijkstra-Partitionierung)
    public static int partition(int[] data, int l, int r) { //partitioniert ein Subarray data[l,r]
        int pivot = data[l]; //wähle das erste Element als Pivotelement
        int i = l;
        int j = l;
        int k = r;

        while (j <= k) {
            if (data[j] > pivot) {
                int temp = data[i]; //vertausche data[i] und data[j]
                data[i] = data[j];
                data[j] = temp;
                i++;
                j++;
            } else if (data[j] < pivot) {
                int temp = data[j]; //vertausche data[j] und data[k]
                data[j] = data[k];
                data[k] = temp;
                k--;
            } else {
                j++;
            }
        }
        return i;  //gib neue Pivotposition zurück
    }

    //b sortiere ein Subarray
    public static int[] qSort(int[] data, int l, int r) {
        if (l < r) {
            int m = partition(data, l, r); //m ist die neue Position von Pivot
            qSort(data, l, m - 1); //sortiere den linke Teil
            qSort(data, m + 1, r); //sortiere den rechten Teil
        }
        return data;
    }

    //c sortiert das gesamte Array
    public static int[] qSort(int[] data) {
        qSort(data, 0, data.length - 1);
        return data;
    }

    public static boolean isSorted(int[] data) { //aus A2.4
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i] < data[i + 1]) {
                return false;
            }
        }
        return true;
    }

    //d Lese ein Array vom Typ int[] von Standard-In (wie Blatt1) ein
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> input = new ArrayList<>();
        while (sc.hasNextLine()) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                input.add(value);
            } catch (NumberFormatException e) {
                System.out.println("Input list contains a non-number");
                return;
            }
        }
        int[] array = new int[input.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = input.get(i).intValue();
        }
        //vermeide das Worst Case
        assert (!isSorted(array)) : "Array is sorted";
        //sortiere Array mit qsort

            Instant start = Instant.now();
            if (array.length < 20 && array.length > 0) {
                System.out.println(Arrays.toString(array));
                qSort(array);
                System.out.println(Arrays.toString(array));
            } else if (array.length == 0) {
                System.out.println("the array is empty and then can not be sorted");
                return;
            } else {
                qSort(array);
            }
            Instant finish = Instant.now();

            //Laufzeitmessung
            long time = Duration.between(start, finish).toMillis();
            System.out.println("Time: " + time);

        int max = array[0];
        int min = array[array.length - 1];
        double median = 0;
        assert (isSorted(array));

        // gerade Länge: Median = Durchschnitt von 2 in der Mitte stehenden Zahlen
        // ungerade Länge: Median = Die Zahl in der Mitte
        if (array.length % 2 == 0) {
            median = (array[array.length / 2] + array[array.length / 2 - 1]) / 2.0;
        } else {
            median = array[array.length / 2];
        }

        System.out.printf("Min: %d, Medien: %.1f, Max: %d", min, median, max);

    }

}


