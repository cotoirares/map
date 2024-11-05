package Model.Expression;

import Controller.MyException;
import Model.Value.Value;
import Utils.MyIDictionary;
import Model.Type.BoolType;
import Model.Value.BoolValue;

public class LogicExpression implements Expression{
    private Expression exp1, exp2;
    private String operation;

    public LogicExpression(Expression exp1, Expression exp2, String operation){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> SymbolTable) throws MyException{
        Value v1, v2;
        try{
            v1 = exp1.evaluate(SymbolTable);
        }
        catch (MyException e){
            throw new MyException(e.getMessage());
        }

    }


}
