package Model.Expression;

import Exceptions.ExceptionCustom;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IHeap;

public class LogicalExpression implements Expression {
    private Expression e1, e2;
    private int op;
    // 1 - and, 2 - or, 3 - not

    public LogicalExpression(int operation, Expression left, Expression right){
        e1 = left;
        e2 = right;
        op = operation;
    }
    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Integer, Value> hp) throws ExceptionCustom {
        Value v1, v2;
        v1 = e1.eval(table, hp);
        if(v1.getType().equals(new BoolType())){
            v2 = e2.eval(table, hp);
            boolean n1;
            n1 = ((BoolValue) v1).getVal();
            if(op == 3)
                return new BoolValue(!n1);
            if(v2.getType().equals(new BoolType())){
                BoolValue b1 = (BoolValue)v1;
                BoolValue b2 = (BoolValue)v2;
                boolean n2;

                n2 = ((BoolValue) v2).getVal();

                if(op == 1)
                    return new BoolValue(n1 && n2);

                if(op == 2)
                    return new BoolValue(n1 || n2);


            }
            else throw new ExceptionCustom("Second op not bool");


        }
        else throw new ExceptionCustom("First op not bool");


        return null;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        Type t1, t2;
        t1 = e1.typecheck(typeEnv);
        t2 = e2.typecheck(typeEnv);

        if (t1.equals(new BoolType())){
            if (t2.equals(new BoolType())){
                return new BoolType();
            }
            else throw new ExceptionCustom("LOGICAL EXPRESSION: Second op is not bool");
        }
        else throw new ExceptionCustom("LOGICAL EXPRESSION: First op is not bool");
    }

    @Override
    public Expression deepcopy() {
        return new LogicalExpression(this.op, this.e1.deepcopy(), this.e2.deepcopy());
    }

    @Override
    public String toString() {
        return "" + e1 + op + e2;
    }
}
