package components.simulationDetails;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import pDTOS.ActionsDTO.*;
import pDTOS.SimulationDTO;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SimulationTreeViewRefresher extends TimerTask
{
    TreeView treeView;

    public SimulationTreeViewRefresher(TreeView treeView)
    {
        this.treeView = treeView;
        treeView.setRoot(new TreeItem<>("Simulations"));

    }

    @Override
    public void run()
    {
        try
        {
            // Fetch data from the server (e.g., using HTTP requests)
            List<SimulationDTO> simulationDTOList = fetchDataFromServer().get();

            // Create a map of existing SimulationDTO names to their TreeItem nodes
            Map<String, TreeItem<String>> existingNodes = new HashMap<>();
            for (Object itemObj : treeView.getRoot().getChildren())
            {
                TreeItem<String> item = (TreeItem<String>) itemObj;
                existingNodes.put(item.getValue(), item);

            }
            // Update the JavaFX TreeView on the JavaFX Application Thread
            Platform.runLater(() ->
            {
                // Iterate through simulationDTOList
                for (SimulationDTO simulationDTO : simulationDTOList)
                {
                    String simulationName = simulationDTO.getNameofSimulation();

                    // Check if there's an existing TreeItem for this SimulationDTO
                    TreeItem<String> existingNode = existingNodes.get(simulationName);

                    if (existingNode != null)
                    {
                        // Update the existing TreeItem (e.g., update labels)
                        // You might need to implement an update method for your TreeItems
                        // existingNode.setValue(simulationDTO.getNameofSimulation());
                    }
                    else
                    {
                        // Create a new TreeItem for the SimulationDTO
                        TreeItem<String> newSimulationNode = simulationDTO.generateTreeView();
                        existingNodes.put(simulationName, newSimulationNode);
                        treeView.getRoot().getChildren().add(newSimulationNode);
                    }
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    // Helper function to get the expanded TreeItems
    private List<TreeItem<String>> getExpandedItems(TreeItem<String> root)
    {
        List<TreeItem<String>> expandedItems = new ArrayList<>();
        for (TreeItem<String> item : root.getChildren())
        {
            if (item.isExpanded()) {
                expandedItems.add(item);
                expandedItems.addAll(getExpandedItems(item));
            }
        }
        return expandedItems;
    }

    // Helper function to restore the expanded state
    private void restoreExpandedItems(TreeItem<String> root, List<TreeItem<String>> expandedItems) {
        for (TreeItem<String> item : root.getChildren()) {
            if (expandedItems.contains(item)) {
                item.setExpanded(true);
                restoreExpandedItems(item, expandedItems);
            }
        }
    }


    private CompletableFuture<List<SimulationDTO>> fetchDataFromServer()
    {
        // Replace this URL with the actual URL of your server endpoint
        String serverUrl = "http://localhost:8080/get_simulations_details"; // Example URL

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
                    TypeToken<List<SimulationDTO>> typeToken = new TypeToken<List<SimulationDTO>>() {};
                    List<SimulationDTO> simulationDTOList = gson.fromJson(rawBody, typeToken.getType());
                    future.complete(simulationDTOList);
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


}
