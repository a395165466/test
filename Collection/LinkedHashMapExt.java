import java.util.*;

/**
 * Created by zhangguoqing.zgq on 2017/8/28.
 */
public class LinkedHashMapExt<K, V> extends HashMap<K, V> implements Map<K, V> {

    //双向循环链表的头结点
    private transient Entry<K, V> header;

    //访问顺序，false代表按照插入顺序
    private final boolean accessOrder;

    public LinkedHashMapExt(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        accessOrder = false;
    }

    public LinkedHashMapExt(int initialCapacity) {
        this(initialCapacity, 0.75f);
//        super(initialCapacity);
//        accessOrder = false;
    }

    public LinkedHashMapExt() {
        this(16, 0.75f);
//        super();
//        accessOrder = false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    /**
     * entry的数据结构，多了两个指向前后节点的引用，这样的话会记录entry的前后关系.
     */
    private static abstract class Entry<K,V> implements Map.Entry<K,V> {
        // These fields comprise the doubly linked list used for iteration.
        Entry<K,V> before, after;

        Entry(int hash, K key, V value, Map.Entry<K,V> next) {
//            super(hash, key, value, next);
        }

        /**
         * Removes this entry from the linked list.
         */
        private void remove() {
            before.after = after;
            after.before = before;
        }

        /**
         * Inserts this entry before the specified existing entry in the list.
         */
        private void addBefore(Entry<K,V> existingEntry) {
            after  = existingEntry;
            before = existingEntry.before;
            before.after = this;
            after.before = this;
        }

        /**
         * This method is invoked by the superclass whenever the value
         * of a pre-existing entry is read by Map.get or modified by Map.set.
         * If the enclosing Map is access-ordered, it moves the entry
         * to the end of the list; otherwise, it does nothing.
         */
        void recordAccess(HashMap<K,V> m) {
            LinkedHashMap<K,V> lm = (LinkedHashMap<K,V>)m;
//            if (lm.accessOrder) {
//                lm.modCount++;
//                remove();
//                addBefore(lm.header);
//            }
        }

        void recordRemoval(HashMap<K,V> m) {
            remove();
        }
    }

    private class keyInterator extends LinkedHashIterator<K> {

    }

    private class LinkedHashIterator<K> implements Iterator<K> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public K next() {
            return null;
        }

        @Override
        public void remove() {

        }
    }

}
