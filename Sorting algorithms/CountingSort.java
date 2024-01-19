import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CountingSort {
    public static void main(String[] args) {
        int[] data = readInput();
        if (data.length <= 0) return;
        //int[] data = {7, 5, 4, 5, 9, 1, 3, 1, 2};
        CountingSort list = new CountingSort(data);
        System.out.println(Arrays.toString(data));
        System.out.println(Arrays.toString(list.count()));
        System.out.println(Arrays.toString(list.countingSort()));
    }

    public static int[] readInput() throws NumberFormatException {
        int t, i = 0;
        ArrayList<Integer> a = new ArrayList<>();
        Scanner sc1 = new Scanner(System.in);
        while (sc1.hasNextLine()) {
            try {
                t = Integer.parseInt(sc1.nextLine());
                i++;
                a.ensureCapacity(i);
                a.add(i - 1, t);
            } catch (NumberFormatException e) {
                System.out.print("Input hat eine non-Integer!");
                sc1.close();
                int[] empty = new int[0];
                return empty;
            }
        }
        sc1.close();
        int[] c = new int[a.size()];
        for (int j = 0; j < i; j++) {
            c[j] = a.get(j);
        }
        return c;
    }

    public int[] data;

    public CountingSort(int[] data) {
        this.data = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = data[i];
        }
    }

    public int getMin() {
        int t = data[0];
        for (int i = 0; i < data.length; i++) {
            if (data[i] < t) t = data[i];
        }
        return t;
    }

    public int getMax() {
        int t = data[0];
        for (int i = 0; i < data.length; i++) {
            if (data[i] > t) t = data[i];
        }
        return t;
    }

    public int[] count() {
        int[] re = new int[this.getMax() - this.getMin() + 1];
        for (int i = 0; i < re.length; i++) {
            re[i] = 0;
        }
        for (int j = 0; j < this.data.length; j++) {
            re[this.data[j] - this.getMin()]++;
        }
        return re;
    }

    public int[] countingSort() {
        int[] f = this.count();
        int min = this.getMin();
        int indexf = f.length - 1;
        for (int i = 0; i < this.data.length; i++) {
            while (f[indexf] == 0) {
                indexf--;
            }
            this.data[i] = min + indexf;
            f[indexf]--;
        }
        return this.count();
    }
}


