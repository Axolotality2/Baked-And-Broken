package q3aa2_tau_regaladorm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Q3AA2_TAU_RegaladoRM extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("assets/cs4-proj-font.ttf"), 12);      
        
        Parent root = FXMLLoader.load(getClass().getResource("view/HomeScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Bake or Break");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
