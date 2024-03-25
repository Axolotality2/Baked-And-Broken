package q3aa2_tau_regaladorm.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Workstation implements Clickable {
    private String name;
    private ArrayList<Ingredient> contents;
    
    public Workstation(String name) {
        this.name = name;
    }

    @Override
    public void click() {
        
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