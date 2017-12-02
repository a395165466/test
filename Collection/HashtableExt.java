import java.io.Serializable;
import java.util.*;

/**
 * HashTable总体结构跟HashMap挺像的，只不过HashTable继承自Dictory<K, V>类，
 * HashMap继承自AbstractMap<K, V>类。
 * Created by zhangguoqing.zgq on 2017/8/25.
 */
public class HashtableExt<K, V> extends Dictionary<K, V> implements Map<K, V>, Serializable {

    private transient Entry<K, V>[] table;

    private transient int size;

    private transient float loadFactor;

    private transient int threshold;

    private transient int modCount;

    private transient boolean useAltHashing;

    public HashtableExt(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException();
        }

        if (loadFactor < 0) {
            throw new IllegalArgumentException();
        }

        table = new Entry[initialCapacity];

        size = 0;

        this.loadFactor = loadFactor;
    }

    public HashtableExt(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public HashtableExt() {
        this(11, 0.75f);
    }

    @Override
    public synchronized int size() {
        return size;
    }

    @Override
    public synchronized boolean isEmpty() {
        return size == 0;
    }


    @Override
    public synchronized boolean containsKey(Object key) {
        return false;
    }

    @Override
    public synchronized boolean containsValue(Object value) {
        return false;
    }

    @Override
    public synchronized Enumeration<K> keys() {
        return null;
    }

    @Override
    public Enumeration<V> elements() {
        return null;
    }

    @Override
    public synchronized V get(Object key) {
        if (key == null) {
            return null;
        }

        int hash = hash(key);
        int index = indexFor(hash, size);
        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.getKey() == key) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public synchronized V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }

        if (value == null) {
            throw new NullPointerException();
        }

        int hash = hash(key);
        int index = indexFor(hash, size);
        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.getKey() == key) {
                entry.setValue(value);
            }
        }

        Entry<K, V> temp = new Entry<K, V>(hash, key, value, table[index]);
        table[index] = temp;

        modCount++;

        return value;
    }

    @Override
    public synchronized V remove(Object key) {
        return null;
    }

    @Override
    public synchronized void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            table[i] = null;
        }

        modCount++;

        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }

    private int hash(Object k) {
        if (useAltHashing) {
            if (k.getClass() == String.class) {
                return sun.misc.Hashing.stringHash32((String) k);
            } else {
//                int h = hashSeed ^ k.hashCode();

                // This function ensures that hashCodes that differ only by
                // constant multiples at each bit position have a bounded
                // number of collisions (approximately 8 at default load factor).
//                h ^= (h >>> 20) ^ (h >>> 12);
//                return h ^ (h >>> 7) ^ (h >>> 4);
                return 0;
            }
        } else  {
            return k.hashCode();
        }
    }

    private int indexFor(int hash, int length) {
        return (hash & 0x7FFFFFFF) % length;
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {

        private int hash;

        private K key;

        private V value;

        private Entry<K, V> next;

        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return this.value;
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }


}
