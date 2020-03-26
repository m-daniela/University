package Model.Statement.Heap;

import Exceptions.ExceptionCustom;
import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.Statement.InterfaceStatement;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.RefValue;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IHeap;

import java.io.IOException;

public class WriteHeap implements InterfaceStatement {

    private String var_name;
    private Expression exp;

    public WriteHeap(String name, Expression e){
        var_name = name;
        exp = e;
    }


    @Override
    public ProgramState execute(ProgramState state) throws ExceptionCustom, IOException {
        IDictionary<String, Value> sym = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        sym.isDefined(var_name);
        Value val = sym.getValue(var_name);

        if(!(val.getType().equals(new RefType(new IntType()))))
            throw new ExceptionCustom("Not RefType");



        int addr = ((RefValue)val).getAddress();
        heap.contains(addr);
        Type t = ((RefValue) val).getLocationType();
        Value v = exp.eval(sym, heap);

        if(!(v.getType().equals(t)))
            throw new ExceptionCustom("Not same type");


        heap.update(addr, v);

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        Type tv = typeEnv.lookupID(var_name);
        Type te = exp.typecheck(typeEnv);
        if (tv.equals(new RefType(te)))
            return typeEnv;
        else throw new ExceptionCustom("WRITE HEAP: right hand side and left hand side have different types ");
    }

    @Override
    public InterfaceStatement deepcopy() {
        return new WriteHeap(this.var_name, this.exp.deepcopy());
    }


    @Override
    public String toString() {
        return "Write(" + var_name + "-> " + exp.toString() + ")";
    }
}
