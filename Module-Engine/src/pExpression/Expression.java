package pExpression;
import pEntity.EntityInstance;
import pEntity.Property;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Expression
{
    private final Map<String, Function<String[], String>> FUNCTIONS = new HashMap<>();

    private final AuxiliaryMethods auxiliaryMethods;

    private final EntityInstance entityInstance;
    private  EntityInstance entityInstanceA;
    private EntityInstance entityInstanceB;

    public Expression(AuxiliaryMethods f, EntityInstance entityInstance)
    {
        this.entityInstance = entityInstance;
        this.auxiliaryMethods = f;
        FUNCTIONS.put("random", args -> generateRandom(args[0], entityInstanceA, entityInstanceB));
        FUNCTIONS.put("environment", args -> environment(args[0], entityInstanceA, entityInstanceB));
        FUNCTIONS.put("percent", args -> percent(args[0],args[1], entityInstanceA, entityInstanceB));
        FUNCTIONS.put("evaluate", args -> evaluate(args[0], entityInstanceA, entityInstanceB));
        FUNCTIONS.put("ticks", args -> ticks(args[0], entityInstanceA, entityInstanceB));
        // Add more functions as needed
    }

    private String generateRandom(String arg, EntityInstance a, EntityInstance b) {
        String valToReturn = auxiliaryMethods.random(arg);
        return "DECIMAL." + valToReturn;
    }


    private String percent(String exp1, String exp2, EntityInstance a, EntityInstance b)
    {
        try
        {
            String valAndDataType1 = this.evaluateExpression(exp1, a, b);
            String valAndDataType2 = this.evaluateExpression(exp2, a, b);

            int indexOfPeriod1 = valAndDataType1.indexOf(".");
            int indexOfPeriod2 = valAndDataType2.indexOf(".");

            String returnedFromPercent = auxiliaryMethods.percent(valAndDataType1.substring(indexOfPeriod1 + 1), valAndDataType2.substring(indexOfPeriod2 + 1));
            return "FLOAT." + returnedFromPercent;

        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private String evaluate(String arg, EntityInstance a, EntityInstance b ){
        if(arg.contains(a.getNameOfEntity()))
        {
            auxiliaryMethods.setEntityInstanceToExtractPropertyFrom(a);
        }
        else if(arg.contains(b.getNameOfEntity()))
        {
            auxiliaryMethods.setEntityInstanceToExtractPropertyFrom(b);
        }
        String valToReturn = auxiliaryMethods.evaluate(arg);
        return auxiliaryMethods.getReturnedValueTypeFromEvaluate().toString() + "." + valToReturn;
    }

    private String ticks(String arg, EntityInstance a, EntityInstance b)
    {
        if(arg.contains(a.getNameOfEntity()))
        {
            auxiliaryMethods.setEntityInstanceToExtractTicksFrom(a);
        }
        else if(arg.contains(b.getNameOfEntity()))
        {
            auxiliaryMethods.setEntityInstanceToExtractTicksFrom(b);
        }
        int valToReturn = auxiliaryMethods.ticks(arg);
        return  "DECIMAL." + Integer.toString(valToReturn);
    }

    private String environment(String arg, EntityInstance a, EntityInstance b) {
        String valToReturn = auxiliaryMethods.environment(arg);
        return auxiliaryMethods.getReturnedValueTypeFromEnvironment().toString() + "."  + valToReturn;
    }

    public String evaluateExpression(String expression, EntityInstance ...args)
    {
        if(args.length != 0)
        {
            entityInstanceA = args[0];
            if(args.length > 1)
            {
                entityInstanceB = args[1];
            }
        }
        if (expression.isEmpty())
        {
            return ""; // Empty expression
        }

        String firstWord = "";
        boolean isAMethod = false;
        int startIndex = expression.indexOf("(");
        int endIndex = expression.length() - 1;
        String arguments = "";
        String[] argumentArray = null;
        if (startIndex > 0)
        {
            firstWord = expression.substring(0, startIndex);
            arguments = expression.substring(startIndex + 1, endIndex);
            argumentArray = arguments.split(",");

            isAMethod = true;
        }

        if (FUNCTIONS.containsKey(firstWord) && isAMethod)
        {
            Function<String[], String> function = FUNCTIONS.get(firstWord);
            return function.apply(argumentArray);
        } else
        {
            for (Property p : entityInstance.getPropertiesOfTheEntity())
            {
                if (p.getNameOfProperty().equals(expression))
                {
                    return p.getData().getDataType() + "." + p.getData().getDataString();
                }
            }
            try
            {
                // Try to convert to number
                Double.parseDouble(firstWord);
                return "FLOAT." + firstWord;
            } catch (NumberFormatException e)
            {
                // Try to parse as boolean
                if (firstWord.equalsIgnoreCase("true") || firstWord.equalsIgnoreCase("false")) {
                    return "BOOLEAN." + firstWord;
                }
                // Just treat as a string
                return "STRING." + expression;
            }
        }
    }
}