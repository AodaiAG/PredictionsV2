package Rules.ActionTypes;

import Entity.Entity;
import Entity.Properties;

public class DecreaseAction extends Action
{
    public String entityName;
    public String propertyName;
    public String expression;


    public DecreaseAction()
    {
        super("decrease");
        propertyName=new String();
        entityName=new String();
        expression=new String();

    }

    @Override
    void ActivateAction(Entity e)
    {


        Object value = new Object();
        // value =evaluteExpression();


        for(Properties t : e.getPropertiesOfTheEnitiy())
        {
            if(t.getNameOfProperty().equals(propertyName))
            {
                switch (t.getType().getClass().getSimpleName())
                {
                    case "Integer":
                    {

                        if((Integer)t.getType()-(Integer)value<=t.range[1] && (Integer)t.getType()-(Integer)value>=t.range[0])
                        {
                            t.setType((Integer)t.getType()-(Integer)value);

                        }
                    }
                    case "Float":
                    {
                        if((Float)t.getType()-(Float)value<=t.range[1] && (Float)t.getType()-(Float)value>=t.range[0])
                        {
                            t.setType((Float)t.getType()-(Float)value);

                        }

                    }
                }

            }
        }


    }
}
