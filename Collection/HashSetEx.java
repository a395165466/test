import java.io.Serializable;
import java.util.*;

/**
 * Created by zhangguoqing.zgq on 2017/8/29.
 */
public class HashSetEx<E> extends AbstractSet<E> implements Set<E>, Cloneable, Serializable {

    private transient HashMap<E, Object> hashMap;

    private final transient Object valueObject = new Object();

    public HashSetEx(int initialCapacity, float loadFactor) {
        hashMap = new HashMap<E, Object>(initialCapacity, loadFactor);
    }

    public HashSetEx(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public HashSetEx() {
        this(16, 0.75f);
    }

    public HashSetEx(Collection<? extends E> c) {
        hashMap = new HashMap<E, Object>((int) (c.size() / 0.75f) + 1);
        addAll(c);
    }

    public boolean add(E e) {
        hashMap.put(e, valueObject);
        return true;
    }

    public void clear() {
        hashMap.clear();
    }

    public boolean contain(Object o) {
        return hashMap.containsKey(o);
    }

    public boolean isEmpty() {
        return hashMap.isEmpty();
    }

    public boolean remove(Object o) {
        return hashMap.remove(o) == valueObject;
    }

    @Override
    public Iterator<E> iterator() {
        return hashMap.keySet().iterator();
    }

    @Override
    public int size() {
        return hashMap.size();
    }
}
