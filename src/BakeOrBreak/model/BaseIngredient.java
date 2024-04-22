package BakeOrBreak.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class BaseIngredient extends ImageView {

    private static WeightedDist<String> ALLERGENS;
    private static BaseIngredient[] INGREDIENTS;
    
    private final IngredientData ingredientData;

    public BaseIngredient(IngredientData data) {
        this.ingredientData = data;
        this.setImage(new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/food/" + data.getName() + ".png")));
        this.setId("BASE-" + data.getName());
    }

    public IngredientData getIngredientData() {
        return ingredientData;
    }
    
    public static WeightedDist<String> getALLERGENS() {
        return ALLERGENS;
    }

    public static void initINGREDIENTS(String filepath) throws FileNotFoundException, IOException {
        FileReader ingFile = new FileReader(filepath);
        String contents = "";

        int i;
        while ((i = ingFile.read()) != -1) {
            contents += (char) i;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        IngredientData[] jsonIngs = gson.fromJson(contents, IngredientData[].class);
        BaseIngredient[] baseIngs = new BaseIngredient[jsonIngs.length];
        for (int j = 0; j < jsonIngs.length; j++) {
            baseIngs[j] = new BaseIngredient(new IngredientData(jsonIngs[j]));
        }

        INGREDIENTS = baseIngs;
    }

    public static void show(Pane pantry, Pane dragPane) {
        for (BaseIngredient bi : INGREDIENTS) {
            bi.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent event) -> {
                DragIngredient dragIngredient = new DragIngredient(bi);

                dragPane.getChildren().add(dragIngredient);
                dragIngredient.requestFocus();
            });
        }

        pantry.getChildren().addAll(INGREDIENTS);
    }
}
