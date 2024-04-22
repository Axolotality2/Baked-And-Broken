package BakeOrBreak.model;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Workstation extends ItemReceiver {

    private static ArrayList<Workstation> workstations;
    private final String name;
    private transient Image image;
    private ArrayList<DragIngredient> contents;

    public Workstation(String name) {
        this.name = name;
        this.image = new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/" + name + ".png"));
        this.contents = new ArrayList<>();
        this.setId("station-" + name);
        this.setImage(image);

        itemZones.add(this);

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, (final MouseEvent mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    releaseContents();
                }
            }
        });
    }

    public ArrayList<DragIngredient> use() throws Exception {
        Step checkedStep = new Step((DragIngredient[]) contents.toArray(), this).reference();
        if (checkedStep == null) {
            throw new Exception();
        }

        return (this.contents = new ArrayList<>(Arrays.asList(checkedStep.getOutput())));
    }

    public void insert(DragIngredient ingredient) {
        contents.add(ingredient);
    }

    public DragIngredient[] releaseContents() {
        return (DragIngredient[]) contents.toArray();
    }

    @Override
    void put(DragIngredient ingredient) {
        insert(ingredient);
        ((Pane) ingredient.getParent()).getChildren().remove(ingredient);
    }

    public String getName() {
        return name;
    }

    public ArrayList<DragIngredient> getContents() {
        return contents;
    }

    public static ArrayList<Workstation> getWorkstations() {
        return workstations;
    }
}
