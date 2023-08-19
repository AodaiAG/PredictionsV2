package Expression;
import Entity.EntityInstance;
import Entity.Property;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import Entity.Data; //to delete

public class Expression
{
    private final Map<String, Function<String[], String>> FUNCTIONS = new HashMap<>();

    private final AuxiliaryMethods auxiliaryMethods;

    private final EntityInstance entityInstance;

    public Expression(AuxiliaryMethods f, EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
        this.auxiliaryMethods = f;
        FUNCTIONS.put("random", args -> generateRandom(args[0]));
        FUNCTIONS.put("environment", args -> getEnvironmentValue(args[0]));
        // Add more functions as needed
    }

    private String generateRandom(String arg) {
        return auxiliaryMethods.random(arg);
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

    // Example usage
//    public static void main(String[] args)
//    {
//        EntityInstance ei = new EntityInstance();
//        ei.setNameOfEntity("Dora");
//        ei.setPropertiesOfTheEntity(new HashSet<>());
//        Property p = new Property();
//        p.setRandomInitialize(false);
//        p.setNameOfProperty("P1");
//        Data d = new Data();
//        d.setDataString("Str");
//        ei.getPropertiesOfTheEntity().add(p);
//        String expression2 = "environment(cigarets-critical)";
//        String result2 = evaluateExpression(expression2, ei );
//        System.out.println("Result 2: " + result2);
//    }
