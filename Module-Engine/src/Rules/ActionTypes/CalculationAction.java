package Rules.ActionTypes;

import Entity.Property;
import Entity.EntityInstance;
import Expression.Expression;


public class CalculationAction extends Action
{
    private String entityName;

    private String typeOfCondition;

    private String resultProp;

    private String calType;

    private String expression1;

    private String expression2;

    public CalculationAction()
    {
        resultProp=new String();
        entityName = "";
        typeOfCondition=new String();
        calType=new String();
        expression1=new String();
        expression2=new String();
        typeOfCondition="calculation";
    }

    @Override
    public String getNameOfEntity() {
        return entityName;
    }
    public String getTypeOfCondition()
    {
        return typeOfCondition;
    }

    public String getNameOfAction()
    {
        return "calculation";
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
    public void ActivateAction(EntityInstance entityInstance) throws Exception
    {
        Expression expression = new Expression(getFunctions(), entityInstance);

        String arg1=expression.evaluateExpression(expression1);
        String arg2=expression.evaluateExpression(expression2);

        for(Property t : entityInstance.getPropertiesOfTheEntity())
        {
            if(t.getNameOfProperty().equals(resultProp))
            {
                switch (calType)
                {
                    case "divide":
                    {
                        try {
                            t.getData().divide(arg1,arg2);
                        } catch (Exception ex)
                        {
                            throw ex;
                        }
                    }

                    case "multiply":
                    {
                        try {
                            t.getData().multiply(arg1,arg2);
                        } catch (Exception ex) {
                            throw ex;
                        }

                    }
                }
            }
        }
    }
}