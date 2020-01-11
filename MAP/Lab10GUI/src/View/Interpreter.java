package View;

import Controller.Controller;
import Exceptions.ExceptionCustom;
import Model.Expression.*;
import Model.ProgramState.ProgramState;
import Model.Statement.*;
import Model.Statement.Files.CloseFile;
import Model.Statement.Files.OpenFile;
import Model.Statement.Files.ReadFile;
import Model.Statement.Heap.NewHeap;
import Model.Statement.Heap.WriteHeap;
import Model.Type.*;
import Model.Value.*;
import Repository.RepositoryInterface;
import Repository.Repository;
import Utils.*;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args){


        TextMenu menu = new TextMenu();

        try {

            IDictionary<String, Type> typeEnv1 = new Dictionary<>();

            InterfaceStatement s1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                            new PrintStatement(new VariableExpression("v"))));


            s1.typecheck(typeEnv1);
            IExeStack<InterfaceStatement> stack1 = new ExeStack<>();
            IDictionary<String, Value> d1 = new Dictionary<>();
            IList<Value> l1 = new MyList<>();
            IFileTable<StringValue, BufferedReader> f1 = new FileTable<>();
            IHeap<Integer, Value> h1 = new Heap<>();

            ProgramState prg1 = new ProgramState(stack1, d1, l1, f1, h1, s1);

            RepositoryInterface repo1 = new Repository(prg1, "log1.txt");
            Controller c1 = new Controller(repo1);

            menu.addCommand(new RunExample("1",s1.toString(),c1));


        }
        catch (ExceptionCustom x){
            System.out.println(x);
        }

        try {

            IDictionary<String, Type> typeEnv2 = new Dictionary<>();
            InterfaceStatement s2 = new CompoundStatement(
                    new VariableDeclarationStatement(
                            "a", new IntType()),
                    new CompoundStatement(
                            new VariableDeclarationStatement(
                                    "b", new IntType()),
                            new CompoundStatement(
                                    new AssignmentStatement(
                                            "a", new ArithmeticExpression(
                                            '+', new ValueExpression(
                                            new IntValue(2)),
                                            new ArithmeticExpression(
                                                    '*', new ValueExpression(
                                                    new IntValue(3)),
                                                    new ValueExpression(
                                                            new IntValue(5))))),
                                    new CompoundStatement(
                                            new AssignmentStatement(
                                                    "b", new ArithmeticExpression(
                                                    '+', new VariableExpression("a"),
                                                    new ValueExpression(new IntValue(1)))),
                                            new PrintStatement(
                                                    new VariableExpression("b"))))));

            s2.typecheck(typeEnv2);

            IExeStack<InterfaceStatement> stack2 = new ExeStack<>();
            IDictionary<String, Value> d2 = new Dictionary<>();
            IList<Value> l2 = new MyList<>();
            IFileTable<StringValue, BufferedReader> f2 = new FileTable<>();
            IHeap<Integer, Value> h2 = new Heap<>();


            ProgramState prg2 = new ProgramState(stack2, d2, l2, f2, h2, s2);

            RepositoryInterface repo2 = new Repository(prg2, "log2.txt");
            Controller c2 = new Controller(repo2);

            menu.addCommand(new RunExample("2",s2.toString(),c2));

        }
        catch (ExceptionCustom x){
            System.out.println(x);
        }

        try {

            IDictionary<String, Type> typeEnv3 = new Dictionary<>();
            InterfaceStatement s3 = new CompoundStatement(
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

            IExeStack<InterfaceStatement> stack3 = new ExeStack<>();
            IDictionary<String, Value> d3 = new Dictionary<>();
            IList<Value> l3 = new MyList<>();
            IFileTable<StringValue, BufferedReader> f3 = new FileTable<>();
            IHeap<Integer, Value> h3 = new Heap<>();


            ProgramState prg3 = new ProgramState(stack3, d3, l3, f3, h3, s3);

            RepositoryInterface repo3 = new Repository(prg3, "log3.txt");
            Controller c3 = new Controller(repo3);

            menu.addCommand(new RunExample("3",s3.toString(),c3));
        }
        catch (ExceptionCustom x) {
            System.out.println(x);
        }

        try {

            IDictionary<String, Type> typeEnv4 = new Dictionary<>();
            InterfaceStatement declare1 = new VariableDeclarationStatement("varf", new StringType());
            InterfaceStatement assign1 = new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in")));
            InterfaceStatement open1 = new OpenFile(new VariableExpression("varf"));
            InterfaceStatement open2 = new OpenFile(new VariableExpression("varf"));

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

            IExeStack<InterfaceStatement> s = new ExeStack<>();
            IDictionary<String, Value> d = new Dictionary<>();
            IList<Value> l = new MyList<>();
            IFileTable<StringValue, BufferedReader> f = new FileTable<>();
            IHeap<Integer, Value> h = new Heap<>();


            ProgramState prg4 = new ProgramState(s, d, l, f, h, s4);

            RepositoryInterface repo = new Repository(prg4, "log4.txt");
            Controller c = new Controller(repo);

            menu.addCommand(new RunExample("4",s4.toString(),c));

        }
        catch (ExceptionCustom x) {
            System.out.println(x);
        }


        try {

            IDictionary<String, Type> typeEnv5 = new Dictionary<>();

// Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)

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

            IExeStack<InterfaceStatement> stack5 = new ExeStack<>();
            IDictionary<String, Value> d5 = new Dictionary<>();
            IList<Value> l5 = new MyList<>();
            IFileTable<StringValue, BufferedReader> f5 = new FileTable<>();
            IHeap<Integer, Value> h5 = new Heap<>();

            ProgramState prg5 = new ProgramState(stack5, d5, l5, f5, h5, s5);

            RepositoryInterface repo5 = new Repository(prg5, "log5.txt");
            Controller c5 = new Controller(repo5);

            menu.addCommand(new RunExample("5",s5.toString(),c5));

        }
        catch (ExceptionCustom x) {
            System.out.println(x);
        }

        try {

            IDictionary<String, Type> typeEnv6 = new Dictionary<>();

//        Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)

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

            IExeStack<InterfaceStatement> stack6 = new ExeStack<>();
            IDictionary<String, Value> d6 = new Dictionary<>();
            IList<Value> l6 = new MyList<>();
            IFileTable<StringValue, BufferedReader> f6 = new FileTable<>();
            IHeap<Integer, Value> h6 = new Heap<>();

            ProgramState prg6 = new ProgramState(stack6, d6, l6, f6, h6, s6);


            RepositoryInterface repo6 = new Repository(prg6, "log6.txt");
            Controller c6 = new Controller(repo6);

            menu.addCommand(new RunExample("6",s6.toString(),c6));

        }
        catch (ExceptionCustom x) {
            System.out.println(x);
        }

        try {

//        int v; v=4; (while (v>0) print(v);v=v-1);print(v)

            IDictionary<String, Type> typeEnv7 = new Dictionary<>();

            InterfaceStatement decl71 = new VariableDeclarationStatement("v", new IntType());
            InterfaceStatement as71 = new AssignmentStatement("v", new ValueExpression(new IntValue(4)));
            InterfaceStatement while71 = new WhileStatement(new RelationalExpression(4, new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                    new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1))))));
            InterfaceStatement pr71 = new PrintStatement(new VariableExpression("v"));

            InterfaceStatement s7 = new CompoundStatement(decl71,
                    new CompoundStatement(as71,
                            new CompoundStatement(while71, pr71)));


            s7.typecheck(typeEnv7);

            IExeStack<InterfaceStatement> stack7 = new ExeStack<>();
            IDictionary<String, Value> d7 = new Dictionary<>();
            IList<Value> l7 = new MyList<>();
            IFileTable<StringValue, BufferedReader> f7 = new FileTable<>();
            IHeap<Integer, Value> h7 = new Heap<>();


            ProgramState prg7 = new ProgramState(stack7, d7, l7, f7, h7, s7);


            RepositoryInterface repo7 = new Repository(prg7, "log9.txt");
            Controller c7 = new Controller(repo7);

            menu.addCommand(new RunExample("9",s7.toString(),c7));
        }
        catch (ExceptionCustom x) {
            System.out.println(x);
        }

        try {


            IDictionary<String, Type> typeEnv8 = new Dictionary<>();

//        Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a))

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


            IExeStack<InterfaceStatement> stack8 = new ExeStack<>();
            IDictionary<String, Value> d8 = new Dictionary<>();
            IList<Value> l8 = new MyList<>();
            IFileTable<StringValue, BufferedReader> f8 = new FileTable<>();
            IHeap<Integer, Value> h8 = new Heap<>();


            ProgramState prg8 = new ProgramState(stack8, d8, l8, f8, h8, s8);


            RepositoryInterface repo8 = new Repository(prg8, "log8.txt");
            Controller c8 = new Controller(repo8);

            menu.addCommand(new RunExample("8",s8.toString(),c8));

        }
        catch (ExceptionCustom x) {
            System.out.println(x);
        }


        try {

            IDictionary<String, Type> typeEnv9 = new Dictionary<>();
//        Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);

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

            IExeStack<InterfaceStatement> stack9 = new ExeStack<>();
            IDictionary<String, Value> d9 = new Dictionary<>();
            IList<Value> l9 = new MyList<>();
            IFileTable<StringValue, BufferedReader> f9 = new FileTable<>();
            IHeap<Integer, Value> h9 = new Heap<>();


            ProgramState prg9 = new ProgramState(stack9, d9, l9, f9, h9, s9);


            RepositoryInterface repo9 = new Repository(prg9, "log7.txt");
            Controller c9 = new Controller(repo9);
            menu.addCommand(new RunExample("7",s9.toString(),c9));
        }
        catch (ExceptionCustom x) {
            System.out.println(x);
        }

//        int v; Ref int a; v=10;new(a,22);
//        fork(wH(a,30);v=32;print(v);print(rH(a)));
//        print(v);print(rH(a))

        try {
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

            IExeStack<InterfaceStatement> stack10 = new ExeStack<>();
            IDictionary<String, Value> d10 = new Dictionary<>();
            IList<Value> l10 = new MyList<>();
            IFileTable<StringValue, BufferedReader> f10 = new FileTable<>();
            IHeap<Integer, Value> h10 = new Heap<>();


            ProgramState prg10 = new ProgramState(stack10, d10, l10, f10, h10, s10);


            RepositoryInterface repo10 = new Repository(prg10, "log10.txt");
            Controller c10 = new Controller(repo10);

            menu.addCommand(new RunExample("10",s10.toString(),c10));
        }
        catch (ExceptionCustom x) {
            System.out.println(x);
        }





        menu.addCommand(new ExitCommand("0", "exit"));

        menu.show();

    }
}
