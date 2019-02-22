package outcoded;

import custom.CustomController;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller implements Initializable{
    private SudokuModel model;
    private CustomController controller;
    private SudokuSolver solver;
    
    @FXML
    private GridPane gameBoard ;
    @FXML
    private ComboBox level;
    
    public Map levels = new HashMap<String,Integer>();
    
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.model=new SudokuModel();
        this.solver = new SudokuSolver(this.model);
        this.levels.put("Easy", 5);
        this.levels.put("Medium", 6);
        this.levels.put("Hard", 7);
        level.getItems().addAll(this.levels.keySet());
        level.getSelectionModel().selectFirst();
        newGame(false);
    }
    
    private void fillBoard(){
        this.gameBoard.getChildren().clear();
        boolean[] reds = new boolean[ this.model.getBoard().length*this.model.getBoard().length];
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++){
                if(i/3==0 && j%9<6 && j%9>2) reds[i*9+j] = true;
                if(i/3==2 && j%9<6 && j%9>2) reds[i*9+j] = true;
                if(i/3==1 && j%9<3 && j%9>=0) reds[i*9+j] = true;
                if(i/3==1 && j%9<=8 && j%9>5) reds[i*9+j] = true;
            }   
                
        for(int i = 0; i < this.model.getBoard().length; i++)
            for(int j = 0; j < this.model.getBoard().length; j++){
                int v = this.model.getBoard()[i][j];
                
                NumberField numberField = new NumberField(i *9 + j,v);
                if(v==0){
                    numberField.setOnMouseClicked((t) -> {
                        numberField.increase();
                        this.model.getBoard()[numberField.getIndex()/9][numberField.getIndex()%9] = numberField.getValue();
                        if(this.model.isSolved()){
                            displayAlert(Alert.AlertType.INFORMATION,"Congratulation","Game has been solved","You have won").showAndWait();
                        }
                    });
                }
                if(reds[numberField.getIndex()])
                    numberField.getStyleClass().add("red");
                this.gameBoard.add(numberField,j,i);
            }
    }
    
    public void newGame(boolean customBoard) {
        this.gameBoard.getChildren().clear();
        if(customBoard) this.model.loadCustomBoard(controller.board); else {
            this.model.setLevel((int)levels.get(level.getSelectionModel().getSelectedItem()));
            this.model.initBoard();
        }
        fillBoard();
    }
    
    
    @FXML
    public void exitGameAction() {
        Stage stage = (Stage) gameBoard.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void newGameAction(){
        newGame(false);
    }
    @FXML
    public void customGameAction() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/custom/custom.fxml"));
        BorderPane root1 = fxmlLoader.load();
        controller = fxmlLoader.<CustomController>getController();
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Load custom game");
        stage.setScene(new Scene(root1));  
        stage.show();
        stage.setOnCloseRequest((t) -> {
            if(controller.board!=null){
                newGame(true);
            }
        });
    }
    @FXML
    public void solveGameAction(){
        if(this.model.isValid()){
            solver.Solve(this.model.getBoard());
            fillBoard();
            if(this.model.isSolved()){
                displayAlert(Alert.AlertType.INFORMATION,"Congratulation","Game has been solved","You have won").showAndWait();
            }
        } else {
            displayAlert(Alert.AlertType.ERROR,"Error","Incorrect board","Please check if the board is correct").showAndWait();
        }
    }
    
    public Alert displayAlert(Alert.AlertType type,String title, String header, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }
}
