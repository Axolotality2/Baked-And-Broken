package BakeOrBreak.Item.Processor;

import BakeOrBreak.Item.IngredientData;
import java.util.Arrays;

public class Step {

    private final IngredientData[] input, output;
    private final Workstation station;
    private static final Step[] legalSteps = new Step[]{};

    public Step(IngredientData[] input, IngredientData[] output, Workstation station) {
        this.input = input;
        this.output = output;
        this.station = station;
    }

    public Step(IngredientData[] input, Workstation station) {
        this.input = input;
        this.output = null;
        this.station = station;
    }

    public Step reference() {
        for (Step step : legalSteps) {
            if (Arrays.equals(getInput(), step.getInput())
                    && station.equals(step.getStation())) {
                return step;
            }
        }
        
        return null;
    }

    public IngredientData[] getInput() {
        return input;
    }

    public IngredientData[] getOutput() {
        return output;
    }

    public Workstation getStation() {
        return station;
    }

    public static Step[] getLegalSteps() {
        return legalSteps;
    }
}
