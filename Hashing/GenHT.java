import java.util.ArrayList;

interface SimpleHashFunction<K> {
    public int getHash(K key, int m);
}

public class GenHT<K, V> {
    public int size;
    public ArrayList<ArrayList<Pair<K, V> >> data;
    public SimpleHashFunction<K> hashFunction;

    //Anzahl der Elemente in HT
    public GenHT(int capacity) {
        size = 0;
        data = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            this.data.add(new ArrayList<>());
        }

        hashFunction = (key, m) -> Math.floorMod(key.hashCode(), m);
    }
    //Innere Klasse: Paar von Key und Value in HT speichern
    public static class Pair<K, V> {
        K key;
        V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;

        }
    }
    //gibt aus, welche List zuständig ist
    //Hashfunktion: berechne den Index in der ArrayList
    public int addressOf(K key) {
        if (data.size() > 0) {
            return hashFunction.getHash(key, data.size());
        }
        throw new IllegalArgumentException();
    }

    //füge Paar ein
    public void insert(K key, V value) {
        if (data.size() == 0) {
            data.add(new ArrayList<>());
            data.get(0).add(new Pair<K, V>(key, value));
        } else {
            int index = addressOf(key);
            ArrayList<Pair<K, V>> list = data.get(index);

            // prüft, ob ein Paar mit gleichem key bereits existiert
            for (Pair<K, V> pair : list) {
                if (pair.key.equals(key)) {
                    // der Wert (value) wird überschrieben
                    pair.value = value;
                    return;
                }
            }
            // sonst, der Wert wird in der ArrayList hinzugefügt
            list.add(new Pair<>(key, value));
            size++;
        }
    }

    //gebe Wert zurück
    public V get(K key) {
        int index = addressOf(key);
        ArrayList<Pair<K, V> > list = data.get(index);

        for (Pair<K, V>  pair : list) {
            if (pair.key.equals(key)) {
                return pair.value;
            }
        }
        return null; // Key nicht gefunden
    }


    //entferne Paar
    public boolean remove(K key) {
        int index = addressOf(key);
        ArrayList<Pair<K, V>> list = data.get(index);

        for (Pair pair : list) {
            if (pair.key.equals(key)) {
                list.remove(pair);
                size--;
                return true;
            }
        }
        return false; // Key nicht gefunden
    }
}