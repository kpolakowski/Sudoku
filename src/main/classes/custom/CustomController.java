package custom;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CustomController implements Initializable{
    
    public int[][] board;
    
    @FXML
    private TextArea customGame ;
    @FXML
    private Button cancelButton ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void loadCustom() {
        if(this.customGame.getText().length()>0){
            this.board = convertStringTo2DArray(this.customGame.getText());
            exit();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Something went wrong");
            alert.setHeaderText("Array is empty or in a wrong format. ");
            alert.setContentText("There has to be 81 numbers, separated or not.\n");
            alert.showAndWait();
        }
    }
    @FXML
    public void clearCustom() {
        this.customGame.clear();
    }
    @FXML
    public void cancelCustom() {
        this.board = null;
        exit();  
    }
    
    private void exit(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
    private int[][] convertStringTo2DArray(String string){
        int[][] result = new int[9][9];
        String temp = string.replaceAll("[^0-9]", "");
        if(temp.length()==81){
            int i = 0;
            for(char t : temp.toCharArray()){
                result[i/9][i%9] =Character.getNumericValue(t);
                i++;
            }
            return result;
        } else return null;
    }
}
