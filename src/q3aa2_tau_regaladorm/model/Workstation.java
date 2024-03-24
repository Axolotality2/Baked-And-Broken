package q3aa2_tau_regaladorm.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Workstation implements Clickable {
    private String name;
    private ArrayList<Ingredient> contents;
    private final String[] useMethods;
    
    public Workstation(String name, String[] useMethods) {
        this.name = name;
        this.useMethods = useMethods;
    }

    @Override
    public void click() {
        
    }
    
    public void use(String useMethod) throws Exception {
        if (!Arrays.asList(useMethods).contains(useMethod))
            throw new Exception();
        
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

    public String[] getUseMethods() {
        return useMethods;
    }
}