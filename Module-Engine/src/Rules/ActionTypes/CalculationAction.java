package Rules.ActionTypes;

import Entity.Entity;
import Entity.Properties;

public class CalculationAction extends Action
{
private String resultProp;
private String calType;
private String expression1;
private String expression2;
    public CalculationAction()
    {
        super("calculation");
        resultProp=new String();
        calType=new String();
        expression1=new String();
        expression2=new String();
    }


    public String getResultProp() {
        return resultProp;
    }

    public void setResultProp(String resultProp) {
        this.resultProp = resultProp;
    }

    public String getCalType() {
        return calType;
    }

    public void setCalType(String calType) {
        this.calType = calType;
    }

    public String getExpression1() {
        return expression1;
    }

    public void setExpression1(String expression1) {
        this.expression1 = expression1;
    }

    public String getExpression2() {
        return expression2;
    }

    public void setExpression2(String expression2) {
        this.expression2 = expression2;
    }

    @Override
    void ActivateAction(Entity e)
    {
        Object arg1=new Object();
        Object arg2=new Object();

        //arg1=evalute(exp1)
        //arg2=evalute(exp2)

        for(Properties t : e.getPropertiesOfTheEnitiy())
        {
            if(t.getNameOfProperty().equals(resultProp))
            {
                switch (t.getType().getClass().getSimpleName())
                {
                    case "Integer":
                    {
                        if(calType.equals("multiply"))
                        {
                            t.setType((Integer)((Integer)arg1*(Integer)arg2));
                        }
                        if(calType.equals("divide"))
                        {

                            t.setType((Integer)((Integer)arg1/(Integer)arg2));
                        }

                    }
                    case "Float":
                    {
                        if(calType.equals("multiply"))
                        {
                            t.setType((Float)((Float)arg1*(Float)arg2));
                        }
                        if(calType.equals("divide"))
                        {
                            t.setType((Float)((Float)arg1/(Float)arg2));

                        }

                    }
                }
            }
        }






    }
}
