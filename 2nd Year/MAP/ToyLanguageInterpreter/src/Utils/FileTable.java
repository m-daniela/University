package Utils;

import Utils.IFileTable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class FileTable<K, V> implements IFileTable<K, V> {

    private ConcurrentHashMap<K, V> file_dict;

    public FileTable(){
        file_dict = new ConcurrentHashMap<>();
    }

    @Override
    public void add(K key, V val) {
        file_dict.put(key, val);
    }

    @Override
    public void delete(K key) {
        file_dict.remove(key);
    }

    @Override
    public V get(K key) {
        return file_dict.get(key);
    }

    @Override
    public boolean contains(K key) {
        return file_dict.containsKey(key);
    }

    @Override
    public Iterable<K> getAllKeys() {
        return file_dict.keySet();
    }

    @Override
    public String toString() {
        String str = "";
        for(ConcurrentHashMap.Entry<K, V> e : file_dict.entrySet()){
            str = str + "<"+e.getKey() + ", " + e.getValue() + ">\n";
        }
        return str;
    }
}
