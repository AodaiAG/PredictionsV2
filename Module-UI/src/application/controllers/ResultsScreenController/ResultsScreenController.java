package application.controllers.ResultsScreenController;

import application.manager.UserInterfaceManager;
import javafx.collections.ObservableList;
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
    public void initialize() {
        TabPane tabPane = uiManager.getTabPane();

        // Add the TabPane to the mainAnchor
        mainAnchor.getChildren().add(tabPane);

        // Get the list of tabs
        ObservableList<Tab> tabs = tabPane.getTabs();

        // Select the last added tab
        if (!tabs.isEmpty()) {
            tabPane.getSelectionModel().select(tabs.size() - 1);
        }
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
