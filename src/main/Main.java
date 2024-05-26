import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    
    private final String ingFilepath = "src/BakeOrBreak/constants/ingredients.json";
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Font.loadFont(getClass().getResourceAsStream("assets/cs4-proj-font.ttf"), 12);      
        
        Parent root = FXMLLoader.load(getClass().getResource("mainGame/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Bake or Break");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
