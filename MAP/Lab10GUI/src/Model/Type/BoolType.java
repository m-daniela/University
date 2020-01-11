package Model.Type;

import Model.Value.BoolValue;
import Model.Value.Value;

public class BoolType implements Type {

    Boolean b;

    public boolean equals(Object another){
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type deepcopy() {
        return new BoolType();
    }
}