package sample;

import Controller.Controller;
import Exceptions.ExceptionCustom;
import Model.ProgramState.ProgramState;
import Model.Statement.InterfaceStatement;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.RepositoryInterface;
import Repository.Repository;
import Utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;

public class ControllerMainProgram {

    public Scene scene;
    public Button btn;
    private Controller ctrl;
    private ProgramState prg;
    private RepositoryInterface repo;

    // tables
    @FXML private ObservableList<TableViewClass> heapList = FXCollections.observableArrayList();
    @FXML private TableView<TableViewClass> heapTable;

    @FXML private TableColumn<TableViewClass, String> addrColumn = new TableColumn<>("Address");
    @FXML private TableColumn<TableViewClass, String> valueColumn = new TableColumn<>("Value");


    @FXML private ObservableList<TableViewClass> symTableList = FXCollections.observableArrayList();
    @FXML private TableView<TableViewClass> symTable;

    @FXML private TableColumn<TableViewClass, String> varNameColumn = new TableColumn<>("Variable Name");
    @FXML private TableColumn<TableViewClass, String> valueColumnSym = new TableColumn<>("Value");

    // lists
    @FXML private ObservableList<String> idList = FXCollections.observableArrayList();
    @FXML private ListView<String> showId;
    @FXML private ObservableList<String> outList = FXCollections.observableArrayList();
    @FXML private ListView<String> showOut;
    @FXML private ObservableList<String> fileList = FXCollections.observableArrayList();
    @FXML private ListView<String> showFile;
    @FXML private ObservableList<String> stackList = FXCollections.observableArrayList();
    @FXML private ListView<String> showStack;


    @FXML
    private Text progNr;

    @FXML
    public void initialize(){
        IExeStack<InterfaceStatement> stack = new ExeStack<>();
        IDictionary<String, Value> dict = new Dictionary<>();
        IList<Value> list = new MyList<>();
        IFileTable<StringValue, BufferedReader> files = new FileTable<>();
        IHeap<Integer, Value> heap = new Heap<>();
        prg = new ProgramState(stack, dict, list, files, heap, ControllerSelect.getState());

//        print into the corresponding log file
        String fileName = "log" + ControllerSelect.getProgID() + ".txt";
        repo = new Repository(prg, fileName);
        ctrl = new Controller(repo);

        setAll();
    }

    @FXML
    private void setPrograms(){
        progNr.setText("Program states left: " + String.valueOf(repo.getProgramList().size()));
        if (repo.getProgramList().size() == 0)
            btn.setDisable(true);
    }


    @FXML
    private void setHeap(){
        IHeap<Integer, Value> hp = prg.getHeap();
        heapTable.getItems().clear();

        for (Integer i: hp.getAll()){
            heapList.add(new TableViewClass(String.valueOf(i), hp.get(i).toString()));
        }

        addrColumn.setCellValueFactory(new PropertyValueFactory<TableViewClass, String>("Key"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<TableViewClass, String>("Value"));
        heapTable.setItems(heapList);
    }

    @FXML
    private void setSymTable(IDictionary<String, Value> sym){
        symTable.getItems().clear();

        try {
            for (String i : sym.keys()) {
                symTableList.add(new TableViewClass(i, sym.lookupID(i).toString()));
            }

            varNameColumn.setCellValueFactory(new PropertyValueFactory<TableViewClass, String>("Key"));
            valueColumnSym.setCellValueFactory(new PropertyValueFactory<TableViewClass, String>("Value"));

            symTable.setItems(symTableList);
        }
        catch (ExceptionCustom x){
            System.out.println(x);
        }
    }

    @FXML
    private void setIdList(){

        showId.getItems().clear();
        for(Integer i: ctrl.getIds())
            idList.add(String.valueOf(i));
        showId.setItems(idList);

    }

    @FXML
    private void setOutList(){
        showOut.getItems().clear();
        IList<Value> l = prg.getOut();
        for(Value v: l.getAll())
            outList.add(v.toString());
        showOut.setItems(outList);

    }

    @FXML
    private void setStackList(IExeStack<InterfaceStatement> s){

        showStack.getItems().clear();
        for(InterfaceStatement is: s.getAll())
            stackList.add(is.toString());
        Collections.reverse(stackList);
        showStack.setItems(stackList);

    }


    // runs with the main program's stack and symtable
    // if there are more
    private void updateStack(){
        setStackList(prg.getExeStack());
    }
    private void updateSymTable(){
        setSymTable(prg.getSymTable());
    }

    @FXML
    private void setFileList(){
        showFile.getItems().clear();
        IFileTable<StringValue, BufferedReader> t = prg.getFileTable();
        for(StringValue s: t.getAllKeys())
            fileList.add(s.toString());
        showFile.setItems(fileList);

    }

    @FXML
    private void setAll(){
        setPrograms();
        setOutList();
        setFileList();
        setIdList();
        updateStack();
        setHeap();
        updateSymTable();
    }

    @FXML
    public void executeOneStep() throws IOException, ExceptionCustom {
        ctrl.allStep();
        setAll();
    }

    @FXML
    public void lineClick(){
        int id = Integer.parseInt(idList.get(showId.getSelectionModel().getSelectedIndex()));
        ProgramState p = ctrl.getById(id);
        setStackList(p.getExeStack());
        setSymTable(p.getSymTable());

    }



}
