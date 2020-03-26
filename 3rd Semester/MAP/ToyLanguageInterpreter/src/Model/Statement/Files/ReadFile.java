package Model.Statement.Files;

import Exceptions.ExceptionCustom;
import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.Statement.InterfaceStatement;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IExeStack;
import Utils.IFileTable;
import Utils.IHeap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFile implements InterfaceStatement {

    private Expression exp;
    private StringValue f;
    private String var_name;

    public ReadFile(Expression e, String var){
        exp = e;
        var_name = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExceptionCustom, IOException {

        IExeStack<InterfaceStatement> stack = state.getExeStack();
        IFileTable<StringValue, BufferedReader> file = state.getFileTable();
        IDictionary<String, Value> sym = state.getSymTable();
        IHeap<Integer, Value> hp = state.getHeap();
        // pop statement
//        stack.pop();

        // check if defined in symtable and check if int
        sym.isDefined(var_name);
        Value val = sym.lookupID(var_name);
        if (!val.getType().equals(new IntType()))
            throw new ExceptionCustom("Not int");

        // check if expression is stringValue
        Value vex = exp.eval(sym, hp);
        if (!(vex instanceof StringValue))
            throw new ExceptionCustom("Value not string");

        // get the BufferedReader assoc to vex
        BufferedReader bf = file.get((StringValue) vex);
        if (bf == null)
            throw new ExceptionCustom("File not opened");
        // read a line and update the symtable
        String b = bf.readLine();
        if (b == null || b.equals("")){
            sym.setValue(var_name, new IntValue(0));
        }
        else
            sym.setValue(var_name, new IntValue(Integer.parseInt(b)));

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        Type tv = typeEnv.lookupID(var_name);
        Type te = exp.typecheck(typeEnv);

        if(te.equals(new StringType())){
            return typeEnv;
        }
        else throw new ExceptionCustom("READ FILE: right hand side and left hand side have different types ");


    }

    @Override
    public InterfaceStatement deepcopy() {
        return new ReadFile(this.exp.deepcopy(), this.var_name);
    }

    @Override
    public String toString() {
        return "Read(" + var_name + " from " + exp.toString() + ")";
    }
}
