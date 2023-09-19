package application.manager;

import application.controllers.NewExecutionScreenController;
import application.controllers.PrimaryScreenController;
import application.controllers.ResultsScreenController.ResultsScreenController;
import application.controllers.ResultsScreenController.SimulationDetailsTabController;
import application.controllers.SimulationTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pDTOS.EntityDTO;
import pDTOS.EnvironmentDTO;
import pDTOS.WorldDTO;
import pSystem.Engine;
import pSystem.IEngine;
import javafx.stage.FileChooser;
import pSystem.Simulation;
import pSystem.World;

import java.io.File;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public enum UserInterfaceManager
{
    INSTANCE;
    public String directoryPath;
    String simulationName="Simulation";
    private int tabCounter=0;
    private Stage stage;
    private NewExecutionScreenController newExecutionController;
    private ResultsScreenController resultsController;
    private TabPane tabPane=new TabPane();
    private Scene primaryScene;
    private ExecutorService threadPool;
    private final IEngine engine = new Engine();
    private PrimaryScreenController primaryScreenController;
    private int waitingSimulations = 0;
    private int executingSimulations = 0;
    private int completedSimulations = 0;



    public void initApplication()
    {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/scenePrimary.fxml"));

        Parent root = null;
        try
        {
            root = loader.load();
            primaryScreenController=loader.getController();
            primaryScene = new Scene(root);
            stage.setScene(primaryScene);
            stage.setTitle("Main Application");
            stage.show();
        } catch (IOException e) {
            System.out.println("failed to load scenePrimary.fxml");
        }
    }

    public PrimaryScreenController getPrimaryScreenController()
    {
        return primaryScreenController;
    }

    public void setPrimaryScreenController(PrimaryScreenController primaryScreenController) {
        this.primaryScreenController = primaryScreenController;
    }

    public void switchToResultsScreen(ActionEvent event)
    {
        primaryScreenController.switchToResultsScene(event);
    }
    public String getSimulationName() {
        return simulationName;
    }

    @FXML
    public void loadXmlFile(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null)
        {
            // Prompt the user to enter the name of the simulation

             directoryPath = selectedFile.getParent();
                // Load and process the XML file with the simulation name
                try
                {
                    engine.ParseXmlAndLoadWorld(selectedFile);

                    // Notify the user of successful loading
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("File Loaded Successfully");
                    alert.setHeaderText("The XML file was loaded successfully.");
                    alert.showAndWait();
                   int numThreads = engine.getNumThreads();
                    threadPool = Executors.newFixedThreadPool(numThreads);

                    // Optionally, switch to a different scene or update UI components here
                } catch (Exception e)
                {
                    // Handle any exceptions that may occur during XML parsing
                    e.printStackTrace();

                    // Notify the user of the error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Loading File");
                    alert.setHeaderText("An error occurred while loading the XML file.");
                    alert.setContentText("Reason: " + e.getMessage());
                    alert.showAndWait();
                }
            }


    }

    public ExecutorService getThreadPool()
    {
        return threadPool;
    }

    public void shutdownThreadPool()
    {
        if (threadPool != null) {
            threadPool.shutdown();
        }
    }


    public TreeView<String> generateWorldDetails()
    {
        World world = engine.getWorld();
        return engine.convertWorldToDTO(world).generateTreeView();
    }

    public List<EnvironmentDTO> getEnvironmentsDTO()
    {
        return engine.getWorldBeforeChanging().getEnvironmentDTOS();
    }



    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDataToEnvironmentVar(EnvironmentDTO selectedEnvironment, String enteredData) throws Exception {
        try
        {
            this.engine.setDataToEnvironmentVar(selectedEnvironment,enteredData);
        }
        catch (Exception e)
        {

            throw e;
        }
    }

    public void setResultsController(ResultsScreenController resultsController)
    {
        this.resultsController = resultsController;
    }

    public List<EntityDTO> getEntityDto()
    {
        World world = engine.getWorld();
        return this.engine.convertWorldToDTO(world).getEntityDTOSet();
    }

    public void generatePopulation(EntityDTO selectedentityDTO, int populationNumber)
    {
        engine.createEntityPopulation(populationNumber,selectedentityDTO);
    }

    public Boolean isThereASimulation()
    {
        return !engine.isWordNull();
    }

    public EnvironmentDTO updateEnvironment(EnvironmentDTO modifiedEnvironment)
    {
        World world = engine.getWorld();
        WorldDTO worldDTO = engine.convertWorldToDTO(world);
       for(EnvironmentDTO environmentDTO : worldDTO.getEnvironmentDTOS())
       {
           if (environmentDTO.getEnProperty().getNameOfProperty().equals(modifiedEnvironment.getEnProperty().getNameOfProperty()))
            {
               return environmentDTO;
           }
       }

       return modifiedEnvironment;
    }

    public Map<UUID, Simulation> getSimulations()
    {
        return engine.getSimulations();
    }

    public void runSimulation()
    {

       try
       {
           if(engine.isWordNull())
           {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText("Please Load A File First !");
               alert.showAndWait();
           }
           else
           {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/SimulationDetails.fxml"));
               AnchorPane simulationDetails = loader.load();
               SimulationDetailsTabController simulationDetailsTabController=loader.getController();
               Tab tab=resultsController.createAndAddNewTab(this.tabPane);
               tab.setContent(simulationDetails);
               SimulationTask simulationTask = new SimulationTask(engine, this,simulationDetailsTabController);
               simulationDetailsTabController.setSimulationTask(simulationTask);
               simulationTask.bindComponentsToTask();
               incrementWaitingSimulations();
               threadPool.submit(simulationTask);

           }

       }
       catch (Exception e)
       {

       }
    }

        public int getWaitingSimulations() {
            return waitingSimulations;
        }

        public int getExecutingSimulations() {
            return executingSimulations;
        }

        public int getCompletedSimulations() {
            return completedSimulations;
        }

        public synchronized void incrementExecutingSimulations()
        {
            executingSimulations++;
            primaryScreenController.executingSimulations.set(executingSimulations);
        }


        public synchronized void incrementWaitingSimulations()
        {
            waitingSimulations++;
            primaryScreenController.waitingSimulations.set(waitingSimulations);

        }
    public synchronized void decrementWaitingSimulations()
    {
        waitingSimulations--;
        primaryScreenController.waitingSimulations.set(waitingSimulations);

    }

        public synchronized void incrementCompletedSimulations()
        {
            completedSimulations++;
            primaryScreenController.completedSimulations.set(completedSimulations);
        }




    public void updateSimulationResultsTab(Tab tab,Simulation simulation)
    {

    }

    public TabPane getTabPane()
    {
        return this.tabPane;
    }

    public Tab createAndAddNewTab(TabPane tabPane)
    {

        Tab tab = new Tab("Simulation " + tabCounter);
        tabPane.getTabs().add(tab);
        return tab;
    }

    public synchronized void decrementExecutingSimulations()
    {

        executingSimulations--;
        primaryScreenController.executingSimulations.set(executingSimulations);
    }
}