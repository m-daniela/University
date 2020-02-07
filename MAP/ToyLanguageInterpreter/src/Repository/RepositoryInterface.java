package Repository;

import Model.ProgramState.ProgramState;

import java.io.IOException;
import java.util.List;


public interface RepositoryInterface {

    public void add(ProgramState prg);
//    public ProgramState getCrtPrg();
    public void logPrgStateExec(ProgramState prg) throws IOException;
    public List<ProgramState> getProgramList();

    void setProgramList(List<ProgramState> progList);
    ProgramState getById(int id);
}