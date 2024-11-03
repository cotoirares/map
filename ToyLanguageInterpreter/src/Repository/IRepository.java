package Repository;

import Model.ProgState;

public interface IRepository {
    ProgState getCurrentProgram();

    void add(ProgState s);
}
