package Model.Statement.Heap;

import Exceptions.ExceptionCustom;
import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.Statement.InterfaceStatement;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IHeap;

import java.io.IOException;

public class NewHeap implements InterfaceStatement {

    private String var_name;
    private Expression exp;

    public NewHeap(String var_name, Expression exp){
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExceptionCustom, IOException {
        IDictionary<String, Value> sym = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        int number = state.getHeapAddress();

        sym.isDefined(var_name);
        Value val = sym.getValue(var_name);
        if(!(val.getType().equals(new RefType(new IntType()))))
            throw new ExceptionCustom("Not RefType");


        Value val_expr = exp.eval(sym, heap);

        Type loc_var = val.getType();
        Type loc_exp = val_expr.getType();




        if (!(loc_exp.equals(((RefType)loc_var).getInner())))
            throw new ExceptionCustom("Not same type");

//        if(!(val_expr.getType().equals(t)))
//            throw new ExceptionCustom("Not same type");

        heap.add(number, val_expr);
//        Value r = new RefValue(number, loc_var);
        Value r = new RefValue(number, loc_exp);
        sym.updateValue(var_name, r);



        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        Type tv = typeEnv.lookupID(var_name);
        Type te = exp.typecheck(typeEnv);
        if (tv.equals(new RefType(te)))
            return typeEnv;
        else throw new ExceptionCustom("NEW HEAP: right hand side and left hand side have different types ");
    }

    @Override
    public InterfaceStatement deepcopy() {
        return new NewHeap(this.var_name, this.exp.deepcopy());
    }


    @Override
    public String toString() {
        return "New(" + var_name + ", " + exp.toString() + ")";
    }
}
