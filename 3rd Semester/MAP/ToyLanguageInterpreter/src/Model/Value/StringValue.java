package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value {

    private String val;
    public StringValue(String v){
        val = v;
    }

    public boolean equals(Object another){
        return another instanceof StringValue;
    }


    public String getVal(){
        return val;
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepcopy() {
        return new StringValue(this.val);
    }
}
