package Utils;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface IHeap<K, V> {
    void add(K key, V value);
    boolean contains(K key);
    void update(K key, V value);
    V get(K key);
    Iterable<K> getAll();
    void setContent(Map<K,V> m);
    ConcurrentHashMap<K, V> getContent();
}
