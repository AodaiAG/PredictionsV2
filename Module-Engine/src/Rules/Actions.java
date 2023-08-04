package Rules;

import Entity.Entity;
import Entity.Properties;

import java.util.Collection;

import static Rules.FunctionHelper.RandomFun;
import static Rules.FunctionHelper.getTypeOfEntity;


public class Actions extends Throwable
{


    public void increase( Entity e,String nameOfProperty,int value)
    {


               for(Properties t : e.propertiesOfTheEnitiy)
               {
                   if(t.NameOfProperty==nameOfProperty)
                   {
                      if((Double)t.Type+value<=t.range[1])
                      {
                          t.Type=(Double)t.Type+value;
                      }
                   }
               }
    }

    void Decrease(Entity e,String nameOfProperty,int value)
    {
        for(Properties t : e.propertiesOfTheEnitiy)
        {
            if(t.NameOfProperty==nameOfProperty)
            {
                if((Double)t.Type-value>=t.range[0])
                {
                    t.Type=(Double)t.Type+value;//you have to do"-" not "+" 
                }
            }
        }
    }

    void Calculation(Entity e,String nameOfProperty,String Operation)
    {
        switch (Operation)
        {

            case  "Multiply":
            {

            }
                break;

            case  "Divide":
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + Operation);
        }

    }

    void Multiply(Entity e,String nameOfProperty,int arg1,int arg2)
    {
        for(Properties t : e.propertiesOfTheEnitiy)
        {
            if(t.NameOfProperty==nameOfProperty)
            {
                double res=arg1*arg2;
                if(res>=t.range[0] && res<=t.range[1])
                {
                    t.Type=res;
                    return;
                }
            }
        }
    }
    void Divide(Entity e,String nameOfProperty,int arg1,int arg2)
    {
        for(Properties t : e.propertiesOfTheEnitiy)
        {
            if(t.NameOfProperty==nameOfProperty)
            {
                if(arg2==0)
                    return;
                else
                {
                    double res=arg1/arg2;
                    if(res>=t.range[0] && res<=t.range[1])
                    {
                        t.Type=res;
                        return;
                    }
                }


            }
        }
    }

    public static boolean SingularityCondition(Entity e, String operator, String nameOfProperty , int value)
    {
        for(Properties t : e.propertiesOfTheEnitiy)
        {
            if(t.NameOfProperty==nameOfProperty)
            {

                String type=getTypeOfEntity(e);
                switch (type)
                {
                    case "int":
                    {
                        int res=(int)t.Type;
                        switch (operator)
                        {
                            case "=":
                                return res==value;
                            case "!=":
                                return res!=value;
                            case "bt":
                                return value>res;
                            case "Lt":
                                return res>value;
                        }

                    }


                    case "double":
                        double res2=(double)t.Type;
                        switch (operator)
                        {
                            case "=":
                                return res2==value;
                            case "!=":
                                return res2!=value;
                            case "bt":
                                return value>res2;
                            case "Lt":
                                return res2>value;
                        }


                }

            }
        }


        return false;

    }

    public static boolean MultipleCondition(String operator,boolean co1,boolean co2)
    {

        switch (operator)
        {
            case "or":
                return co1||co2;
            case "and":
                return co1&&co2;

        }
        return false;
    }


    public void Set(Entity e,String nameOfProperty,int value)
    {
        for(Properties t : e.propertiesOfTheEnitiy)
        {
            if(t.NameOfProperty==nameOfProperty)
            {
               t.Type= RandomFun(t,value);
               return;
            }
        }
    }

    void Kill(Entity e, Collection<?> c)
    {

        c.remove(e);
    }


}
