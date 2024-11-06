package Model.Expression;

import Exceptions.MyException;
import Model.Value.Value;
import Utils.MyIDictionary;

public interface IExpression {
    Value evaluate(MyIDictionary<String,Value> SymbolTable) throws MyException;
    IExpression deepCopy();
}
