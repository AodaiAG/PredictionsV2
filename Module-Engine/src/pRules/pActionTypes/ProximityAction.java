package pRules.pActionTypes;

import pEntity.EntityInstance;
import pExpression.AuxiliaryMethods;

public class ProximityAction extends Action
{

    String sourceEntity;
    String targetEntity;
    String of;


    @Override
    public void setFunctions(AuxiliaryMethods functions)
    {

    }

    @Override
    public void ActivateAction(EntityInstance... args) throws Exception
    {


    }

    @Override
    public String getNameOfAction() {
        return null;
    }

    @Override
    public String getNameOfEntity() {
        return null;
    }
}
