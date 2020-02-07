package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value{
    private int address;
    private Type locationType;

    public RefValue(int address, Type locationType){
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof RefValue;
    }

    public Type getLocationType() {
        return locationType;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public Type getType() {

        return new RefType(locationType);
    }

    @Override
    public Value deepcopy() {
        return new RefValue(this.address, this.locationType.deepcopy());
    }

    @Override
    public String toString() {
        return "("+ address + ", " + locationType.toString() + ")";
    }
}
