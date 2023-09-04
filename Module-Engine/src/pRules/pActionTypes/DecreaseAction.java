package pRules.pActionTypes;

import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.ActionsDTO.DecreaseActionDTO;
import pEntity.Property;
import pEntity.EntityInstance;
import pExpression.*;

public class DecreaseAction extends Action
{
    private String entityName;

    private String propertyName;

    private String expressionStr;

    public DecreaseAction() {
        propertyName = "";
        entityName = "";
        expressionStr = "";
    }

    @Override
    public ActionDTO convertToDTO()
    {
        DecreaseActionDTO decreaseActionDTO=new DecreaseActionDTO();
        decreaseActionDTO.setNameOfAction("decrease");
        if (this.prDsecondaryEntity != null) {
            decreaseActionDTO.setSecondaryEntityNameActionWorksOn(getPrDsecondaryEntity().getNameOfSecondEntity());

        }
        decreaseActionDTO.setMainEntityNameActionWorksOn(entityName);
        decreaseActionDTO.setExpressionStr(expressionStr);
        decreaseActionDTO.setPropertyName(propertyName);
        return decreaseActionDTO;
    }

    @Override
    public void setFunctions(AuxiliaryMethods functions) {
        super.functions = functions;
    }

    public String getEntityName() {
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

    public String getExpressionStr() {
        return expressionStr;
    }

    public void setExpressionStr(String expressionStr) {
        this.expressionStr = expressionStr;
    }

    @Override
    public String getNameOfAction() {
        return "decrease";
    }

    @Override
    public String getNameOfEntity() {
        return entityName;
    }

    @Override
    public void ActivateAction(EntityInstance... args) throws Exception
    {
        EntityInstance e=args[0];
        for(EntityInstance eI:args)
        {
            if(eI.getNameOfEntity().equals(this.entityName))
            {
                e=eI;
                break;
            }
        }
        Expression expression = new Expression(super.getFunctions(), e);
        String strVal = expression.evaluateExpression(expressionStr);

        for (Property property : e.getPropertiesOfTheEntity()) {
            if (property.getNameOfProperty().equals(propertyName)) {
                try {
                    property.getData().decrease(strVal);
                    break;
                } catch (Exception ex) {

                }
            }
        }
    }
}