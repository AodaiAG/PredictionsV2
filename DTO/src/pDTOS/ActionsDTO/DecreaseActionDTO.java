package pDTOS.ActionsDTO;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class DecreaseActionDTO extends ActionDTO
{
    private String propertyName;

    private String expressionStr;

    public TreeItem<String> generateTreeView()
    {
        TreeItem<String> rootItem = new TreeItem<>("Decrease Action");
        TreeItem<String> mainEntity = new TreeItem<>("Main Entity: " + this.getMainEntityNameActionWorksOn());
        TreeItem<String> secondaryEntity = new TreeItem<>("Secondary Entity: " + this.getSecondaryEntityNameActionWorksOn());
        TreeItem<String> propertyNameItem = new TreeItem<>("Property Name: " + propertyName);
        TreeItem<String> expressionStrItem = new TreeItem<>("Expression String: " + expressionStr);

        rootItem.getChildren().addAll(propertyNameItem, expressionStrItem,mainEntity,secondaryEntity);

        TreeView<String> treeView = new TreeView<>(rootItem);
        return rootItem;
    }
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getExpressionStr() {
        return expressionStr;
    }

    public void setExpressionStr(String expressionStr) {
        this.expressionStr = expressionStr;
    }
}
