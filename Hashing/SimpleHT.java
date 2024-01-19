import java.util.ArrayList;

public class SimpleHT {

    //Innere Klasse: Paar von Key und Value in HT speichern
    public static class Pair {
        Integer key;
        Integer value;

        public Pair(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    //Anzahl der Elemente in HT
    public int size;

    //data: ArrayList von ArrayLists
    public ArrayList<ArrayList<Pair>> data;


    //Constructor: initialisiere die HT mit Kapazität
    public SimpleHT(int capacity) {
        size = 0;
        if (capacity < 0) {
            throw new IllegalStateException("not a valid capacity");
        }
        data = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            data.add(new ArrayList<>());
        }
    }

    //gibt aus, welche List zuständig ist
    //Hashfunktion: berechne den Index in der ArrayList
    public int addressOf(Integer key) {
        if (data.size() > 0) {
            return Math.floorMod(key, data.size());
        }
        throw new IllegalArgumentException();
    }

    //füge Paar ein
    public void insert(Integer key, Integer value) {
        if (data.size() == 0) {  //Falls ArrayList leer ist, füge dieses als erstes Element
            data.add(new ArrayList<>());
            data.get(0).add(new Pair(key, value));
        } else {
            int index = addressOf(key);
            ArrayList<Pair> list = data.get(index);

            // prüft, ob ein Paar mit gleichem key bereits existiert
            for (Pair pair : list) {
                if (pair.key.equals(key)) {
                    // der Wert (value) wird überschrieben
                    pair.value = value;
                    return;
                }
            }
            // sonst, das Paar wird in der ArrayList hinzugefügt
            list.add(new Pair(key, value));
            size++;
        }
    }

    //gebe Wert zurück
    public Integer get(Integer key) {
        int index = addressOf(key);
        ArrayList<Pair> list = data.get(index);

        for (Pair pair : list) {
            if (pair.key.equals(key)) {
                return pair.value;
            }
        }
        return null; // Key nicht gefunden
    }

    //entferne Paar
    public boolean remove(Integer key) {
        int index = addressOf(key);
        ArrayList<Pair> list = data.get(index);

        for (Pair pair : list) {
            if (pair.key.equals(key)) {
                list.remove(pair);
                size--;
                return true;
            }
        }
        return false; //Key nicht gefunden
    }
}
