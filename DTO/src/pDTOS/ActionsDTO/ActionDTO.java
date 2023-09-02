package pDTOS.ActionsDTO;

import javafx.scene.control.TreeView;

public abstract class ActionDTO
{
    protected String nameOfAction;
    protected String mainEntityNameActionWorksOn;
    protected String secondaryEntityNameActionWorksOn;

    public String getNameOfAction() {
        return nameOfAction;
    }

    public abstract TreeView generateTreeView();

    public void setNameOfAction(String nameOfAction) {
        this.nameOfAction = nameOfAction;
    }

    public String getMainEntityNameActionWorksOn()
    {
        return mainEntityNameActionWorksOn;
    }

    public void setMainEntityNameActionWorksOn(String mainEntityNameActionWorksOn) {
        this.mainEntityNameActionWorksOn = mainEntityNameActionWorksOn;
    }

    public String getSecondaryEntityNameActionWorksOn() {
        return secondaryEntityNameActionWorksOn;
    }

    public void setSecondaryEntityNameActionWorksOn(String secondaryEntityNameActionWorksOn) {
        this.secondaryEntityNameActionWorksOn = secondaryEntityNameActionWorksOn;
    }
}
