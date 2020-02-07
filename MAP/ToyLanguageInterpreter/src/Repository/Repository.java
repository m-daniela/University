package Repository;
import Model.ProgramState.ProgramState;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements RepositoryInterface {
    private String logFilePath;
    private List<ProgramState> stateList;

    public Repository(ProgramState ps, String filename){
        stateList = new ArrayList<ProgramState>();
//        this.add(ps);
        this.stateList.add(ps);


        logFilePath = filename;
    }
    @Override
    public void add(ProgramState prg) {
        this.stateList.add(prg);
    }


    @Override
    public void logPrgStateExec(ProgramState prg) throws IOException {

        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));

        logFile.println(prg.toString());
        logFile.close();



    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.stateList;
    }

    @Override
    public void setProgramList(List<ProgramState> progList) {
        this.stateList = progList;
    }

    @Override
    public ProgramState getById(int id) {
        for(ProgramState p: stateList) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }
}
