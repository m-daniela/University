package Model.Statement;

import Exceptions.ExceptionCustom;
import Model.ProgramState.ProgramState;
import Model.Type.Type;
import Utils.IDictionary;

public class NoOperationStatement implements InterfaceStatement {
    public NoOperationStatement() {
    }

    @Override
    public String toString() {
        return "NopStatement{}";
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExceptionCustom {
//        IExeStack<InterfaceStatement> stack=state.getExeStack();
//        if(stack.isEmpty())
//            throw new MyException("trying to execute when the execution stack is empty");
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        return typeEnv;
    }

    @Override
    public InterfaceStatement deepcopy() {
        return new NoOperationStatement();
    }
}
