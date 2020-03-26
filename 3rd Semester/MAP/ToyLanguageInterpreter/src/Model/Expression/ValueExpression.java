package Model.Expression;

import Exceptions.ExceptionCustom;
import Model.Expression.Expression;
import Model.Type.Type;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IHeap;

public class ValueExpression implements Expression {
    private Value e;
    public ValueExpression(Value x){
        e = x;
    }
    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Integer, Value> hp) throws ExceptionCustom {
        return e;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        return e.getType();
    }

    @Override
    public Expression deepcopy() {
        return new ValueExpression(this.e.deepcopy());
    }

    @Override
    public String toString() {
        return "" + e;
    }
}