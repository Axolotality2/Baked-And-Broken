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
import q3aa2_tau_regaladorm.model.GameMngr;

public class HomeScreenController implements Initializable {

    @FXML
    private Button startGame;
    @FXML
    private Button loadGame;
    @FXML
    private Button openSettings;

    @FXML
    private void startNewGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/q3aa2_tau_regaladorm/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        GameMngr.getGameManager().getLevelMngr().startBlankDay();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void loadSavedGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/q3aa2_tau_regaladorm/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void openSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/q3aa2_tau_regaladorm/view/SettingsScreen.fxml"));
        
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        SettingsScreenController controller = loader.getController();
        controller.setPrevScene(((Node) event.getSource()).getScene());
        
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
