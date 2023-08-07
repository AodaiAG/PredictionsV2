package Rules.ActionTypes;

import Entity.Entity;

import java.util.ArrayList;
import java.util.List;

public  class ConditionAction implements Action
{
    private Boolean conditionResult;
    private ConditionAction condition;
    List<Action> actionsToDoIfTrue;
    List<Action> actionsToDoIfFalse;
    String nameOfEntity;


    public Boolean getConditionResult()
    {
        return conditionResult;
    }

    public void setConditionResult(Boolean conditionResult) {
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
        condition=new ConditionAction();
        nameOfEntity=new String();
        actionsToDoIfTrue=new ArrayList<>();
        actionsToDoIfFalse=new ArrayList<>();

    }



}
