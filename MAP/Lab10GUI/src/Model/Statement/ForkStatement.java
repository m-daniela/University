package Model.Statement;

import Exceptions.ExceptionCustom;
import Model.ProgramState.ProgramState;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;
import Utils.*;

import java.io.BufferedReader;
import java.io.IOException;

public class ForkStatement implements InterfaceStatement {

    private InterfaceStatement s;

    public ForkStatement(InterfaceStatement s){
        this.s = s;
    }

    @Override
    public ProgramState execute(ProgramState state) throws CloneNotSupportedException {

        IDictionary<String, Value> newDict = state.getSymTable();
        IExeStack<InterfaceStatement> stack = state.getExeStack();
        IHeap<Integer, Value> hp = state.getHeap();
        IList<Value> l = state.getOut();
        IFileTable<StringValue, BufferedReader> f = state.getFileTable();

        IExeStack<InterfaceStatement> stack2 = new ExeStack<InterfaceStatement>();
//        IHeap<Integer, Value> heap2 = new Heap<>();
//        state.setHeap(heap2);
        return new ProgramState(stack2, newDict.deepcopy(), l, f, hp, this.s);

    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        return s.typecheck(typeEnv.deepcopy());
    }

    @Override
    public String toString() {
        return "Fork(" + s.toString() + ")";
    }

    @Override
    public InterfaceStatement deepcopy() {
        return new ForkStatement(this.s.deepcopy());
    }
}
