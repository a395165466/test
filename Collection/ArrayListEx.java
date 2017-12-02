import java.io.Serializable;
import java.util.*;

/**
 * Created by zhangguoqing.zgq on 2017/8/8.
 * 简单重写写ArrayList类，实现基本功能
 */
public class ArrayListEx<E> extends AbstractList<E> implements Serializable, Cloneable {

    private static final int MAX_CAPACITY = Integer.MAX_VALUE - 8;

    private transient Object[] elementData;

    private int size;

    public ArrayListEx() {
        this(10);
    }

    public ArrayListEx(int initCapacity) {
        super();
        elementData = new Object[initCapacity];
    }

    public E get(int i) {
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException("index i error!");
        }

        return (E) elementData[i];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 将元素添加的list尾部
     * @param element
     * @return
     */
    public boolean add(E element) {
        ensureCapacityInternal(size + 1);

        elementData[size++] = element;

        return true;
    }

    /**
     *将元素添加到指定位置
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index i error!");
        }

        ensureCapacityInternal(size + 1);

        System.arraycopy(elementData, index, elementData, index + 1, size - index);

        elementData[index] = element;
        size++;
    }

    /**
     * 将元素集合添加到list的指定位置
     * @param index
     * @param c
     * @return
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index i error!");
        }

        Object arr[] = c.toArray();

        int length = c.size();

        ensureCapacityInternal(size + length);

        System.arraycopy(elementData, index, elementData, index + length, size - index);

        System.arraycopy(arr, 0, elementData, index, length);

        return true;
    }

    /**
     * 移除表中所有元素
     */
    public void clear() {
        elementData = null;
        modCount++;
    }

    /**
     * 返回此list的拷贝
     * @return
     */
    public Object clone() {
        try {
            ArrayListEx<E> copy = (ArrayListEx<E>) super.clone();
            copy.elementData = Arrays.copyOf(elementData, size);

            return copy;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean contains(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return true;                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    return true;
                }
            }
        }

        return false;
    }

    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    public E remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index error!");
        }

        E element = (E) elementData[index];

        System.arraycopy(elementData, index+1, elementData, index, size-index);

        return element;
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean addAll(Collection<? extends E> c) {
        Object ob[] = c.toArray();
        int length = ob.length;

        ensureCapacityInternal(size + length);

        System.arraycopy(ob, 0, elementData, size, length);

        size = size + length;

        return true;
    }

    public void ensureCapacityInternal(int capacity) {
        if (elementData.length < capacity) {//需要扩容
            grow(capacity);
        }
    }

    public void grow(int capacity) {
        if (capacity > elementData.length) {
            int newCapacity = elementData.length + elementData.length >> 1;
            if (capacity > newCapacity) {
                newCapacity = capacity;
            }

            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    public Iterator<E> iterator() {
        return new Iter();
    }

    class Iter implements Iterator<E> {

        int cursor;//当前指针
        int lastRet = -1;//上次返回指针

        int expectModidyCount;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            if (cursor >= size) {
                throw new IndexOutOfBoundsException();
            }

            checkForModification();

            E element = (E) elementData[cursor++];

            lastRet = cursor;

            return element;
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalArgumentException();
            }

            checkForModification();

            ArrayListEx.this.remove(lastRet);

            cursor = lastRet;

            lastRet = -1;

            expectModidyCount = modCount;
        }

        public void checkForModification() {
            if (modCount != expectModidyCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
