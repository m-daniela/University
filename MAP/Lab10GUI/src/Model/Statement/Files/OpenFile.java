package Model.Statement.Files;

import Exceptions.ExceptionCustom;
import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.Statement.InterfaceStatement;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;
import Utils.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenFile implements InterfaceStatement {
    private Expression exp;
    private StringValue f;

    public OpenFile(Expression e){
        exp = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExceptionCustom, FileNotFoundException {
        IExeStack<InterfaceStatement> stack = state.getExeStack();
        IDictionary<String, Value> sym = state.getSymTable();
        IFileTable<StringValue, BufferedReader> file = state.getFileTable();
        IHeap<Integer, Value> hp = state.getHeap();

        // pop statement
        // stack.pop();
        // eval the expression
        Value val = exp.eval(sym, hp);

        // check if stringtype
        if (!val.getType().equals(new StringType())) {
            throw new ExceptionCustom("Value is not string type");
        }

        // check if in filetable

        if (file.contains((StringValue)val)) {
            throw new ExceptionCustom("Already in filetable");
        }


        BufferedReader bf = new BufferedReader(new FileReader(((StringValue) val).getVal()));
        file.add((StringValue)val, bf);



        return null;

    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {

        Type te = exp.typecheck(typeEnv);
        if (te.equals(new StringType()))
            return typeEnv;
        else throw new ExceptionCustom("OPEN FILE: not StringType");

    }

    @Override
    public InterfaceStatement deepcopy() {
        return new OpenFile(this.exp.deepcopy());
    }

    @Override
    public String toString() {
        return "Open(" + exp.toString() + ")";
    }
}
