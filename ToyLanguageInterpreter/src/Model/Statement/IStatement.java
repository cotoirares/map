package Model.Statement;

import Controller.MyException;
import Model.ProgState;

public interface IStatement {
    ProgState execute(ProgState state) throws MyException;
    IStatement deepCopy();
}
