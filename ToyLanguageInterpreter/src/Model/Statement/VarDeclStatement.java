package Model.Statement;

import Exceptions.MyException;
import Model.ProgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.MyIDictionary;

public class VarDeclStatement implements IStatement {
    private String id;
    private Type type;

    public VarDeclStatement(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public ProgState execute(ProgState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        if (symbolTable.isDefined(id))
            throw new MyException("Variable " + id + " is already defined!");
        symbolTable.put(id, type.defaultValue());
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclStatement(id, new IntType());
    }

    public String toString() {
        return this.type.toString() + " " + this.id;
    }
}
