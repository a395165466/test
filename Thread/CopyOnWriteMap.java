
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangguoqing.zgq on 2017/6/19.
 */
public abstract class CopyOnWriteMap<K, V> implements Map<K, V>, Cloneable, Serializable {
    private Map<K, V> map;

    public V put(K key, V value) {
        synchronized (this) {
            Map<K, V> temp = new HashMap<K, V>(map);
            temp.put(key, value);
            map = temp;

            return value;
        }
    }

    public V get(Object key) {
        return map.get(key);
    }

    public V remove(Object key) {
        return map.remove(key);
    }

    public Collection<V>  values() {
        return null;
    }

    public boolean isEmpty() {
        return true;
    }
}

