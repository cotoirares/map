package Model.Statement;

import Controller.MyException;
import Model.ProgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;

public class VarDeclStatement implements IStatement {
    private String id;
    Type type;

    public VarDeclStatement(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public ProgState execute(ProgState state) throws MyException {
        state.getSymbolTable().put(id, new IntValue(0));
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
