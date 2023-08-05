package Rules.ActionTypes;

import Entity.Entity;
import Entity.Properties;

public class CalculationAction extends Action
{
public String resultProp;
public String calType;
public String expression1;
public String expression2;
    public CalculationAction()
    {
        super("calculation");
        resultProp=new String();
        calType=new String();
        expression1=new String();
        expression2=new String();
    }

    @Override
    void ActivateAction(Entity e)
    {
        Object arg1=new Object();
        Object arg2=new Object();

        //arg1=evalute(exp1)
        //arg2=evalute(exp2)

        for(Properties t : e.propertiesOfTheEnitiy)
        {
            if(t.NameOfProperty.equals(resultProp))
            {
                switch (t.Type.getClass().getSimpleName())
                {
                    case "Integer":
                    {
                        if(calType.equals("multiply"))
                        {
                            t.Type=(Integer)((Integer)arg1*(Integer)arg2);
                        }
                        if(calType.equals("divide"))
                        {

                            t.Type=(Integer)((Integer)arg1/(Integer)arg2);
                        }

                    }
                    case "Float":
                    {
                        if(calType.equals("multiply"))
                        {
                            t.Type=(Float)((Float)arg1*(Float)arg2);
                        }
                        if(calType.equals("divide"))
                        {
                            t.Type=(Float)((Float)arg1/(Float)arg2);

                        }

                    }
                }
            }
        }






    }
}
