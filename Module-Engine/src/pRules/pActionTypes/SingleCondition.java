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
    public void ActivateAction(EntityInstance ...args) throws Exception
    {
        EntityInstance e=args[0];
        Property wanted = new Property();
        Expression exp = new Expression(getFunctions(), e);
        String svalue = exp.evaluateExpression(value);
        String propertyAfter = exp.evaluateExpression(nameofProperty);
        try {
            Data eD = new Data();
            eD.setDataType(DataType.valueOf("FLOAT"));
            wanted.setData(eD);
            eD.setDataString(propertyAfter);
            this.conditionResult = wanted.getData().compareTo(svalue, operator);

        } catch (Exception ex) {
        }
    }

    @Override
    public String getNameOfAction() {
        return "SingleCondition";
    }
}