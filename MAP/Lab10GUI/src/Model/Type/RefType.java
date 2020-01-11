package Model.Type;

import Model.Value.RefValue;
import Model.Value.Value;


public class RefType implements Type {

    private Type inner;

    public RefType(Type inner){this.inner = inner;}

    public Type getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof RefType;
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public Type deepcopy() {
        return new RefType(new IntType());
    }

    @Override
    public String toString() {
        return "Ref(" +inner.toString() + ")";
    }
}
