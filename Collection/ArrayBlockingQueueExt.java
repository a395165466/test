import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhangguoqing.zgq on 2017/8/31.
 */
public class ArrayBlockingQueueExt<E> extends AbstractQueue<E>
        implements BlockingQueue<E>, java.io.Serializable {

    private Object[] items;

    private int count;

    private int takeIndex;

    private int putIndex;

    private ReentrantLock lock;

    private Condition notFull;

    private Condition notEmpty;

    public ArrayBlockingQueueExt(int capacity) {
        this(capacity, false);
    }

    public ArrayBlockingQueueExt(int capacity, boolean fair) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }

        this.items = new Object[capacity];
        this.lock = new ReentrantLock(fair);
        this.notFull = lock.newCondition();
        this.notEmpty = lock.newCondition();

        count = 0;
    }

    public ArrayBlockingQueueExt(int capacity, boolean fair, Collection<? extends E> c) {
        this(capacity, fair);

        this.lock.lock();//对该阻塞队列的操作都要加锁

        try{
            for (E e : c) {
                if (e != null) {
                    items[count++] = e;
                }
            }

            putIndex = count;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return count;
    }

    /**
     * 往队列中添加元素（阻塞）
     *
     * @param e
     * @throws InterruptedException
     */
    @Override
    public void put(E e) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }

        this.lock.lock();

        try {
            items[putIndex++] = e;
            count++;

            notEmpty.signalAll();

            if (items.length == count) {
                notFull.await();
            }
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * 从队列中获取元素（阻塞）
     *
     * @return
     * @throws InterruptedException
     */
    @Override
    public E take() throws InterruptedException {
        this.lock.lock();

        E element;
        try{
            while (count == 0) {
                notFull.await();
                notEmpty.signalAll();
            }

            element = (E) items[takeIndex--];
            count--;

        } finally {
            lock.unlock();
        }

        return element;
    }

    /**
     * （添加元素）非阻塞
     *
     * @param e
     * @return
     */
    @Override
    public boolean offer(E e) {
        if (e == null) {
            return false;
        }
        this.lock.lock();

        try {
            if (count == items.length) {
                return false;
            }

            items[putIndex++] = e;
            notEmpty.signalAll();
            return true;

        } finally {
            this.lock.unlock();
        }
    }

    /**
     * 从队列中取元素（非阻塞）
     *
     * @return
     */
    @Override
    public E poll() {
        if (count == 0) {
            return null;
        }

        this.lock.lock();
        E element;
        try {
            element = (E) items[takeIndex--];
            count--;
            return element;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        return 0;
    }

    @Override
    public E peek() {
        return null;
    }
}
