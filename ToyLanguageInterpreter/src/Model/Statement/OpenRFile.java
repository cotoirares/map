package Model.Statement;

import Exceptions.ExpressionException;
import Exceptions.MyException;
import Model.Expression.IExpression;
import Model.ProgState;
import Model.Type.StringType;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStatement{
    private IExpression exp;

    public OpenRFile(IExpression exp){
        this.exp = exp;
    }
    @Override
    public ProgState execute(ProgState state) throws MyException {
        Value value;
        try{
            value = exp.evaluate(state.getSymbolTable(), state.getHeap());
        }
        catch (ExpressionException | MyException e){
            throw new MyException(e.getMessage());
        }
        if (!value.getType().equals(new StringType())){
            throw new MyException("The file name is not a string");
        }
        StringValue fileName = (StringValue) value;
        if (state.getFileTable().isDefined(fileName)){
            throw new MyException("The file is already opened");
        }
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName.getVal()));
            state.getFileTable().put(fileName, bufferedReader);
        } catch (FileNotFoundException e) {
            throw new MyException(e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

}
