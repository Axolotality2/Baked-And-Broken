package BakeOrBreak.model;

import java.util.Arrays;

public class Step {

    private final Ingredient[] input, output;
    private final Workstation station;
    private static final Step[] legalSteps = new Step[]{};

    public Step(Ingredient[] input, Ingredient[] output, Workstation station) {
        this.input = input;
        this.output = output;
        this.station = station;
    }

    public Step(Ingredient[] input, Workstation station) {
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

    public Ingredient[] getInput() {
        return input;
    }

    public Ingredient[] getOutput() {
        return output;
    }

    public Workstation getStation() {
        return station;
    }

    public static Step[] getLegalSteps() {
        return legalSteps;
    }
}
