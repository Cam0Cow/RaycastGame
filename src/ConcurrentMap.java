/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.util.*;
import java.util.concurrent.*;

/**
 * Represents a thread-safe hash map that must be occasionally flushed
 */
public class ConcurrentMap<K, V>  {
    
    private ConcurrentLinkedQueue<Pair<K,V>> vals;
    private HashMap<K, V> map;
    
    /**
     * Constructs a new concurrent map
     */
    public ConcurrentMap() {
        map = new HashMap<K, V>();
        vals = new ConcurrentLinkedQueue<Pair<K,V>>();
    }
    
    /**
     * Queues a key-value pair to be placed in the map
     * @param key the key
     * @param val the value
     */
    public void put(K key, V val) {
        vals.offer(new Pair<K,V>(key, val, true));
    }
    
    /**
     * Gets a value that has already been flushed to the map, or null
     * @param key the key
     * @return the value corresponding to the given key
     */
    public V get(K key) {
        return map.get(key);
    }
    
    /**
     * queues a removal from the map
     * @param key the given key
     */
    public void remove(K key) {
        vals.offer(new Pair<K,V>(key, null, false));
    }
    
    /**
     * Flushes the queue and returns the map's key set
     * @return the map's key set
     */
    public Set<K> keySet() {
        flush();
        return map.keySet();
    }
    
    /**
     * Flushes the latest updates to the map
     */
    public void flush() {
        while (!vals.isEmpty()) {
            Pair<K, V> p = vals.poll();
            if (p.isPut()) {
                map.put(p.getKey(), p.getVal());
            } else {
                map.remove(p.getKey());
            }
        }
    }
    
    /**
     * Represents a key-value pair to be added or removed
     */
    private static class Pair<K,V> {
        
        private K k;
        private V v;
        private boolean put;
        
        /**
         * Constructs a new key-value pair
         * @param k the key
         * @param v the value
         * @param p whether pair is being added or removed
         */
        public Pair(K k, V v, boolean p) {
            this.k = k;
            this.v = v;
            put = p;
        }
        
        /**
         * Returns the key
         * @return the key
         */
        public K getKey() {
            return k;
        }
        
        /**
         * Returns the value
         * @return the value
         */
        public V getVal() {
            return v;
        }
        
        /**
         * The pair is being added if true, and removed otherwise
         * @return whether the pair is being removed or added
         */
        public boolean isPut() {
            return put;
        }
    }
}
