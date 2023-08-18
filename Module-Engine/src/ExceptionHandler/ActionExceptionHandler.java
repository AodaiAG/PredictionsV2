package ExceptionHandler;

import Entity.Entity;
import Entity.Property;
import Environment.EnvironmentInstance;

import java.util.List;
import java.util.Map;
import java.util.Set;

import System.World;
public class ActionExceptionHandler
{

    public void checkProbabiltyActivation(String prob) throws Exception
    {
        try
        {
            Float res=Float.parseFloat(prob);
            if(res<0 || res>1)
            {
                throw new Exception("Probability should be between 0-1 !");
            }

        }
        catch (Exception e)
        {
            throw new RuntimeException("probability Activation for rule/action should be numeric!");
        }

    }

    public void checkTicksActivation(String ticks) throws Exception
    {
        try
        {
            Integer res=Integer.parseInt(ticks);
        }

        catch (Exception e)
        {
            throw new Exception("ticks should be Integer!");
        }

    }

    public void checkIfEntityExists(List<Entity> entities, String checked) throws Exception
    {

        for (Entity entity:entities)
        {
            if(entity.getNameOfEntity().equals(checked))
                return;
        }

        throw new Exception("entity "+ checked+ " doesn't exist! ");

    }

    public void checkIfActionTypeValid(String type) throws Exception
    {
        String lowc=type.toLowerCase();

        if (!lowc.equals("condition")&&!lowc.equals("multiple")&&!lowc.equals("increase")&&!lowc.equals("decrease")&&!lowc.equals("calculation")&&!lowc.equals("set")&&!lowc.equals("kill"))
        {
            throw new Exception("Action type "+type+" not Supported!");
        }
    }

    public void conditionCheckoperator(String operator) throws Exception
    {
        if (operator.equals("or")||operator.equals("and"))
        {
            return;
        }
        else
        {
            throw new Exception("Action condition: operator "+operator+" not supported!");
        }
    }
    public void conditionSingleCheckoperator(String operator) throws Exception
    {
        if (operator.equals("=")||operator.equals("!=")||operator.equals("bt")||operator.equals("lt"))
        {
            return;
        }
        else
        {
            throw new Exception("Action condition(single): operator "+operator+" not supported!");
        }
    }
    public void conditionCheckSingularity(String s) throws Exception
    {
        if(s.equals("single") || s.equals("multiple"))
        {
            return;
        }
        else
        {
            throw new Exception("Action condition: singularity type is not supported!");
        }

    }

    public void checkIfEnviormentExists(Map<String,EnvironmentInstance> environmentInstanceMap,String checked) throws Exception
    {
        EnvironmentInstance environmentInstance= environmentInstanceMap.get(checked);
        if(environmentInstance==null)
        {
            throw new Exception("Environment variable "+ checked+" doesn't exist!");
        }

    }
    public boolean checkifAuxiliaryMethods(String checked)
    {
        int startIndex = checked.indexOf("(");
        if(startIndex>0)
        {
            String firstWord = checked.substring(0, startIndex);
            if(firstWord.equals("environment")||firstWord.equals("random"))
            {
                return true;
            }
        }

        return false;

    }
     public boolean checkIfPropertyExists(Set<Property> properties, String checked)
    {
        for(Property property:properties)
        {
            if(property.getNameOfProperty().equals(checked))
                return true;
        }

        return false;
    }
    public void checkIfExpressionisValid(String exp,String entityWorksOn, World world,String typeofAction) throws Exception
    {

        Entity entitywanted = null;
        for(Entity entity:world.getEntities())
        {
            if(entity.getNameOfEntity().equals(entityWorksOn));
            entitywanted=entity;

        }
        if(checkifAuxiliaryMethods(exp))
            return;

       if(checkIfPropertyExists(entitywanted.getPropertiesOfTheEntity(),exp))
           return;

       switch (typeofAction)
       {
           case"increase":
           {
               try
               {
                   Float.parseFloat(exp);
               }
               catch (Exception e)
               {
                   throw new Exception(exp+" is not a predefined method,nor a property in "+entityWorksOn+ " nor does it match the action type "+typeofAction +" !");
               }
               break;
           }
//Calculation
           case"decrease":
           {
               try
               {
                   Float.parseFloat(exp);
               }
               catch (Exception e)
               {
                   throw new Exception("Expression is not a predefined method,nor a property in "+entityWorksOn+ " nor does it match the action type! ");

               }
               break;
           }
           case"calculation":
           {
               try
               {
                   Float.parseFloat(exp);
               }
               catch (Exception e)
               {
                   throw new Exception("Expression is not a predefined method,nor a property in "+entityWorksOn+ " nor does it match the action type! ");

               }
               break;
           }


       }



    }



}
