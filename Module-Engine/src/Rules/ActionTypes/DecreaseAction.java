package Rules.ActionTypes;

import Entity.Entity;
import Entity.Properties;

public class DecreaseAction implements Action
{
    private String entityName;
    private String propertyName;
    private String expression;


    public DecreaseAction()
    {

        propertyName=new String();
        entityName=new String();
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


        Object value = new Object();
        // value =evaluteExpression();


        for(Properties t : e.getPropertiesOfTheEntity())
        {
            if(t.getNameOfProperty().equals(propertyName))
            {
                switch (t.getType().getClass().getSimpleName())
                {
                    case "Integer":
                    {

                        if((Integer)t.getType()-(Integer)value<=(Integer)t.range[1] && (Integer)t.getType()-(Integer)value>=(Integer)t.range[0])
                        {
                            t.setType((Integer)t.getType()-(Integer)value);

                        }
                    }
                    case "Float":
                    {
                        if((Float)t.getType()-(Float)value<=(Float)t.range[1] && (Float)t.getType()-(Float)value>=(Float)t.range[0])
                        {
                            t.setType((Float)t.getType()-(Float)value);

                        }

                    }
                }

            }
        }


    }
}
