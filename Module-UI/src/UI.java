import DTOS.*;
import com.sun.jndi.toolkit.url.Uri;

import java.io.File;
import java.io.PrintWriter;
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

    public void programFlow()
    {
        WorldDTO worldDTOBeforeSimulation = engine.convertWorldToDTO(); //old values be kept
        userChoiceHandler();
    }

    public void userChoiceHandler()
    {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        do
        {
            printMainMenu();
            int choice = sc.nextInt();
            switch (choice)
            {
                case 1:
                {
                    {
                       try
                       {
                           getFileDirectoryAndLoadSimulation();
                       }
                       catch (Exception e)
                       {
                           System.out.println(e.getMessage());
                       }
                        break;
                    }
                }

                case 2:
                {
                    {
                        PrintWorldDetails(engine.convertWorldToDTO());
                        break;
                    }
                }
                case 3:
                {
                    runSimulation(engine.convertWorldToDTO());
                    break;
                }
                case 4:
                {
                    showAllSimulationsChooseWhichToShowDetails();
                }
                case 5:
                {
                    {
                        exit =true;
                        break;
                    }
                }
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
                    break;
            }
        }while (!exit);
    }

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
        System.out.println("There are " + worldDTO.getEntityDTOSet().size() + " entities," + worldDTO.getRulesDTOSet().size() + " Laws in the current simulation");
        Printer newPr = new Printer();
        System.out.println("** ENTITIES **");
        for (EntityDTO entityDTO : worldDTO.getEntityDTOSet())
        {
          newPr.printEntity(entityDTO);
        }
        System.out.println("\n** RULES **");
        for (RulesDTO ruleDTO: worldDTO.getRulesDTOSet()) {
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
        Map<UUID, Simulation> simulations = this.engine.getSimulations();
        System.out.println("**Simulations in the system** : ");
        Object[] array = simulations.keySet().toArray(); // array[uuid]
        for(int i = 0; i < array.length; i++)
        {
            System.out.println("Simulation number " + (i + 1) + " ID: " + array[i]);

        }
        System.out.println("Please, enter the simulation's number for which you would like to see the results: ");
        int userChoice = sc.nextInt();
        checkIfNumberIsWithinRange(userChoice, array.length);
        UUID chosenSimulationId= (UUID) array[userChoice -1];
        Simulation choosenSimulation = simulations.get(chosenSimulationId);
        printSimulationDetails(choosenSimulation);
    }

    void PrintSimulationAccordingToAmounts(Simulation simulation)
    {
        WorldDTO worldBefore = simulation.getWordBeforeSimulation();
        WorldDTO worldAfter = simulation.getWordAfterSimulation();

        for(EntityDTO entityDTOAfter : worldAfter.getEntityDTOSet())
        {
            for(EntityDTO entityDtoBefore : worldBefore.getEntityDTOSet())
            {
                if(entityDTOAfter.getName().equals(entityDtoBefore.getName()))
                {
                    System.out.println("Entity name: " + entityDTOAfter.getName() + '\n');
                    System.out.println("Population(Before - After): " + entityDtoBefore.getNumberOfInstances() + "-" + entityDTOAfter.getNumberOfInstances());
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
        WorldDTO wordAfterSimulation = simulation.getWordAfterSimulation();
        List<EntityDTO> entities = wordAfterSimulation.getEntityDTOSet();
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < entities.size(); i++)
        {
            System.out.println((i + 1) + " - " + entities.get(i).getName());
        }
        System.out.println("Please, Choose an entity: ");
        int userChoice = sc.nextInt();
        checkIfNumberIsWithinRange(userChoice, entities.size());
        EntityDTO wantedEntity= entities.get(userChoice-1);
        Map<String,Map<String,Integer>> map=setHistogramToAllPropertyInEntity(wantedEntity);

        System.out.println("Properties of Entity: "+ wantedEntity.getName()+'\n');
        for (int i = 0; i <wantedEntity.getProperties().size(); i++)
        {
            System.out.println((i + 1) + " - " + wantedEntity.getProperties().get(i).getNameOfProperty()+'\n');
        }

        System.out.println("Please, choose a property of which you would like to get the Histogram");
        userChoice = sc.nextInt();
        checkIfNumberIsWithinRange(userChoice, wantedEntity.getProperties().size());
        PropertyDTO wantedProperty= wantedEntity.getProperties().get(userChoice-1);

        Map<String,Integer> value2count=map.get(wantedProperty.getNameOfProperty());

        for (Map.Entry<String, Integer> entry : value2count.entrySet())
        {
            System.out.println("Value-Count:"+entry.getKey()+"-"+entry.getValue());
        }
    }

    void printSimulationDetails(Simulation simulation)
    {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit)
        {
            System.out.println("Please choose an option: (Enter the option number)");
            System.out.println("1. Show entities amount before and after the simulation.");
            System.out.println("2. Show property histogram");
            System.out.println("3. Return to main menu");

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
                    userChoiceHandler();
                    exit = true;
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
        option = (number >= 1 && number <= bound);
        Scanner sc = new Scanner(System.in);
        while(!option)
        {
            System.out.println("Please, choose a valid option number :");
            number = sc.nextInt();
            option = (number >= 1 && number <= bound);
        }
    }

    public void environmentInitByUser(List<EnvironmentDTO> eDlist, Printer pr)
    {
        Scanner sc = new Scanner(System.in);
        int userChoice;
        boolean validData = false;

        int i = 0;
        System.out.println('\n' + "** Environment Variables **: ");
        for (EnvironmentDTO environmentDTO : eDlist)
        {
            System.out.println("Property number " + (i + 1) + ": ");
            pr.printProperty(environmentDTO.getEnProperty(), true);
            System.out.println('\n');
            i++;
        }

        PrintWriter writer= new PrintWriter(System.out);

        String enteredData = "";
        boolean isValid = false;
        boolean validOption = false;
        boolean finished = false;
        String userChoiceString;

        while (!finished)
        {
            System.out.println("Please enter a number of a variable you wish to init (0 if you're done): ");
            userChoiceString = sc.next();
            if (userChoiceString.matches("\\d+")) {
                userChoice = Integer.parseInt(userChoiceString);
                if (userChoice == 0)
                {
                    finished =true;
                    break;
                } else if (userChoice >= 1 && userChoice <= eDlist.size())
                {
                    while (!isValid)
                    {
                        String chosenTypeToInit = eDlist.get(userChoice - 1).getEnProperty().getNameOfDataType();
                        System.out.println("Please enter a value of type " + chosenTypeToInit + " within the given range: ");
                        //move to checking function
                        if(chosenTypeToInit.equals("Boolean"))
                        {
                            boolean booleanUserChoiceValid = false;
                            do{
                                System.out.println("Choose an option (1/2):");
                                System.out.println("1. true");
                                System.out.println("2. false");
                                String booleanUserChoice = sc.next();
                                if(booleanUserChoice.equals("1"))
                                {
                                    enteredData = "true";
                                    booleanUserChoiceValid = true;
                                } else if (booleanUserChoice.equals("2")) {
                                    enteredData = "false";
                                    booleanUserChoiceValid = true;
                                }
                                else
                                {
                                    System.out.println("Invalid input, please choose 1 or 2: ");
                                }
                            }while (!booleanUserChoiceValid);
                        } else if (chosenTypeToInit.equals("String")) {
                            enteredData = sc.next();
                           //check

                        } else {
                            enteredData = sc.next();
                        }
                        try
                        {
                            this.engine.setDataToEnvironmentVar(eDlist.get(userChoice - 1), enteredData);
                            System.out.println("Initialized successfully !");
                            isValid = true;
                        } catch (Exception e)
                        {
                            System.out.print("The data you entered is not valid, since ");
                            System.out.println(e.getMessage());
                            System.out.println("Please try again ");
                        }
                    }
                    isValid=false;
                } else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            } else
            {
                System.out.println("Please , enter a valid option: ");
                continue;
            }
        }
    }

    public void printMainMenu()
    {
        System.out.println();
        System.out.println("1. Load xml File ");
        System.out.println("2. Show simulation details");
        System.out.println("3. Start simulation");
        System.out.println("4. Show previous simulations");
        System.out.println("5. Exit");
    }
}