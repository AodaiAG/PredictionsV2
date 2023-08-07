package Rules.ActionTypes;

import Entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class MultipleCondition extends ConditionAction
{
    private Boolean conditionResult;
    String logical;
    List<ConditionAction> listOfConditions;

    @Override
    public void ActivateAction(Entity e)
    {

            switch (logical)
            {
                    case "and":
                    {
                        for (ConditionAction c:listOfConditions)
                        {

                            c.ActivateAction(e);
                            Boolean result=c.getConditionResult();
                            if(result==false)
                            {
                                conditionResult=false;
                                break;
                            }

                        }

                        break;
                    }
                    case"or":
                    {
                        conditionResult=false;
                        for (ConditionAction c:listOfConditions)
                        {
                            c.ActivateAction(e);
                            Boolean result=c.getConditionResult();

                            if(result==true)
                            {
                                conditionResult=true;
                            }

                        }
                        break;

                    }

        }
    }

    public MultipleCondition()
    {
        logical=new String();
        listOfConditions=new ArrayList<>();
        conditionResult=true;
    }


}
