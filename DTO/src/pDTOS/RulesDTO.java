package pDTOS;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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


    public TreeItem<String> generateTreeView()
    {
        TreeItem<String> rootNode = new TreeItem<>("Rule: " + nameOfRule);
        TreeView<String> treeView = new TreeView<>(rootNode);

        rootNode.getChildren().add(new TreeItem<>("Name of Rule: " + nameOfRule));
        rootNode.getChildren().add(new TreeItem<>("Ticks: " + ticks));
        rootNode.getChildren().add(new TreeItem<>("Probability: " + probability));
        rootNode.getChildren().add(new TreeItem<>("Number of Actions: " + numberOfActions));

        TreeItem<String> actionsNode = new TreeItem<>("Actions");
        rootNode.getChildren().add(actionsNode);

        for (ActionDTO action : Actions)
        {

            actionsNode.getChildren().add(action.generateTreeView());

        }

        treeView.setShowRoot(false);
        return rootNode;
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
