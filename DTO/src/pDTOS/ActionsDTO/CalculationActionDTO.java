package pDTOS.ActionsDTO;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class CalculationActionDTO extends ActionDTO
{

    private String calType;
    private String arg1;
    private String arg2;

    @Override
    public TreeView<String> generateTreeView()
    {

        TreeItem<String> rootItem = new TreeItem<>("Calculation Action");
        TreeItem<String> mainEntity = new TreeItem<>("Main Entity: " + this.getMainEntityNameActionWorksOn());
        TreeItem<String> secondaryEntity = new TreeItem<>("Secondary Entity: " + this.getSecondaryEntityNameActionWorksOn());
        TreeItem<String> calTypeItem = new TreeItem<>("Calculation Type: " + calType);
        TreeItem<String> arg1Item = new TreeItem<>("Argument 1: " + arg1);
        TreeItem<String> arg2Item = new TreeItem<>("Argument 2: " + arg2);

        rootItem.getChildren().addAll(calTypeItem, arg1Item, arg2Item,mainEntity,secondaryEntity);

        TreeView<String> treeView = new TreeView<>(rootItem);
        return treeView;
    }

    public String getCalType() {
        return calType;
    }

    public void setCalType(String calType) {
        this.calType = calType;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }
}
