package components.Results.simulationTabResults.simulationRefreshers;

import Requests.SimulationRequestDetails;
import Requests.SimulationRequestExecuter.SimulationTaskHelper.ObservableEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class populationRefresher extends TimerTask
{
    private TableView<ObservableEntity> populationProgress;
    UUID requestId;
    UUID executionId;

    public populationRefresher(TableView<ObservableEntity> populationProgress, UUID requestId, UUID executionId)
    {
        this.populationProgress = populationProgress;
        this.requestId = requestId;
        this.executionId = executionId;
    }

    @Override
    public void run()
    {
        try
        {
            ObservableList<ObservableEntity> tickspopRes = fetchDataFromServer().get();
            if (tickspopRes == null)
                return;
            Platform.runLater(() ->
            {
                populationProgress.setItems(tickspopRes);
            });

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private CompletableFuture< ObservableList<ObservableEntity>> fetchDataFromServer()
    {
        String serverUrl = "http://localhost:8080/population_progress?r_id="+requestId.toString()+"&e_id="+executionId.toString(); // Example URL
        CompletableFuture<ObservableList<ObservableEntity>> future = new CompletableFuture<>();
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
                    String rawBody = response.body().string();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    TypeToken< ObservableList<ObservableEntity>> typeToken = new TypeToken< ObservableList<ObservableEntity>>() {};
                    ObservableList<ObservableEntity> simulationReqs = gson.fromJson(rawBody, typeToken.getType());
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
