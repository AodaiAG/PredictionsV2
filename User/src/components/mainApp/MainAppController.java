package components.mainApp;
import components.execution.ExecutionController;
import components.requests.RequestsController;
import components.results.ResultsController;
import components.simulationDetails.SimulationDetailsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;

import static util.Constants.*;

public class MainAppController
{
    ExecutionController executionController;
    AnchorPane executionComponent;

    RequestsController requestsController;
    AnchorPane requestsComponent;

    ResultsController resultsController;
    AnchorPane resultsComponent;

    SimulationDetailsController simulationDetailsController;
    AnchorPane simulationDetailsComponent;

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
    private Button magnmentBtn;
    @FXML
    private Button AllocationsBtn;
    @FXML
    private Button ExecutionsBtn;
    @FXML
    private AnchorPane mainAnchorpane;

    public void initApplication()
    {
        loadExecution();
        loadRequests();
        loadResults();
        loadSimulationDetails();
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

//
    @FXML
    void switchToExecutionScreen(ActionEvent event)
    {
        setMainPanelTo(executionComponent);
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
        //writing something else?
    }
}
