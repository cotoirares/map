package Model.Expression;

import Exceptions.MyException;
import Model.Value.Value;
import Utils.MyIDictionary;

public class ValueExpression implements IExpression {

    private Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> SymbolTable) throws MyException {
        return value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value.deepCopy());
    }

    public String toString() {
        return this.value.toString();
    }
}
