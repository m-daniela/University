package Model.Statement;
import Exceptions.ExceptionCustom;
import Model.ProgramState.ProgramState;
import Model.Type.Type;
import Utils.IDictionary;

import javax.annotation.processing.FilerException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface InterfaceStatement {
    ProgramState execute(ProgramState state) throws ExceptionCustom, IOException, CloneNotSupportedException;

    IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws ExceptionCustom;
    InterfaceStatement deepcopy();
}





