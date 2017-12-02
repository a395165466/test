/**
 * Created by zhangguoqing.zgq on 2017/3/16.
 */
public class Pair<T> {
    private T first;
    private T second;

    public void Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getFirst() {
        return this.first;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    public T getSecond() {
        return this.second;
    }
}
