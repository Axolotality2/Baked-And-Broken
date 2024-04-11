package BakeOrBreak.model;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.image.Image;

public class Workstation {

    private static ArrayList<Workstation> workstations = new ArrayList<>();
    private String name;
    private Image image;
    private ArrayList<Ingredient> contents;

    public Workstation(String name) {
        this.name = name;
        this.image = new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/" + name + ".png"));
        this.contents = new ArrayList<>();
        workstations.add(this);
    }

    public void use() throws Exception {
        Step checkedStep = new Step((Ingredient[]) contents.toArray(), this).reference();
        if (checkedStep == null) {
            throw new Exception();
        }

        this.contents = new ArrayList<>(Arrays.asList(checkedStep.getOutput()));
    }

    public void insert(Ingredient ingredient) {
        contents.add(ingredient);
    }

    public Ingredient[] releaseProducts() {
        return (Ingredient[]) contents.toArray();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getContents() {
        return contents;
    }

    public Image getImage() {
        return image;
    }

    public static ArrayList<Workstation> getWorkstations() {
        return workstations;
    }
}
