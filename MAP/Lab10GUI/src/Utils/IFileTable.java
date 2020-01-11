package Utils;

import Model.Value.StringValue;

import java.io.BufferedReader;
import java.util.ArrayList;

public interface IFileTable<K, V> {
    void add(K key, V val);
    void delete(K key);
    V get(K key);
    boolean contains(K key);
    Iterable<K> getAllKeys();
}
