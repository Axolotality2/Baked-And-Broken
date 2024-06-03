package main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Core.CustomerGen.Difficulty;
import main.MainGame.MainController;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Font.loadFont(getClass().getResourceAsStream("assets/cs4-proj-font.ttf"), 12);      
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainGame/Main.fxml"));
        Parent root = fxmlLoader.load();
        
        MainController ctrlr = fxmlLoader.getController();
        ctrlr.start(new Difficulty(1));
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/main/Assets/AppIcons/36.png")));
        stage.setTitle("Bake or Break");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
