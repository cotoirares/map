package Model.Statement;

import Exceptions.DictException;
import Exceptions.ExpressionException;
import Exceptions.MyException;
import Model.ProgState;
import Model.Expression.IExpression;
import Model.Type.Type;
import Model.Type.RefType;
import Model.Value.Value;
import Model.Value.RefValue;

public class NewStatement implements IStatement{
    private String varName;
    private IExpression expression;

  public NewStatement(String varName, IExpression expression) {
    this.varName = varName;
    this.expression = expression;
  }

  @Override
  public ProgState execute(ProgState state) throws MyException {
    if (!state.getSymbolTable().isDefined(varName)) {
      throw new MyException("Variable " + varName + " was not declared");
    }
    Type type;
    try {
      type = state.getSymbolTable().lookUp(varName).getType();
    } catch (DictException e) {
      throw new MyException(e.getMessage());
    }
    if (!(type instanceof RefType)) {
      throw new MyException("Variable " + varName + " is not of type RefType");
    }

    Value value;
    try {
      value = expression.evaluate(state.getSymbolTable(), state.getHeap());
    } catch (ExpressionException | MyException e) {
      throw new MyException(e.getMessage());
    }
    if (!value.getType().equals(((RefType) type).getInner())) {
      throw new MyException("Type mismatch: expected " + ((RefType) type).getInner() +
          " but got " + value.getType());
    }
    Integer newAddress = state.getHeap().allocate();
    state.getHeap().put(newAddress, value);
    state.getSymbolTable().put(varName, new RefValue(newAddress, value.getType()));
    return null;
  }

  @Override
  public IStatement deepCopy() {
    return new NewStatement(varName, expression.deepCopy());
  }

  @Override
  public String toString() {
    return "NewStatement(" + varName + ", " + expression + ")";
  }
}
