package Model.Expression;

import Controller.MyException;
import Model.Value.Value;
import Utils.MyIDictionary;

public class VariableExpression implements Expression {
    private String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> SymbolTable) throws MyException {
        return SymbolTable.lookUp(variable);
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(variable);
    }

    public String toString() {
        return variable;
    }
}
