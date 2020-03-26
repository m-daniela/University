package Model.ProgramState;

import Exceptions.ExceptionCustom;
import Utils.IFileTable;
import Model.Statement.InterfaceStatement;
import Model.Value.StringValue;
import Model.Value.Value;
import Utils.*;

import java.io.BufferedReader;
import java.io.IOException;

public class ProgramState implements Cloneable {

    private IExeStack<InterfaceStatement> exeStack;
    private IDictionary<String, Value> symTable;
    private IList<Value> out;
    private InterfaceStatement originalProg;
    private IFileTable<StringValue, BufferedReader> fileTable;
    private IHeap<Integer, Value> heap;
    private HeapAddr heapAddress = new HeapAddr();
    private int id = 0;
    private static int id2 = 0;

    public ProgramState(IExeStack<InterfaceStatement> s, IDictionary<String, Value> sym, IList<Value> o, IFileTable<StringValue, BufferedReader> file, IHeap<Integer, Value> h, InterfaceStatement prg){
        exeStack = s;
        symTable = sym;
        out = o;
        fileTable = file;
        heap = h;
        originalProg = prg.deepcopy();
        this.id = getId2();
        s.push(prg);
    }


    private static synchronized int getId2(){
        id2++;
        return id2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InterfaceStatement getOriginalProg() {
        return originalProg;
    }

    public IDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public IExeStack<InterfaceStatement> getExeStack() {
        return exeStack;
    }

    public IList<Value> getOut() {
        return out;
    }

    public void setSymTable(IDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public void setOut(IList<Value> out) {
        this.out = out;
    }

    public void setOriginalProg(InterfaceStatement originalProg) {
        this.originalProg = originalProg;
    }

    public void setExeStack(IExeStack<InterfaceStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public void setFileTable(IFileTable<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IFileTable<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IHeap<Integer, Value> getHeap() {
        return heap;
    }

    public void setHeap(IHeap<Integer, Value> heap) {
        this.heap = heap;
    }

    public int getHeapAddress() {
        return heapAddress.getId();
    }


    public Boolean isNotCompleted() {
        return !this.exeStack.isEmpty();
    }

    public ProgramState oneStep() throws ExceptionCustom, IOException, CloneNotSupportedException {
        if (exeStack.isEmpty())
//            return null;
            throw new ExceptionCustom("Stack empty");



        InterfaceStatement crt = exeStack.pop();
        return crt.execute(this);
    }

    @Override
    public String toString() {

        String str = "";
        str = str + "Program State\n" +
                "ID\n" + id + "\n" +
                "Stack\n" +
                exeStack + "\n\n" +
                "SymTable\n" +
                symTable +  "\n\n" +
                "Output\n" +
                out + "\n\n" +
                "Heap\n" +
                heap + "\n\n" +
                "FileTable\n" +
                fileTable +  "\n--------------------" +
                "\n\n";
        return str;

    }
}
