package Rules.ActionTypes;

import Entity.EntityInstance;
import Entity.Properties;

public class SingleCondition extends ConditionAction
{
    private String nameofEntity;
    private String nameofProperty;
    private String operator;
    private String value;
    private Boolean conditionResult;

    public SingleCondition()
    {
        nameofEntity=new String();
        nameofProperty=new String();
        operator=new String();
        value=new String();

    }

    public String getNameofEntity() {
        return nameofEntity;
    }

    public void setNameofEntity(String nameofEntity) {
        this.nameofEntity = nameofEntity;
    }

    public String getNameofProperty() {
        return nameofProperty;
    }

    public void setNameofProperty(String nameofProperty) {
        this.nameofProperty = nameofProperty;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getConditionResult() {
        return conditionResult;
    }

    public void setConditionResult(Boolean conditionResult) {
        this.conditionResult = conditionResult;
    }

    @Override
    public void ActivateAction(EntityInstance e)
    {
    Object value=new Object();
        for (Properties p : e.getPropertiesOfTheEnitiy())
        {
            if(p.getNameOfProperty().equals(nameofProperty))
            {
                value=p.getType();
            }

        }
        this.conditionResult=ActivateAction(value);

    }

    public boolean ActivateAction(Object data)
    {
        String dataType=data.getClass().getSimpleName();
        Object ComparedData=new Object();
        //ComparedData=evaluteExpression()

    switch (dataType)
    {
        case "Integer":
        {
            switch (operator)
            {
                case "=":
                {
                    if(ComparedData instanceof Integer)
                    {
                        return ((Integer)data==(Integer) ComparedData);
                    }
                    else
                    {
                        // throw execption

                    }


                }
                case "!=":
                {
                    if(ComparedData instanceof Integer)
                    {
                        return ((Integer)data!=(Integer) ComparedData);
                    }
                    else
                    {
                        // throw execption
                    }


                }
                case "Bt":
                {
                    if(ComparedData instanceof Integer)
                    {
                        return ((Integer)data>(Integer) ComparedData);
                    }
                    else
                    {
                        // throw execption

                    }

                }
                case "Lt":
                {
                    if(ComparedData instanceof Integer)
                    {
                        return ((Integer)data<(Integer) ComparedData);
                    }
                    else
                    {
                        // throw execption

                    }

                }
            }

        }
        case "Float":
        {
            switch (operator)
            {
                case "=":
                {
                    if(ComparedData instanceof Float)
                    {
                        return ((Float)data==(Float) ComparedData);
                    }
                    else
                    {
                        // throw execption

                    }


                }
                case "!=":
                {
                    if(ComparedData instanceof Float)
                    {
                        return ((Float)data!=(Float) ComparedData);
                    }
                    else
                    {
                        // throw execption
                    }


                }
                case "Bt":
                {
                    if(ComparedData instanceof Float)
                    {
                        return ((Float)data>(Float) ComparedData);
                    }
                    else
                    {
                        // throw execption

                    }

                }
                case "Lt":
                {
                    if(ComparedData instanceof Float)
                    {
                        return ((Float)data<(Float) ComparedData);
                    }
                    else
                    {
                        // throw execption

                    }

                }
            }

        }
        case "Boolean":
        {
            switch (operator)
            {
                case "=":
                {
                    if(ComparedData instanceof Boolean)
                    {
                        return ((Boolean)data==(Boolean) ComparedData);
                    }
                    else
                    {
                        // throw execption

                    }


                }
                case "!=":
                {
                    if(ComparedData instanceof Boolean)
                    {
                        return ((Boolean)data!=(Boolean) ComparedData);
                    }
                    else
                    {
                        // throw execption
                    }


                }
                case "Bt":
                {

                        // throw execption



                }
                case "Lt":
                {

                        // throw execption

                }
            }



        }
        case "String":
        {
            switch (operator)
            {
                case "=":
                {
                    if(ComparedData instanceof String)
                    {
                        return ((String)data==(String) ComparedData);
                    }
                    else
                    {
                        // throw execption

                    }


                }
                case "!=":
                {
                    if(ComparedData instanceof String)
                    {
                        return ((String)data!=(String) ComparedData);
                    }
                    else
                    {
                        // throw execption
                    }


                }
                case "Bt":
                {

                    // throw execption



                }
                case "Lt":
                {

                    // throw execption

                }
            }



        }
    }

    throw new RuntimeException("f you");

    }


}
