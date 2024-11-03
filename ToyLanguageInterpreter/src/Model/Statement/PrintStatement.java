package Model.Statement;

import Controller.MyException;
import Model.Expression.Expression;
import Model.ProgState;

public class PrintStatement implements IStatement {
    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgState execute(ProgState state) throws MyException {
        state.getOutput().add(expression.evaluate(state.getSymbolTable()));
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
