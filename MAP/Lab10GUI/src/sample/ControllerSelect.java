package sample;

import Exceptions.ExceptionCustom;
import Model.Expression.*;
import Model.Statement.*;
import Model.Statement.Files.CloseFile;
import Model.Statement.Files.OpenFile;
import Model.Statement.Files.ReadFile;
import Model.Statement.Heap.NewHeap;
import Model.Statement.Heap.WriteHeap;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerSelect {

    private Alert alertTypeChecker =  new Alert(Alert.AlertType.INFORMATION);
    private Alert alertNotSelected =  new Alert(Alert.AlertType.INFORMATION);
    @FXML
    private ListView<String> programs = new ListView<>();
    private ObservableList<String> list = FXCollections.observableArrayList();
    private static InterfaceStatement state;
    private static int progID;
    private java.util.List<InterfaceStatement> listOfStates = new ArrayList<>();

    @FXML
    public static InterfaceStatement getState(){
        return state;
    }
    public static int getProgID(){return progID;}

    @FXML
    public void initialize(){

        System.out.println("The contents of Type Environment for each program");
        alertTypeChecker.setTitle("Wrong type");

        try {

//            int v; v=2;Print(v)

            IDictionary<String, Type> typeEnv1 = new Dictionary<>();

            InterfaceStatement decl11 = new VariableDeclarationStatement("v", new IntType());
            InterfaceStatement assign11 = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
            InterfaceStatement print11 = new PrintStatement(new VariableExpression("v"));

            InterfaceStatement s1 = new CompoundStatement(decl11,
                    new CompoundStatement(assign11, print11));


            s1.typecheck(typeEnv1);
            System.out.println("1\n" + typeEnv1);

            list.add(s1.toString());
            listOfStates.add(s1);


        }
        catch (ExceptionCustom x){
            System.out.println(x);
            alertTypeChecker.setContentText("Program 1 has not passed the typechecker test");
            alertTypeChecker.showAndWait();
        }

        try {

//            int a;int b; a=2+3*5;b=a+1;Print(b)

            IDictionary<String, Type> typeEnv2 = new Dictionary<>();

            InterfaceStatement decl21 = new VariableDeclarationStatement("a", new IntType());
            InterfaceStatement decl22 = new VariableDeclarationStatement("b", new IntType());
            Expression arithm21 = new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)));
            Expression arithm22 = new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), arithm21);
            InterfaceStatement assign21 = new AssignmentStatement("a", arithm22);
            Expression arithm23 = new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1)));
            InterfaceStatement assign22 = new AssignmentStatement("b", arithm23);
            InterfaceStatement print21 = new PrintStatement(new VariableExpression("b"));

            InterfaceStatement s2 = new CompoundStatement(decl21,
                    new CompoundStatement(decl22,
                            new CompoundStatement(assign21,
                                    new CompoundStatement(assign22, print21))));

            s2.typecheck(typeEnv2);

            System.out.println("2\n" + typeEnv2);

            list.add(s2.toString());
            listOfStates.add(s2);

        }
        catch (ExceptionCustom x){
            System.out.println(x);
            alertTypeChecker.setContentText("Program 2 has not passed the typechecker test");
            alertTypeChecker.showAndWait();
        }

        try {

//            bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)

            IDictionary<String, Type> typeEnv3 = new Dictionary<>();

            InterfaceStatement decl31 = new VariableDeclarationStatement("a", new BoolType());
            InterfaceStatement decl32 = new VariableDeclarationStatement("v", new IntType());
            InterfaceStatement assign31 = new AssignmentStatement("a", new ValueExpression(new BoolValue(true)));
            InterfaceStatement assign32 = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
            InterfaceStatement assign33 = new AssignmentStatement("v", new ValueExpression(new IntValue(3)));
            InterfaceStatement if31 = new IfStatement(new VariableExpression("a"), assign32, assign33);
            InterfaceStatement print31 = new PrintStatement(new VariableExpression("v"));

            InterfaceStatement s3 = new CompoundStatement(decl31,
                    new CompoundStatement(decl32,
                            new CompoundStatement(assign31,
                                    new CompoundStatement(if31, print31))));

            InterfaceStatement s31 = new CompoundStatement(
                    new VariableDeclarationStatement("a", new BoolType()),
                    new CompoundStatement(
                            new VariableDeclarationStatement("v", new IntType()),
                            new CompoundStatement(
                                    new AssignmentStatement("a", new ValueExpression(
                                            new BoolValue(true))),
                                    new CompoundStatement(
                                            new IfStatement(
                                                    new VariableExpression("a"), new AssignmentStatement(
                                                    "v", new ValueExpression(
                                                    new IntValue(2))), new AssignmentStatement(
                                                    "v", new ValueExpression(
                                                    new IntValue(3)))),
                                            new PrintStatement(new VariableExpression("v"))))));

            s3.typecheck(typeEnv3);

            System.out.println("3\n" + typeEnv3);
            list.add(s3.toString());
            listOfStates.add(s3);
        }
        catch (ExceptionCustom x) {
            System.out.println(x);
            alertTypeChecker.setContentText("Program 3 has not passed the typechecker test");
            alertTypeChecker.showAndWait();
        }

        try {

//            string varf;
//            varf="test.in";
//            openRFile(varf);
//            int varc;
//            readFile(varf,varc);
//            print(varc);
//            readFile(varf,varc);
//            print(varc);
//            closeRFile(varf)

            IDictionary<String, Type> typeEnv4 = new Dictionary<>();

            InterfaceStatement declare1 = new VariableDeclarationStatement("varf", new StringType());
            InterfaceStatement assign1 = new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in")));
            InterfaceStatement open1 = new OpenFile(new VariableExpression("varf"));
            InterfaceStatement declare2 = new VariableDeclarationStatement("varc", new IntType());
            InterfaceStatement read1 = new ReadFile(new VariableExpression("varf"), "varc");
            InterfaceStatement print1 = new PrintStatement(new VariableExpression("varc"));
            InterfaceStatement read2 = new ReadFile(new VariableExpression("varf"), "varc");
            InterfaceStatement print2 = new PrintStatement(new VariableExpression("varc"));
            InterfaceStatement close1 = new CloseFile(new VariableExpression("varf"));


            InterfaceStatement s4 = new CompoundStatement(declare1,
                    new CompoundStatement(assign1,
                            new CompoundStatement(open1,
                                    new CompoundStatement(declare2,
                                            new CompoundStatement(read1,
                                                    new CompoundStatement(print1,
                                                            new CompoundStatement(read2,
                                                                    new CompoundStatement(print2, close1))))))));


            s4.typecheck(typeEnv4);

            System.out.println("4\n" + typeEnv4);

            list.add(s4.toString());
            listOfStates.add(s4);
        }
        catch (ExceptionCustom x) {
            System.out.println(x);
            alertTypeChecker.setContentText("Program 4 has not passed the typechecker test");
            alertTypeChecker.showAndWait();
        }


        try {

// Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)

            IDictionary<String, Type> typeEnv5 = new Dictionary<>();

            InterfaceStatement ref51 = new VariableDeclarationStatement("v", new RefType(new IntType()));
            InterfaceStatement new51 = new NewHeap("v", new ValueExpression(new IntValue(20)));
            InterfaceStatement ref52 = new VariableDeclarationStatement("a", new RefType(new RefType(new IntType())));
            InterfaceStatement new52 = new NewHeap("a", new VariableExpression("v"));
            InterfaceStatement print51 = new PrintStatement(new VariableExpression("v"));
            InterfaceStatement print52 = new PrintStatement(new VariableExpression("a"));

            InterfaceStatement s5 = new CompoundStatement(ref51,
                    new CompoundStatement(new51,
                            new CompoundStatement(ref52,
                                    new CompoundStatement(new52,
                                            new CompoundStatement( print51, print52)))));



            s5.typecheck(typeEnv5);

            System.out.println("5\n" + typeEnv5);

            list.add(s5.toString());
            listOfStates.add(s5);

        }
        catch (ExceptionCustom x) {
            System.out.println(x);
            alertTypeChecker.setContentText("Program 5 has not passed the typechecker test");
            alertTypeChecker.showAndWait();
        }

        try {

//        Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)

            IDictionary<String, Type> typeEnv6 = new Dictionary<>();

            InterfaceStatement ref61 = new VariableDeclarationStatement("v", new RefType(new IntType()));
            InterfaceStatement new61 = new NewHeap("v", new ValueExpression(new IntValue(20)));
            InterfaceStatement write61 = new WriteHeap("v", new ValueExpression(new IntValue(20)));
            InterfaceStatement ref62 = new VariableDeclarationStatement("a", new RefType(new RefType(new IntType())));
            InterfaceStatement new62 = new NewHeap("a", new VariableExpression("v"));
            InterfaceStatement pr61 = new PrintStatement(new ReadHeap(new VariableExpression("v")));
            Expression succRead = new ReadHeap(new ReadHeap(new VariableExpression("a")));
            InterfaceStatement pr62 = new PrintStatement(new ArithmeticExpression('+', succRead, new ValueExpression(new IntValue(5))));

            InterfaceStatement s6 = new CompoundStatement(ref61,
                    new CompoundStatement(new61,
                            new CompoundStatement(write61,
                                    new CompoundStatement(ref62,
                                            new CompoundStatement(new62,
                                                    new CompoundStatement(pr61, pr62))))));


            s6.typecheck(typeEnv6);

            System.out.println("6\n" + typeEnv6);

            list.add(s6.toString());
            listOfStates.add(s6);

        }
        catch (ExceptionCustom x) {
            System.out.println(x);
            alertTypeChecker.setContentText("Program 6 has not passed the typechecker test");
            alertTypeChecker.showAndWait();
        }

        try {

//        int v; v=4; (while (v>0) print(v);v=v-1);print(v)

            IDictionary<String, Type> typeEnv7 = new Dictionary<>();

            InterfaceStatement decl71 = new VariableDeclarationStatement("v", new IntType());
            InterfaceStatement as71 = new AssignmentStatement("v", new ValueExpression(new IntValue(4)));
            Expression rel71 = new RelationalExpression(4, new VariableExpression("v"), new ValueExpression(new IntValue(0)));
            InterfaceStatement print71 = new PrintStatement(new VariableExpression("v"));
            Expression arithm71 = new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)));
            InterfaceStatement assign72 = new AssignmentStatement("v", arithm71);
            InterfaceStatement while71 = new WhileStatement(rel71, new CompoundStatement(print71, assign72));
            InterfaceStatement pr72 = new PrintStatement(new VariableExpression("v"));

            InterfaceStatement s7 = new CompoundStatement(decl71,
                    new CompoundStatement(as71,
                            new CompoundStatement(while71, pr72)));


            s7.typecheck(typeEnv7);

            System.out.println("7\n" + typeEnv7);


            list.add(s7.toString());
            listOfStates.add(s7);
        }
        catch (ExceptionCustom x) {
            System.out.println(x);
            alertTypeChecker.setContentText("Program 7 has not passed the typechecker test");
            alertTypeChecker.showAndWait();
        }

        try {

//        Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a))

            IDictionary<String, Type> typeEnv8 = new Dictionary<>();

            InterfaceStatement ref81 = new VariableDeclarationStatement("v", new RefType(new IntType()));
            InterfaceStatement new81 = new NewHeap("v", new ValueExpression(new IntValue(20)));
            InterfaceStatement ref82 = new VariableDeclarationStatement("a", new RefType(new RefType(new IntType())));
            InterfaceStatement new82 = new NewHeap("a", new VariableExpression("v"));
            InterfaceStatement new83 = new NewHeap("v", new ValueExpression(new IntValue(30)));
            InterfaceStatement print81 = new PrintStatement(new ReadHeap(new ReadHeap(new VariableExpression("a"))));

            InterfaceStatement s8 = new CompoundStatement(ref81,
                    new CompoundStatement(new81,
                            new CompoundStatement(ref82,
                                    new CompoundStatement(new82,
                                            new CompoundStatement(new83, print81)))));


            s8.typecheck(typeEnv8);

            System.out.println("8\n" + typeEnv8);

            list.add(s8.toString());
            listOfStates.add(s8);

        }
        catch (ExceptionCustom x) {
            System.out.println(x);
            alertTypeChecker.setContentText("Program 8 has not passed the typechecker test");
            alertTypeChecker.showAndWait();
        }


        try {

//        Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);

            IDictionary<String, Type> typeEnv9 = new Dictionary<>();

            InterfaceStatement ref91 = new VariableDeclarationStatement("v", new RefType(new IntType()));
            InterfaceStatement new91 = new NewHeap("v", new ValueExpression(new IntValue(20)));
            InterfaceStatement pr91 = new PrintStatement(new ReadHeap(new VariableExpression("v")));
            InterfaceStatement write91 = new WriteHeap("v", new ValueExpression(new IntValue(30)));
            InterfaceStatement pr92 = new PrintStatement(new ArithmeticExpression('+', new ReadHeap(new VariableExpression("v")), new ValueExpression(new IntValue(5))));

            InterfaceStatement s9 = new CompoundStatement(ref91,
                    new CompoundStatement(new91,
                            new CompoundStatement(pr91,
                                    new CompoundStatement(write91, pr92))));

            s9.typecheck(typeEnv9);

            System.out.println("9\n" + typeEnv9);

            list.add(s9.toString());
            listOfStates.add(s9);
        }
        catch (ExceptionCustom x) {
            System.out.println(x);
            alertTypeChecker.setContentText("Program 9 has not passed the typechecker test");
            alertTypeChecker.showAndWait();
        }


        try {

//        int v; Ref int a; v=10;new(a,22);
//        fork(wH(a,30);v=32;print(v);print(rH(a)));
//        print(v);print(rH(a))

            IDictionary<String, Type> typeEnv10 = new Dictionary<>();

            InterfaceStatement decl101 = new VariableDeclarationStatement("v", new IntType());
            InterfaceStatement decl102 = new VariableDeclarationStatement("a", new RefType(new IntType()));
            InterfaceStatement assign101 = new AssignmentStatement("v", new ValueExpression(new IntValue(10)));
            InterfaceStatement newh101 = new NewHeap("a", new ValueExpression(new IntValue(22)));
            InterfaceStatement write101 = new WriteHeap("a", new ValueExpression(new IntValue(30)));
            InterfaceStatement assign102 = new AssignmentStatement("v", new ValueExpression(new IntValue(32)));
            InterfaceStatement print101 = new PrintStatement(new VariableExpression("v"));
            InterfaceStatement print102 = new PrintStatement(new ReadHeap(new VariableExpression("a")));
            InterfaceStatement infork101 = new CompoundStatement(write101,
                    new CompoundStatement(assign102,
                            new CompoundStatement(print101, print102)));
            InterfaceStatement fork101 = new ForkStatement(infork101);

            InterfaceStatement s10 = new CompoundStatement(decl101,
                    new CompoundStatement(decl102,
                            new CompoundStatement(assign101,
                                    new CompoundStatement(newh101,
                                            new CompoundStatement(fork101,
                                                    new CompoundStatement(print101, print102))))));

            s10.typecheck(typeEnv10);
            System.out.println("10\n" + typeEnv10);


            list.add(s10.toString());
            listOfStates.add(s10);
        }
        catch (ExceptionCustom x) {
            System.out.println(x);
            alertTypeChecker.setContentText("Program 10 has not passed the typechecker test");
            alertTypeChecker.showAndWait();
        }

        try{

            // this is the broken program
            // will throw an exception, just
            // just to test the type checker

            IDictionary<String, Type> typeEnv11 = new Dictionary<>();

            InterfaceStatement ref81 = new VariableDeclarationStatement("v", new RefType(new IntType()));
            InterfaceStatement new81 = new NewHeap("v", new ValueExpression(new IntValue(20)));
            InterfaceStatement write81 = new WriteHeap("v", new ValueExpression(new IntValue(20)));
            InterfaceStatement ref82 = new VariableDeclarationStatement("a", new RefType(new RefType(new IntType())));
            InterfaceStatement new82 = new NewHeap("a", new VariableExpression("v"));
            InterfaceStatement as81 = new AssignmentStatement("a", new ReadHeap(new VariableExpression("v")));
            InterfaceStatement new83 = new NewHeap("v", new ValueExpression(new IntValue(30)));
            InterfaceStatement write82 = new WriteHeap("v", new ValueExpression(new IntValue(30)));
            InterfaceStatement pr81 = new PrintStatement(new ReadHeap(new ReadHeap(new VariableExpression("a"))));


            InterfaceStatement s8 = new CompoundStatement(ref81,
                    new CompoundStatement(new81,
                            new CompoundStatement(write81,
                                    new CompoundStatement(ref82,
                                            new CompoundStatement(new82,
                                                    new CompoundStatement(as81,
                                                            new CompoundStatement(new83,
                                                                    new CompoundStatement(write82, pr81))))))));

            System.out.println("11\n" + typeEnv11);
            s8.typecheck(typeEnv11);


            list.add(s8.toString());
            listOfStates.add(s8);

        }
        catch (ExceptionCustom x){
            System.out.println(x);
            alertTypeChecker.setContentText("Program 11 has not passed the typechecker test");
            alertTypeChecker.showAndWait();
        }


        programs.setItems(list);

    }

    @FXML
    public void startExecution() throws IOException {

        if(programs.getSelectionModel().getSelectedIndex()<0)
        {
            alertNotSelected.setTitle("Nothing selected");
            alertNotSelected.setContentText("Please select a program");
            alertNotSelected.showAndWait();
        }
        else {
            state = listOfStates.get(programs.getSelectionModel().getSelectedIndex());
            progID = programs.getSelectionModel().getSelectedIndex() + 1;


            Stage mainPrg = new Stage();

            Parent root = FXMLLoader.load(getClass().getResource("MainProgram.fxml"));
            mainPrg.setTitle("Running program " + progID);

            mainPrg.initModality(Modality.APPLICATION_MODAL);
            mainPrg.setScene(new Scene(root, 600, 700));
            mainPrg.show();
        }

    }


}
