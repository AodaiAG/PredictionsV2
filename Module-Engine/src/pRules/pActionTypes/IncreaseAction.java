package pRules.pActionTypes;

import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.ActionsDTO.DecreaseActionDTO;
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
        increaseActionDTO.setSecondaryEntityNameActionWorksOn(getPrDsecondaryEntity().nameOfSecondEntity);
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
    public void ActivateAction(EntityInstance ...args) throws Exception
    {
        EntityInstance e=args[0];
        Expression exp = new Expression(getFunctions(), e);
        String sValue = exp.evaluateExpression(expression);

        for (Property property : e.getPropertiesOfTheEntity()) {
            if (property.getNameOfProperty().equals(propertyName)) {
                try {
                    property.getData().increase(sValue);
                    break;
                } catch (Exception ex) {

                }
            }
        }
    }
}