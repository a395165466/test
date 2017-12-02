import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

/**
 * Created by zhangguoqing.zgq on 2017/8/15.
 */
public abstract class LinkedListEx<E> extends AbstractSequentialList<E> implements List<E>, Deque<E>, Cloneable, Serializable {
    transient int size = 0;

    transient Node<E> first;

    transient Node<E> last;

    public LinkedListEx() {

    }

    public LinkedListEx(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    /**
     * 添加到list尾部
     * @param e
     * @return
     */
    public boolean add(E e) {
        Node<E> node = new Node<E>(last, e, null);
        last = node;

        if (last == null) {
            first = node;
        } else {
            last.next = node;
        }

        size++;
        modCount++;

        return true;
    }

    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> currentNode = new Node<E>(null, e, null);
        //添加到index对应的元素前面
        Node<E> node = getNode(index);
        currentNode.next = node;

        node.prev.next = currentNode;

        size++;
        modCount++;
    }

    //获取index对应的node元素
    private Node<E> getNode(int index) {
        if (index == 0) {
            return first;
        }

        if (index == size) {
            return last;
        }

        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        return node;
    }

    public boolean addAll(Collection<? extends E> c) {
        return true;
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        return true;
    }

    public void addFirst(E e) {
        Node<E> node = new Node<E>(null, e, null);
        if (first == null) {
            first = node;
            last = node;
        }

        node.next = first;
        first = node;
    }

    public void addLast(E e) {

    }

    public void clear() {

    }

    public Object clone() {
        return null;
    }

    public boolean contain(Object o) {
        return true;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> node = getNode(index);
        return node.item;
    }

    public E getFirst() {
        return first.item;
    }

    public E getLast() {
        return last.item;
    }

    public int size() {
        return size;
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

}
