package Rules.ActionTypes;

import Entity.Entity;
import Entity.Property;

public class SetAction implements Action
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
        Object value=new Object();
        //Object value=eval(expression)
        String sValue=new String();
        for(Property t : e.getPropertiesOfTheEntity())
        {
            if (t.getNameOfProperty().equals(propertyName))
            {
                try
                {
                    t.getEdata().setNewValue(sValue);
                } catch (Exception ex)
                {
                    throw ex;
                }

            }
        }
    }


}
