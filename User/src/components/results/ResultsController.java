package components.results;

import components.results.simulationTabResults.SimulationDetailsTabController;
import components.mainApp.UserMainAppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.util.UUID;

public class ResultsController
{
    UserMainAppController mainAppController;
    @FXML
    private TabPane tabPane;
    int tabCounter=1;

    public void setAppMainController(UserMainAppController mainAppController)
    {
        this.mainAppController = mainAppController;
    }

    public void createAndInitSimulationTab(UUID requestId, UUID executedSimulationId)
    {

       try
       {
           Tab tab = new Tab("Simulation " + tabCounter);
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/results/simulationTabResults/SimulationDetails.fxml"));
           AnchorPane simulationDetails = loader.load();
           SimulationDetailsTabController simulationDetailsTabController = loader.getController();
           simulationDetailsTabController.initTab(requestId,executedSimulationId);
           simulationDetailsTabController.setMainApp(mainAppController);
           tab.setContent(simulationDetails);
           tabPane.getTabs().add(tab);
           tabCounter++;
       }

       catch (Exception e)
       {

       }

    }
}
