package outcoded;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application{
    private SudokuModel m;
    
    @Override
    public void start(Stage stage) throws Exception {
        m = new SudokuModel();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("app.fxml"));
        
        BorderPane root = fxmlLoader.load();
        Controller controller = fxmlLoader.<Controller>getController();
        
        stage.setTitle("Sudoku");
        stage.setScene(new Scene(root, 800, 600));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
    
}
