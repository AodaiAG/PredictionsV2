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
import java.util.List;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RequestsRefresher extends TimerTask
{
    TableView<SimulationRequest> tableView;
    public RequestsRefresher(TableView<SimulationRequest> tableView)
    {
        this.tableView = tableView;
    }

    @Override
    public void run() {
        try {
            Set<SimulationRequest> simulationRequests = fetchDataFromServer().get();
            Platform.runLater(()->{  ObservableList<SimulationRequest> observableList = FXCollections.observableArrayList(simulationRequests);

                // Set the items of the table
                tableView.setItems(observableList);

            });


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private CompletableFuture<Set<SimulationRequest>> fetchDataFromServer()
    {
        CompletableFuture<Set<SimulationRequest>> future = new CompletableFuture<>();

        String finalUrl = HttpUrl
                .parse(Constants.ALLOCATIONS_PATH)
                .newBuilder()
                .addQueryParameter("type", "admin")
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                try
                {
                    if (!response.isSuccessful())
                    {
                        return;
                    }
                    String rawBody = response.body().string();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    TypeToken<Set<SimulationRequest>> typeToken = new TypeToken<Set<SimulationRequest>>() {};
                    Set<SimulationRequest> simulationReqs = gson.fromJson(rawBody, typeToken.getType());
                    future.complete(simulationReqs);
                } catch (IOException e) {
                   future.completeExceptionally(e);
                }
            }

        });

        return future;
    }
}
