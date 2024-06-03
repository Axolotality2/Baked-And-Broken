package main.Controls;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ControlsScreenController implements Initializable {
    
    @FXML
    private Button exitBtn;
    @FXML
    private Button pauseScreen;
    @FXML
    private Button togglePantry;
    @FXML
    private Button swapStation;
    @FXML
    private Button dropContents;
    
    @FXML
    private void pressKey(ActionEvent event) {
        ((Node)event.getSource()).requestFocus();
    }
    
    @FXML
    private void changeKey(KeyEvent event) {
        Button source = (Button)event.getSource();
        
        if(!source.isFocused()) {
            return;
        }
        
        source.setText(event.getCode().getName().toLowerCase());
        source.getScene().getRoot().requestFocus();
    }
    
    @FXML
    private void saveAndExit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/BakeOrBreak/view/SettingsScreen.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
