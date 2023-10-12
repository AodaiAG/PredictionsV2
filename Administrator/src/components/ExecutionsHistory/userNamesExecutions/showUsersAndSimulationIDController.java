package components.ExecutionsHistory.userNamesExecutions;

import Requests.RequestInfoHelper;
import Requests.SimulationRequestDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import components.ExecutionsHistory.showSimulationResult.SimulationResultsForUserController;
import components.ExecutionsHistory.userNamesExecutions.Refreshers.UserNameRefresher;
import components.ExecutionsHistory.userNamesExecutions.Refreshers.SimulationIdRefresher;
import components.mainApp.MainAppController;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class showUsersAndSimulationIDController
{
    SimulationResultsForUserController simulationResultsForUserController;

    @FXML
    private ChoiceBox<String> usernameChoiceBox;

    @FXML
    private ChoiceBox<String> simulationIdChoiceBox;
    Timer simulationIdTimer = new Timer();

    public void initialize()
    {
        usernameChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
            {
                // Populate the simulationIdChoiceBox based on the selected username
                startSimulationIdRefresher(newValue);
                // Call the function you want
                //simulationIdTimer.cancel();
            }
        });
        simulationIdChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue != null)
            {
                try
                {
                    RequestInfoHelper requestInfoHelper= sendHttpRequestAndGetExecutionID(UUID.fromString(newValue)).get();
                    simulationResultsForUserController.setSimulationResultsPane(requestInfoHelper.getRequestId(),requestInfoHelper.getRequestExecutorId());
                } catch ( Exception e)
                {
                   e.printStackTrace();
                }
                //  simulationResultsForUserController.setSimulationResultsPane();
            }
        });
        startUserNameRefresher();


    }
    MainAppController mainAppController;
    public void setAppMainController(MainAppController mainAppController)
    {

    }

    private void startUserNameRefresher()
    {
        Timer timer = new Timer();
        TimerTask task = new UserNameRefresher(usernameChoiceBox);
        long delay = 0; // Initial delay (0 milliseconds)
        long period = 2000; // Repeat every 2 seconds (2000 milliseconds)
        timer.scheduleAtFixedRate(task, delay, period);
    }
    private void startSimulationIdRefresher(String userName)
    {
        TimerTask task = new SimulationIdRefresher(simulationIdChoiceBox,userName);
        long delay = 0; // Initial delay (0 milliseconds)
        long period = 2000; // Repeat every 2 seconds (2000 milliseconds)
        simulationIdTimer.scheduleAtFixedRate(task, delay, period);
    }

    public void setResultsController(SimulationResultsForUserController simulationResultsForUserController)
    {
        this.simulationResultsForUserController=simulationResultsForUserController;
    }

    private CompletableFuture<RequestInfoHelper> sendHttpRequestAndGetExecutionID(UUID execId)
    {
        // Replace this URL with the actual URL of your server endpoint
        String serverUrl = "http://localhost:8080/get_req_from_exec?id="+execId.toString(); // Example URL
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(serverUrl).method("POST",body)
                .build();

        Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
        CompletableFuture<RequestInfoHelper> future = new CompletableFuture<>();
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                try
                {
                    String rawBody = response.body().string();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    TypeToken<RequestInfoHelper> typeToken = new TypeToken<RequestInfoHelper>() {};
                    RequestInfoHelper simulationReqs = gson.fromJson(rawBody, typeToken.getType());
                    future.complete(simulationReqs);
                }
                catch (Exception e)
                {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }

}
