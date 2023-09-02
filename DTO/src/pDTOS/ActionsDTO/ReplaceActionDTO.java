package pDTOS.ActionsDTO;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ReplaceActionDTO extends ActionDTO
{
    private String entityToKill;
    private String entityToCreate;
    private String mode;


    public TreeView<String> generateTreeView() {
        TreeItem<String> rootItem = new TreeItem<>("Replace Action");
        TreeItem<String> mainEntity = new TreeItem<>("Main Entity: " + this.getMainEntityNameActionWorksOn());
        TreeItem<String> secondaryEntity = new TreeItem<>("Secondary Entity: " + this.getSecondaryEntityNameActionWorksOn());

        TreeItem<String> entityToKillItem = new TreeItem<>("Entity to Kill: " + entityToKill);
        TreeItem<String> entityToCreateItem = new TreeItem<>("Entity to Create: " + entityToCreate);
        TreeItem<String> modeItem = new TreeItem<>("Mode: " + mode);

        rootItem.getChildren().addAll(entityToKillItem, entityToCreateItem, modeItem,mainEntity,secondaryEntity);

        TreeView<String> treeView = new TreeView<>(rootItem);
        return treeView;
    }

    public String getEntityToKill() {
        return entityToKill;
    }

    public void setEntityToKill(String entityToKill) {
        this.entityToKill = entityToKill;
    }

    public String getEntityToCreate() {
        return entityToCreate;
    }

    public void setEntityToCreate(String entityToCreate) {
        this.entityToCreate = entityToCreate;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
