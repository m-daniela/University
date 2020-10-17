package Model.Statement;

import Exceptions.ExceptionCustom;
import Model.Expression.Expression;
import Model.ProgramState.ProgramState;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.Value;
import Utils.IDictionary;
import Utils.IExeStack;
import Utils.IHeap;

public class AssignmentStatement implements InterfaceStatement {
    private String id;
    private Expression exp;

    public AssignmentStatement(String a, Expression e) {
        id = a;
        exp = e;
    }

    @Override
    public String toString() {
        return id+" = "+ exp.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws ExceptionCustom {
        IDictionary<String, Value> symTbl = state.getSymTable();
        IHeap<Integer, Value> hp = state.getHeap();

        if (symTbl.isDefined(id)){
            Value val = exp.eval(symTbl, hp);
            Type typeID = (symTbl.lookupID(id)).getType();
            if(val.getType().equals(typeID))
                symTbl.updateValue(id, val);
//            else throw new ExceptionCustom("declared type of variable "+id+" and type of  the assigned expression do not match");
        }
        else throw new ExceptionCustom("the used variable" +id + " was not declared before");
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom {
        Type tv = typeEnv.lookupID(id);
        Type te = exp.typecheck(typeEnv);

        if(tv.equals(te)){
            return typeEnv;
        }
        else throw new ExceptionCustom("ASSIGNMENT: right hand side and left hand side have different types ");
    }

    @Override
    public InterfaceStatement deepcopy() {
        return new AssignmentStatement(this.id, this.exp.deepcopy());
    }
}
