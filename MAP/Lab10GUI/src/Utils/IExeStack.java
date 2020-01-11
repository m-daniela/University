package Utils;

public interface IExeStack <T>{
    void push(T x);
    T pop();
    boolean isEmpty();
    Iterable<T> getAll();

}
