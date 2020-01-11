package Model.Statement;

import Exceptions.ExceptionCustom;

import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.Type;
import Model.Value.Value;
import Utils.*;

public class PrintStatement implements InterfaceStatement {

    private Expression exp;

    public PrintStatement(Expression v) {
        exp = v;
    }

    public String toString(){
        return "Print(" + exp.toString() +")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExceptionCustom{


        IList<Value> list = state.getOut();
        IDictionary<String, Value> symtable = state.getSymTable();
        IHeap<Integer, Value> hp = state.getHeap();

        Value val = exp.eval(symtable, hp);
        list.add(val);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public InterfaceStatement deepcopy() {
        return new PrintStatement(this.exp.deepcopy());
    }


}
