package Utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Heap<K, V> implements IHeap<K, V> {

    private ConcurrentHashMap<K, V> heap;

    public Heap(){
        heap = new ConcurrentHashMap<>();
    }

    @Override
    public void add(K key, V value) {
        heap.put(key, value);
    }

    @Override
    public boolean contains(K key) {
        return heap.containsKey(key);
    }

    @Override
    public void update(K key, V value) {
        heap.put(key, value);
    }

    @Override
    public V get(K key) {
        return heap.get(key);
    }

    @Override
    public Iterable<K> getAll() {
        return heap.keySet();
    }

    @Override
    public void setContent(Map<K, V> m) {
        heap = (ConcurrentHashMap<K, V>) m;

    }

    @Override
    public ConcurrentHashMap<K, V> getContent() {
        ConcurrentHashMap<K, V> m;
        m = this.heap;
        return m;
    }


    @Override
    public String toString() {
        String str = "";
        for (ConcurrentHashMap.Entry<K, V> e: heap.entrySet())
            str += "<"+e.getKey() + "->" + e.getValue() + ">\n";
        return str;
    }
}
