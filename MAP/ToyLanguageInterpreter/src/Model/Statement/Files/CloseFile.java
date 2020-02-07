package Model.Statement.Files;

import Exceptions.ExceptionCustom;
import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.Statement.InterfaceStatement;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IExeStack;
import Utils.IFileTable;
import Utils.IHeap;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFile implements InterfaceStatement {

    Expression exp;

    public CloseFile(Expression e){
        exp = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExceptionCustom, IOException {

        IExeStack<InterfaceStatement> stack = state.getExeStack();
        IDictionary<String, Value> sym = state.getSymTable();
        IFileTable<StringValue, BufferedReader> file = state.getFileTable();
        IHeap<Integer, Value> hp = state.getHeap();

        // pop statement
//        stack.pop();

        // check if exp stringValue
        Value val = exp.eval(sym, hp);
        if(!(val instanceof StringValue))
            throw new ExceptionCustom("Not a string");

        // get BufferedReader from fileTable
        BufferedReader bf = file.get((StringValue)val);

        bf.close();
        file.delete((StringValue)val);

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        Type te = exp.typecheck(typeEnv);
        if (te.equals(new StringType()))
            return typeEnv;
        else throw new ExceptionCustom("CLOSE FILE: not StringType");
    }

    @Override
    public InterfaceStatement deepcopy() {
        return new CloseFile(this.exp.deepcopy());
    }

    @Override
    public String toString() {
        return "Close(" + exp.toString() + ")";
    }
}
