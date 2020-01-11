package Utils;

public class HeapAddr {
    private int id;
    public HeapAddr(){
        id = 1;
    }
    public int getId(){
        this.id += 1;
        return this.id-1;
    }
}
