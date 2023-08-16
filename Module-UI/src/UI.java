import DTOS.*;
import com.sun.jndi.toolkit.url.Uri;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import System.IEngine;
import System.Engine;
import System.Simulation;

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



    public void runSimulation(WorldDTO worldDTO)
    {
        Printer pr = new Printer();
        environmentInitByUser(worldDTO.getEnvironmentDTOS(),pr);
        UUID currSimulationID = engine.startSimulation();
        System.out.println("Simulation id: "+ currSimulationID);

    }
    void showAllSimulationsChooseWhichToShowDetails()
    {

        Scanner sc = new Scanner(System.in);
        Map<UUID, Simulation> simulations=this.engine.getSimulations();
        System.out.println("**Simulations in the system** : ");
        Object[] array = simulations.keySet().toArray(); // array[uuid]
        for(int i=0;i<array.length;i++)
        {
            System.out.println("Simulation number "+(i+1)+" ID: "+array[i]);

        }
        System.out.println("Please, enter the simulation's number for which you would like to see the results: ");
        int userChoise=sc.nextInt();
        checkIfNumberIsWithinRange(userChoise,array.length);
        UUID choosenSimulationId= (UUID) array[userChoise-1];
        Simulation choosenSimulation=simulations.get(choosenSimulationId);
        printSimulationDetails(choosenSimulation);


    }
    void PrintSimulationAccordingToAmounts(Simulation simulation)
    {
        WorldDTO worldBefore=simulation.getWordBeforeSimulation();
        WorldDTO worldAfter=simulation.getWordAfterSimulation();

        for(EntityDTO afterdto:worldAfter.getEntityDTOSet())
        {
            for(EntityDTO beforedto:worldBefore.getEntityDTOSet())
            {
                if(afterdto.getName().equals(beforedto.getName()))
                {
                    System.out.println("Entity name: "+afterdto.getName()+'\n');
                    System.out.println("Population(Before-After): "+beforedto.getNumberOfInstances()+"-"+afterdto.getNumberOfInstances());
                }
            }
        }
    }

    Map<String,Integer> setHistogramToOneProperty(PropertyDTO property, List<EntityInstancesDTO> instancesDTOS)
    {
        // value // count
        Map<String,Integer> value2count=new HashMap<>();
        String propertyName=property.getNameOfProperty();

        for(EntityInstancesDTO entityInstancesDTO: instancesDTOS)
        {
            for(PropertyDTO propertyDTO:entityInstancesDTO.getProperties())
            {
                if(propertyName.equals(propertyDTO.getNameOfProperty()))
                {
                    Integer count=value2count.get(propertyDTO.getDataString());
                    if(count==null)
                    {
                        value2count.put(propertyDTO.getDataString(),1);
                    }
                    else
                    {
                        count++;
                        value2count.put(propertyDTO.getDataString(),count);
                    }
                }
            }

        }


        return value2count;
    }

    Map<String,Map<String,Integer>> setHistogramToAllPropertyInEntity(EntityDTO entity)
    {

        Map<String,Map<String,Integer>> pName2pCount=new HashMap<>();

        List<PropertyDTO> propertyDTOList=entity.getProperties();
        for(PropertyDTO propertyDTO:propertyDTOList)
        {
           pName2pCount.put(propertyDTO.getNameOfProperty(), setHistogramToOneProperty(propertyDTO,entity.getInstancesDTOS()));
        }

        return pName2pCount;

    }
    void PrintSimulationAccordingToHistogram(Simulation simulation)
    {

        System.out.println("Entities in the simulation: " + '\n');
        WorldDTO worldafter = simulation.getWordAfterSimulation();
        List<EntityDTO> entites = worldafter.getEntityDTOSet();
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < entites.size(); i++)
        {
            System.out.println((i + 1) + " - " + entites.get(i).getName());
        }
        System.out.println("Please, Choose an entity: ");
        int userChoise = sc.nextInt();
        checkIfNumberIsWithinRange(userChoise, entites.size());
        EntityDTO wantedEntity=entites.get(userChoise-1);
        Map<String,Map<String,Integer>> map=setHistogramToAllPropertyInEntity(wantedEntity);

        System.out.println("Properties of Entity: "+ wantedEntity.getName()+'\n');
        for (int i = 0; i <wantedEntity.getProperties().size(); i++)
        {
            System.out.println((i + 1) + " - " + wantedEntity.getProperties().get(i).getNameOfProperty()+'\n');
        }

        System.out.println("Please, choose a property of which you would like to get the Histogram");
        userChoise = sc.nextInt();
        checkIfNumberIsWithinRange(userChoise, wantedEntity.getProperties().size());
        PropertyDTO wantedProperty= wantedEntity.getProperties().get(userChoise-1);

        Map<String,Integer> value2count=map.get(wantedProperty.getNameOfProperty());

        for (Map.Entry<String, Integer> entry : value2count.entrySet())
        {
            System.out.println("Value-Count:"+entry.getKey()+"-"+entry.getValue());
        }

    }


    void printSimulationDetails(Simulation simulation)
    {
        Scanner sc = new Scanner(System.in);
        boolean validChoice = false;

        while (!validChoice)
        {
            System.out.println("Please choose an option: (Enter the option number)");
            System.out.println("1. Show entities amount before and after the simulation.");
            System.out.println("2. Show Property Histogram");
            System.out.println("3. Exit");

            int choice = sc.nextInt();
            switch (choice)
            {
                case 1:
                {
                    PrintSimulationAccordingToAmounts(simulation);
                    break;
                }

                case 2:
                {
                    PrintSimulationAccordingToHistogram(simulation);
                    break;
                }
                case 3:
                {
                    validChoice=true;
                    break;
                }

                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
                    break;
            }
        }
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