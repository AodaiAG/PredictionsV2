package pDTOS.ActionsDTO;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class KillActionDTO extends ActionDTO
{
    private String entityToKill;


    public TreeItem<String> generateTreeView() {
        TreeItem<String> rootItem = new TreeItem<>("Kill Action");
        TreeItem<String> mainEntity = new TreeItem<>("Main Entity: " + this.getMainEntityNameActionWorksOn());
        TreeItem<String> secondaryEntity = new TreeItem<>("Secondary Entity: " + this.getSecondaryEntityNameActionWorksOn());

        TreeItem<String> entityToKillItem = new TreeItem<>("Entity to Kill: " + entityToKill);

        rootItem.getChildren().addAll(entityToKillItem,mainEntity,secondaryEntity);

        TreeView<String> treeView = new TreeView<>(rootItem);
        return rootItem;
    }
    public String getEntityToKill() {
        return entityToKill;
    }

    public void setEntityToKill(String entityToKill) {
        this.entityToKill = entityToKill;
    }
}
