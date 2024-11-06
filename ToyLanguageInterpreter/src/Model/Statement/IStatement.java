package Model.Statement;

import Exceptions.MyException;
import Model.ProgState;

public interface IStatement {
    ProgState execute(ProgState state) throws MyException;
    IStatement deepCopy();
}
