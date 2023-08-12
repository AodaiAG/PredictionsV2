import Rules.ActionTypes.Action;
import jdk.nashorn.internal.parser.JSONParser;

public class Printer {

    public void printEntity(EntityDTO entityDTO) {
        System.out.println("Entity Name: " + entityDTO.getName());
        System.out.println("Population: " + entityDTO.getNumberOfInstances());
        System.out.println("Properties: ");
        for (PropertyDTO pd: entityDTO.getProperties())
        {
            printProperty(pd);
        }
    }

    public void printProperty(PropertyDTO propertyDTO)
    {
        System.out.println("Property Name: " + propertyDTO.getNameOfProperty());
        System.out.print(("Type: " + propertyDTO.getNameOfDataType()));
        System.out.println((propertyDTO.isRange()) ? "\nRange: from = " + propertyDTO.getFrom() + " to = " + propertyDTO.getTo():"");
        System.out.println("Is Random Initialized: " + (propertyDTO.isRandomInitialize() ? "true" : "false"));
    }

    public void printRule(RulesDTO ruleDTO) {
        System.out.println("Rule Name: " + ruleDTO.getNameOfRule());
        System.out.println("Ticks: " + ruleDTO.getTicks());
        System.out.println("Probability: " + ruleDTO.getProbability());
        System.out.println("Number Of Actions: " + ruleDTO.getNumberOfActions());
        for (String nameOfAction : ruleDTO.getNamesOfActions()) {
            System.out.println("Name Of Action: " + nameOfAction);
        }
    }
}