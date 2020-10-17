package Model.Expression;

import Exceptions.ExceptionCustom;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IHeap;


public class ArithmeticExpression implements Expression {
    private Expression e1, e2;
    private char op;
    // 1 - +, 2 - -, 3 - *, 4 - /

    public ArithmeticExpression(char operation, Expression left, Expression right){
        op = operation;
        e1 = left;
        e2 = right;
    }

    @Override
    public Value eval(IDictionary<String, Value> table, IHeap<Integer, Value> hp) throws ExceptionCustom {
        Value v1, v2;
        v1 = e1.eval(table, hp);
        if(v1.getType().equals(new IntType())) {
            v2 = e2.eval(table, hp);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                int n1, n2;
                n1 = ((IntValue) v1).getVal();
                n2 = ((IntValue) v2).getVal();

                if(op == '+')
                    return new IntValue(n1 + n2);

                if(op == '-')
                    return new IntValue(n1 - n2);

                if(op == '*')
                    return new IntValue(n1 * n2);

                if(op == '/'){

                    if (n2 == 0)
                        throw new ExceptionCustom("Div by zero");
                    return new IntValue(n1 / n2);
                }

            }
            else throw new ExceptionCustom("Second op is not int");
        }
        else throw new ExceptionCustom("First op is not int");
        return new IntValue(0);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        Type t1, t2;
        t1 = e1.typecheck(typeEnv);
        t2 = e2.typecheck(typeEnv);

        if (t1.equals(new IntType())){
            if (t2.equals(new IntType())){
                return new IntType();
            }
            else throw new ExceptionCustom("ARITHMETIC EXPRESSION: Second op is not int");
        }
        else throw new ExceptionCustom("ARITHMETIC EXPRESSION: First op is not int");
    }

    @Override
    public Expression deepcopy() {
        return new ArithmeticExpression(this.op, this.e1.deepcopy(), this.e2.deepcopy());
    }

    @Override
    public String toString() {
        return "" + e1 + op + e2;
    }
}
