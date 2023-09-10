package application.controllers;

import application.manager.UserInterfaceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pDTOS.EntityDTO;
import pSystem.Simulation;

public class ResultsScreenController
{

    @FXML
    private ListView<UUID> simulationListView;
    @FXML
    private TabPane tabPane;

    private Simulation selectedSimulation;

    private UserInterfaceManager uiManager;
    public void initialize()
    {

        int counter=0;
        List<UUID> uuidList = new ArrayList<>(uiManager.getSimulations().keySet());
        for(UUID uuid:uuidList)
        {
            selectedSimulation = uiManager.getSimulations().get(uuid);
            addSimulationTab(selectedSimulation,counter);
            counter++;
        }


    }


    public void addSimulationTab(Simulation simulation,int index)
    {
        try
        {
            // Load the SimulationDetails.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/SimulationDetails.fxml"));
            AnchorPane simulationDetails = loader.load();
            // Set the controller for the simulation details
            SimulationDetailsController detailsController = loader.getController();
            detailsController.initialize(simulation); // Pass the simulation data to the controller

            // Create a new tab and set its content
            Tab tab = new Tab("Simulation " + index);
            tab.setContent(simulationDetails);

            // Add the tab to the tabPane
            tabPane.getTabs().add(tab);

        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    public ResultsScreenController()
    {
        uiManager = UserInterfaceManager.INSTANCE;
    }

}
