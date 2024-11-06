package Model.Expression;

import Exceptions.MyException;
import Model.Value.Value;
import Utils.MyIDictionary;

public class VariableExpression implements IExpression {
    private String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> SymbolTable) throws MyException {
        if (SymbolTable.isDefined(this.variable)){
            try{
                return SymbolTable.lookUp(variable);
            }
            catch (MyException e){
                throw new MyException(e.getMessage());
            }
        }
        else throw new MyException("Variable not declared");
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(variable);
    }

    public String toString() {
        return variable;
    }
}
