package View;

import Controller.Service;
import Exceptions.MyException;
import Model.Expression.ArithmeticExpression;
import Model.Expression.VariableExpression;
import Model.ProgState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import Utils.*;
import Model.Expression.ValueExpression;

import java.util.InputMismatchException;
import java.util.Scanner;

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
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IStatement selectedProgram = null;

        while (selectedProgram == null) {
            System.out.println("1. int v; v=2; Print(v)");
            System.out.println("2. int a; int b; a=2+3*5; b=a+1; Print(b)");
            System.out.println("3. bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)");
            System.out.println("4. int a; bool b; a = b; expected: type error");
            System.out.println("\nSelect the program to execute: ");

            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        selectedProgram = example1();
                        break;
                    case 2:
                        selectedProgram = example2();
                        break;
                    case 3:
                        selectedProgram = example3();
                        break;
                    case 4:
                        selectedProgram = example4();
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (MyException e) {
                System.out.println("Please enter a valid number!");
                scanner.nextLine();
            }
        }
        MyIStack<IStatement> stk = new MyStack<>();
        MyIDictionary<String, Value> symTable = new MyDictionary<>();
        MyIList<Value> output = new MyList<>();

        ProgState prg = new ProgState(stk, symTable, output, selectedProgram);
        IRepository repo = new Repository();
        repo.add(prg);
        Service ctrl = new Service(repo);

        try {
            ctrl.allStep();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}