package pDTOS;

import pDTOS.ActionsDTO.ActionDTO;

import java.util.List;

public class RulesDTO
{
    private String nameOfRule;
    private int ticks;
    private double probability;
    private int numberOfActions;
    private List<ActionDTO> Actions;

    public RulesDTO(String nameOfRule, int ticks, double probability, int numberOfActions, List<ActionDTO> Actionslist) {
        this.nameOfRule = nameOfRule;
        this.ticks = ticks;
        this.probability = probability;
        this.numberOfActions = numberOfActions;
        this.Actions = Actionslist;
    }

    public List<ActionDTO> getActions()
    {
        return Actions;
    }

    public void setActions(List<ActionDTO> actions) {
        Actions = actions;
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



}
