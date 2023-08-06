package Rules.ActionTypes;

import Entity.Entity;

public class ConditionAction extends Action
{
    @Override
    public String getTypeOfAction() {
        return super.getTypeOfAction();
    }

    @Override
    void ActivateAction(Entity e) {

    }

    public ConditionAction(String actionType) {
        super(actionType);
    }
}
