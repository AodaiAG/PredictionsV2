package Rules.ActionTypes;

import Entity.Entity;

import java.util.ArrayList;
import java.util.List;

public  class ConditionAction implements Action
{
    private Boolean conditionResult;
    private ConditionAction condition;
    private List<Action> actionsToDoIfTrue;
    private List<Action> actionsToDoIfFalse;
    private String nameOfEntity;

    public ConditionAction getCondition() {
        return condition;
    }

    public void setCondition(ConditionAction condition) {
        this.condition = condition;
    }

    public List<Action> getActionsToDoIfTrue() {
        return actionsToDoIfTrue;
    }

    public void setActionsToDoIfTrue(List<Action> actionsToDoIfTrue) {
        this.actionsToDoIfTrue = actionsToDoIfTrue;
    }

    public List<Action> getActionsToDoIfFalse() {
        return actionsToDoIfFalse;
    }

    public void setActionsToDoIfFalse(List<Action> actionsToDoIfFalse) {
        this.actionsToDoIfFalse = actionsToDoIfFalse;
    }

    public String getNameOfEntity() {
        return nameOfEntity;
    }

    public void setNameOfEntity(String nameOfEntity) {
        this.nameOfEntity = nameOfEntity;
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
    public void ActivateAction(Entity e)
    {

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

        nameOfEntity=new String();
        actionsToDoIfTrue=new ArrayList<>();
        actionsToDoIfFalse=new ArrayList<>();

    }



}
