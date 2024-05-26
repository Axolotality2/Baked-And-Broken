package main.Core.FoodProcessing;


import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.image.Image;

public class Workstation {

    private static ArrayList<Workstation> workstations;
    private final String name;
    private final transient Image image;
    private ArrayList<Ingredient> contents;

    public Workstation(String name) {
        this.name = name;
        this.image = new Image(getClass().getResourceAsStream("/BakeOrBreak/view/assets/" + name + ".png"));
        this.contents = new ArrayList<>();
    }

    public ArrayList<Ingredient> use() throws Exception {
        Step checkedStep = new Step((Ingredient[]) contents.toArray(), this)
                .reference();
        if (checkedStep == null) {
            throw new Exception();
        }

        return (this.contents = new ArrayList<>(Arrays.asList(checkedStep.getOutput())));
    }

    public void insert(Ingredient ingredient) {
        contents.add(ingredient);
    }

    public ArrayList<Ingredient> releaseContents() {
        ArrayList<Ingredient> arrList = new ArrayList<>(contents);
        contents.clear();
        
        return arrList;
    }

    public void put(Ingredient ingredient) {
        insert(ingredient);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getContents() {
        return contents;
    }

    public static ArrayList<Workstation> getWorkstations() {
        return workstations;
    }

    public Image getImage() {
        return image;
    }
}
