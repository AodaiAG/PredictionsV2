package application.controllers.ResultsScreenController;

import application.manager.UserInterfaceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import pSystem.Simulation;

public class ResultsScreenController
{

    int tabCounter=0;

    @FXML
    private ListView<UUID> simulationListView;
    @FXML
    private TabPane tabPane;

    private Simulation selectedSimulation;

    private UserInterfaceManager uiManager;
    @FXML
    private AnchorPane mainAnchor;
    public void initialize()
    {
        mainAnchor.getChildren().add(uiManager.getTabPane());
    }

    public Tab createAndAddNewTab(TabPane tabPane)
    {
        Tab tab = new Tab("Simulation " + tabCounter);
        tabPane.getTabs().add(tab);
        tabCounter++;
        return tab;
    }

    public void setSimulationDetailsTab(Tab tab,Simulation simulation)
    {
        try
        {
            // Load the SimulationDetails.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/SimulationDetails.fxml"));
            AnchorPane simulationDetails = loader.load();
            // Set the controller for the simulation details
            SimulationDetailsController detailsController = loader.getController();
            detailsController.initialize(simulation); // Pass the simulation data to the controller
            tab.setContent(simulationDetails);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static ResultsScreenController getInstance()
    {
        return Holder.INSTANCE;
    }

    // Rest of your code...

    // Private static inner class to hold the instance
    private static class Holder
    {
        private static final ResultsScreenController INSTANCE = new ResultsScreenController();
    }




public ResultsScreenController()
    {
        uiManager = UserInterfaceManager.INSTANCE;

    }

}
