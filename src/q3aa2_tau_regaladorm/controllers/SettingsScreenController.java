package q3aa2_tau_regaladorm.controllers;

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
import javafx.stage.Stage;

public class SettingsScreenController implements Initializable {

    @FXML 
    private Button maximize;
    @FXML 
    private Button controls;
    @FXML 
    private Button back;
    @FXML 
    private Button quitGame;
    private Scene prevScene;
    
    @FXML
    public void setPrevScene(Scene s) {
        this.prevScene = s;
    }
    
    @FXML
    private void toggleMaximize() {
        
    }
    
    @FXML 
    private void openControls(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/q3aa2_tau_regaladorm/view/ControlsScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.hide();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML 
    private void back(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.hide();
        if (prevScene != null) {
            stage.setScene(prevScene);
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/q3aa2_tau_regaladorm/view/HomeScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
        stage.show();
    }
    
    @FXML 
    private void quitGame() {
        javafx.application.Platform.exit();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
