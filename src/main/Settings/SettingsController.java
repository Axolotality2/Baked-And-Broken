package main.Settings;

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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SettingsController implements Initializable {

    @FXML
    private Pane rt;
    @FXML
    private Button maximize, controls, back, quitGame;
    @FXML
    private Scene prevScene;
    @FXML
    private Slider mainSlider, ambientSlider, musicSlider;
    @FXML
    private ImageView background, frame;

    private final double BASE_WIDTH = 360;
    private final double BASE_HEIGHT = 270;

    public void setPrevScene(Scene s) {
        this.prevScene = s;
    }

    @FXML
    public void toggleMaximize(ActionEvent event) {
        Stage currentStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        currentStage.setFullScreen(!currentStage.isFullScreen());
        
        if (currentStage.isFullScreen()) {
            if (currentStage.getWidth() / 4 < currentStage.getHeight() / 3) {
                rt.setScaleX(currentStage.getWidth() / BASE_WIDTH);
                rt.setScaleY(currentStage.getWidth() / BASE_WIDTH);
            } else {
                rt.setScaleX(currentStage.getHeight() / BASE_HEIGHT);
                rt.setScaleY(currentStage.getHeight() / BASE_HEIGHT);
            }
        } else {
            rt.setScaleX(1);
            rt.setScaleY(1);
        }
    }

    @FXML
    private void openControls(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/BakeOrBreak/view/ControlsScreen.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("/BakeOrBreak/view/HomeScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
        stage.show();
    }

    @FXML
    private void quitGame() {
        javafx.application.Platform.exit();
    }

//    private void groupChildrenTransform() {
//        rt.getChildren().add(new Group(rt.getChildren()));
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        groupChildrenTransform();
        String path = new File("src/main/Assets/backgroundmusic.mp3").getAbsolutePath();
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
        
        mainSlider.setValue(mediaPlayer.getVolume() * 100);
        mainSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable observable) {
                mediaPlayer.setVolume(mainSlider.getValue() / 100);
            }
        });
        
        musicSlider.setValue(mediaPlayer.getVolume() * 100);
        musicSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable observable) {
                mediaPlayer.setVolume((musicSlider.getValue() * (mainSlider.getValue() / 100)) / 100);
            }
        });
    }

}
