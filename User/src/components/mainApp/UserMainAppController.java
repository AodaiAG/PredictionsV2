package components.mainApp;
import Requests.SimulationRequestDetails;
import components.results.ResultsController;
import components.execution.ExecutionController;
import components.login.LoginController;
import components.requests.RequestsController;

import components.simulationDetails.SimulationDetailsController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import static util.Constants.*;

public class UserMainAppController
{
    private LoginController logicController;
    private GridPane loginComponent;

    ExecutionController executionController;
    AnchorPane executionComponent;

    RequestsController requestsController;
    AnchorPane requestsComponent;

    ResultsController resultsController;
    AnchorPane resultsComponent;

    SimulationDetailsController simulationDetailsController;
    AnchorPane simulationDetailsComponent;

    private final StringProperty currentUserName;


    @FXML
    private AnchorPane mainAnchorProgram;
    @FXML
    private VBox Leftvbox;
    @FXML
    private Pane pnlStatus;
    @FXML
    private Label pnlsMessage;
    @FXML
    private Pane buttonsPane;
    @FXML
    private Button btnLoad;
    @FXML
    private TextField filePathLabel;
    @FXML
    private Button ExecutionBtn;
    @FXML
    private AnchorPane mainAnchorpane;
    @FXML private Label userGreetingLabel;
    public UserMainAppController() {
        currentUserName = new SimpleStringProperty(JHON_DOE);
    }

    @FXML
    public void initialize()
    {

       userGreetingLabel.textProperty().bind(Bindings.concat( currentUserName));
        setExecutionsBtnVisibility(true);

        // prepare components
        //loadLoginPage();
    }
    public String getUserName()
    {
        return currentUserName.getValue();
    }

    public void setExecutionsBtnVisibility(boolean b)
    {
        ExecutionBtn.setDisable(b);
    }

    public void showMainAppStage()
    {
        try {
            // Load the FXML file for the main app
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/MainApp.fxml"));
            Parent mainAppRoot = loader.load();

            // Create a new scene with the main app's root
            Scene mainAppScene = new Scene(mainAppRoot);

            // Create a new stage for the main app
            Stage mainAppStage = new Stage();
            mainAppStage.setScene(mainAppScene);
            mainAppStage.setTitle("Main App"); // Set the title for the main app window

            // Show the main app stage
            mainAppStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void initApplication()
    {
        loadExecution();
        loadRequests();
        loadResults();
        loadSimulationDetails();
        setMainPanelTo(simulationDetailsComponent);
    }

    private void loadLoginPage()
    {
        URL loginPageUrl = getClass().getResource(LOGIN_PAGE_FXML_RESOURCE_LOCATION);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            loginComponent = fxmlLoader.load();
            logicController = fxmlLoader.getController();
            logicController.setAppMainController(this);
            setMainPanelTo(loginComponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadExecution()
    {
        URL loginPageUrl = getClass().getResource(EXECUTION_FXML_RESOURCE_LOCATION);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            executionComponent = fxmlLoader.load();
            executionController = fxmlLoader.getController();
            executionController.setAppMainController(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadRequests()
    {
        URL loginPageUrl = getClass().getResource(REQUESTS_FXML_RESOURCE_LOCATION);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            requestsComponent = fxmlLoader.load();
            requestsController = fxmlLoader.getController();
            requestsController.setAppMainController(this);
            requestsController.initApplication();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadResults()
    {
        URL loginPageUrl = getClass().getResource(RESULTS_FXML_RESOURCE_LOCATION);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            resultsComponent = fxmlLoader.load();
            resultsController = fxmlLoader.getController();
            resultsController.setAppMainController(this);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void loadSimulationDetails()
    {
        URL loginPageUrl = getClass().getResource(SIMULATION_DETAILS_FXML_RESOURCE_LOCATION);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            simulationDetailsComponent = fxmlLoader.load();
            simulationDetailsController = fxmlLoader.getController();
            simulationDetailsController.setAppMainController(this);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setMainPanelTo(Parent pane)
    {
        mainAnchorpane.getChildren().clear();
        mainAnchorpane.getChildren().add(pane);
        AnchorPane.setBottomAnchor(pane, 1.0);
        AnchorPane.setTopAnchor(pane, 1.0);
        AnchorPane.setLeftAnchor(pane, 1.0);
        AnchorPane.setRightAnchor(pane, 1.0);
    }




    @FXML
    void switchToRequestsScene(ActionEvent event)
    {
        setMainPanelTo(requestsComponent);
    }

    @FXML
    void switchToResultsScene(ActionEvent event)
    {
        setMainPanelTo(resultsComponent);
    }

    @FXML
    void switchToSimulationDetailsScene(ActionEvent event)
    {
        setMainPanelTo(simulationDetailsComponent);

    }

    public void switchToExecutionScene(ActionEvent event)
    {
        setMainPanelTo(executionComponent);
    }

    public void updateUserName(String userName)
    {
        this.currentUserName.set(userName);
    }

    public void initExecuterPageWithDetails(SimulationRequestDetails simulationRequestDetails)
    {
        executionController.initializeController(simulationRequestDetails);
    }

    public void switchToExecutionPage()
    {
        setMainPanelTo(executionComponent);
    }

    public void initExecutionTracker(UUID requestId, UUID executedSimulationId)
    {
        resultsController.createAndInitSimulationTab( requestId,  executedSimulationId);
    }

    public void switchToResultsPage()
    {
        setMainPanelTo(resultsComponent);
    }

    public void switchToRequestsPage()
    {
        setMainPanelTo(requestsComponent);
    }
}