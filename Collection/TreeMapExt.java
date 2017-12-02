import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;

/**
 * Created by zhangguoqing.zgq on 2017/8/29.
 * TreeMap基于红黑树
 */
public abstract class TreeMapExt<K, V> extends AbstractMap<K, V>
        implements NavigableMap<K, V>, Cloneable, Serializable {

    private transient int size;

    private transient Entry<K, V> root = null;

    private transient int modCount;

    private transient Comparator<? extends K> comparator;

    public TreeMapExt() {
        comparator = null;
        modCount = 0;
        size = 0;
    }

    public TreeMapExt(Comparator<? extends K> comparator) {
        this.comparator = comparator;
        modCount = 0;
        size = 0;
    }

    public TreeMapExt(Map<? extends K, ? extends V> map) {
        this.comparator = null;
        modCount++;
        size = map.size();
        putAll(map);
    }

    public int size() {
        return size;
    }

    public V put(K k, V v) {
        return null;
    }

    public boolean containKey(Object key) {
        return getEntry(key).getKey() != null;
    }

    private Entry<K, V> getEntry(Object key) {
        return null;
    }
}
