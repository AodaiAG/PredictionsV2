package components.Allocations;

import Requests.SimulationRequest;
import components.Management.SimulationTreeViewRefresher;
import components.mainApp.MainAppController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class AllocationsController
{
    MainAppController mainAppController;
    @FXML
    private TableView<SimulationRequest> requestTableView;

    @FXML
    private TableColumn<SimulationRequest, String> idColumn;

    @FXML
    private TableColumn<SimulationRequest, String> simulationNameColumn;

    @FXML
    private TableColumn<SimulationRequest, String> userNameColumn;

    @FXML
    private TableColumn<SimulationRequest, String> numOfExecutionsColumn;

    @FXML
    private TableColumn<SimulationRequest, String> terminationConditionsColumn;

    @FXML
    private TableColumn<SimulationRequest, String> executionsRunningColumn;

    @FXML
    private TableColumn<SimulationRequest, String> executionsFinishedColumn;

    public void setChatAppMainController(MainAppController mainAppController)
    {
        this.mainAppController=mainAppController;
    }
    public void startRequestRefresher()
    {
        Timer timer = new Timer();
        TimerTask task = new RequestsRefresher(requestTableView);
        long delay = 2000; // Initial delay (0 milliseconds)
        long period = 2000; // Repeat every 2 seconds (2000 milliseconds)
        timer.scheduleAtFixedRate(task, delay, period);

    }

    public void initialize()
    {
        idColumn.setCellValueFactory(cellData ->
        {
            UUID id = cellData.getValue().getId();
            String idString = id != null ? id.toString() : ""; // Convert UUID to String or set an empty string if it's null
            return new SimpleStringProperty(idString);
        });
        simulationNameColumn.setCellValueFactory(cellData-> {
            String sName = cellData.getValue().getSimulationName();
            return new SimpleStringProperty(sName != null ? sName : "");
        });
        userNameColumn.setCellValueFactory(cellData->
        {
            String uName = cellData.getValue().getUserName();
            return new SimpleStringProperty(uName != null ? uName : "");
        });
        numOfExecutionsColumn.setCellValueFactory(cellData-> {
            int numOfExecutions = cellData.getValue().getNumOfExecutions();
            return new SimpleStringProperty(String.valueOf(numOfExecutions));
        });
        terminationConditionsColumn.setCellValueFactory(cellData->
        {
            String terminationConditions = cellData.getValue().getTerminationConditions().toString();
            return new SimpleStringProperty(terminationConditions);
        });
        executionsRunningColumn.setCellValueFactory(cellData->
        {
            String executionsRunningAmount = cellData.getValue().getExecutionsRunningAmount();
            return new SimpleStringProperty(executionsRunningAmount);
        });
        executionsFinishedColumn.setCellValueFactory(cellData->
        {
            String executionsFinishedAmount = cellData.getValue().getExecutionsFinishedAmount();
            return new SimpleStringProperty(executionsFinishedAmount);
        });

        startRequestRefresher();
    }
}
