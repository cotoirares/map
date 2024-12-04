package Model.Statement;

import Exceptions.MyException;
import Model.Expression.IExpression;
import Model.ProgState;

public class PrintStatement implements IStatement {
    private IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgState execute(ProgState state) throws MyException {
        state.getOutput().add(expression.evaluate(state.getSymbolTable(), state.getHeap()));
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
