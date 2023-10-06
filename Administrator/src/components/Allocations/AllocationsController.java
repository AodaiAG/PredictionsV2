package components.Allocations;

import Requests.SimulationRequestDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.mainApp.MainAppController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.*;

public class AllocationsController
{
    MainAppController mainAppController;
    @FXML
    private TableView<SimulationRequestDetails> requestTableView;

    @FXML
    private TableColumn<SimulationRequestDetails, String> idColumn;

    @FXML
    private TableColumn<SimulationRequestDetails, String> simulationNameColumn;

    @FXML
    private TableColumn<SimulationRequestDetails, String> userNameColumn;

    @FXML
    private TableColumn<SimulationRequestDetails, String> numOfExecutionsColumn;

    @FXML
    private TableColumn<SimulationRequestDetails, String> terminationConditionsColumn;

    @FXML
    private TableColumn<SimulationRequestDetails, String> executionsRunningColumn;

    @FXML
    private TableColumn<SimulationRequestDetails, String> executionsFinishedColumn;
    @FXML
    private TableColumn<SimulationRequestDetails, Void> approveColumn;
    @FXML
    private TableColumn<SimulationRequestDetails, Void> declineColumn;
    @FXML
    private TableColumn<SimulationRequestDetails, Void> handleRequestColumn;
    private final Map<UUID, Boolean> approvalStatusMap = new HashMap<>();




    public void setChatAppMainController(MainAppController mainAppController)
    {
        this.mainAppController = mainAppController;
    }
    public void startRequestRefresher()
    {
        Timer timer = new Timer();
        TimerTask task = new RequestsRefresher(requestTableView);
        long delay = 0; // Initial delay (0 milliseconds)
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
        simulationNameColumn.setCellValueFactory(cellData->
        {
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


        handleRequestColumn.setCellFactory(param -> new TableCell<SimulationRequestDetails, Void>()
        {
            private final Button approveButton = new Button("Approve");
            private final Button declineButton = new Button("Decline");

            @Override
            protected void updateItem(Void item, boolean empty)
            {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                SimulationRequestDetails simulationRequestDetails = getTableView().getItems().get(getIndex());

                // Create a property to track approval status

                // Bind button properties to the approval status


                approveButton.setStyle("-fx-background-color: #2feb0c; -fx-text-fill: white;");
                declineButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                // Create an HBox with spacing

                HBox buttonsBox = new HBox(10); // Adjust the spacing value (10 in this case)
                if(simulationRequestDetails.getRequestStatus().equals("unhandled"))
                {
                    buttonsBox.setDisable(false);
                }
                else
                {
                    buttonsBox.setDisable(true);
                }

                buttonsBox.getChildren().addAll(approveButton, declineButton);

                setGraphic(buttonsBox);

                approveButton.setOnAction(event ->
                {
                    // Handle the approve action for simulationRequest here
                    handleApprove(simulationRequestDetails,buttonsBox);

                    // Set the approval status to true

                });

                declineButton.setOnAction(event ->
                {
                    // Handle the decline action for simulationRequest here
                    handleDecline(simulationRequestDetails,buttonsBox);



                });
            }
        });




        startRequestRefresher();
    }

    private void handleDecline(SimulationRequestDetails simulationRequestDetails, HBox buttonsBox)
    {
        buttonsBox.setDisable(true);

        Gson gson = new GsonBuilder() .setPrettyPrinting().create();
        String url = "http://localhost:8080/handle_request?status=declined&username="+ simulationRequestDetails.getUserName();        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, gson.toJson(simulationRequestDetails.getId()));
        // Create a request object
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
            Callback callback=new Callback()
            {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e)
                {
                    Platform.runLater(() ->
                    {
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    });

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                {

                    Platform.runLater(() ->
                    {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Request Declined");
                        alert.showAndWait();
                    });

                }
            };
        Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
        call.enqueue(callback);


    }
        private void handleApprove(SimulationRequestDetails simulationRequestDetails, HBox buttonsBox)
    {
        buttonsBox.setDisable(true);
        Gson gson = new GsonBuilder() .setPrettyPrinting().create();
        String url = "http://localhost:8080/handle_request?status=approved&username="+ simulationRequestDetails.getUserName();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, gson.toJson(simulationRequestDetails.getId()));
        // Create a request object
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Callback callback=new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                Platform.runLater(() ->
                {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {

                Platform.runLater(() ->
                {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Request Approved");
                    alert.showAndWait();
                });

            }
        };
        Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
        call.enqueue(callback);
    }
}
