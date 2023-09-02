import pDTOS.*;

import java.util.List;

public class Printer {
    public void printEntity(EntityDTO entityDTO) {
        int index = 1;
        System.out.println("Entity Name: " + entityDTO.getName());
        System.out.println("Population: " + entityDTO.getNumberOfInstances());
        System.out.println("Properties: ");
        for (PropertyDTO pd : entityDTO.getProperties()) {
            System.out.println();
            System.out.println("#Property Number " + index + ":");
            printProperty(pd, false);
            index++;
        }
    }

    public void printProperty(PropertyDTO propertyDTO, boolean isEnvironmentVar) {
        System.out.println((isEnvironmentVar ? "Environment Variable Name: " : "Property Name: ") + propertyDTO.getNameOfProperty());
        System.out.print(("Type: " + propertyDTO.getNameOfDataType()));
        System.out.println((propertyDTO.isRange()) ? "\nRange: " + propertyDTO.getFrom() + " - " + propertyDTO.getTo() : "");
        System.out.println("Is Random Initialized: " + (propertyDTO.isRandomInitialize() ? "true" : "false"));
    }

    public void printRule(RulesDTO ruleDTO)
    {
        System.out.println("Rule Name: " + ruleDTO.getNameOfRule());
        System.out.println("Ticks: " + ruleDTO.getTicks());
        System.out.println("Probability: " + ruleDTO.getProbability());
        System.out.println("Number Of Actions: " + ruleDTO.getNumberOfActions());

    }

    public void printTermination(TerminationDTO terminationDTO) {
        System.out.println("\n** Terminations **:");
        System.out.println("termination by ticks: " + terminationDTO.getTerminationTicks());
        System.out.println("termination by seconds: " + terminationDTO.getTerminationSeconds());
    }
}