package Rules.ActionTypes;

import Entity.Entity;
import Entity.Property;
import Entity.EntityInstance;
import Expression.AuxiliaryMethods;
import Expression.Expression;

public class SingleCondition extends ConditionAction
{
    private String nameofEntity;
    private String nameofProperty;
    private String operator;
    private String value;
    private Boolean conditionResult;

    public SingleCondition()
    {
        nameofEntity=new String();
        nameofProperty=new String();
        operator=new String();
        value=new String();

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
    public void ActivateAction(EntityInstance e) throws Exception
    {
        Property wanted = new Property();
        Expression exp = new Expression(getFunctions(), e);
        String svalue = exp.evaluateExpression(value);

        for (Property p : e.getPropertiesOfTheEntity())
        {
            if(p.getNameOfProperty().equals(nameofProperty))
            {
                wanted = p;
                break;
            }
        }
        try
        {
            this.conditionResult = wanted.getData().compareTo(svalue, operator);

        } catch (Exception ex)
        {

        }

    }
    @Override
    public String getNameOfAction()
    {
        return "SingleCondition";
    }

}
