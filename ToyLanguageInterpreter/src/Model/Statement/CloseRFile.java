package Model.Statement;

import Exceptions.ExpressionException;
import Exceptions.MyException;
import Model.Expression.IExpression;
import Model.ProgState;
import Model.Type.StringType;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class CloseRFile implements IStatement {
    private IExpression expression;

    public CloseRFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgState execute(ProgState state) throws MyException {
        Value value;
        try{
            value = expression.evaluate(state.getSymbolTable(), state.getHeap());
        }
        catch (ExpressionException | MyException e){
            throw new MyException(e.getMessage());
        }
        if (!value.getType().equals(new StringType())){
            throw new MyException("The expression is not a string");
        }
        StringValue file = (StringValue) value;
        BufferedReader reader;
        try{
            reader = state.getFileTable().lookUp(file);
        }
        catch (MyException e){
            throw new MyException(e.getMessage());
        }
        if (reader == null){
            throw new MyException("The file is not opened");
        }
        try{
            reader.close();
        }
        catch (Exception e){
            throw new MyException(e.getMessage());
        }
        state.getFileTable().put(file, null);
        return null;
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFile(expression.deepCopy());
    }
}
