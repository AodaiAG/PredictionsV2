package components.requests.showUserRequests;

import Requests.SimulationRequestDetails;
import components.mainApp.UserMainAppController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class UserRequestsController
{

    private UserMainAppController userMainAppController;

    @FXML
    private TableView<SimulationRequestDetails> userRequestTableView;

    @FXML
    private TableColumn<SimulationRequestDetails, String> idColumn;

    @FXML
    private TableColumn<SimulationRequestDetails, String> simulationNameColumn;

    @FXML
    private TableColumn<SimulationRequestDetails, String> numOfExecutionsColumn;

    @FXML
    private TableColumn<SimulationRequestDetails, String> requestStatusColumn;

    @FXML
    private TableColumn<SimulationRequestDetails, String> executionsRunningColumn;

    @FXML
    private TableColumn<SimulationRequestDetails, String> executionsFinishedColumn;
    @FXML
    private TableColumn<SimulationRequestDetails, Void> executeColumn;
    @FXML
    private TableColumn<SimulationRequestDetails, String> leftCoulmn;


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
        numOfExecutionsColumn.setCellValueFactory(cellData-> {
            int numOfExecutions = cellData.getValue().getNumOfExecutions();
            return new SimpleStringProperty(String.valueOf(numOfExecutions));
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
        requestStatusColumn.setCellValueFactory(cellData->
        {
            String requestStatus = cellData.getValue().getRequestStatus();
            return new SimpleStringProperty(requestStatus);
        });
        leftCoulmn.setCellValueFactory(cellData->
        {
            String requestStatus = cellData.getValue().getExecutionsLeftAmount();

            return new SimpleStringProperty(requestStatus);

        });

        executeColumn.setCellFactory(param -> new TableCell<SimulationRequestDetails, Void>()
        {
            private final Button executeButton = new Button("Execute");
            @Override
            protected void updateItem(Void item, boolean empty)
            {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }
                SimulationRequestDetails simulationRequestDetails = getTableView().getItems().get(getIndex());
                executeButton.setStyle("-fx-background-color: #2feb0c; -fx-text-fill: white;");
                // Create an HBox with spacing
                HBox buttonsBox = new HBox(); // Adjust the spacing value (10 in this case)
                buttonsBox.getChildren().addAll(executeButton);
                if(!simulationRequestDetails.getRequestStatus().equals("approved"))
                {
                    buttonsBox.setDisable(true);
                }
                else
                {
                    buttonsBox.setDisable(false);
                }
                setGraphic(buttonsBox);
                executeButton.setOnAction(event ->
                {
                    handleExecute(simulationRequestDetails,buttonsBox);
                    userMainAppController.switchToExecutionPage();
                });


            }
        });
        startRequestRefresher();
    }

    private void handleExecute(SimulationRequestDetails simulationRequestDetails, HBox buttonsBox)
    {
        userMainAppController.initExecuterPageWithDetails(simulationRequestDetails);
    }

    public void startRequestRefresher()
    {
        Timer timer = new Timer();
        TimerTask task = new RequestsRefresher(userRequestTableView);
        long delay = 0; // Initial delay (0 milliseconds)
        long period = 2000; // Repeat every 2 seconds (2000 milliseconds)
        timer.scheduleAtFixedRate(task, delay, period);
    }

    public void setAppMainController(UserMainAppController mainAppController) {
        this.userMainAppController = mainAppController;
    }
}
