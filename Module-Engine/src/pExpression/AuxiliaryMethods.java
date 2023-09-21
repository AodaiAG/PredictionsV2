package pExpression;

import pEntity.*;
import pEnvironment.EnvironmentInstance;

import java.util.IllegalFormatException;
import java.util.Map;
import java.util.Random;
import pSystem.World;

public class AuxiliaryMethods
{
    private World world;
    private DataType returnedValueTypeFromEvaluate;

    private EntityInstance entityInstanceToExtractPropertyFrom = null;

    private EntityInstance entityInstanceToExtractTicksFrom = null;
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

    public String percent(String arg1, String arg2) throws Exception
    {
        try
        {
            double a1 = Double.parseDouble(arg1);
            double a2 = Double.parseDouble(arg2);
            double res = (a2/100) * a1;

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
        String PropertyName = arg.substring(startIndex + 1, arg.length());
        for (Property property : entityInstanceToExtractTicksFrom.getPropertiesOfTheEntity()) {
            if (property.getNameOfProperty().equals(PropertyName))
            {
                lastUnchanged = property.getLastUnchangedTicks();
                break;
            }
        }
        return world.ticksCounter - lastUnchanged;
    }

    public String evaluate(String arg)
    {
        int startIndex = arg.indexOf(".");
        String entityName = arg.substring(0, startIndex);
        String PropertyName = arg.substring(startIndex + 1, arg.length());
        for (Property property : entityInstanceToExtractPropertyFrom.getPropertiesOfTheEntity())
        {
            if (property.getNameOfProperty().equals(PropertyName)) {
                returnedValueTypeFromEvaluate = property.getData().getDataType();
                return property.getData().getDataString();
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
    public EntityInstance getEntityInstanceToExtractPropertyFrom() {
        return entityInstanceToExtractPropertyFrom;
    }

    public void setEntityInstanceToExtractPropertyFrom(EntityInstance entityInstanceToExtractPropertyFrom) {
        this.entityInstanceToExtractPropertyFrom = entityInstanceToExtractPropertyFrom;
    }

    public EntityInstance getEntityInstanceToExtractTicksFrom() {
        return entityInstanceToExtractTicksFrom;
    }

    public void setEntityInstanceToExtractTicksFrom(EntityInstance entityInstanceToExtractTicksFrom) {
        this.entityInstanceToExtractTicksFrom = entityInstanceToExtractTicksFrom;
    }
}
