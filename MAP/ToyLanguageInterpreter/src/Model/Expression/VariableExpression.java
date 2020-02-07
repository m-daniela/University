package Model.Expression;

import Exceptions.ExceptionCustom;
import Model.Type.Type;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IHeap;

public class VariableExpression implements Expression {
    String id;

    public VariableExpression(String new_id){
        id = new_id;
    }


    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Integer, Value> hp) throws ExceptionCustom {
        return table.lookupID(id);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        return typeEnv.lookupID(id);
    }

    @Override
    public Expression deepcopy() {
        return new VariableExpression(this.id);
    }

    @Override
    public String toString() {
        return id;
    }
}
