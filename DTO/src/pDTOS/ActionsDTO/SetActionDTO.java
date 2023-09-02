package pDTOS.ActionsDTO;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SetActionDTO extends ActionDTO
{
    private String entityName;
    private String propertyName;
    private String arg1;


    public TreeItem<String> generateTreeView()
    {
        TreeItem<String> rootItem = new TreeItem<>("Set Action");
        TreeItem<String> mainEntity = new TreeItem<>("Main Entity: " + this.getMainEntityNameActionWorksOn());
        TreeItem<String> secondaryEntity = new TreeItem<>("Secondary Entity: " + this.getSecondaryEntityNameActionWorksOn());
        TreeItem<String> entityNameItem = new TreeItem<>("Entity Name: " + entityName);
        TreeItem<String> propertyNameItem = new TreeItem<>("Property Name: " + propertyName);
        TreeItem<String> arg1Item = new TreeItem<>("Argument 1: " + arg1);
        rootItem.getChildren().addAll(entityNameItem, propertyNameItem, arg1Item,mainEntity,secondaryEntity);
        TreeView<String> treeView = new TreeView<>(rootItem);
        return rootItem;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }
}
