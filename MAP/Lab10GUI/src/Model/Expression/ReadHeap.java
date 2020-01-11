package Model.Expression;

import Exceptions.ExceptionCustom;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IHeap;

public class ReadHeap implements Expression {

    Expression exp;

    public ReadHeap(Expression e){
        exp = e;
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Integer, Value> hp) throws ExceptionCustom {

        Value val = exp.eval(table, hp);

        if (!(val instanceof RefValue))
            throw new ExceptionCustom("Not a ref value");

        int rv =  ((RefValue) val).getAddress();

        hp.contains(rv);


        return hp.get(rv);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        Type t = exp.typecheck(typeEnv);
        if (t instanceof RefType){
            RefType ref = (RefType) t;
            return ref.getInner();
        }
        else throw new ExceptionCustom("READ HEAP: argument is not a Ref Type");
    }

    @Override
    public Expression deepcopy() {
        return new ReadHeap(this.exp.deepcopy());
    }

    @Override
    public String toString() {
        return "ReadHeap (" + exp.toString() + ")";
    }


}
