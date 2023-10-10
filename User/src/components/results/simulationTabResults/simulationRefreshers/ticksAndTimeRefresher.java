package components.Results.simulationTabResults.simulationRefreshers;

import Requests.SimulationRequestDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
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

public class ticksAndTimeRefresher extends TimerTask
{
    Label updateLabel;
    UUID requestId;
    UUID executionId;

    public ticksAndTimeRefresher(Label updateLabel, UUID requestId, UUID executionId)
    {
        this.updateLabel = updateLabel;
        this.requestId = requestId;
        this.executionId = executionId;
    }

    @Override
    public void run()
    {
        try
        {
            String tickspopRes = fetchDataFromServer().get();
            if (tickspopRes == null)
                return;
            Platform.runLater(() ->
            {
                updateLabel.setText(tickspopRes);
            });

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private CompletableFuture<String> fetchDataFromServer()
    {
        String serverUrl = "http://localhost:8080/ticks_time?r_id="+requestId.toString()+"&e_id="+executionId.toString(); // Example URL
        CompletableFuture<String> future = new CompletableFuture<>();
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
                    String res = response.body().string();
                    future.complete(res);
                } catch (IOException e)
                {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }
}
