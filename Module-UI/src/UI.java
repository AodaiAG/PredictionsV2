import DTOS.EntityDTO;
import DTOS.EnvironmentDTO;
import DTOS.RulesDTO;
import DTOS.WorldDTO;
import com.sun.jndi.toolkit.url.Uri;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import System.IEngine;
import System.Engine;

public class UI
{
    IEngine engine = new Engine();

    void getFileDirectoryAndLoadSimulation()
    {
        Scanner sc= new Scanner(System.in);
        System.out.println("Please, enter the directory of the file you wish to load : ");
        String path=sc.nextLine();
        try
        {
            Path dd = Paths.get(path);
            while(!Files.exists(dd))
            {
                System.out.println("You have entered a wrong file directory,please enter a valid one: ");
                path=sc.nextLine();
                dd = Paths.get(path);
            }

            Uri uri=new Uri(path);
            File f=new File(String.valueOf(uri));
            engine.ParseXmlAndLoadWorld(f);
            System.out.println("File Loaded successfully!");

        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    void PrintWorldDetails(WorldDTO worldDTO)
    {
        int index = 1;
        Scanner sc= new Scanner(System.in);
        System.out.println("There are " + worldDTO.getEntityDTOSet().size() + " entities," + worldDTO.getRulesDTOSet().size() + " Laws in the current simulation");
        Printer newPr = new Printer();
        System.out.println("** ENTITIES **");
        for (EntityDTO edto: worldDTO.getEntityDTOSet())
        {
          newPr.printEntity(edto);
        }
        System.out.println("\n** RULES **");
        for (RulesDTO ruleDTO: worldDTO.getRulesDTOSet()) {
            System.out.println();
            System.out.println("#Rule Number " + index + ":");
            newPr.printRule(ruleDTO);
            index++;
        }
        newPr.printTermination(worldDTO.getTerminationDTO());
    }

    public void endOfSimulation(UUID currSimulationID, WorldDTO worldDTO) {
        Scanner sc = new Scanner(System.in);
        boolean validChoice = false;

        while (!validChoice) {
            System.out.println("Please choose an option: (press the option number)");
            System.out.println("1. Show entities amount before and after the simulation.");
            System.out.println("2. Show Property Histogram");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    Map<String, Integer> initialQuantitiesMap= engine.endOfSimulationHandlerShowQuantities(currSimulationID);
                    showEntitiesAmount(initialQuantitiesMap, worldDTO);
                    validChoice = true; // Set flag to exit the loop
                    break;
                case 2:

                    showPropertyHistogram();
                    validChoice = true; // Set flag to exit the loop
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
                    break;
            }
        }
    }

    public void runSimulation(WorldDTO worldDTO)
    {
        Printer pr = new Printer();
        environmentInitByUser(worldDTO.getEnvironmentDTOS(),pr);
        UUID currSimulationID = engine.startSimulation();
        endOfSimulation(currSimulationID, worldDTO);
    }

    private void showEntitiesAmount(Map<String, Integer> initialQuantitiesMap, WorldDTO worldDTO) {
        for (EntityDTO eD:worldDTO.getEntityDTOSet()) {
            int initialQuantity = initialQuantitiesMap.getOrDefault(eD.getName(), 0);
            int finalQuantity = eD.getNumberOfInstances();
            System.out.println(eD.getName() + ": Initial=" + initialQuantity + ", Final=" + finalQuantity);
        }
    }

    private void showPropertyHistogram() {
        // Implement showPropertyHistogram method as previously shown
    }

    void checkIfNumberIsWithinRange(int number,int bound)
    {
        boolean option;
        option=number>=1&&number<=bound;
        Scanner sc= new Scanner(System.in);
        while(!option)
        {
            System.out.println("Please, choose a valid option number :");
            number=sc.nextInt();
            option=number>=1&&number<=bound;
        }
    }


    public void environmentInitByUser(List<EnvironmentDTO> eDlist, Printer pr)
    {
        Scanner sc= new Scanner(System.in);
        String userChoice = "";
        boolean validData = false;

        int i=0;
        System.out.println('\n'+"** Environment Variables **: ");
        for(EnvironmentDTO ed: eDlist)
        {
            if(i!=0)
            {
                System.out.println();
            }
            pr.printProperty(ed.getEnProperty(), true);
            System.out.println("Would you like to initialize the value? (yes / no)");

            do {
                userChoice = sc.nextLine();
                userChoice = userChoice.toLowerCase();
                validData = userChoice.equals("yes") || userChoice.equals("no");

                if (!validData) {
                    System.out.println("Please enter yes or no");
                }
            } while (!validData);

            String enteredData ="";
            switch (userChoice) {
                case "yes":
                    boolean isValid = false;
                    while (!isValid) {
                        System.out.println("Please enter a value of type " + ed.getEnProperty().getNameOfDataType() + " within the given range: ");
                        enteredData = sc.nextLine();
                        try {
                            this.engine.setDataToEnvironmentVar(ed, enteredData);
                            isValid = true;
                        } catch (Exception e) {
                            System.out.print("The data you entered is not valid, since ");
                            System.out.println(e.getMessage());
                            System.out.println("Please try again ");
                        }
                    }
                    break;
                case "no":
                    System.out.println("Value initialized automatically");
                    break;
            }
            i++;
        }
    }
}