package components.requests.submitNewRequest;

import Requests.SimulationRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.mainApp.MainAppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.TerminationDTO;
import util.http.HttpClientUtil;

import java.util.UUID;

import static util.Constants.FULL_SERVER_PATH;
import static util.Constants.NEW_REQUEST;


public class SubmitNewRequestController
{
    @FXML
    private TextField simulatioNameText;
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

    MainAppController mainAppController;


    public void initialize()
    {
        ticksOption.setOnAction(event -> handleOptionChange(ticksOption, ticksText));
        timeOption.setOnAction(event -> handleOptionChange(timeOption, timeText));
    }
    private void handleOptionChange(CheckBox checkBox, TextField textField)
    {
        boolean isSelected = checkBox.isSelected();

        if (isSelected) {
            textField.clear();
            textField.setDisable(false);
        } else {
            textField.clear();
            textField.setDisable(true);
        }
    }

    public void setAppMainController(MainAppController mainAppController)
    {
        this.mainAppController = mainAppController;
    }


    @FXML
    void submitClicked(ActionEvent event)
    {

        try
        {

            Gson gson = new GsonBuilder() .setPrettyPrinting().create();
            SimulationRequest simulationRequest=new SimulationRequest(new UUID(6,6),
                    simulatioNameText.getText(),
                    Integer.parseInt(numOfExecutions.getText()),getTermination());


            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, gson.toJson(simulationRequest));
            Request request = new Request.Builder()
                    .url(FULL_SERVER_PATH+NEW_REQUEST)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response= HttpClientUtil.HTTP_CLIENT.newCall(request).execute();
        }
        catch(Exception e)
        {

        }

    }

    TerminationDTO getTermination()
    {
        TerminationDTO terminationDTO=new TerminationDTO();
        boolean isUserOptionSelected = userOption.isSelected();
        boolean isTicksOptionSelected = ticksOption.isSelected();
        boolean isTimeOptionSelected = timeOption.isSelected();

        terminationDTO.setByUser(isUserOptionSelected);
        if(isTicksOptionSelected)
        terminationDTO.setTerminationTicks(Integer.parseInt(ticksText.getText()));

        if(isTimeOptionSelected)
            terminationDTO.setTerminationSeconds(Integer.parseInt(ticksText.getText()));

            return terminationDTO;
    }

}
