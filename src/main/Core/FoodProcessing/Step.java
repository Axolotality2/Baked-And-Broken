package main.Core.FoodProcessing;

import java.util.Arrays;

public class Step {

    private final Ingredient[] input, output;
    private final Workstation station;
    private static final Step[] LEGAL_STEPS = new Step[]{};

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
        for (Step step : LEGAL_STEPS) {
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

    public static Step[] getLEGAL_STEPS() {
        return LEGAL_STEPS;
    }
}
