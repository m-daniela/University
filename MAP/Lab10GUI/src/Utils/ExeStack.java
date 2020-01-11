package Utils;

import java.util.Stack;

public class ExeStack<T> implements IExeStack<T> {

    private Stack<T> stack = new Stack<T>();

    @Override
    public void push(T x) {
        stack.push(x);
    }

    @Override
    public T pop() {

        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.size() == 0;
    }

    @Override
    public Iterable<T> getAll() {
        return stack;
    }

    @Override
    public String toString(){
        String str = "";
        for(T i : stack){
            str = i.toString() + "\n" + str + "\n";
        }
        return str;
    }
}