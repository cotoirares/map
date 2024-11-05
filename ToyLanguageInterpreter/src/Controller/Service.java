package Controller;

import Model.ProgState;
import Utils.MyIStack;
import Model.Statement.IStatement;
import Repository.IRepository;

import java.util.EmptyStackException;

public class Service {
    private IRepository repository;

    public Service(IRepository repo) {
        this.repository = repo;
    }

    private ProgState oneStep(ProgState state) throws MyException {
        MyIStack<IStatement> execStack = state.getExecStack();
        if (execStack.isEmpty())
            throw new MyException("ProgState Stack is empty!");
        IStatement currentStatement;
        try {
            currentStatement = execStack.pop();
        } catch (EmptyStackException e) {
            throw new MyException(e.getMessage());
        }
        return currentStatement.execute(state);
    }

    public void allStep(){
        ProgState currentProgram = repository.getCurrentProgram();
        System.out.println(currentProgram);

        while (!currentProgram.getExecStack().isEmpty()){
            oneStep(currentProgram);
            System.out.println(currentProgram);
        }
    }
}
