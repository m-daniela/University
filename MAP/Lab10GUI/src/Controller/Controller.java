package Controller;

import Exceptions.ExceptionCustom;
import Model.ProgramState.ProgramState;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.RepositoryInterface;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    private RepositoryInterface repo;
    private ExecutorService executor;

    public Controller(RepositoryInterface ri){
        repo = ri;
    }




    public List<ProgramState> removeCompletedPrograms(List<ProgramState> progList){


        return progList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());


    }

    private void conservativeGarbageCollector(List<ProgramState> completedProgramList)
    {
        List<Integer> symtable=completedProgramList.stream()
                .map(e->e.getSymTable().getContent().values())
                .map(e->getAddrFromSymTable(e))
                .reduce(Stream.of(0).collect(Collectors.toList()), (s1, s2)->Stream.concat(s1.stream(),s2.stream()).collect(Collectors.toList()));
        Collection<Value> heapTableValues=completedProgramList.get(0).getHeap().getContent().values();
        List<Integer> heap=heapTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue val=(RefValue)v;return val.getAddress();})
                .collect(Collectors.toList());
        symtable.addAll(heap);
        completedProgramList.get(0).getHeap().setContent(unsafeGarbageCollector(symtable,completedProgramList.get(0).getHeap().getContent()));
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> addresses, Map<Integer, Value> heap){
        return heap.entrySet().stream().filter(e->addresses.contains(e.getKey())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream().filter(v->v instanceof RefValue).
                map(v->((RefValue)v).getAddress()).
                collect(Collectors.toList());
    }

    private List<Integer> getAddrFromHeap(Collection<Value> heapValues){
        return heapValues.stream().filter(v->v instanceof RefValue).
                map(v->((RefValue)v).getAddress()).
                collect(Collectors.toList());
    }


    private void oneStepForAll(List<ProgramState> progList) {

        progList.forEach(prg-> {
            try {
                repo.logPrgStateExec(prg);
//                System.out.println(prg.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        List<Callable<ProgramState>> callableList=progList.stream()
                .map((ProgramState p)->(Callable<ProgramState>)(()-> {return p.oneStep();}))
                .collect(Collectors.toList());


        try{List<ProgramState> newProgList=executor.invokeAll(callableList).stream()
                .map(future->{
                    try{
                        return future.get();
                    }
                    catch (InterruptedException | ExecutionException e) {System.out.println(e.getMessage());}
                    return null;})
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            progList.addAll(newProgList);
            progList.forEach(prg-> {
                try {
                    repo.logPrgStateExec(prg);
//                    System.out.println(prg.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            repo.setProgramList(progList);}
        catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void allStep() throws ExceptionCustom, IOException {

        executor = Executors.newFixedThreadPool(2);

        List<ProgramState> prgList = removeCompletedPrograms(repo.getProgramList());



        if (prgList.size() > 0) {

            oneStepForAll(prgList);
//            conservativeGarbageCollector(prgList);
            prgList = removeCompletedPrograms(repo.getProgramList());

        }
        else {
            executor.shutdownNow();

            repo.setProgramList(prgList);
        }


    }

    public Iterable<Integer> getIds(){
        List<Integer> ids = new ArrayList<>();
        for(ProgramState ps: repo.getProgramList())
            ids.add(ps.getId());
        return ids;
    }

    public ProgramState getById(int id){
        return repo.getById(id);
    }

//    public ProgramState getCrt(){
//        try {
//            return repo.getProgramList().get(0);
//        }
//        catch (InterruptedException x){
//            System.out.println(x);
//        }
//    }

}
