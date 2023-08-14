package Expression;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Expression
{
    private  final Map<String, Function<String[], String>> FUNCTIONS = new HashMap<>();
    AuxiliaryMethods auxiliaryMethods;

    public Expression(AuxiliaryMethods f)
    {
        auxiliaryMethods =f;
        // Define auxiliary functions here
        FUNCTIONS.put("random", args -> generateRandom(args[0]));
        FUNCTIONS.put("environment", args -> getEnvironmentValue(args[0]));
        // Add more functions as needed
    }

    private  String generateRandom(String arg)
    {

        return auxiliaryMethods.random(arg);
    }
    private  String getEnvironmentValue(String arg)
    {

        return auxiliaryMethods.environment(arg);
    }

    public  String evaluateExpression(String expression)
    {

        if (expression.length() == 0) {
            return ""; // Empty expression
        }

     String firstWord;
        int startIndex=expression.indexOf("(");
        int endIndex=expression.indexOf(")");
        firstWord=expression.substring(0,startIndex);

        String arguments=expression.substring(startIndex+1,endIndex);
        String[] argumentArray = arguments.split(",");


        if (FUNCTIONS.containsKey(firstWord))
        {
            Function<String[], String> function = FUNCTIONS.get(firstWord);
            return function.apply(argumentArray);
        }
        else
        {
            // Assuming contextEntity and contextProperty are defined elsewhere
            // Replace with actual logic to fetch context entity and property
           // if (contextEntity != null && contextProperty != null)

          //  {
               // return contextEntity.getPropertyValue(contextProperty, firstWord);
          //  }
            if (true)
            {

            }
            else
            {
                try
                {
                    // Try to convert to number
                    Double.parseDouble(firstWord);
                    return firstWord;
                }
                catch (NumberFormatException e)
                {
                    // Try to parse as boolean
                    if (firstWord.equalsIgnoreCase("true") || firstWord.equalsIgnoreCase("false"))
                    {
                        return firstWord;
                    }
                    // Just treat as a string
                    return expression;
                }
            }
        }
        return "h";
    }

    // Example usage
    public  void main(String[] args) {


        String expression2 = "environment(cigarets-critical)";
        String result2 = evaluateExpression(expression2);
        System.out.println("Result 2: " + result2);
    }
}
