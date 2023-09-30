package components.Management;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import pDTOS.SimulationDTO;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

public class SimulationTreeViewRefresher extends TimerTask
{
    TreeView treeView;

    public SimulationTreeViewRefresher(TreeView treeView)
    {
        this.treeView = treeView;
    }

    @Override
    public void run()
    {
        try
        {
            // Fetch data from the server (e.g., using HTTP requests)
            List<SimulationDTO> simulationDTOList = fetchDataFromServer().get();

            // Update the JavaFX TreeView on the JavaFX Application Thread
            Platform.runLater(() ->
            {
                // Clear the existing tree items
                treeView.getRoot().getChildren().clear();

                // Iterate through simulationDTOList and populate the TreeView
                for (SimulationDTO simulationDTO : simulationDTOList)
                {
                    treeView.getRoot().getChildren().add(simulationDTO.generateTreeView());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CompletableFuture<List<SimulationDTO>> fetchDataFromServer()
    {
        // Replace this URL with the actual URL of your server endpoint
        String serverUrl = "http://localhost:8080/simulations"; // Example URL

        Request request = new Request.Builder()
                .url(serverUrl)
                .build();

        Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);

        CompletableFuture<List<SimulationDTO>> future = new CompletableFuture<>();

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (!response.isSuccessful())
                    {
                        throw new IOException("Unexpected HTTP response: " + response);
                    }

                    String rawBody = response.body().string();
                    Gson gson = new Gson();
                    TypeToken<List<SimulationDTO>> typeToken = new TypeToken<List<SimulationDTO>>() {};
                    List<SimulationDTO> simulationDTOList = gson.fromJson(rawBody, typeToken.getType());

                    future.complete(simulationDTOList);
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }


}
