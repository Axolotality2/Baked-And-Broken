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

    private static WeightedDist<BaseIngredient> ALLERGENS;
    private static BaseIngredient[] INGREDIENTS;

    private final String name;
    private final boolean isFood;
    private final boolean isAllergen;

    public BaseIngredient(String name, boolean isFood, boolean isAllergen) {
        this.name = name;
        this.isFood = isFood;
        this.isAllergen = isAllergen;

        this.setImage(new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/food/" + name + ".png")));
        this.setId("base-" + name);
    }

    public static WeightedDist<BaseIngredient> getALLERGENS() {
        return ALLERGENS;
    }

    public String getName() {
        return name;
    }

    public boolean isFood() {
        return isFood;
    }

    public boolean isAllergen() {
        return isAllergen;
    }

    public static void initINGREDIENTS(String filepath) throws FileNotFoundException, IOException {
        FileReader ingFile = new FileReader(filepath);
        String contents = "";

        int i;
        while ((i = ingFile.read()) != -1) {
            contents += (char) i;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JSONIngredient[] jsonIngs = gson.fromJson(contents, JSONIngredient[].class);
        BaseIngredient[] baseIngs = new BaseIngredient[jsonIngs.length];
        for (i = 0; i < jsonIngs.length; i++) {
            baseIngs[i] = jsonIngs[i].toBaseIngredient();
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
