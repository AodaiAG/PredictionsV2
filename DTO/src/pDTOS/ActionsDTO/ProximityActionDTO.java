package pDTOS.ActionsDTO;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ProximityActionDTO extends ActionDTO
{
    String sourceEntity;
    String targetEntity;
    String of;
    private int numberOfActions;

    public TreeItem<String> generateTreeView()
    {
        TreeItem<String> rootItem = new TreeItem<>("Proximity Action");

        TreeItem<String> sourceEntityItem = new TreeItem<>("Source Entity: " + sourceEntity);
        TreeItem<String> targetEntityItem = new TreeItem<>("Target Entity: " + targetEntity);
        TreeItem<String> ofItem = new TreeItem<>("Of: " + of);
        TreeItem<String> numberOfActionsItem = new TreeItem<>("Number of Actions: " + numberOfActions);

        rootItem.getChildren().addAll(sourceEntityItem, targetEntityItem, ofItem, numberOfActionsItem);
        TreeView<String> treeView = new TreeView<>(rootItem);
        return rootItem;
    }
    public String getSourceEntity() {
        return sourceEntity;
    }

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public void setNumberOfActions(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    public void setSourceEntity(String sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

    public String getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(String targetEntity) {
        this.targetEntity = targetEntity;
    }

    public String getOf() {
        return of;
    }

    public void setOf(String of) {
        this.of = of;
    }

}
