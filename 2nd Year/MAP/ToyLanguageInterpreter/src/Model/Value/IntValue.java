package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

public class IntValue implements Value {
    private int val;
    public IntValue(int v){val = v;}

    public int getVal() {
        return val;
    }

    public boolean equals(Object another){
        return another instanceof IntValue;
    }

    public String toString(){
        return "" + val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepcopy() {
        return new IntValue(this.val);
    }
}