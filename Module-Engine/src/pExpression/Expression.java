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

    public Expression(AuxiliaryMethods f, EntityInstance entityInstance)
    {
        this.entityInstance = entityInstance;
        this.auxiliaryMethods = f;
        FUNCTIONS.put("random", args -> generateRandom(args[0]));
        FUNCTIONS.put("environment", args -> getEnvironmentValue(args[0]));
        FUNCTIONS.put("percent", args -> percent(args[0],args[1]));
        FUNCTIONS.put("evaluate", args -> evaluate(args[0]));
        FUNCTIONS.put("ticks", args -> ticks(args[0]));
        // Add more functions as needed
    }

    private String generateRandom(String arg) {
        return auxiliaryMethods.random(arg);
    }

    private String percent(String exp1,String exp2)
    {
        try
        {
            return auxiliaryMethods.percent(this.evaluateExpression(exp1),this.evaluateExpression(exp2));

        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
    private String evaluate(String arg)
    {
        return auxiliaryMethods.evaluate(arg);
    }

    private String ticks(String arg)
    {
        int valToReturn = auxiliaryMethods.ticks(arg);
        return  Integer.toString(valToReturn);
    }

    private String getEnvironmentValue(String arg) {
        return auxiliaryMethods.environment(arg);
    }

    public String evaluateExpression(String expression)
    {
        if (expression.isEmpty())
        {
            return ""; // Empty expression
        }

        String firstWord = "";
        boolean isAMethod = false;
        int startIndex = expression.indexOf("(");
        int endIndex = expression.indexOf(")");
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
                    return p.getData().getDataString();
                }
            }
            try
            {
                // Try to convert to number
                Double.parseDouble(firstWord);
                return firstWord;
            } catch (NumberFormatException e)
            {
                // Try to parse as boolean
                if (firstWord.equalsIgnoreCase("true") || firstWord.equalsIgnoreCase("false")) {
                    return firstWord;
                }
                // Just treat as a string
                return expression;
            }
        }
    }
}