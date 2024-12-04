package Model.Statement;

import Exceptions.ExpressionException;
import Exceptions.MyException;
import Model.ProgState;
import Model.Expression.IExpression;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.Value;

public class WhileStatement implements IStatement{
    private IExpression expression;
    private IStatement statement;

  public WhileStatement(IExpression expression, IStatement statement) {
    this.expression = expression;
    this.statement = statement;
  }

  @Override
  public ProgState execute(ProgState state) throws MyException {
    Value value;
    try {
      value = expression.evaluate(state.getSymbolTable(), state.getHeap());
    } catch (ExpressionException | MyException e) {
      throw new MyException(e.getMessage());
    }
    if (!value.getType().equals(new BoolType())) {
      throw new MyException("Expression is not of type Bool");
    }
    BoolValue boolValue = (BoolValue) value;
    if (boolValue.getVal()) {
      state.getExecStack().push(this);
      state.getExecStack().push(statement);
    }
    return state;
  }

  @Override
  public IStatement deepCopy() {
    return new WhileStatement(expression.deepCopy(), statement.deepCopy());
  }

  @Override
  public String toString() {
    return "WhileStatement(" + expression + ", " + statement + ")";
  }
}
