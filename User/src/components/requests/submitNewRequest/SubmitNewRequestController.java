package components.requests.submitNewRequest;

import Requests.SimulationRequestDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.mainApp.UserMainAppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pDTOS.TerminationDTO;
import util.http.HttpClientUtil;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static util.Constants.FULL_SERVER_PATH;
import static util.Constants.NEW_REQUEST;


public class SubmitNewRequestController
{
    @FXML
    private TextField numOfExecutions;
    @FXML
    private CheckBox ticksOption;
    @FXML
    private CheckBox timeOption;
    @FXML
    private CheckBox userOption;
    @FXML
    private TextField ticksText;
    @FXML
    private TextField timeText;
    @FXML
    private Button submitBTN;
    UserMainAppController mainAppController;
    @FXML
    private ChoiceBox<String> simulationNamesCHoiceBox;


    public void initialize()
    {
        submitBTN.setDisable(true);
        startSimulationNamesRefresher();
    }

    public void startSimulationNamesRefresher()
    {
        Timer timer = new Timer();
        TimerTask task = new simulationNamesRefresher(simulationNamesCHoiceBox);
        long delay = 0; // Initial delay (0 milliseconds)
        long period = 2000; // Repeat every 2 seconds (2000 milliseconds)
        timer.scheduleAtFixedRate(task, delay, period);
    }

    public void setAppMainController(UserMainAppController mainAppController)
    {
        this.mainAppController = mainAppController;
    }

    @FXML
    void userCheckBoxClicked(ActionEvent event) {
        if(userOption.isSelected()) {
            ticksOption.setDisable(true);
            ticksOption.setSelected(false);
            timeOption.setDisable(true);
            timeOption.setSelected(false);
            submitBTN.setDisable(false);
        }
        else {
            ticksOption.setDisable(false);
            timeOption.setDisable(false);
            submitBTN.setDisable(true);
        }
    }

    @FXML
    void timeCheckBoxClicked(ActionEvent event) {
        if(timeOption.isSelected())
        {
            userOption.setDisable(true);
            submitBTN.setDisable(false);
            timeText.clear();
            timeText.setDisable(false);
        }
        else {
            if(!ticksOption.isSelected())
            {
                userOption.setDisable(false);
                submitBTN.setDisable(true);
            }
            timeText.clear();
            timeText.setDisable(true);
        }
    }

    @FXML
    void ticksCheckBoxClicked(ActionEvent event) {
        if(ticksOption.isSelected())
        {
            userOption.setDisable(true);
            submitBTN.setDisable(false);
            ticksText.clear();
            ticksText.setDisable(false);
        }
        else {
            if(!timeOption.isSelected())
            {
                userOption.setDisable(false);
                submitBTN.setDisable(true);
            }
            ticksText.clear();
            ticksText.setDisable(true);
        }
    }
    @FXML
    void submitClicked(ActionEvent event)
    {
        try {
            String numOfExecutionsText = numOfExecutions.getText();
            String selectedSimulation = simulationNamesCHoiceBox.getValue();

            if (numOfExecutionsText.isEmpty() || selectedSimulation == null)
            {
                String errorMessage = "Please fill in all required fields.";
                if (numOfExecutionsText.isEmpty())
                {
                    errorMessage += "\n- Number of Executions is empty.";
                }
                if (selectedSimulation == null) {
                    errorMessage += "\n- No simulation selected.";
                }

                // Display an alert with the error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(errorMessage);
                alert.showAndWait();
            } else {
                // Fields are valid; proceed with the request
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                SimulationRequestDetails simulationRequestDetails = new SimulationRequestDetails(
                        UUID.randomUUID(),
                        selectedSimulation,
                        Integer.parseInt(numOfExecutionsText),
                        getTermination(),
                        mainAppController.getUserName()
                );

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, gson.toJson(simulationRequestDetails));
                Request request = new Request.Builder()
                        .url(FULL_SERVER_PATH + NEW_REQUEST)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response response = HttpClientUtil.HTTP_CLIENT.newCall(request).execute();

                // Display a success message in the alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Status");
                alert.setHeaderText(null);
                alert.setContentText(response.body().string());
                alert.showAndWait();
            }
        } catch (Exception e) {
            // Handle other exceptions (e.g., network issues, server errors)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    TerminationDTO getTermination()
    {
        TerminationDTO terminationDTO = new TerminationDTO();
        boolean isUserOptionSelected = userOption.isSelected();
        boolean isTicksOptionSelected = ticksOption.isSelected();
        boolean isTimeOptionSelected = timeOption.isSelected();

        terminationDTO.setByUser(isUserOptionSelected);
        if(isTicksOptionSelected)
        {
            terminationDTO.setTerminationTicks(Integer.parseInt(ticksText.getText()));
        }

        if(isTimeOptionSelected)
        {
            terminationDTO.setTerminationSeconds(Integer.parseInt(timeText.getText()));
        }

        return terminationDTO;
    }
}