import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.time.Instant;
import java.time.Duration;

public class Quicksort2 { //Dual-Pivot

    //Hilfsmethode für Vertauschung 2 Elemente
    private static void swap(int[] arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
    public static int[] partition(int[] data, int l, int r) { //partitioniert ein Subarray data[l,r]
        if(data[l] < data[r]){
            swap(data, l, r); //vertausche das erste und letzte Element
        }
        int leftPivot = data[l]; //wähle erstes Element als Pivotelement
        int rightPivot = data[r]; //wähle letztes Element als Pivotelement

        int leftPointer  = l + 1; //leftPointer
        int rightPointer  = r - 1; //rightPointer

        int i = l + 1;

        while (i <= rightPointer) {
            if (data[i] >  leftPivot ) {
                swap(data, i, leftPointer);
                i++;
                leftPointer++;
            } else if (data[i] < rightPivot ){
                swap(data, i, rightPointer );
                rightPointer--;
            } else {
                i++;
            }
        }
        leftPointer--;
        rightPointer++;
        swap(data, l, leftPointer);
        swap(data, r, rightPointer);
        return new int[] {leftPointer, rightPointer} ;  //gib ein Array mit den neuen Positionen von m1, m2 zurück
    }

    //sortiere Teilarrays
    public static int[] qSort(int[] data, int l, int r) {
        if (l < r) {
            int[] pivot = partition(data, l, r); //bring die Pivots in die richtige Position

            qSort(data, l, pivot[0] - 1);
            qSort(data, pivot[0] + 1, pivot[1] - 1);
            qSort(data, pivot[1] + 1, r );
        }
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

    // sortiert das gesamte Array
    public static int[] qSort(int[] data) {
        qSort(data, 0, data.length - 1);
        return data;
    }


    //
    public static void main(String[] args) { //
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
        if (array.length == 0) {
            System.out.println("the array is empty and then can not be sorted");
            return;
        }

        //vermeide das Worst-Case
        assert (!isSorted(array)) : "Array is sorted";

        Instant start = Instant.now();

        if (array.length < 20 && array.length > 0) {
            System.out.println(Arrays.toString(array));
            qSort(array);
            System.out.println(Arrays.toString(array));
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

        // gerade Länge: Median = Durchschnitt von 2 in der Mitte stehenden Zahlen
        // ungerade Länge: Median = Die Zahl in der Mitte
        if(array.length%2 == 0){
            median = (array[array.length/2] + array[array.length/2-1]) / 2.0;
        } else {
            median = array[array.length/2];
        }

        System.out.printf("Min: %d, Medien: %.1f, Max: %d", min, median, max);

    }

}
