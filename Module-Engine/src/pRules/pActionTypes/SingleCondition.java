package pRules.pActionTypes;

import pEntity.Data;
import pEntity.DataType;
import pEntity.Property;
import pEntity.EntityInstance;
import pExpression.Expression;

public class SingleCondition extends ConditionAction
{
    private String nameofEntity;
    private String nameofProperty;
    private String operator;
    private String value;
    private Boolean conditionResult;

    public SingleCondition() {
        nameofEntity = "";
        nameofProperty = "";
        operator = "";
        value = "";
    }

    public String getNameofEntity() {
        return nameofEntity;
    }

    public void setNameofEntity(String nameofEntity) {
        this.nameofEntity = nameofEntity;
    }

    public String getNameofProperty() {
        return nameofProperty;
    }

    public void setNameofProperty(String nameofProperty) {
        this.nameofProperty = nameofProperty;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getConditionResult() {
        return conditionResult;
    }

    public void setConditionResult(Boolean conditionResult) {
        this.conditionResult = conditionResult;
    }

    @Override
    public void ActivateAction(int currTick, EntityInstance ...args) throws Exception
    {
        EntityInstance e=args[0];
        Property wanted = new Property();
        Expression exp = new Expression(getFunctions(), e);
        String sValue = exp.evaluateExpression(value); //"BOOLEAN.true"
        int indexOfPeriodInValue = sValue.indexOf(".");
        String propertyAfter = exp.evaluateExpression(nameofProperty);
        int indexOfPeriodInPropertyAfter = propertyAfter.indexOf('.');
        try {
            Data eD = new Data();
            eD.setDataType(DataType.valueOf(propertyAfter.substring(0, indexOfPeriodInPropertyAfter)));
            wanted.setData(eD);
            eD.setDataString(propertyAfter.substring(indexOfPeriodInPropertyAfter + 1));
            this.conditionResult = wanted.getData().compareTo(sValue.substring(indexOfPeriodInValue + 1), operator);
        } catch (Exception ex) {
        }
    }

    @Override
    public String getNameOfAction() {
        return "SingleCondition";
    }
}