package Rules.ActionTypes;

import Entity.Entity;
import Entity.Property;

public class IncreaseAction implements Action
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

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public void ActivateAction(Entity e) throws Exception
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
                    t.getEdata().decrease(sValue);
                } catch (Exception ex)
                {
                    throw ex;
                }

            }
        }


    }
}
