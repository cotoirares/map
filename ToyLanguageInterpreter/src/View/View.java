package View;

import Controller.Service;
import Exceptions.MyException;
import Model.Expression.ArithmeticExpression;
import Model.Expression.VariableExpression;
import Model.ProgState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import Utils.*;
import Model.Expression.ValueExpression;
import View.Command.ExitCommand;
import View.Command.RunExample;

import java.io.BufferedReader;

public class View {
    public static IStatement example1(){
        //int v; v = 2; Print(v)
        IStatement ex1= new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                new PrintStatement(new VariableExpression("v"))));
        return ex1;
    }

    public static IStatement example2(){
        // int a; int b; a=2+3*5; b=a+1; Print(b)
        IStatement ex2 = new CompStatement(
                new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()),
                new CompStatement(
                        new AssignStatement("a", new ArithmeticExpression("+", new ValueExpression(new IntValue(2)), new ArithmeticExpression("*", new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                        new CompStatement(
                                new AssignStatement("b", new ArithmeticExpression("+", new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                new PrintStatement(new VariableExpression("b"))))));
        return ex2;
    }

    public static IStatement example3(){
        // bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        IStatement ex3 = new CompStatement(
                new VarDeclStatement("a", new BoolType()),
                new CompStatement(
                        new VarDeclStatement("v", new IntType()),
                        new CompStatement(
                                new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(
                                        new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignStatement("v", new ValueExpression(new IntValue(2)))),
                                        new PrintStatement(new VariableExpression("v")))
                                )
                        )
                );
        return ex3;
    }

    public static IStatement example4(){
        // int a; bool b; a = b; expected: type error
        IStatement ex4 = new CompStatement(
                new VarDeclStatement("a", new IntType()),
                new AssignStatement("a", new ValueExpression(new BoolValue(true)))
        );
        return ex4;
    }

    public static IStatement example5(){
        // string varf; varf = "test.in"; openRFile(varf); int varc; readFile(varf, varc); Print(varc); readFile(varf, varc); Print(varc); closeRFile(varf);
        IStatement ex5 = new CompStatement(
                new VarDeclStatement("varf", new StringType()),
                new CompStatement(
                        new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompStatement(
                                new OpenRFile(new VariableExpression("varf")),
                                new CompStatement(
                                        new VarDeclStatement("varc", new IntType()),
                                        new CompStatement(
                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        return ex5;
    }

    private static ProgState createState (IStatement program){
        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, Value> symTable = new MyDictionary<>();
        MyIList<Value> output = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        return new ProgState(stack, symTable, output, program, fileTable);
    }

    private static Service createService(IStatement program, String logFilePath){
        ProgState state = createState(program);
        IRepository repo = new Repository(state, logFilePath);
        return new Service(repo);
    }

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();

        menu.addCommand(new RunExample("1", example1(), createService(example1(), "log1.txt")));
        menu.addCommand(new RunExample("2", example2(), createService(example2(), "log2.txt")));
        menu.addCommand(new RunExample("3", example3(), createService(example3(), "log3.txt")));
        menu.addCommand(new RunExample("4", example4(), createService(example4(), "log4.txt")));
        menu.addCommand(new RunExample("5", example5(), createService(example5(), "log5.txt")));
        menu.addCommand(new ExitCommand("0", "Exit"));

        try {
            menu.show();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}