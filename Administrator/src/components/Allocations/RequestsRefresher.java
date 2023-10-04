package components.Allocations;

import Requests.SimulationRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import components.Management.SimulationTreeViewRefresher;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.SimulationDTO;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class RequestsRefresher extends TimerTask
{
    TableView<SimulationRequest> tableView;
    public RequestsRefresher(TableView<SimulationRequest> tableView)
    {
        this.tableView = tableView;
    }

    @Override
    public void run()
    {
        try
        {
            List<SimulationRequest> simulationRequests = fetchDataFromServer().get();
            if (simulationRequests == null)
                return;
            ObservableList<SimulationRequest> observableList = FXCollections.observableArrayList(simulationRequests);
            Platform.runLater(() ->
            {
                tableView.setItems(observableList);
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private CompletableFuture<List<SimulationRequest>> fetchDataFromServer()
    {
        String serverUrl = "http://localhost:8080/allocations?type=admin"; // Example URL
        CompletableFuture<List<SimulationRequest>> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url(serverUrl)
                .build();
        Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
        call.enqueue(new Callback()
        { @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e)
        {
        }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                try
                {

                    String rawBody = response.body().string();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    TypeToken<List<SimulationRequest>> typeToken = new TypeToken<List<SimulationRequest>>() {};
                    List<SimulationRequest> simulationReqs = gson.fromJson(rawBody, typeToken.getType());
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
