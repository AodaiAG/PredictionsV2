package Rules.ActionTypes;

import Entity.Entity;
import Entity.Properties;

public class SetAction extends Action
{
    public String entityName;
    public String propertyName;
    public String expression;

    public SetAction()
    {
        super("set");

        entityName=new String();
        propertyName=new String();
        expression=new String();

    }
    @Override
    void ActivateAction(Entity e)
    {
        Object value=new Object();
        //Object value=eval(expression)
        for(Properties t : e.propertiesOfTheEnitiy)
        {
            if (t.NameOfProperty.equals(propertyName))
            {
                switch (t.Type.getClass().getSimpleName())
                {
                    case "Integer":
                    {
                        if((Integer)value>=t.range[0] && (Integer)value<=t.range[1])
                        {
                            t.Type=value;
                        }
                    }
                    case "Float":
                    {
                        if((Float)value>=t.range[0] && (Float)value<=t.range[1])
                        {
                            t.Type=value;
                        }
                    }
                    case "Boolean":
                    {

                        t.Type=value;
                    }
                    case "String":
                    {

                        t.Type=value;
                    }


                }

            }
        }
    }


}
