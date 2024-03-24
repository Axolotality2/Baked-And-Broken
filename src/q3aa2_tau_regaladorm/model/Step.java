package q3aa2_tau_regaladorm.model;

import java.util.Arrays;

public class Step {

    private final Ingredient[] input, output;
    private final Workstation station;
    private final String useMethod;
    private static final Step[] legalSteps = new Step[]{};

    public Step(Ingredient[] input, Ingredient[] output, Workstation station, String useMethod) {
        this.input = input;
        this.output = output;
        this.station = station;
        this.useMethod = useMethod;
    }

    public Step(Ingredient[] input, Workstation station, String useMethod) {
        this.input = input;
        this.output = null;
        this.station = station;
        this.useMethod = useMethod;
    }

    public Step reference() {
        for (Step step : legalSteps) {
            if (Arrays.equals(getInput(), step.getInput())
                    && station.equals(step.getStation())
                    && useMethod.equals(step.getUseMethod())) {
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

    public String getUseMethod() {
        return useMethod;
    }

    public static Step[] getLegalSteps() {
        return legalSteps;
    }
}
