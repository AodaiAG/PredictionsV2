package Rules.ActionTypes;

import Entity.Entity;
import Entity.Properties;

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
    public void ActivateAction(Entity e)
    {
        Object value=new Object();
        //Object value=eval(expression)
        for(Properties t : e.getPropertiesOfTheEnitiy())
        {
            if (t.getNameOfProperty().equals(propertyName))
            {
                switch (t.getType().getClass().getSimpleName())
                {
                    case "Integer":
                    {
                        if((Integer)value>=(Integer)t.range[0] && (Integer)value<=(Integer)t.range[1])
                        {
                            t.setType(value);
                        }
                    }
                    case "Float":
                    {
                        if((Float)value>=(Float)t.range[0] && (Float)value<=(Float)t.range[1])
                        {
                            t.setType(value);
                        }
                    }
                    case "Boolean":
                    {

                        t.setType(value);
                    }
                    case "String":
                    {

                        t.setType(value);
                    }


                }

            }
        }
    }


}
