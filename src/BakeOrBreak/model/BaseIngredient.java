package BakeOrBreak.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class BaseIngredient extends ImageView {

    private static final WeightDist<String> ALLERGENS = new WeightDist<>();
    private static final ArrayList<BaseIngredient> INGREDIENTS = new ArrayList<>();
    private static Pane dragPane;

    private final IngredientData ingredientData;

    public BaseIngredient(IngredientData data) {
        this.ingredientData = data;
        this.setImage(new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/food/" + data.getName() + ".png")));
        this.setId("BASE-" + data.getName());
    }

    public static void initINGREDIENTS(String filepath) throws FileNotFoundException, IOException {
        System.out.println("GOMBURZA");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader ingFile = new FileReader(filepath);
        IngredientData[] jsonIngs;
        String contents = "";

        int i;
        while ((i = ingFile.read()) != -1) {
            contents += (char) i;
        }

        jsonIngs = gson.fromJson(contents, IngredientData[].class);
        for (IngredientData jsonIng : jsonIngs) {
            INGREDIENTS.add(new BaseIngredient(new IngredientData(jsonIng)));
            for (String allergen : jsonIng.getAllergens()) {
                if(!ALLERGENS.getValues().contains(allergen)) {
                    ALLERGENS.addEntry(allergen, 1);
                }
            }
        }
    }

    public static void show(Pane pantry) {
        for (BaseIngredient bi : INGREDIENTS) {
            bi.addEventFilter(MouseEvent.MOUSE_PRESSED, (final MouseEvent event) -> {
                new DragIngredient(bi);
            });
            
            System.out.println(bi);
        }

        pantry.getChildren().addAll(INGREDIENTS);
    }

    public static String getAllergen() {
        return ALLERGENS.pickRandom();
    }

    public static String[] getAllergen(int count) {
        return ALLERGENS.pickRandom(count);
    }

    public IngredientData getIngredientData() {
        return ingredientData;
    }
    
    public static void setDragPane(Pane p) {
        dragPane = p;
    }
}
