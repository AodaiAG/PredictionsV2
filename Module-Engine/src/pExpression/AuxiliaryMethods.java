package pExpression;

import pEntity.Data;
import pEntity.DataType;
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
    private DataType returnedValueTypeFromEvaluate;

    private DataType returnedValueTypeFromEnvironment;

    public DataType getReturnedValueTypeFromEvaluate() {
        return returnedValueTypeFromEvaluate;
    }


    public AuxiliaryMethods(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }


    public DataType getReturnedValueTypeFromEnvironment() {
        return returnedValueTypeFromEnvironment;
    }


    public void setWorld(World world) {
        this.world = world;
    }

    public String environment(String nameOfEnv)
    {
        Map<String, EnvironmentInstance> name2Env = world.getName2Env();
        EnvironmentInstance en = name2Env.get(nameOfEnv);
        returnedValueTypeFromEnvironment = en.getEnvironmentProperty().getData().getDataType();
        return en.getEnvironmentProperty().getData().getDataString();
    }

    public String percent(String arg1,String arg2) throws Exception
    {
        try
        {
            int a1 = Integer.parseInt(arg1);
            int a2 = Integer.parseInt(arg2);
            int res = (a2/100) * a1;

            return (String.valueOf(res));
        }
        catch(IllegalFormatException e)
        {
            throw e;
        }
    }

    public int ticks(String arg)
    {
        int lastUnchanged = 0;
        int startIndex = arg.indexOf(".");
        String entityName = arg.substring(0, startIndex);
        String PropertyName = arg.substring(startIndex, arg.length());
        for(Entity entity:world.getEntities())
        {
            if (entity.getNameOfEntity().equals(entityName)) {
                for (Property property : entity.getPropertiesOfTheEntity()) {
                    if (property.getNameOfProperty().equals(PropertyName))
                    {
                        lastUnchanged = property.getLastUnchangedTicks();

                    }
                }
            }
        }
        return world.ticksCounter - lastUnchanged;
    }

    public String evaluate(String arg)
    {
        int startIndex = arg.indexOf(".");
        String entityName = arg.substring(0, startIndex);
        String PropertyName = arg.substring(startIndex, arg.length());
        for(Entity entity:world.getEntities()) {
            if (entity.getNameOfEntity().equals(entityName)) {
                for (Property property : entity.getPropertiesOfTheEntity()) {
                    if (property.getNameOfProperty().equals(PropertyName)) {
                        returnedValueTypeFromEvaluate = property.getData().getDataType();
                        return property.getData().getDataString();
                    }
                }
            }
        }

            // throw exception ( not found )
        throw new RuntimeException("entity");
    }

    public String random(String arg) {
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