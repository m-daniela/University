package Model.Statement;

import Exceptions.ExceptionCustom;
import Model.Type.BoolType;
import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IExeStack;
import Utils.IHeap;


public class IfStatement implements InterfaceStatement {

    private Expression exp;
    private InterfaceStatement thenS, elseS;

    public IfStatement(Expression e, InterfaceStatement t, InterfaceStatement el){
        exp = e;
        thenS = t;
        elseS = el;
    }

    public String toString(){
        return "If("+ exp.toString()+")then(" +thenS.toString()+")else("+elseS.toString()+")";
    }


    @Override
    public ProgramState execute(ProgramState state) throws ExceptionCustom {
        IDictionary<String, Value> newDict = state.getSymTable();
        IExeStack<InterfaceStatement> stack = state.getExeStack();
        IHeap<Integer, Value> hp = state.getHeap();
        Value val = exp.eval(newDict, hp);

        if (((BoolValue)val).getVal())
            stack.push(thenS);
        else
            stack.push(elseS);

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        Type te = exp.typecheck(typeEnv);
        if (te.equals(new BoolType()))
        {
            thenS.typecheck(typeEnv.deepcopy());
            elseS.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else throw new ExceptionCustom("IF: The condition of if has not the type bool");
    }

    @Override
    public InterfaceStatement deepcopy() {
        return new IfStatement(this.exp.deepcopy(), this.thenS.deepcopy(), this.elseS.deepcopy());
    }
}
