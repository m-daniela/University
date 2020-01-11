package Utils;

import Exceptions.ExceptionCustom;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface IDictionary<K, V>{
    boolean isDefined(K key);
    V lookupID(K key) throws ExceptionCustom;
    V getValue(K key) throws ExceptionCustom;
//    public boolean isEmpty();
    void setValue(K key, V value);
    void updateValue(K key, V value);
    IDictionary<K, V> deepcopy();
    ConcurrentHashMap<K, V> getContent();
//    void setContent(Map<K, V> newMap);

//    Collection<V> values();
    Iterable<K> keys();
}
