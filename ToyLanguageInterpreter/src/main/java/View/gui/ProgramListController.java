package View.gui;

import View.gui.MainWindowController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import Model.Statement.IStatement;
import Model.Statement.CompStatement;
import Model.Statement.AssignStatement;
import Model.Statement.PrintStatement;
import Model.Statement.IfStatement;
import Model.Statement.VarDeclStatement;
import Model.Statement.WhileStatement;
import Model.Statement.NewStatement;
import Model.Statement.WriteHeapStatement;
import Model.Statement.ForkStatement;
import Model.Statement.OpenRFile;
import Model.Statement.ReadFile;
import Model.Statement.CloseRFile;
import Model.Expression.VariableExpression;
import Model.Expression.ArithmeticExpression;
import Model.Expression.RelationalExpression;
import Model.Expression.ValueExpression;
import Model.Expression.RefExpression;
import Model.Type.IntType;
import Model.Type.BoolType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.IntValue;
import Model.Value.BoolValue;
import Model.Value.StringValue;
import Model.ProgState;
import Repository.IRepository;
import Repository.Repository;
import Controller.Service;
import Utils.MyStack;
import Utils.MyDictionary;
import Utils.MyList;
import Utils.MyHeap;
import java.util.ArrayList;
import java.util.List;

public class ProgramListController {
    @FXML
    private ListView<String> programListView;
    private List<IStatement> programs;

    @FXML
    public void initialize() {
        programs = new ArrayList<>();

        // Example 1: int v; v=2; Print(v)
        IStatement prog1 = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                new PrintStatement(new VariableExpression("v"))));
        programs.add(prog1);

        // Example 2: int a; int b; a=2+3*5; b=a+1; Print(b)
        IStatement prog2 = new CompStatement(
                new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()),
                        new CompStatement(
                                new AssignStatement("a", new ArithmeticExpression("+", new ValueExpression(new IntValue(2)), new ArithmeticExpression("*", new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompStatement(
                                        new AssignStatement("b", new ArithmeticExpression("+", new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                        new PrintStatement(new VariableExpression("b"))))));
        programs.add(prog2);

        // Example 3: bool a; int v; a=true; If a Then v=2 Else v=3; Print(v)
        IStatement prog3 = new CompStatement(
                new VarDeclStatement("a", new BoolType()),
                new CompStatement(
                        new VarDeclStatement("v", new IntType()),
                        new CompStatement(
                                new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(
                                        new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v")))
                        )
                )
        );
        programs.add(prog3);

        // Example 4: string varf; varf = "test.in"; openRFile(varf); int varc;
        // readFile(varf, varc); Print(varc); readFile(varf, varc); Print(varc);
        // closeRFile(varf);
        IStatement prog4 = new CompStatement(
                new VarDeclStatement("varf", new StringType()),
                new CompStatement(
                        new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompStatement(
                                new OpenRFile(new VariableExpression("varf")),
                                new CompStatement(
                                        new VarDeclStatement("varc", new IntType()),
                                        new CompStatement(
                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        programs.add(prog4);

        // Example 5: Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStatement prog5 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStatement("a",
                                        new RefType(new RefType(
                                                new IntType()))),
                                new CompStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpression(
                                                        "v")),
                                                new PrintStatement(new VariableExpression(
                                                        "a"))
                                        )
                                )
                        )
                )
        );
        programs.add(prog5);

        // Example 6: Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStatement prog6 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStatement("a",
                                        new RefType(new RefType(
                                                new IntType()))),
                                new CompStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new PrintStatement(new RefExpression(
                                                        new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression(
                                                        "+",
                                                        new RefExpression(new RefExpression(
                                                                new VariableExpression("a"))),
                                                        new ValueExpression(
                                                                new IntValue(5))
                                                )
                                                )
                                        )
                                )
                        )
                )
        );
        programs.add(prog6);

        // Example 7: Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStatement prog7 = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new PrintStatement(new RefExpression(new VariableExpression("v"))),
                                new CompStatement(
                                        new WriteHeapStatement("v",
                                                new ValueExpression(
                                                        new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression("+",
                                                new RefExpression(new VariableExpression(
                                                        "v")),
                                                new ValueExpression(
                                                        new IntValue(5))))))));
        programs.add(prog7);

        // Example 8: Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStatement prog8 =  new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new VarDeclStatement("a",
                                        new RefType(new RefType(
                                                new IntType()))),
                                new CompStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompStatement(
                                                new NewStatement("v", new ValueExpression(
                                                        new IntValue(30))),
                                                new PrintStatement(new RefExpression(
                                                        new RefExpression(new VariableExpression(
                                                                "a")))))))));
        programs.add(prog8);

        // Example 9: int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement prog9 = new CompStatement(
                new VarDeclStatement("v", new IntType()),
                new CompStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(
                                new WhileStatement(
                                        new RelationalExpression(">",
                                                new VariableExpression("v"),
                                                new ValueExpression(
                                                        new IntValue(0))
                                        ),
                                        new CompStatement(
                                                new PrintStatement(new VariableExpression(
                                                        "v")),
                                                new AssignStatement("v",
                                                        new ArithmeticExpression("-",
                                                                new VariableExpression("v"),
                                                                new ValueExpression(
                                                                        new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));
        programs.add(prog9);

        // Example 10: int v; Ref int a; v=10; new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))
        IStatement prog10 = new CompStatement(
                new VarDeclStatement("v", new IntType()),
                new CompStatement(
                        new VarDeclStatement("a", new RefType(new IntType())),
                        new CompStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompStatement(
                                        new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompStatement(
                                                new ForkStatement(
                                                        new CompStatement(
                                                                new WriteHeapStatement("a",
                                                                        new ValueExpression(new IntValue(30))),
                                                                new CompStatement(
                                                                        new AssignStatement("v",
                                                                                new ValueExpression(new IntValue(32))),
                                                                        new PrintStatement(new VariableExpression("v"))))),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new RefExpression(new VariableExpression("a")))))))));
        programs.add(prog10);
        // Example 11: Ref (int) a; int v; new(a, 10); fork(v=20;fork(wH(a, 40); print(rH(a));); print(v);); v = 30; print(v); print(rH(a));
        IStatement prog11 = new CompStatement(
                new VarDeclStatement("a", new RefType(new IntType())),
                new CompStatement(
                        new VarDeclStatement("v", new IntType()),
                        new CompStatement(
                                new NewStatement("a", new ValueExpression(new IntValue(10))),
                                new CompStatement(
                                        new ForkStatement(
                                                new CompStatement(
                                                        new AssignStatement("v", new ValueExpression(new IntValue(20))),
                                                        new CompStatement(
                                                                new ForkStatement(
                                                                        new CompStatement(
                                                                                new WriteHeapStatement("a", new ValueExpression(new IntValue(40))),
                                                                                new CompStatement(
                                                                                        new PrintStatement(new RefExpression(new VariableExpression("a"))),
                                                                                        new PrintStatement(new VariableExpression("v"))
                                                                                )
                                                                        )
                                                                ),
                                                                new PrintStatement(new VariableExpression("v"))
                                                        )
                                                )),
                                        new CompStatement(
                                                new AssignStatement("v", new ValueExpression(new IntValue(30))),
                                                new CompStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new RefExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        programs.add(prog11);

        ObservableList<String> programStrings = FXCollections.observableArrayList();
        for (IStatement stmt : programs) {
            programStrings.add(stmt.toString());
        }
        programListView.setItems(programStrings);
    }

    @FXML
    private void executeProgram() {
        String selectedProgram = programListView.getSelectionModel().getSelectedItem();
        if (selectedProgram == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No program selected!");
            alert.showAndWait();
            return;
        }

        int index = programListView.getSelectionModel().getSelectedIndex();
        IStatement program = programs.get(index);

        try {
            ProgState prgState = new ProgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program, new MyDictionary<>(), new MyHeap<>());
            IRepository repo = new Repository(prgState, "log" + (index + 1) + ".txt");
            Service controller = new Service(repo);
            // Create and show the main window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
            Parent root = loader.load();
            MainWindowController mainWindowController = loader.getController();
            mainWindowController.setController(controller);

            Stage stage = new Stage();
            stage.setTitle("Program Execution");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
            ((Stage) programListView.getScene().getWindow()).close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error creating program state: " + e.getMessage());
            alert.showAndWait();
        }
    }
}