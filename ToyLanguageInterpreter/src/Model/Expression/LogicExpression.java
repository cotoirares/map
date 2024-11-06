package Model.Expression;

import Exceptions.MyException;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.Value;
import Utils.MyIDictionary;

public class LogicExpression implements IExpression {
    private IExpression exp1, exp2;
    private String operation;

    public LogicExpression(IExpression exp1, IExpression exp2, String operation){
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
        if (v1.getType().equals(new BoolType())){
            try {
                v2 = exp2.evaluate(SymbolTable);
            }
            catch (MyException e){
                throw new MyException(e.getMessage());
            }
            if (v2.getType().equals(new BoolType())) {
                BoolValue val1 = (BoolValue) v1;
                BoolValue val2 = (BoolValue) v2;
                boolean b1, b2;
                b1 = Boolean.parseBoolean(val1.toString());
                b2 = Boolean.parseBoolean(val2.toString());
                if (operation.equals("and")) return new BoolValue(b1 && b2);
                else if (operation.equals("or")) return new BoolValue(b1 || b2);
                else throw new MyException("invalid operation");
            }
            else throw new MyException("Operand 2 is not boolean");
        }
        else throw new MyException("Operand 1 is not boolean");
    }

    @Override
    public IExpression deepCopy(){
        return new LogicExpression(exp1.deepCopy(), exp2.deepCopy(), operation);
    }

    @Override
    public String toString(){
        return exp1.toString() + " " + operation + " " + exp2.toString();
    }
}
