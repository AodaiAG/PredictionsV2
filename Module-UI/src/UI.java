import pDTOS.*;
import com.sun.jndi.toolkit.url.Uri;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import pSystem.IEngine;
import pSystem.Engine;
import pSystem.Simulation;

public class UI {
    IEngine engine = new Engine();

    private boolean shouldExit = false;

    public void programFlow() {
        userChoiceHandler();
    }

    public void userChoiceHandler() {
        Scanner sc = new Scanner(System.in);
        do {
            printMainMenu();
            int choice = getUserChoice(sc, 1, 5);
            switch (choice) {
                case 1: {
                    try {
                        getFileDirectoryAndLoadSimulation();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 2: {
                    PrintWorldDetails(engine);
                    break;
                }
                case 3: {
                    checkAndRunSimulation(engine);
                    break;
                }
                case 4: {
                    showAllSimulationsChooseWhichToShowDetails();
                    break;
                }
                case 5: {
                    shouldExit = true;
                    break;
                }
            }
        } while (!shouldExit);
    }

    void getFileDirectoryAndLoadSimulation()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please, enter the directory of the file you wish to load : ");
        String path = sc.nextLine();
        try {
            Path dd = Paths.get(path);
            while (!Files.exists(dd)) {
                System.out.println("You have entered a wrong file directory,please enter a valid one: ");
                path = sc.nextLine();
                dd = Paths.get(path);
            }

            Uri uri = new Uri(path);
            File f = new File(String.valueOf(uri));
            engine.ParseXmlAndLoadWorld(f);
            System.out.println("File Loaded successfully!");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void PrintWorldDetails(IEngine engine) {
        if (engine.isWordNull()) {
            System.out.println("There is no current simulation in the system");
        } else {
            PrintWorldDTODetails(engine.getWorldBeforeChanging());
        }
    }

    void PrintWorldDTODetails(WorldDTO worldDTO) {

        int index = 1;
        int numOfEntities = worldDTO.getEntityDTOSet().size();
        int numOfRules = worldDTO.getRulesDTOSet().size();
        System.out.println("There " + (numOfEntities == 1 ? "is" : "are") + " " + numOfEntities + (numOfEntities == 1 ? " entity, " : " entities, ") + numOfRules + " Law" + (numOfRules == 1 ? "" : "s") + " in the current simulation.");

        Printer newPr = new Printer();
        System.out.println("** ENTITIES **");
        for (EntityDTO entityDTO : worldDTO.getEntityDTOSet())
        {
            newPr.printEntity(entityDTO);
        }
        System.out.println("\n** RULES **");
        for (RulesDTO ruleDTO : worldDTO.getRulesDTOSet()) {
            System.out.println();
            System.out.println("#Rule Number " + index + ":");
            newPr.printRule(ruleDTO);
            index++;
        }
        newPr.printTermination(worldDTO.getTerminationDTO());
    }

    public void checkAndRunSimulation(IEngine engine)
    {
        if (engine.isWordNull()) {
            System.out.println("There is no current simulation in the system");
        } else
        {

            runSimulation(engine.convertWorldToDTO());
        }
    }

    public void runSimulation(WorldDTO worldDTO) {
        Printer pr = new Printer();
        environmentInitByUser(worldDTO.getEnvironmentDTOS(), pr);
        printEnvironmentVariable(worldDTO.getEnvironmentDTOS(), pr);
        UUID currSimulationID = engine.startSimulation();
        Simulation lastSimulation = engine.getSimulations().get(currSimulationID);
        System.out.println("the simulation ended by " + lastSimulation.getReasonForTermination());
        System.out.println("Simulation Id: " + currSimulationID);
        printSimulationDetails(lastSimulation);

    }

    void showAllSimulationsChooseWhichToShowDetails() {
        int simulationCount = this.engine.getSimulations().size();

        if (simulationCount == 0) {
            System.out.println("There is not current simulations in the system,");

        } else {
            List<Map.Entry<UUID, Simulation>> sortedSimulations = new ArrayList<>(engine.getSimulations().entrySet());

            // Sort the simulations by running date
            sortedSimulations.sort(Comparator.comparing(entry -> entry.getValue().getRunningDate()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");
            String formattedDate;
            System.out.println("\n**Simulations in the system** : ");
            for (int i = 0; i < sortedSimulations.size(); i++)
            {
                Map.Entry<UUID, Simulation> entry = sortedSimulations.get(i);
                Simulation simulation = entry.getValue();
                Date runningDate = simulation.getRunningDate();
                formattedDate = dateFormat.format(runningDate);
                System.out.println((i + 1) + ". Date|Time : " + formattedDate + " | Simulation ID: " + entry.getKey());
            }
            System.out.println("Choose a simulation to view details:");
            Scanner sc = new Scanner(System.in);
            int userChoice = getUserChoice(sc, 1, sortedSimulations.size());
            Map.Entry<UUID, Simulation> chosenEntry = sortedSimulations.get(userChoice - 1);
            Simulation chosenSimulation = chosenEntry.getValue();
            printSimulationDetails(chosenSimulation);
        }
    }

    void printSimulationAccordingToAmounts(Simulation simulation) {
        WorldDTO worldBefore = simulation.getWordBeforeSimulation();
        WorldDTO worldAfter = simulation.getWordAfterSimulation();

        for (EntityDTO entityDTOAfter : worldAfter.getEntityDTOSet()) {
            for (EntityDTO entityDtoBefore : worldBefore.getEntityDTOSet()) {
                if (entityDTOAfter.getName().equals(entityDtoBefore.getName())) {
                    System.out.println("Entity name: " + entityDTOAfter.getName() + '\n');
                    System.out.println("Population(Before - After): " + entityDtoBefore.getNumberOfInstances() + "-" + entityDTOAfter.getNumberOfInstances());
                }
            }
        }
    }

    Map<String, Integer> setHistogramToOneProperty(PropertyDTO property, List<EntityInstancesDTO> instancesDTOS) {
        // value // count
        Map<String, Integer> value2count = new HashMap<>();
        String propertyName = property.getNameOfProperty();

        for (EntityInstancesDTO entityInstancesDTO : instancesDTOS) {
            for (PropertyDTO propertyDTO : entityInstancesDTO.getProperties()) {
                if (propertyName.equals(propertyDTO.getNameOfProperty())) {
                    Integer count = value2count.get(propertyDTO.getDataString());
                    if (count == null) {
                        value2count.put(propertyDTO.getDataString(), 1);
                    } else {
                        count++;
                        value2count.put(propertyDTO.getDataString(), count);
                    }
                }
            }
        }
        return value2count;
    }

    Map<String, Map<String, Integer>> setHistogramToAllPropertyInEntity(EntityDTO entity) {

        Map<String, Map<String, Integer>> pName2pCount = new HashMap<>();

        List<PropertyDTO> propertyDTOList = entity.getProperties();
        for (PropertyDTO propertyDTO : propertyDTOList) {
            pName2pCount.put(propertyDTO.getNameOfProperty(), setHistogramToOneProperty(propertyDTO, entity.getInstancesDTOS()));
        }
        return pName2pCount;
    }

    void printSimulationAccordingToHistogram(Simulation simulation) {
        System.out.println("Entities in the simulation:" + '\n');
        WorldDTO wordAfterSimulation = simulation.getWordAfterSimulation();
        List<EntityDTO> entities = wordAfterSimulation.getEntityDTOSet();
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < entities.size(); i++) {
            System.out.println((i + 1) + " - " + entities.get(i).getName());
        }
        System.out.println("Please, Choose an entity:");
        int userChoice = getUserChoice(sc, 1, entities.size());
        EntityDTO wantedEntity = entities.get(userChoice - 1);
        Map<String, Map<String, Integer>> map = setHistogramToAllPropertyInEntity(wantedEntity);

        System.out.println("Properties of Entity:" + wantedEntity.getName() + '\n');
        for (int i = 0; i < wantedEntity.getProperties().size(); i++) {
            System.out.println((i + 1) + "- " + wantedEntity.getProperties().get(i).getNameOfProperty() + '\n');
        }

        System.out.println("Please, choose a property of which you would like to get the Histogram");
        userChoice = getUserChoice(sc, 1, wantedEntity.getProperties().size());
        PropertyDTO wantedProperty = wantedEntity.getProperties().get(userChoice - 1);

        Map<String, Integer> value2count = map.get(wantedProperty.getNameOfProperty());

        for (Map.Entry<String, Integer> entry : value2count.entrySet()) {
            System.out.println("Value-Count: \"" + entry.getKey() + "\" --> " + entry.getValue());
        }
    }

    void printSimulationDetails(Simulation simulation) {
        Scanner sc = new Scanner(System.in);

        while (!shouldExit) {
            System.out.println("\nPlease choose an option: (Enter the option number)");
            System.out.println("1. Show entities amount before and after the simulation.");
            System.out.println("2. Show property histogram");
            System.out.println("3. Return to main menu");
            int choice = getUserChoice(sc, 1, 3);
            switch (choice) {
                case 1: {
                    printSimulationAccordingToAmounts(simulation);
                    break;
                }
                case 2: {
                    printSimulationAccordingToHistogram(simulation);
                    break;
                }
                case 3: {
                    return;
                }
            }
        }
    }

    public void printEnvironmentVariable(List<EnvironmentDTO> eDlist, Printer pr) {
        int i = 0;
        System.out.println();
        System.out.println("** Environment Variables **:");
        System.out.println("Name --> Value:");
        System.out.println();

        for (EnvironmentDTO environmentDTO : eDlist)
        {
            System.out.println("#Environment Variable Number " + (i + 1) + ":");
            System.out.println(environmentDTO.getEnProperty().getNameOfProperty()+" --> "+environmentDTO.getEnProperty().getDataString());
            System.out.println();
            i++;
        }
    }

    public void environmentInitByUser(List<EnvironmentDTO> eDlist, Printer pr) {
        Scanner sc = new Scanner(System.in);

        int i = 0;
        System.out.println();
        System.out.println("** Environment Variables **: ");
        for (EnvironmentDTO environmentDTO : eDlist) {
            System.out.println("Property number " + (i + 1) + ": ");
            pr.printProperty(environmentDTO.getEnProperty(), true);
            System.out.println();
            i++;
        }

        String enteredData;
        boolean finished = false;

        while (!finished) {
            System.out.println("Please enter a number of a variable you wish to init (0 if you're done):");
            int userChoiceEnvFromList = getUserChoice(sc, 0, eDlist.size());

            if (userChoiceEnvFromList == 0) {
                finished = true;
            } else if (userChoiceEnvFromList >= 1 && userChoiceEnvFromList <= eDlist.size()) {
                boolean isEnteredDataValid = false;
                do {
                    EnvironmentDTO chosenEnvironmentDTO = eDlist.get(userChoiceEnvFromList - 1);
                    String chosenTypeToInit = chosenEnvironmentDTO.getEnProperty().getNameOfDataType();

                    enteredData = getUserInput(sc, chosenTypeToInit);

                    try {
                        this.engine.setDataToEnvironmentVar(chosenEnvironmentDTO, enteredData);
                        System.out.println("Initialized successfully !");
                        isEnteredDataValid = true;
                    } catch (Exception e) {
                        System.out.print("The data you entered is not valid, since ");
                        System.out.println(e.getMessage());
                        System.out.println("Please try again ");
                    }
                } while (!isEnteredDataValid);
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private int getUserChoice(Scanner sc, int minChoice, int maxChoice) {
        int userChoice = 0;
        boolean validOption = false;
        while (!validOption) {
            try {
                userChoice = Integer.parseInt(sc.nextLine());
                if (userChoice >= minChoice && userChoice <= maxChoice) {
                    validOption = true;
                } else {
                    System.out.println("Invalid choice. Please enter an option in from the list.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a numeric option: ");
            }
        }
        return userChoice;
    }

    private String getUserInput(Scanner sc, String type) {
        if (type.equals("Boolean")) {
            return getBooleanUserInput(sc);
        } else {
            System.out.println("Please enter a value of type " + type + " within the given range: ");
            return sc.nextLine();
        }
    }

    private String getBooleanUserInput(Scanner sc) {
        boolean isValid = false;
        String returnedVal = "";
        String booleanUserChoice;
        while (!isValid) {
            System.out.println("Choose an option (1/2):");
            System.out.println("1. true");
            System.out.println("2. false");
            booleanUserChoice = sc.nextLine();
            if (booleanUserChoice.equals("1")) {
                returnedVal = "true";
                isValid = true;
            } else if (booleanUserChoice.equals("2")) {
                returnedVal = "false";
                isValid = true;
            } else {
                System.out.println("Invalid input. Please choose 1 or 2: ");
            }
        }
        return returnedVal;
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1. Load xml File ");
        System.out.println("2. Show simulation details");
        System.out.println("3. Start simulation");
        System.out.println("4. Show previous simulations");
        System.out.println("5. Exit");
    }
}