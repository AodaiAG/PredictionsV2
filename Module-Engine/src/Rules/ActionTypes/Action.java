package Rules.ActionTypes;

import Entity.Entity;

public abstract class Action
{
    String type;
    public String expression;
    abstract void ActivateAction( Entity e);

    public Action(String actionType)
    {
        type=new String();
        type = actionType;
        expression=new String();

    }
}
