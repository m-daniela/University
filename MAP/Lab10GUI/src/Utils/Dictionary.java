package Utils;


import Exceptions.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Dictionary <K, V> implements IDictionary<K, V> {

    private ConcurrentHashMap<K, V> dict;

    public Dictionary(){
        dict = new ConcurrentHashMap<K, V>();
    }

    @Override
    public boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public V lookupID(K key) throws ExceptionCustom {
        if (dict.containsKey(key))
            return dict.get(key);
        else throw new ExceptionCustom("Val not defined\n");
    }

    @Override
    public V getValue(K key) throws ExceptionCustom {

        if(!isDefined(key))
            throw new ExceptionCustom("Variable not defined in dictionary");
        return dict.get(key);
    }

//    @Override
//    public boolean isEmpty() {
//        return dict.isEmpty();
//    }

    @Override
    public void setValue(K key, V value) {
        dict.put(key, value);
    }

    @Override
    public void updateValue(K key, V value) {
        dict.put(key, value);
    }

    @Override
    public IDictionary<K, V> deepcopy() {
        IDictionary<K, V> newDict = new Dictionary<K, V>();
        for (K key: dict.keySet())
            newDict.setValue(key, dict.get(key));

        return newDict;
    }

    @Override
    public ConcurrentHashMap<K, V> getContent() {
        ConcurrentHashMap<K, V> map;
        map = this.dict;
        return map;
    }
//
//    @Override
//    public void setContent(Map<K, V> newMap) {
//        this.dict = (HashMap<K, V>) newMap;
//    }
//
//    @Override
//    public Collection<V> values() {
//        return dict.values();
//    }

    @Override
    public Iterable<K> keys() {
        return dict.keySet();
    }

    @Override
    public String toString(){
        String str = "";
        for(ConcurrentHashMap.Entry<K, V> e : dict.entrySet()){
            str += "<"+e.getKey() + ", " + e.getValue() + ">\n";
        }
        return str;
    }


}