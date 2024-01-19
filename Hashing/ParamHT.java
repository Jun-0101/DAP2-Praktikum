import java.util.ArrayList;
interface SimpleHashFunction<K> {
    public int getHash(K key, int m);
}

public class ParamHT<K, V> {
    
    //Anzahl der Elemente in HT
    public int size;
    public ArrayList<ArrayList<Pair<K,V>>> data;
    public SimpleHashFunction<K> hashFunction;

    public ParamHT(int capacity) {
        //Verwende die Lambda Funktion, um den Konstruktor mit 2 Parametern aufzurufen
        //Hier verwende die typische Hashfunktion mit dem hashCode() für den Datentyp K
        this(capacity, (key, m) -> Math.floorMod(key.hashCode(), m));
    }

    public ParamHT(int capacity, SimpleHashFunction<K> hashFunction) {
        this.size = 0;
        this.data = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            this.data.add(new ArrayList<>());
        }
        //mache die zu verwendende Hashfunktion sichtbar
        this.hashFunction = hashFunction;
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

    //Hashfunktion: berechne den Index in der ArrayList
    public int addressOf(K key) throws IndexOutOfBoundsException {
        int hash = hashFunction.getHash(key, data.size());

        if (hash < 0 || hash >= data.size()) {
            throw new IndexOutOfBoundsException("Fehler");
        }
        return hash;
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
        return null; //Key nicht gefunden
    }

    //entferne Paar
    public boolean remove(K key) {
        int index = addressOf(key);
        ArrayList<Pair<K, V> > list = data.get(index);

        for (Pair<K, V>  pair : list) {
            if (pair.key.equals(key)) {
                list.remove(pair);
                size--;
                return true;
            }
        }
        return false; //Key nicht gefunden
    }
}
