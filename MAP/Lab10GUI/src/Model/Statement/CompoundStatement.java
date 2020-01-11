package Model.Statement;

import Exceptions.ExceptionCustom;
import Model.ProgramState.ProgramState;
import Model.Type.Type;
import Utils.IDictionary;
import Utils.IExeStack;


public class CompoundStatement implements InterfaceStatement {

    private InterfaceStatement first;
    private InterfaceStatement second;

    public CompoundStatement(InterfaceStatement v, InterfaceStatement v1) {
        first = v;
        second = v1;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IExeStack<InterfaceStatement> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public InterfaceStatement deepcopy() {
        return new CompoundStatement(this.first.deepcopy(), this.second.deepcopy());
    }


    public String toString(){
        return "(" + first.toString() + ";" + second.toString() + ")";
    }
}
