package Rules.ActionTypes;

import Entity.Property;
import Entity.EntityInstance;
import Expression.AuxiliaryMethods;
import Expression.Expression;


public class SetAction extends Action
{
    private String entityName;
    private String propertyName;
    private String expression;

    public SetAction()
    {
        entityName=new String();
        propertyName=new String();
        expression=new String();

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
    public String getNameOfAction()
    {
        return "set";
    }

    @Override
    public void ActivateAction(EntityInstance e) throws Exception
    {
         Expression exp=new Expression(getFunctions(),e);

        String sValue=exp.evaluateExpression(expression);
        for(Property t : e.getPropertiesOfTheEntity())
        {
            if (t.getNameOfProperty().equals(propertyName))
            {
                try
                {
                    t.getData().setNewValue(sValue);
                } catch (Exception ex)
                {

                }
                break;
            }
        }
    }


}
