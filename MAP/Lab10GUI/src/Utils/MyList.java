package Utils;

import java.util.ArrayList;

public class MyList<T> implements IList<T> {

    ArrayList<T> l;
    public MyList(){
        l = new ArrayList<T>();
    }

    @Override
    public void add(T x) {
        l.add(x);
    }

    @Override
    public ArrayList<T> getAll() {
        return l;
    }

    @Override
    public String toString(){
        String str = "";

        for(T i: l)
            str = str + i.toString() + ";\n";


        return str;
    }
}