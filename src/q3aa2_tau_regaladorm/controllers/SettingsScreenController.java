package q3aa2_tau_regaladorm.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class SettingsScreenController implements Initializable {
    
    @FXML
    private Slider mainSlider, ambientSlider, musicSlider;
    private Scene prevScene;
    @FXML
    private ImageView background;
    @FXML
    private ImageView frame;
    
    public void setPrevScene(Scene s) {
        this.prevScene = s;
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
        String path = new File("src/q3aa2_tau_regaladorm/view/assets/backgroundmusic.mp3").getAbsolutePath();
        Media media = new Media(new File(path).toURI().toString()); 
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.setAutoPlay(true);
            }
        });
        mediaPlayer.setAutoPlay(true);
        musicSlider.setValue(mediaPlayer.getVolume() * 100);
        musicSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable observable) {
                mediaPlayer.setVolume(musicSlider.getValue() / 100);
            }
        });
    }    
    
}
