package Model.Statement;

import Exceptions.DictException;
import Exceptions.ExpressionException;
import Exceptions.MyException;
import Model.ProgState;
import Model.Expression.IExpression;
import Model.Type.RefType;
import Model.Value.Value;
import Model.Value.RefValue;

public class WriteHeapStatement implements IStatement{
    private String varName;
    private IExpression expression;

    public WriteHeapStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
  }

  @Override
  public ProgState execute(ProgState prg) throws MyException {
    if (!prg.getSymbolTable().isDefined(varName)) {
      throw new MyException("Variable " + varName + " was not declared");
    }

    Value varValue;
    try {
      varValue = prg.getSymbolTable().lookUp(varName);
    } catch (DictException e) {
      throw new MyException(e.getMessage());
    }
    if (!(varValue instanceof RefValue)) {
      throw new MyException("Variable " + varName + " is not of RefType");
    }

    RefValue refValue = (RefValue) varValue;
    Integer address = refValue.getAddress();

    if (!prg.getHeap().isDefined(address)) {
      throw new MyException("Address " + address + " is not allocated on the heap");
    }

    Value value;
    try {
      value = expression.evaluate(prg.getSymbolTable(), prg.getHeap());
    } catch (ExpressionException | MyException e) {
      throw new MyException(e.getMessage());
    }
    if (!value.getType().equals(((RefType) refValue.getType()).getInner())) {
      throw new MyException("Type of expression and type of variable do not match");
    }

    prg.getHeap().put(address, value);
    return null;
  }

  @Override
  public IStatement deepCopy() {
    return new WriteHeapStatement(varName, expression.deepCopy());
  }

  @Override
  public String toString() {
    return "WriteHeapStmt(" + varName + ", " + expression + ")";
  } 
}
