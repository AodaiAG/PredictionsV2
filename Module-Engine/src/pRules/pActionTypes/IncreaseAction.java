package pRules.pActionTypes;

import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.ActionsDTO.IncreaseActionDTO;
import pEntity.Property;
import pEntity.EntityInstance;
import pExpression.AuxiliaryMethods;
import pExpression.Expression;

public class IncreaseAction extends Action {
    private String entityName;
    private String propertyName;
    private String expression;


    public IncreaseAction() {
        expression = "";
        propertyName = "";
        entityName = "";
    }

    @Override
    public ActionDTO convertToDTO()
    {
        IncreaseActionDTO increaseActionDTO=new IncreaseActionDTO();
        increaseActionDTO.setNameOfAction("increase");
        if (this.prDsecondaryEntity != null) {
            increaseActionDTO.setSecondaryEntityNameActionWorksOn(getPrDsecondaryEntity().getNameOfSecondEntity());

        }
        increaseActionDTO.setMainEntityNameActionWorksOn(entityName);
        increaseActionDTO.setExpressionStr(expression);
        increaseActionDTO.setPropertyName(propertyName);
        return increaseActionDTO;
    }
    @Override
    public void setFunctions(AuxiliaryMethods functions) {
        super.functions = functions;
    }

    @Override
    public String getNameOfEntity() {
        return entityName;
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
        return "increase";
    }

    @Override
    public void ActivateAction(int currTick, EntityInstance ...args) throws Exception
    {
        EntityInstance e = null;
        for(EntityInstance eI:args)
        {
            if(eI.getNameOfEntity().equals(this.entityName))
            {
                e=eI;
                break;
            }
        }

        Expression exp = new Expression(getFunctions(), e);
        String valAndDataType = exp.evaluateExpression(expression, args);
        int indexOfPeriod = valAndDataType.indexOf(".");
        String sValue = valAndDataType.substring(indexOfPeriod + 1);

        for (Property property : e.getPropertiesOfTheEntity())
        {
            if (property.getNameOfProperty().equals(propertyName)) {
                try {
                    if(property.getData().increase(sValue))
                    {
                        property.updateProperty(currTick);
                    }
                    break;
                } catch (Exception ex) {

                }
            }
        }
    }
}