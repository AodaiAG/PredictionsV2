package Rules.ActionTypes;

import Entity.Property;
import Entity.EntityInstance;


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
        Object value = new Object();

         // value =evaluteExpression();
        // need to convert the value to string
        String sValue=new String();


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
