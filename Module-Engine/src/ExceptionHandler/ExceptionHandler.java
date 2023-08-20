package ExceptionHandler;

public class ExceptionHandler
{
    public void checkIfTypeNotSupported(String type) throws Exception
    {
        String lowc=type.toLowerCase();

        if (!lowc.equals("decimal")&&!lowc.equals("boolean")&&!lowc.equals("float")&&!lowc.equals("string"))
        {
            throw new Exception("type "+type+" not Supported!");
        }
    }

    public void checkFromToRange(String from, String to) throws Exception
    {
        try {
            Float fromF = Float.parseFloat(from);
            Float toF = Float.parseFloat(to);

            if (fromF > toF)
            {
                throw new Exception("The range is not mathematically intact!");
            }
        } catch (NumberFormatException e)
        {
            throw new Exception("The range is not numeric!");
        }
    }

    public void checkIfInRange(String value,String from,String to) throws Exception
    {
        Float fValue = Float.parseFloat(value);
        Float fromValue = Float.parseFloat(from);
        Float toValue = Float.parseFloat(to);
        if(fValue >= fromValue && fValue <= toValue)
        {
            //write something???
        }
        else
        {
            throw new Exception("the value you provided is not at the predefined range!");
        }
    }



    public void checkIfValueMatchesType(String value,String type) throws Exception
    {
        switch (type)
        {
            case"decimal":
            {
                try
                {
                    Integer.parseInt(value);
                }
                catch (Exception e)
                {
                    throw new Exception("the value you provided is not of type decimal");
                }
                break;
            }
            case"boolean":
            {
                {
                    if((value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")))
                    {

                    }

                    else
                    {
                        throw new Exception("the value you provided is not of type decimal Boolean ");
                    }
                }
                break;
            }

            case"float":
            {
                try
                {
                    Float.parseFloat(value);
                }
                catch (Exception e)
                {
                    throw new Exception("the value you provided is not of type Float");
                }
                break;
            }
        }
    }
}
