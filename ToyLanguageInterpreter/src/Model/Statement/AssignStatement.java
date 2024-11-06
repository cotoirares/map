package Model.Statement;

import Exceptions.MyException;
import Model.ProgState;
import Model.Type.Type;
import Model.Value.Value;
import Utils.MyIDictionary;
import Utils.MyIStack;

import Model.Expression.IExpression;

public class AssignStatement implements IStatement {
    private String id;
    IExpression expression;

    public AssignStatement(String id, IExpression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgState execute(ProgState state) throws MyException {
        MyIStack<IStatement> stack = state.getExecStack();
        MyIDictionary<String,Value> SymbolTable = state.getSymbolTable();
        if(SymbolTable.isDefined(id)) {
            Value val = this.expression.evaluate(SymbolTable);
            Type typeId = (SymbolTable.lookUp(id)).getType();
            if ((val.getType()).equals(typeId)) {
                SymbolTable.update(id, val);
            } else
                throw new MyException("declared type of variable " + id + " and type of the assigned expression do not match.");
        } else throw new MyException("the used variable " + id + " was not declared before");

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(id, expression.deepCopy());
    }

    public String toString() {
        return this.id + " = " + this.expression.toString();
    }
}
