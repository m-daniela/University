package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value {
    private boolean val;
    public BoolValue(boolean v){val = v;}
    public boolean equals(Object another){
        return another instanceof BoolValue;
    }

    public boolean getVal() {
        return val;
    }

    public String toString(){
        return "" + val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepcopy() {
        return new BoolValue(this.val);
    }
}
