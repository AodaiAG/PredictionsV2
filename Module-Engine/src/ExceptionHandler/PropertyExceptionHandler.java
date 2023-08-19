package ExceptionHandler;

import Entity.Property;

public class PropertyExceptionHandler extends ExceptionHandler
{

  public void Handle(String type,String name,boolean doesitHaveRange,String from,String to,boolean isintrandom,String initvalue) throws Exception
    {

        try
        {
            checkIfTypeNotSupported(type);
            if(doesitHaveRange)
            {
                checkFromToRange(from, to);

                if(!isintrandom)
                {
                    checkIfValueMatchesType(initvalue,type);
                    checkIfInRange(initvalue,from,to);
                }

            }

            if(!isintrandom)
            {

                checkIfValueMatchesType(initvalue,type);

            }
        }

        catch (Exception e)
        {

            throw new Exception("Problem occurred while Parsing xml file in property name "+name +" reasons: "+'\n'+e.getMessage());
        }

    }





}



