package BakeOrBreak.Item.Processor;

import BakeOrBreak.Item.DragIngredient;
import BakeOrBreak.Item.IngredientData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Workstation extends ItemReceiver {

    private static ArrayList<Workstation> workstations;
    private final String name;
    private final transient Image image;
    private ArrayList<IngredientData> contents;

    public Workstation(String name) {
        this.name = name;
        this.image = new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/" + name + ".png"));
        this.contents = new ArrayList<>();
        this.setId("workstation");
        this.setImage(image);

        itemZones.add(this);

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, (final MouseEvent mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    releaseContents();
                } else if ((mouseEvent.getClickCount() == 1)) {
                    try {
                        use();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
    }

    public ArrayList<IngredientData> use() throws Exception {
        Step checkedStep = new Step(contents.toArray(new IngredientData[contents.size()]), this)
                .reference();
        if (checkedStep == null) {
            throw new Exception();
        }

        return (this.contents = new ArrayList<>(Arrays.asList(checkedStep.getOutput())));
    }

    public void insert(DragIngredient ingredient) {
        contents.add(ingredient.getIngredientData());
    }

    public void releaseContents() {
        double spawnTranslate = 0;
        int side = -1;

        for (IngredientData id : contents) {

            DragIngredient di = new DragIngredient(id);
            Bounds thisBounds = this.localToScene(this.getBoundsInLocal());
            double spawnTranslateIncrement = di.getIngredientData().getImg().getWidth() * 0.75;

            di.showAt(
                    Math.round(
                            (thisBounds.getMinX()
                            + (thisBounds.getWidth() - di.getIngredientData().getImg().getWidth()) / 2
                            + spawnTranslate) / 3) * 3,
                    Math.round(thisBounds.getMaxY() / 3) * 3 - 12);

            di.checkForIntersections();

            side *= -1;
            spawnTranslate = spawnTranslate <= 0 ? -spawnTranslate + spawnTranslateIncrement : -spawnTranslate;
        }

        contents.clear();
    }

    @Override
    public void put(DragIngredient ingredient) {
        insert(ingredient);
        ((Pane) ingredient.getParent()).getChildren().remove(ingredient);
    }

    public String getName() {
        return name;
    }

    public ArrayList<IngredientData> getContents() {
        return contents;
    }

    public static ArrayList<Workstation> getWorkstations() {
        return workstations;
    }
}
