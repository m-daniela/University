package Model.Expression;

import Exceptions.ExceptionCustom;
import Model.Type.Type;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IHeap;

public interface Expression {
    Value eval(IDictionary<String, Value> table, IHeap<Integer, Value> hp) throws ExceptionCustom;

    Type typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom;

    Expression deepcopy();
}
