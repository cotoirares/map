package Model.Statement;

import Utils.MyIStack;
import Controller.MyException;
import Model.ProgState;

public class CompStatement implements Model.Statement.IStatement {
    Model.Statement.IStatement first, second;

    public CompStatement(Model.Statement.IStatement first, Model.Statement.IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgState execute(ProgState state) throws MyException {
        MyIStack<Model.Statement.IStatement> execStack = state.getExecStack();

        execStack.push(second);
        execStack.push(first);

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new CompStatement(first.deepCopy(), second.deepCopy());
    }

    public String toString() {
        return "(" + this.first + "; " + this.second + ")";
    }
}
