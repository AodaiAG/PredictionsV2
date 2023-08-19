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
            Float fromf = Float.parseFloat(from);
            Float tof = Float.parseFloat(to);

            if (fromf > tof)
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

        Float fvalue = Float.parseFloat(value);
        Float fromvalue = Float.parseFloat(from);
        Float tovalue = Float.parseFloat(to);
        if(fvalue >= fromvalue && fvalue <= tovalue)
        {

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
