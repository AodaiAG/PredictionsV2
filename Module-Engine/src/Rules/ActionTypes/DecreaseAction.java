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
    }

    @Override
    void ActivateAction(Entity e)
    {


        Object value = new Object();
        // value =evaluteExpression();


        for(Properties t : e.propertiesOfTheEnitiy)
        {
            if(t.NameOfProperty.equals(propertyName))
            {
                switch (t.Type.getClass().getSimpleName())
                {
                    case "Integer":
                    {

                        if((Integer)t.Type-(Integer)value<=t.range[1] && (Integer)t.Type-(Integer)value>=t.range[0])
                        {
                            t.Type=(Integer)t.Type-(Integer)value;

                        }
                    }
                    case "Float":
                    {
                        if((Float)t.Type-(Float)value<=t.range[1] && (Float)t.Type-(Float)value>=t.range[0])
                        {
                            t.Type=(Float)t.Type-(Float)value;

                        }

                    }
                }

            }
        }


    }
}
