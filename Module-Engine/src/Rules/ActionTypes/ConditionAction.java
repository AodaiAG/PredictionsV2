package Rules.ActionTypes;

import Entity.Entity;

import java.util.ArrayList;
import java.util.List;
import Entity.EntityInstance;


public  class ConditionAction extends Action
{
    private Boolean conditionResult;
    private ConditionAction condition;
    private List<Action> actionsToDoIfTrue;
    private List<Action> actionsToDoIfFalse;

    public ConditionAction getCondition() {
        return condition;
    }

    public void setCondition(ConditionAction condition) {
        this.condition = condition;
    }

    public  List<Action> getActionsToDoIfTrue() {
        return actionsToDoIfTrue;
    }

    public void setActionsToDoIfTrue(List<Action> actionsToDoIfTrue) {
        this.actionsToDoIfTrue = actionsToDoIfTrue;
    }

    public  List<Action> getActionsToDoIfFalse() {
        return actionsToDoIfFalse;
    }

    public void setActionsToDoIfFalse(List<Action> actionsToDoIfFalse) {
        this.actionsToDoIfFalse = actionsToDoIfFalse;
    }

    public Boolean getConditionResult()
    {
        return conditionResult;
    }

    public void setConditionResult(Boolean conditionResult)
    {
        this.conditionResult = conditionResult;
    }
    @Override
    public String getNameOfAction()
    {
        return "condition";
    }

    @Override
    public void ActivateAction(EntityInstance e) throws Exception {

        condition.ActivateAction(e);
        conditionResult=condition.getConditionResult();
        if(conditionResult)
        {
            if(actionsToDoIfTrue.size()!=0)
            {
                for(Action a:actionsToDoIfTrue)
                {
                    a.ActivateAction(e);
                }
            }
        }
        else
        {
            if(actionsToDoIfFalse.size()!=0)
            {
                for(Action a:actionsToDoIfFalse)
                {
                    a.ActivateAction(e);
                }
            }
        }
    }

    public ConditionAction()
    {
        actionsToDoIfTrue=new ArrayList<>();
        actionsToDoIfFalse=new ArrayList<>();
    }
}