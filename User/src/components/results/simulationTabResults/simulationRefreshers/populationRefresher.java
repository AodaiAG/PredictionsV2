package components.results.simulationTabResults.simulationRefreshers;

import Requests.SimulationRequestDetails;
import Requests.SimulationRequestExecuter.SimulationTaskHelper.ObservableEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import okhttp3.*;
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
            List<ObservableEntity> tickspopRes = fetchDataFromServer().get();
            ObservableList<ObservableEntity> observableList = FXCollections.observableArrayList(tickspopRes);

            if (tickspopRes == null)
                return;
            Platform.runLater(() ->
            {
                populationProgress.setItems(observableList);
            });

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private CompletableFuture< List<ObservableEntity>> fetchDataFromServer()
    {
        String serverUrl = "http://localhost:8080/population_progress?r_id="+requestId.toString()+"&e_id="+executionId.toString(); // Example URL
        CompletableFuture<List<ObservableEntity>> future = new CompletableFuture<>();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(serverUrl).method("POST",body )
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
                    TypeToken< List<ObservableEntity>> typeToken = new TypeToken< List<ObservableEntity>>() {};
                    List<ObservableEntity> simulationReqs = gson.fromJson(rawBody, typeToken.getType());
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
