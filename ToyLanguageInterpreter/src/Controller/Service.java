package Controller;

import Model.ProgState;
import Utils.MyIStack;
import Model.Statement.IStatement;
import Repository.IRepository;

public class Service {
    private IRepository repository;

    public Service(IRepository repo) {
        this.repository = repo;
    }

    private ProgState oneStep(ProgState state) throws MyException {
        MyIStack<IStatement> execStack = state.getExecStack();
        if(execStack.isEmpty())
            throw new MyException("ProgState Stack is empty!");

        IStatement currentStatement = execStack.pop();
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
