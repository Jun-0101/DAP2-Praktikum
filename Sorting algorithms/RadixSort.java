import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class RadixSort {
    public static void main(String[] args) {
        int[] data = readInput();
        if (data.length > 0) {
            RadixSort obj_lsd = new RadixSort(data);
            RadixSort obj_msd = new RadixSort(data);

            System.out.println("Before sorting: " + Arrays.toString(data));

            // LSD-Radixsort
            long startTime = System.nanoTime();
            obj_lsd.msdRadix(0, data.length - 1, 3);
            long endTime = System.nanoTime();
            System.out.println("Sort after LSD: " + Arrays.toString(data));
            System.out.println("LSD took: " + (endTime - startTime));

            // MSD-Radixsort
            startTime = System.nanoTime();
            obj_msd.msdRadix(0, data.length - 1, 3);
            endTime = System.nanoTime();
            System.out.println("Sort after MSD: " + Arrays.toString(data));
            System.out.println("MSD took: " + (endTime - startTime));

        } else {
            System.out.println("Not enough input values");
        }

    }

    public static int[] readInput() throws NumberFormatException {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList<>();
        while (sc.hasNextLine()) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                list.add(value);
            } catch (NumberFormatException e) {
                System.out.println("Input is not a number");
            }
        }
        //ArrayList => Array
        int[] data = new int[list.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = list.get(i);
        }
        return data;
    }

    public int[] data;

    public RadixSort(int[] data){
        this.data = data;
    }

    //Konzept wie Counting-Sort
    public void sortByByte ( int l, int r, int b) {
        int[] frequencies = new int[256]; //zähle die Häufigkeit jedes Wertes
        int[] output = new int[r - l + 1]; //speichere die sortierten Elemente

        //zähle die Häufigkeit der Werte im b-niederwertigsten Byte
        for (int i = l; i <= r; i++) {
            int wert = (data[i] >> (8 * b)) & 0xFF; //berechne b-te Byte von a
            frequencies[wert]++;
        }

        //berechne die laufende Summe der Frequenzen
        for (int i = 1; i < frequencies.length; i++) {
            frequencies[i] += frequencies[i-1];
        }

        //platziere die Elemente in der aufsteigende Reihenfolge
        for (int i = l; i <= r; i++) {
            int wert = (data[i] >> (8 * b)) & 0xFF;
            output[frequencies[wert]-1] = data[i]; //speichere in Output entsprechende Werte
            frequencies[wert]--;
        }

        // kopiere die Elemente in der Richtung von Ende zum Anfang des arrays output zurück in data
        int index = l;
        for (int i = output.length - 1; i >= 0; i--) {
            data[index] = output[i];
            index++;
        }
    }


    public void lsdRadix() {
        // sortiere die Zahlen nach dem b-niederwertigsten Wert
        for (int b = 0; b <= 3 ; b++) {
            sortByByte(0, data.length - 1, b);
        }
    }

    public void msdRadix(int l, int r, int b) {
        if ((r - l + 1) <= 32) {
            insertionSort(0, data.length - 1);
        } else {

            int[] counts = new int[257];

            for (int i = l; i <= r; i++) {
                int wert = (data[i] >> (8 * b)) & 0xFF;
                counts[wert]++;
            }

            // Berechne die laufende Summe der Frequenzen
            for (int i = 1; i < counts.length; i++) {
                counts[i] += counts[i-1];
            }

            // betrachte alle Intervallen aus dem Array, in jeder die Elemente jeweils das gleiche b-niederwertigste Byte haben.
            for (int i = 0; i < counts.length - 1; i++) {
                l = counts[i];
                r = counts[i+1] - 1;

                if (l <= r){
                    sortByByte(l, r, b);
                    msdRadix(l, r, b--);
                }
            }
        }
    }


    private void insertionSort(int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int key = data[i];
            int j = i-1;

            // verschiebe die Elemente, die größer als key ist, um eine Position nach rechts
            while(j >= 0 && data[j] < key){
                data[j+1] = data[j];
                j--;
            }

            // fügre das Key in der richtigen position ein
            data[j+1] = key;
        }
    }

}
