package Model.Expression;

import Exceptions.ExpressionException;
import Exceptions.MyException;
import Model.Value.Value;
import Utils.MyIDictionary;
import Utils.MyIHeap;

public class VariableExpression implements IExpression {
    private String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symTable, MyIHeap<Integer, Value> heap)
    throws MyException, ExpressionException {
        if (symTable.isDefined(this.variable)){
            try{
                return symTable.lookUp(variable);
            }
            catch (MyException e){
                throw new MyException(e.getMessage());
            }
        }
        else throw new ExpressionException("Variable not declared");
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(variable);
    }

    public String toString() {
        return variable;
    }
}
