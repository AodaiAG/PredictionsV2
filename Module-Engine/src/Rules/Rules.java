package Rules;

import Rules.ActionTypes.Action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rules
{

    private String nameOfRule;
    private String whenActivated;
    private int ticks=1;
    private double probability;
    private List<Action> actions;

    public String getNameOfRule() {
        return nameOfRule;
    }

    public void setNameOfRule(String nameOfRule) {
        this.nameOfRule = nameOfRule;
    }

    public String getWhenActivated() {
        return whenActivated;
    }

    public void setWhenActivated(String whenActivated) {
        this.whenActivated = whenActivated;
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

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Rules()
    {
        actions=new ArrayList<>();
    }
}
