package components.requests.showUserRequests;

import Requests.SimulationRequestDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class RequestsRefresher extends TimerTask
{
    TableView<SimulationRequestDetails> tableView;
    public RequestsRefresher(TableView<SimulationRequestDetails> tableView)
    {
        this.tableView = tableView;
    }
    @Override
    public void run()
    {
        try
        {
            List<SimulationRequestDetails> simulationRequestDetails = fetchDataFromServer().get();
            if (simulationRequestDetails == null)
                return;
            ObservableList<SimulationRequestDetails> observableList = FXCollections.observableArrayList(simulationRequestDetails);
            Platform.runLater(() ->
            {
                tableView.setItems(observableList);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private CompletableFuture<List<SimulationRequestDetails>> fetchDataFromServer()
    {
        String serverUrl = "http://localhost:8080/allocations?type=user"; // Example URL
        CompletableFuture<List<SimulationRequestDetails>> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url(serverUrl)
                .build();
        Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
        call.enqueue(new Callback()
        { @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e)
        {
            System.out.println("i'm in onFailure/user");
        }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                try
                {
                    //System.out.println("i'm in onResponse/user");

                    String rawBody = response.body().string();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    TypeToken<List<SimulationRequestDetails>> typeToken = new TypeToken<List<SimulationRequestDetails>>() {};
                    List<SimulationRequestDetails> simulationReqs = gson.fromJson(rawBody, typeToken.getType());
                    future.complete(simulationReqs);
                } catch (IOException e)
                {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }
}
