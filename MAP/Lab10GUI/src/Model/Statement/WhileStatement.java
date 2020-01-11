package Model.Statement;

import Exceptions.ExceptionCustom;
import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IExeStack;
import Utils.IHeap;

import java.io.IOException;

public class WhileStatement implements InterfaceStatement {

    Expression exp;
    InterfaceStatement stm;

    public WhileStatement(Expression exp, InterfaceStatement state){
        this.exp = exp;
        this.stm = state;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExceptionCustom, IOException {
        IDictionary<String, Value> newDict = state.getSymTable();
        IExeStack<InterfaceStatement> stack = state.getExeStack();
        IHeap<Integer, Value> hp = state.getHeap();
        Value val = exp.eval(newDict, hp);

        if(((BoolValue)val).getVal()){
            stack.push(new WhileStatement(exp, stm));
            stack.push(stm);
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        Type te = exp.typecheck(typeEnv);
        if (te.equals(new BoolType())){
            stm.typecheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else throw new ExceptionCustom("WHILE: The condition of while has not the type bool");
    }

    @Override
    public InterfaceStatement deepcopy() {
        return new WhileStatement(this.exp.deepcopy(), this.stm.deepcopy());
    }

    @Override
    public String toString() {
        return "While( " + exp.toString() + " ) " + stm.toString();
    }
}
