package Model.Expression;

import Controller.MyException;
import Model.Value.Value;
import Utils.MyIDictionary;

public interface Expression {
    Value evaluate(MyIDictionary<String,Value> SymbolTable) throws MyException;
    Expression deepCopy();
}
