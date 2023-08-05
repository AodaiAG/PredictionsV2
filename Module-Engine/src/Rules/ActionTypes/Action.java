package Rules.ActionTypes;

public abstract class Action
{
    String type;
    abstract void ActivateAction();

    public Action(String actionType)
    {
        type=new String();
        type = actionType;
    }
}
