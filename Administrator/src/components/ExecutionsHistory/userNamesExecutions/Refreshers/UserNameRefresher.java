package components.ExecutionsHistory.userNamesExecutions.Refreshers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

public class UserNameRefresher extends TimerTask
{
    private ChoiceBox<String> usernameChoiceBox;

    public UserNameRefresher(ChoiceBox<String> usernameChoiceBox)
    {
        this.usernameChoiceBox = usernameChoiceBox;
    }

    @Override
    public void run()
    {
        try
        {
            List<String> listOfSimulationNames = fetchDataFromServer().get();
            if (listOfSimulationNames == null)
                return;
            // Store the selected item
            String selectedItem = usernameChoiceBox.getValue();

            ObservableList<String> observableList = FXCollections.observableArrayList(listOfSimulationNames);
            if(listOfSimulationNames.size()!=usernameChoiceBox.getItems().size())
            {
                Platform.runLater(() ->
                {
                    // Update the ChoiceBox items
                    usernameChoiceBox.setItems(observableList);

                    // Set the previously selected item back as selected
                    usernameChoiceBox.setValue(selectedItem);
                });
            }



        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private CompletableFuture<List<String>> fetchDataFromServer()
    {
        String serverUrl = "http://localhost:8080/get_user_names"; // Example URL
        CompletableFuture<List<String>> future = new CompletableFuture<>();
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
                    TypeToken<List<String>> typeToken = new TypeToken<List<String>>() {};
                    List<String> simulationReqs = gson.fromJson(rawBody, typeToken.getType());

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
