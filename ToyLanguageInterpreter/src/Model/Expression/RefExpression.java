package Model.Expression;

import Exceptions.ExpressionException;
import Exceptions.HeapException;
import Exceptions.MyException;
import Model.Value.Value;
import Model.Value.RefValue;
import Utils.MyIDictionary;
import Utils.MyIHeap;

public class RefExpression implements IExpression {
    private IExpression expression;

    public RefExpression(IExpression expression) {
      this.expression = expression;
    }
  
    @Override
    public Value evaluate(MyIDictionary<String, Value> symTable, MyIHeap<Integer, Value> heap)
        throws MyException, ExpressionException {
        Value value = expression.evaluate(symTable, heap);
        if (!(value instanceof RefValue)) {
            throw new ExpressionException("Expected a RefValue, got " + value);
      }
  
      RefValue refValue = (RefValue) value;
      Integer address = refValue.getAddress();
      if (!heap.isDefined(address)) {
        throw new ExpressionException("Address " + address + " is not defined in the heap");
      }
      try {
        return heap.get(address);
      } catch (HeapException e) {
        throw new MyException(e.getMessage());
      }
    }
  
    @Override
    public IExpression deepCopy() {
      return new RefExpression(expression.deepCopy());
    }
  
    @Override
    public String toString() {
      return "RefExp(" + expression + ")";
    }
    
}
