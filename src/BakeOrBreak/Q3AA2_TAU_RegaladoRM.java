package BakeOrBreak;

import BakeOrBreak.Item.BaseIngredient;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Q3AA2_TAU_RegaladoRM extends Application {
    
    private final String ingFilepath = "src/BakeOrBreak/constants/ingredients.json";
    
    @Override
    public void start(Stage stage) throws Exception {
        try {
            BaseIngredient.initINGREDIENTS(ingFilepath);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.out.println("DI MAHANAP");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        Font.loadFont(getClass().getResourceAsStream("assets/cs4-proj-font.ttf"), 12);      
        
        Parent root = FXMLLoader.load(getClass().getResource("view/MainScreen.fxml"));
        
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
