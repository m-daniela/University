package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type {
    int i;
    public boolean equals(Object another){
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public Type deepcopy() {
        return new IntType();
    }
}