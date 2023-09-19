package pRules.pActionTypes;

import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.ActionsDTO.SetActionDTO;
import pEntity.Property;
import pEntity.EntityInstance;
import pExpression.AuxiliaryMethods;
import pExpression.Expression;

public class SetAction extends Action
{
    private String entityName;
    private String propertyName;
    private String expression;

    public SetAction() {
        entityName = "";
        propertyName = "";
        expression = "";
    }

    public ActionDTO convertToDTO()
    {

        SetActionDTO setActionDTO=new SetActionDTO();
        setActionDTO.setMainEntityNameActionWorksOn(entityName);
        setActionDTO.setNameOfAction("set");
        if (this.prDsecondaryEntity != null) {
            setActionDTO.setSecondaryEntityNameActionWorksOn(getPrDsecondaryEntity().getNameOfSecondEntity());

        }
        setActionDTO.setArg1(expression);
        setActionDTO.setPropertyName(propertyName);

        return setActionDTO;
    }

    @Override
    public String getNameOfEntity() {
        return entityName;
    }

    @Override
    public void setFunctions(AuxiliaryMethods functions) {
        super.functions = functions;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String getNameOfAction() {
        return "set";
    }

    @Override
    public void ActivateAction(int currTick, EntityInstance ...args) throws Exception
    {
        //find according name
        EntityInstance e = args[0];
        for(EntityInstance eI : args)
        {
            if(eI.getNameOfEntity().equals(this.entityName))
            {
                e = eI;
                break;
            }
        }
        Expression exp = new Expression(getFunctions(), e);
        String dataTypeAndValue =  exp.evaluateExpression(expression);
        int indexOfPeriod = dataTypeAndValue.indexOf(".");
        String sValue = dataTypeAndValue.substring(indexOfPeriod + 1);
        for (Property t : e.getPropertiesOfTheEntity()) {
            if (t.getNameOfProperty().equals(propertyName)) {
                try {
                    if(t.getData().setNewValue(sValue))
                    {
                        t.updateProperty(currTick);
                    }
                } catch (Exception ex) {

                }
                break;
            }
        }
    }
}