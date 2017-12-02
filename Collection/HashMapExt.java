import java.io.Serializable;
import java.util.*;

/**
 * Created by zhangguoqing.zgq on 2017/8/16.
 */
public class HashMapExt<K, V> extends AbstractMap<K, V>
        implements Map<K, V>, Cloneable, Serializable {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    private static final int MAX_CAPACITY = 30 << 1;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private transient Entry<K, V>[] table;//键值对，Entry<K, V>是一个对象

//    Map<String, Object> map = new HashMap<String, Object>();
//    map.Entry

    private transient int size;

    private transient int modCount;//结构修改次数

    private int threshold;//当map中元素数量达到该数量时进行resize

    public HashMapExt(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("参数错误！");
        }

        if (loadFactor < 0) {
            throw new IllegalArgumentException("参数错误！");
        }

        //初始值为大于initialCapacity的2的最小倍数
        int capacity = 1;

        while (capacity < initialCapacity) {
            capacity =  1 << capacity;
        }

        table = new Entry[capacity];

        threshold = (int) (capacity * loadFactor);
    }

    public HashMapExt(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMapExt() {
        this(DEFAULT_INITIAL_CAPACITY,DEFAULT_LOAD_FACTOR);
    }

    public HashMapExt(Map<? extends K, ? extends V> map) {

    }

    public void clear() {
        modCount++;

        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }

        size = 0;
    }

    public V get(Object key) {
        if (key == null) {
            return getForNullKey(key);
        }

        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (key == entry.getKey() && entry.hash == hash) {
                return entry.getValue();
            }
        }

        return null;
    }

    public Entry<K, V> getEntry(Object key) {
        if (key == null) {
            Entry<K, V> temp = table[0];
            if (temp.getKey() == null) {
                return temp;
            }
        }

        int hash = hash(key);
        int index = indexFor(hash,table.length);
        for (Entry<K, V> entry = table[index]; entry.next != null; entry = entry.next) {
            if (entry.hash == hash && entry.key == key) {
                return entry;
            }
        }

        return null;
    }

    public V getForNullKey(Object key) {
        Entry<K, V> entry = table[0];
        if (entry.getKey() == null) {
            return entry.getValue();
        }

        return null;
    }

    public V put(K k, V v) {
        if (k == null) {
            return putForNullKey(k, v);
        }

        int hash = hash(k);

        int index = indexFor(hash, table.length);
        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.getKey() == k) {
                entry.value = v;
                return entry.value;
            }
        }

        //在table[index]位置对应的链表后面进行添加
        V value = addEntry(hash, k, v, index);
        return value;
    }

    /**
     * 在链表最前面进行添加
     *
     * @param hash
     * @param k
     * @param v
     * @param index
     * @return
     */
    public V addEntry(int hash, K k, V v, int index) {
        //判断是否超出threshold
        if (size > threshold) {
            //调整数量为原有2倍
        }

        Entry<K, V> entry = table[index];

        Entry<K, V> e = new Entry<K, V>(hash, k, v, entry);
        table[index] = e;

        return e.getValue();
    }

    public boolean containKey(Object key) {
        Entry<K, V> entry = getEntry(key);
        return entry != null;
    }

    public boolean containValue(Object value) {
        if (value == null) {
            Entry<K, V> temp = table[0];
            if (temp.getValue() == null) {
                return true;
            }
        }

        for (int i = 0; i < table.length; i++) {
            for (Entry<K, V> entry = table[i]; entry.next != null; entry = entry.next) {
                if (entry.value == value) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     *
     * HashMap的KeySet返回的KeySet是一个可以对key进行遍历的对象
     * 而不仅仅是key的set集合
     *
     * @return
     */
    public Set<K> keySet() {
//        Set<K> keySet = new HashSet<K>();
//        for (int i = 0; i < table.length; i++) {
//            for (Entry<K, V> entry = table[i]; entry != null; entry = entry.next) {
//                keySet.add(entry.getKey());
//            }
//        }
//
//        return keySet;

//        if (keySet != null) {
//            return keySet;
//        }
//
//        keySet = new KeySet();
//        return keySet;

        return new KeySet();
    }

    private class KeySet extends AbstractSet<K> {

        @Override
        public Iterator<K> iterator() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }

    public boolean isEmty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void putAll(Map<? extends K, ? extends V> map) {

    }

    public V remove(Object k) {
        modCount++;
        return null;
    }

    /**
     * NUll Key元素放到table[0] bucket的第一个元素
     *
     * @param k
     * @param v
     * @return
     */
    public V putForNullKey(K k, V v) {
        Entry<K, V> entry = table[0];
        if (entry.getKey() == null) {
            entry.value = v;
            return entry.value;
        }

        return addEntry(0, null, v, 0);
    }

    /**
     * Entry类
     *
     * @param <K>
     * @param <V>
     */
    class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        Entry<K, V> next;
        int hash;

        Entry(int h, K k, V v, Entry<K, V> next) {
            hash = h;
            key = k;
            value = v;

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
    }
    /**
     *计算key的hash值
     *
     * @param k
     * @return
     */
    public final int hash(Object k) {
        int h = 0;
//        if (useAltHashing) {
//            if (k instanceof String) {
//                return sun.misc.Hashing.stringHash32((String) k);
//            }
//            h = hashSeed;
//        }

        h ^= k.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Returns index for hash code h.
     */
    static int indexFor(int h, int length) {
        return h & (length-1);
    }
}
