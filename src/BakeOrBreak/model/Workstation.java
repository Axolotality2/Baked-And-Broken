package BakeOrBreak.model;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.image.Image;

public class Workstation{
    private String name;
    private Image image;
    private ArrayList<Ingredient> contents;
    
    public Workstation(String name, Image image) {
        this.name = name;
        this.image = image;
    }
    
    public void use(String useMethod) throws Exception{
        Step checkedStep = new Step((Ingredient[]) contents.toArray(), this, useMethod).reference();
        if (checkedStep == null)
            throw new Exception();
        
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
}