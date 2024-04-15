package BakeOrBreak.model;

import java.util.Arrays;

public class Step {

    private final DragIngredient[] input, output;
    private final Workstation station;
    private static final Step[] legalSteps = new Step[]{};

    public Step(DragIngredient[] input, DragIngredient[] output, Workstation station) {
        this.input = input;
        this.output = output;
        this.station = station;
    }

    public Step(DragIngredient[] input, Workstation station) {
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

    public DragIngredient[] getInput() {
        return input;
    }

    public DragIngredient[] getOutput() {
        return output;
    }

    public Workstation getStation() {
        return station;
    }

    public static Step[] getLegalSteps() {
        return legalSteps;
    }
}
