package application.controllers.ResultsScreenController;

import application.manager.UserInterfaceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

import java.io.IOException;
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
