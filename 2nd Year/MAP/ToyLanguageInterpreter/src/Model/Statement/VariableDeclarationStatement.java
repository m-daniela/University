package Model.Statement;

import Exceptions.ExceptionCustom;
import Model.ProgramState.ProgramState;
import Model.Type.*;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IExeStack;



public class VariableDeclarationStatement implements InterfaceStatement {

    private String name;
    private Type type;

    public VariableDeclarationStatement(String nm, Type tp){
        name = nm;
        type = tp;

    }

    @Override
    public ProgramState execute(ProgramState state) throws ExceptionCustom {
        IExeStack<InterfaceStatement> stack = state.getExeStack();
        IDictionary<String, Value> sym = state.getSymTable();

        if (sym.isDefined(name))
            throw new ExceptionCustom("Already declared");
        else{
            if(type.equals(new IntType()))
                sym.setValue(name, type.defaultValue());
            else if (type.equals(new BoolType()))
                sym.setValue(name, type.defaultValue());
            else if (type.equals(new StringType()))
                sym.setValue(name, type.defaultValue());
            else if (type.equals(new RefType(new IntType())))
                sym.setValue(name, type.defaultValue());
        }

        return null;

    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        typeEnv.setValue(name, type);
        return typeEnv;
    }

    @Override
    public InterfaceStatement deepcopy() {
        return new VariableDeclarationStatement(this.name, this.type.deepcopy());
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}
