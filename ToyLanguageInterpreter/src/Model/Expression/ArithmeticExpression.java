package Model.Expression;

import Controller.MyException;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.MyIDictionary;

public class ArithmeticExpression implements Expression{
    private Expression exp1;
    private Expression exp2;
    private String operation;

    public ArithmeticExpression(Expression exp1, Expression exp2, String operation){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(MyIDictionary<String,Value> SymbolTable) throws MyException{
        Value v1, v2;
        v1 = exp1.evaluate(SymbolTable);
        v2 = exp2.evaluate(SymbolTable);
        if (v1.getType().equals(("Int"))) {
            if (v2.getType().equals("Int")) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int val1, val2;
                val1 = i1.getVal();
                val2 = i2.getVal();
                if (operation.equals("+")) return new IntValue(val1 + val2);
                if (operation.equals("-")) return new IntValue(val1 - val2);
                if (operation.equals("*")) return new IntValue(val1 * val2);
                if (operation.equals("/")) {
                    if (val2 == 0) throw new MyException("division by zero");
                    return new IntValue(val1 / val2);
                }
                throw new MyException("invalid operation");
            } else throw new MyException("the operands are not integers");
        } else {
            throw new MyException("the operands are not integers");
        }
    }

    @Override
    public Expression deepCopy(){
        return new ArithmeticExpression(exp1.deepCopy(), exp2.deepCopy(), operation);
    }

    public String toString(){
        return "(" + exp1 + operation + exp2 + ")";
    }
}
