package Repository;

import Exceptions.MyException;
import Model.ProgState;

public interface IRepository {
    ProgState getCurrentProgram();

    void add(ProgState s);

    void logProgState(ProgState state) throws MyException;
}
