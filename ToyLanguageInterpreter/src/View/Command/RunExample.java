package View.Command;

import Controller.Service;
import Model.Statement.IStatement;

public class RunExample extends Command{
    private Service service;

    public RunExample(String key, IStatement statement, Service service) {
        super(key, statement.toString());
        this.service = service;
    }

    @Override
    public void execute() {
        try {
            service.allStep();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
