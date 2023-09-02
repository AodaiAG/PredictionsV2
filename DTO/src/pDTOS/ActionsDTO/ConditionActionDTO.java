package pDTOS.ActionsDTO;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ConditionActionDTO extends ActionDTO
{
   private int numberOfActionsInThen;
   private int numberOfActionsInElse;
   private String singularity;

    public TreeItem<String> generateTreeView()
    {
        TreeItem<String> rootItem = new TreeItem<>("Condition Action");
        TreeItem<String> mainEntity = new TreeItem<>("Main Entity: " + this.getMainEntityNameActionWorksOn());
        TreeItem<String> secondaryEntity = new TreeItem<>("Secondary Entity: " + this.getSecondaryEntityNameActionWorksOn());
        TreeItem<String> thenItem = new TreeItem<>("Number of Actions in 'Then': " + numberOfActionsInThen);
        TreeItem<String> elseItem = new TreeItem<>("Number of Actions in 'Else': " + numberOfActionsInElse);
        TreeItem<String> singularityItem = new TreeItem<>("Singularity: " + singularity);

        rootItem.getChildren().addAll(thenItem, elseItem, singularityItem,mainEntity,secondaryEntity);

        TreeView<String> treeView = new TreeView<>(rootItem);
        return rootItem;
    }
    public int getNumberOfActionsInThen() {
        return numberOfActionsInThen;
    }

    public void setNumberOfActionsInThen(int numberOfActionsInThen) {
        this.numberOfActionsInThen = numberOfActionsInThen;
    }

    public int getNumberOfActionsInElse() {
        return numberOfActionsInElse;
    }

    public void setNumberOfActionsInElse(int numberOfActionsInElse) {
        this.numberOfActionsInElse = numberOfActionsInElse;
    }

    public String getSingularity() {
        return singularity;
    }

    public void setSingularity(String singularity) {
        this.singularity = singularity;
    }


}
