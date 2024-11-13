package Model;

import Model.Value.StringValue;
import Utils.MyIDictionary;
import Utils.MyIList;
import Utils.MyIStack;
import Model.Statement.IStatement;
import Model.Value.Value;

import java.io.BufferedReader;

public class ProgState {
    private MyIDictionary<String, Value> symbolTable;
    private MyIStack<IStatement> execStack;
    private MyIList<Value> output;
    private IStatement originalProgram;
    private MyIDictionary <StringValue, BufferedReader> fileTable;

    public MyIDictionary<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public MyIStack<IStatement> getExecStack() {
        return execStack;
    }

    public MyIList<Value> getOutput() {
        return output;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public ProgState(MyIStack<IStatement> execStack, MyIDictionary<String, Value> symbolTable, MyIList<Value> output, IStatement program, MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.execStack = execStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.originalProgram = program.deepCopy();
        this.fileTable = fileTable;
        this.execStack.push(program);
    }

    @Override
    public String toString() {
        return "ExeStack: " + this.execStack.getReversed() + "\nSymbolTable: " + this.symbolTable.toString() + "\nOutput: " + this.output.toString() + "\n" + "FileTable: " + this.fileTable.toString() + "\n";
    }

}
