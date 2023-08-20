package pDTOS;

import java.util.List;

public class RulesDTO {
    private String nameOfRule;
    private int ticks;
    private double probability;
    private int numberOfActions;
    private List<String> namesOfActions;

    public RulesDTO(String nameOfRule, int ticks, double probability, int numberOfActions, List<String> namesOfActions) {
        this.nameOfRule = nameOfRule;
        this.ticks = ticks;
        this.probability = probability;
        this.numberOfActions = numberOfActions;
        this.namesOfActions = namesOfActions;
    }

    public String getNameOfRule() {
        return nameOfRule;
    }

    public void setNameOfRule(String nameOfRule) {
        this.nameOfRule = nameOfRule;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public void setNumberOfActions(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    public List<String> getNamesOfActions() {
        return namesOfActions;
    }

    public void setNamesOfActions(List<String> namesOfActions) {
        this.namesOfActions = namesOfActions;
    }
}
