package pRules.pActionTypes;

import java.util.ArrayList;
import java.util.List;

import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.ActionsDTO.ConditionActionDTO;
import pEntity.EntityInstance;
import pExpression.AuxiliaryMethods;

public class ConditionAction extends Action
{
    private String entityName;

    private Boolean conditionResult;

    private ConditionAction condition;

    private List<Action> actionsToDoIfTrue;

    private List<Action> actionsToDoIfFalse;

    @Override
    public ActionDTO convertToDTO()
    {
        ConditionActionDTO conditionActionDTO=new ConditionActionDTO();
        conditionActionDTO.setNameOfAction("condition");
        if (this.prDsecondaryEntity != null) {
            conditionActionDTO.setSecondaryEntityNameActionWorksOn(getPrDsecondaryEntity().getNameOfSecondEntity());

        }
        conditionActionDTO.setMainEntityNameActionWorksOn(entityName);
        conditionActionDTO.setNumberOfActionsInThen(actionsToDoIfTrue.size());
        conditionActionDTO.setNumberOfActionsInElse(actionsToDoIfFalse.size());


        return conditionActionDTO;
    }

    public ConditionAction getCondition() {
        return condition;
    }

    public String getNameOfEntity() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public void setFunctions(AuxiliaryMethods functions) {
        super.functions = functions;
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

    public Boolean getConditionResult() {
        return conditionResult;
    }

    public void setConditionResult(Boolean conditionResult) {
        this.conditionResult = conditionResult;
    }

    @Override
    public String getNameOfAction() {
        return "condition";
    }

    @Override
    public void ActivateAction(int currTick, EntityInstance... args) throws Exception
    {
        EntityInstance e = null;
        for(EntityInstance eI : args)
        {
            if(eI.getNameOfEntity().equals(this.entityName))
            {
                e = eI;
                break;
            }
        }
        condition.ActivateAction(currTick, args);
        conditionResult = condition.getConditionResult();
        if (conditionResult) {
            System.out.println("condition = true");
            if (!actionsToDoIfTrue.isEmpty()) {
                for (Action a : actionsToDoIfTrue) {
                    a.ActivateAction(currTick, args);
                }
            }
        } else {
            if (!actionsToDoIfFalse.isEmpty()) {
                System.out.println("condition = false");
                for (Action a : actionsToDoIfFalse) {
                    a.ActivateAction(currTick, args);
                }
            }
        }
    }

    public ConditionAction() {

        actionsToDoIfTrue = new ArrayList<>();
        actionsToDoIfFalse = new ArrayList<>();
    }
}