package pExpression;

import pEntity.Entity;
import pEntity.Property;
import pEnvironment.EnvironmentInstance;

import java.util.IllegalFormatException;
import java.util.Map;
import java.util.Random;
import pSystem.World;

public class AuxiliaryMethods
{
    private World world;

    public AuxiliaryMethods(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public String environment(String nameOfEnv)
    {
        Map<String, EnvironmentInstance> name2Env = world.getName2Env();
        EnvironmentInstance en = name2Env.get(nameOfEnv);
        return en.getEnvironmentProperty().getData().getDataString();
    }

    public String percent(String arg1,String arg2) throws Exception {

        try
        {
            Integer a1 = Integer.parseInt(arg1);
            Integer a2 = Integer.parseInt(arg2);
            if (a2 == 0)
            {
                throw new Exception("You can't divide by 0 !");
            }
            Integer res = (a2/a1);

            return ( res.toString() );

        }
        catch(IllegalFormatException e)
        {

            throw e;
        }
    }
    public String ticks(String arg)
    {
        int startIndex = arg.indexOf(".");
        String entityName = arg.substring(0, startIndex);
        String PropertyName = arg.substring(startIndex, arg.length());
        for(Entity entity:world.getEntities())
        {
            if (entity.getNameOfEntity().equals(entityName)) {
                for (Property property : entity.getPropertiesOfTheEntity()) {
                    if (property.getNameOfProperty().equals(PropertyName))
                    {
                        property.getLastUnchangedTicks();
                    }
                }
            }
        }
        return "0";
    }

    public String evaluate(String arg)
    {
        int startIndex = arg.indexOf(".");
        String entityName = arg.substring(0, startIndex);
        String PropertyName = arg.substring(startIndex, arg.length());
        for(Entity entity:world.getEntities())
        {
            if(entity.getNameOfEntity().equals(entityName))
            {
                for(Property property:entity.getPropertiesOfTheEntity())
                {
                    if(property.getNameOfProperty().equals(PropertyName))
                    {
                        return property.getData().getDataString();
                    }
                }
            }

            // throw exception ( not found )
        }


        throw new RuntimeException("entity");
    }

    public String random(String arg)
    {
        try {
            int maxRange = Integer.parseInt(arg);
            if (maxRange < 0) {
                throw new IllegalArgumentException("Argument must be a non-negative integer");
            }

            Random random = new Random();
            int randomValue = random.nextInt(maxRange + 1);
            return String.valueOf(randomValue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Argument must be a numeric value");
        }
    }
}