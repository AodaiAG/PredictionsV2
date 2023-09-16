package pRules.pActionTypes;

import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.ActionsDTO.CalculationActionDTO;
import pEntity.Property;
import pEntity.EntityInstance;
import pExpression.Expression;
import pExpression.AuxiliaryMethods;

public class CalculationAction extends Action
{
    private String entityName;

    private String typeOfCondition;

    private String resultProp;

    @Override
    public void setFunctions(AuxiliaryMethods functions) {
        super.functions = functions;
    }

    private String calType;

    private String expression1;

    private String expression2;

    public CalculationAction() {
        resultProp = "";
        entityName = "";
        typeOfCondition = "";
        calType = "";
        expression1 = "";
        expression2 = "";
        typeOfCondition = "calculation";
    }

    @Override
    public ActionDTO convertToDTO()
    {
        CalculationActionDTO calculationActionDTO=new CalculationActionDTO();
        if (this.prDsecondaryEntity != null) {
            calculationActionDTO.setSecondaryEntityNameActionWorksOn(getPrDsecondaryEntity().getNameOfSecondEntity());

        }
        calculationActionDTO.setNameOfAction("calculation");
        calculationActionDTO.setMainEntityNameActionWorksOn(entityName);
        calculationActionDTO.setCalType(calType);
        calculationActionDTO.setArg1(expression1);
        calculationActionDTO.setArg2(expression2);
        return calculationActionDTO;

    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String getNameOfEntity() {
        return entityName;
    }

    public String getTypeOfCondition() {
        return typeOfCondition;
    }

    public String getNameOfAction() {
        return "calculation";
    }

    public String getResultProp() {
        return resultProp;
    }

    public void setResultProp(String resultProp) {
        this.resultProp = resultProp;
    }

    public String getCalType() {
        return calType;
    }

    public void setCalType(String calType) {
        this.calType = calType;
    }

    public String getExpression1() {
        return expression1;
    }

    public void setExpression1(String expression1) {
        this.expression1 = expression1;
    }

    public String getExpression2() {
        return expression2;
    }

    public void setExpression2(String expression2) {
        this.expression2 = expression2;
    }

    @Override
    public void ActivateAction(int currTick, EntityInstance...  args) throws Exception
    {
        EntityInstance entityInstance = args[0];
        for(EntityInstance eI:args)
        {
            if(eI.getNameOfEntity().equals(this.entityName))
            {
                entityInstance=eI;
                break;
            }
        }
        Expression expression = new Expression(getFunctions(), entityInstance);

        String arg1 = expression.evaluateExpression(expression1);
        String arg2 = expression.evaluateExpression(expression2);

        for (Property property : entityInstance.getPropertiesOfTheEntity()) {
            if (property.getNameOfProperty().equals(resultProp)) {
                switch (calType) {
                    case "divide": {
                        try {
                            if(property.getData().divide(arg1, arg2))
                            {
                                property.updateProperty(currTick);
                            }
                        } catch (Exception ex) {

                        }
                        break;
                    }

                    case "multiply": {
                        try {
                            if (property.getData().multiply(arg1, arg2)) {
                                property.updateProperty(currTick);
                            }

                        } catch (Exception ex) {

                        }
                        break;
                    }
                }
            }
        }
    }
}