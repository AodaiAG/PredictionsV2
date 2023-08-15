package Rules.ActionTypes;

import Entity.Property;
import Entity.EntityInstance;
import Expression.AuxiliaryMethods;
import Expression.Expression;


public class IncreaseAction extends Action
{
   private String entityName;
   private String propertyName;
  private String expression;


    public IncreaseAction()
    {
        expression=new String();
        propertyName=new String();
        entityName=new String();
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
    public String getNameOfAction()
    {
        return "increase";
    }

    @Override
    public void ActivateAction(EntityInstance e) throws Exception
    {


        Expression exp=new Expression(getFunctions(),e);
        String sValue=exp.evaluateExpression(expression);


        for(Property t : e.getPropertiesOfTheEntity())
        {
            if(t.getNameOfProperty().equals(propertyName))
            {
                try
                {
                    t.getData().decrease(sValue);
                } catch (Exception ex)
                {
                    throw ex;
                }

            }
        }


    }
}
