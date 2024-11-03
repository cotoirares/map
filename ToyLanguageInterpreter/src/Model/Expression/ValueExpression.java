package Model.Expression;

import Controller.MyException;
import Model.Value.Value;
import Utils.MyIDictionary;

public class ValueExpression implements Expression {

    private Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> SymbolTable) throws MyException {
        return value;
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(value.deepCopy());
    }

    public String toString() {
        return this.value.toString();
    }
}
