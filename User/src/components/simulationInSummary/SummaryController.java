package components.simulationInSummary;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import components.execution.ExecutionController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import pDTOS.ActionsDTO.*;
import pDTOS.WorldDTO;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SummaryController
{
    Stage stage;
    @FXML
    private Button proceedBtn;

    @FXML
    private Button abortBTN;
    ExecutionController executionController;
    UUID requestId;
    @FXML
    private TreeView<String> summaryTreeView;

    @FXML
    void abortClicked(ActionEvent event)
    {
       stage.close();
    }

    @FXML
    void procedClicked(ActionEvent event)
    {

        executionController.startTSimulation(event);
        stage.close();
    }

    public void setStage(Stage popupStage)
    {
        this.stage =popupStage;
    }

    public void setExecutionController(ExecutionController executionController)
    {
        this.executionController=executionController;
    }
    public void populateInfo()
    {
        try
        {
            WorldDTO worldDTO=getWorldDtoAfterFromServer().get();
            Platform.runLater(() ->
            {
                TreeView<String> treeView=worldDTO.generateTreeViewForSummary();
                TreeItem<String> rootItem = treeView.getRoot();
                summaryTreeView.setRoot(new TreeItem<>("Simulation Summary"));
                summaryTreeView.getRoot().setExpanded(true);
                for (TreeItem<String> child : rootItem.getChildren())
                {
                    summaryTreeView.getRoot().getChildren().add(child);
                }


            });

        }
        catch (Exception e)
        {

        }
    }

    private CompletableFuture<WorldDTO> getWorldDtoAfterFromServer()
    {
        // Replace this URL with the actual URL of your server endpoint
        String serverUrl = "http://localhost:8080/get_simulation_summary?id="+requestId.toString(); // Example URL
        Request request = new Request.Builder()
                .url(serverUrl)
                .build();

        Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
        CompletableFuture<WorldDTO> future = new CompletableFuture<>();
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
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
                    Gson gson = new GsonBuilder().registerTypeAdapter(ActionDTO.class,new ActionDTODeserilzer())
                            .setPrettyPrinting().create();
                    TypeToken<WorldDTO> typeToken = new TypeToken<WorldDTO>() {};
                    WorldDTO worldDTO = gson.fromJson(rawBody, typeToken.getType());
                    future.complete(worldDTO);
                }
                catch (Exception e)
                {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }
    private static class ActionDTODeserilzer implements JsonDeserializer<ActionDTO>
    {
        @Override
        public ActionDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String actionType = jsonObject.get("nameOfAction").getAsString();

            // Depending on the 'type' field in the JSON, create an instance of the corresponding concrete ActionDTO subclass
            switch (actionType)
            {
                case "calculation":
                    return context.deserialize(json, CalculationActionDTO.class);
                case "condition":
                    return context.deserialize(json, ConditionActionDTO.class);
                case "decrease":
                    return context.deserialize(json, DecreaseActionDTO.class);
                case "increase":
                    return context.deserialize(json, IncreaseActionDTO.class);
                case "kill":
                    return context.deserialize(json, KillActionDTO.class);
                case "proximity":
                    return context.deserialize(json, ProximityActionDTO.class);
                case "replace":
                    return context.deserialize(json, ReplaceActionDTO.class);
                case "set":
                    return context.deserialize(json, SetActionDTO.class);

                // Add more cases for other concrete ActionDTO subclasses as needed
                default:
                    throw new JsonParseException("Unknown action type: " + actionType);
            }
        }

    }

    public void setRequestId(UUID requestId)
    {
        this.requestId=requestId;
    }
}
