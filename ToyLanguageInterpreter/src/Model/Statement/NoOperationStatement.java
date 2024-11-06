package Model.Statement;

import Model.ProgState;

public class NoOperationStatement implements IStatement{
    @Override
    public ProgState execute(ProgState state) {
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new NoOperationStatement();
    }

    @Override
    public String toString() {
        return "No Operation";
    }
}
