package Repository;

import Model.ProgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    private List<ProgState> repository;

    public Repository(){
        this.repository = new ArrayList<>();
    }

    @Override
    public ProgState getCurrentProgram() {
        return this.repository.get(0);
    }

    @Override
    public void add(ProgState s) {
        this.repository.add(s);
    }

}
