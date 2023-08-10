package Rules.ActionTypes;

import Entity.Entity;
import Entity.Property;

public class CalculationAction implements Action
{
    private String typeOfCondition;
    private String resultProp;
    private String calType;
    private String expression1;
    private String expression2;

    public CalculationAction()
    {
        resultProp=new String();
        typeOfCondition=new String();
        calType=new String();
        expression1=new String();
        expression2=new String();
        typeOfCondition="calculation";
    }

    public String getTypeOfCondition()
    {
        return typeOfCondition;
    }


    public String getResultProp()
    {
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

    public String getExpression2()
    {
        return expression2;
    }

    public void setExpression2(String expression2) {
        this.expression2 = expression2;
    }

    @Override
    public void ActivateAction(Entity e) throws Exception {

        String arg1=new String();
        String arg2=new String();
        //arg1=evalute(exp1)
        //arg2=evalute(exp2)

        for(Property t : e.getPropertiesOfTheEntity())
        {
            if(t.getNameOfProperty().equals(resultProp))
            {
                switch (calType)
                {
                    case "divide":
                    {

                        try {
                            t.getEdata().divide(arg1,arg2);
                        } catch (Exception ex)
                        {
                            throw ex;
                        }
                    }

                    case "multiply":
                    {
                        try {
                            t.getEdata().multiply(arg1,arg2);
                        } catch (Exception ex) {
                            throw ex;
                        }

                    }
                }


            }
        }
    }
}
