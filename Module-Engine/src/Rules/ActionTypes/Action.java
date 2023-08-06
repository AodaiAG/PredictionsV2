package Rules.ActionTypes;

import Entity.Entity;

public abstract class Action
{


    private String typeOfAction;
    public String getTypeOfAction() {
        return typeOfAction;
    }

    abstract void ActivateAction( Entity e);

    public Action(String actionType)
    {
        typeOfAction=new String();
        typeOfAction = actionType;


    }
}
