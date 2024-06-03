package main.Homepage;

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
import javafx.stage.Stage;
import main.Settings.SettingsController;

public class HomeController implements Initializable {

    @FXML
    private void startNewGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void loadSavedGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/BakeOrBreak/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.hide();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void openSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/Settings/Settings.fxml"));
        
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        SettingsController controller = loader.getController();
        controller.setPrevScene(((Node) event.getSource()).getScene());
        
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("TAC");
    }

}
