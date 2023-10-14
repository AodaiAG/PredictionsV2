package components.ExecutionsHistory;

import components.ExecutionsHistory.showSimulationResult.SimulationResultsForUserController;
import components.ExecutionsHistory.userNamesExecutions.showUsersAndSimulationIDController;
import components.Management.ManagementController;
import components.mainApp.MainAppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;



public class ExecutionsController
{
    @FXML
    private AnchorPane showusersAnchor;
    SimulationResultsForUserController simulationResultsForUserController;
    @FXML
    private AnchorPane showSimulationAnchor;
    showUsersAndSimulationIDController showUsersAndSimulationIDController;
    MainAppController mainAppController;

    public void initialize()
    {
        loadSimulationResultPage();
        loadUsersExecutionsPage();

    }
    private void loadSimulationResultPage()
    {
        URL loginPage = getClass().getResource("/components/ExecutionsHistory/showSimulationResult/simUserResult.fxml");
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPage);
            AnchorPane toBeSubmitted = fxmlLoader.load();
            simulationResultsForUserController = fxmlLoader.getController();
            simulationResultsForUserController.setAppMainController(this.mainAppController);
            //submitNewRequestController.initApplication();
            showSimulationAnchor.getChildren().clear();
            showSimulationAnchor.getChildren().add(toBeSubmitted);
            AnchorPane.setBottomAnchor(toBeSubmitted, 1.0);
            AnchorPane.setTopAnchor(toBeSubmitted, 1.0);
            AnchorPane.setLeftAnchor(toBeSubmitted, 1.0);
            AnchorPane.setRightAnchor(toBeSubmitted, 1.0);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadUsersExecutionsPage()
    {
        URL loginPage = getClass().getResource("/components/ExecutionsHistory/userNamesExecutions/usersSimulationId.fxml");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPage);
            AnchorPane toBeSubmitted = fxmlLoader.load();
            showUsersAndSimulationIDController = fxmlLoader.getController();
            showUsersAndSimulationIDController.setAppMainController(this.mainAppController);
            showUsersAndSimulationIDController.setResultsController(simulationResultsForUserController);
            //submitNewRequestController.initApplication();
            showusersAnchor.getChildren().clear();
            showusersAnchor.getChildren().add(toBeSubmitted);
            AnchorPane.setTopAnchor(toBeSubmitted, 0.5);
            AnchorPane.setBottomAnchor(toBeSubmitted, 0.5);
            AnchorPane.setLeftAnchor(toBeSubmitted, 0.5);
            AnchorPane.setRightAnchor(toBeSubmitted, 0.5);


        }

        catch (Exception e)
        {

        }
    }

    public void setChatAppMainController(MainAppController mainAppController)
    {
        this.mainAppController = mainAppController;

    }
}
