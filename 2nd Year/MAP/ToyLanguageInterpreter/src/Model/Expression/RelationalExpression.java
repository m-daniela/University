package Model.Expression;

import Exceptions.ExceptionCustom;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IHeap;

import java.util.HashMap;

public class RelationalExpression implements Expression{

    private Expression e1, e2;
    private int rel;
    HashMap<Integer, String> ops;

    public RelationalExpression(int r, Expression left, Expression right){
        e1 = left;
        e2 = right;
        rel = r;

        ops = new HashMap<>();
        ops.put(1, "<");
        ops.put(2, "<=");
        ops.put(3, "==");
        ops.put(4, ">");
        ops.put(5, ">=");
        ops.put(6, "!=");

    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Integer, Value> hp) throws ExceptionCustom {

        Value v1 = e1.eval(table, hp);
        if (v1.getType().equals(new IntType())){
            Value v2 = e2.eval(table, hp);

            if (v2.getType().equals(new IntType())){
                if (rel == 1)
                    return new BoolValue(((IntValue)v1).getVal() < ((IntValue)v2).getVal());
                if (rel == 2)
                    return new BoolValue(((IntValue)v1).getVal() <= ((IntValue)v2).getVal());
                if (rel == 3)
                    return new BoolValue(((IntValue)v1).getVal() == ((IntValue)v2).getVal());
                if (rel == 4)
                    return new BoolValue(((IntValue)v1).getVal() > ((IntValue)v2).getVal());
                if (rel == 5)
                    return new BoolValue(((IntValue)v1).getVal() >= ((IntValue)v2).getVal());
                if (rel == 6)
                    return new BoolValue(((IntValue)v1).getVal() != ((IntValue)v2).getVal());
            }
            else throw new ExceptionCustom("Value 2 is not int");

        }
        else throw new ExceptionCustom("Value 1 is not int");

        return new BoolValue(false);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        Type t1, t2;
        t1 = e1.typecheck(typeEnv);
        t2 = e2.typecheck(typeEnv);

        if (t1.equals(new IntType())){
            if (t2.equals(new IntType())){
                return new BoolType();
            }
            else throw new ExceptionCustom("RELATIONA EXPRESSION: Second op is not int");
        }
        else throw new ExceptionCustom("REALTIONAL EXPRESION: First op is not int");
    }

    @Override
    public Expression deepcopy() {
        return new RelationalExpression(this.rel, this.e1.deepcopy(), this.e2.deepcopy());
    }

    @Override
    public String toString() {

        return "" + e1.toString() + this.ops.get(this.rel) + e2.toString();
    }
}
