package components.execution;

import Requests.SimulationRequestDetails;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import components.mainApp.UserMainAppController;
import components.simulationDetails.SimulationTreeViewRefresher;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import pDTOS.ActionsDTO.*;
import pDTOS.EntityDTO;
import pDTOS.EnvironmentDTO;
import pDTOS.SimulationDTO;
import pDTOS.WorldDTO;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static util.Constants.FULL_SERVER_PATH;
import static util.Constants.NEW_REQUEST;

public class ExecutionController
{
    UserMainAppController mainAppController;
    public VBox environmentDetailsVBox;
    @FXML
    public ListView<String> entitesList;
    @FXML
    private ListView<String> environmentVariableListView;
    private List<EnvironmentDTO> environmentDTOList;
    @FXML
    private AnchorPane detailsPane;
    @FXML
    private AnchorPane listPane;
    @FXML
    private SplitPane splitPane;
    @FXML
    private VBox vbox;
    @FXML
    private TextField dataTextField,populationText;

    private EnvironmentDTO selectedEnvironmentDTO;
    private EntityDTO SelectedentityDTO;
    @FXML
    Button clearBtn;
    private UUID requestId;



    public void initializeController(SimulationRequestDetails simulationRequest)
    {
             requestId=simulationRequest.getId();
             fetchWorldDtoAndUpdateInfo();


    }

    public void fetchWorldDtoAndUpdateInfo()
    {
        try
        {
            WorldDTO worldDTO=fetchWorldDTOFromServer(requestId).get();
            populateListViewsWithInfo(worldDTO);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void populateListViewsWithInfo(WorldDTO worldDTO)
    {
        populationText.setDisable(true);
        List<EntityDTO> entities = worldDTO.getEntityDTOSet();
        ObservableList<String> entitiesList = FXCollections.observableArrayList
                (
                        entities.stream()
                                .map(entityDTO -> entityDTO.getName()) // Replace with the appropriate method to get the string representation of your EntityDTO
                                .collect(Collectors.toList())
                );

        entitesList.setItems(entitiesList);
        // Handle selection change in the list view
        entitesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue != null)
            {
                populationText.setDisable(false);
                SelectedentityDTO = entities.get(entitesList.getSelectionModel().getSelectedIndex());
                populationText.clear();
            }
            else
            {

            }
        });

        dataTextField = new TextField();
        List<EnvironmentDTO> enDTO = worldDTO.getEnvironmentDTOS();
        ObservableList<String> detailsList = FXCollections.observableArrayList();

        for (EnvironmentDTO environmentDTO : enDTO)
        {
            String envName = environmentDTO.getEnProperty().getNameOfProperty();
            detailsList.add(envName);
        }

        // Set the detailsList as the items in the ListView
        environmentVariableListView.setItems(detailsList);

        // Listen for selection changes in the ListView
        environmentVariableListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue != null)
            {
                // Get the selected EnvironmentDTO
                EnvironmentDTO selectedEnvironment = enDTO.get(environmentVariableListView.getSelectionModel().getSelectedIndex());
                //selectedEnvironment = updateEnvironment(selectedEnvironment);
                updateDetailsPane(selectedEnvironment);
                environmentVariableListView.refresh();
            }
        });
    }






    public void setAppMainController(UserMainAppController mainAppController)
    {
        this.mainAppController = mainAppController;
    }



    @FXML
    private void handleAddButtonClick()
    {
        String popString = populationText.getText();
        try
        {
            int popn = Integer.parseInt(popString);
            generatePopulation(SelectedentityDTO, popn);

        }
        catch (IllegalArgumentException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Population should be numeric!");
            alert.showAndWait();
        }
        catch(Exception exception)
        {
            // Create and configure an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred");
            alert.setContentText("Unable to generate population: " + exception.getMessage());
            alert.showAndWait();
        }
    }

    private void generatePopulation(EntityDTO selectedentityDTO, int popn) throws IOException
    {
        try
        {

            Gson gson = new GsonBuilder() .setPrettyPrinting().create();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, gson.toJson(selectedentityDTO));
            Request request = new Request.Builder()
                    .url("http://localhost:8080/add_population?id="+requestId+"&value="+(popn))
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response= HttpClientUtil.HTTP_CLIENT.newCall(request).execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(response.body().string());
            alert.showAndWait();


        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public void updateDetailsPane(EnvironmentDTO selectedEnvironment)
    {
        // Clear any existing content in the detailsPane
        detailsPane.getChildren().clear();
        Button modifyButton = new Button("Modify data");
        // Create and add UI elements to detailsPane based on selectedEnvironment
        Label nameLabel = new Label("Name: " + selectedEnvironment.getEnProperty().getNameOfProperty());
        Label type = new Label("Type: " + selectedEnvironment.getEnProperty().getNameOfDataType());
        Label range = new Label("Range: " + selectedEnvironment.getEnProperty().getFrom()+" - "+selectedEnvironment.getEnProperty().getTo());
        Label datavalue = new Label("Data value: " + selectedEnvironment.getEnProperty().getDataString());
        // Add other labels for range, data, etc.
        modifyButton.setOnAction(event ->
        {
            // Handle the "Modify" button click here
            handleModifyButtonClick(selectedEnvironment);
            fetchWorldDtoAndUpdateInfo();

        });

        // Define the layout of elements within detailsPane
        vbox.getChildren().clear();
        vbox.getChildren().addAll(nameLabel,type,range,datavalue, dataTextField, modifyButton);

        // Add the layout to detailsPane
        detailsPane.getChildren().add(vbox);

        // Set the SplitPane divider position to control the width of list and details
        SplitPane.setResizableWithParent(listPane, Boolean.FALSE);
        SplitPane.setResizableWithParent(detailsPane, Boolean.FALSE);
        splitPane.setDividerPositions(0.2); // Adjust this value as needed
    }

    private void handleModifyButtonClick(EnvironmentDTO selectedEnvironment)
    {
        String dataEnterd=dataTextField.getText();
        setDataToEnvironmentVar(selectedEnvironment,dataEnterd);
    }
    public void setDataToEnvironmentVar(EnvironmentDTO selectedEnvironment, String enteredData) {
        try
        {
            Gson gson = new GsonBuilder() .setPrettyPrinting().create();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, gson.toJson(selectedEnvironment));
            Request request = new Request.Builder()
                    .url("http://localhost:8080/update_env?id="+requestId+"&user_value="+enteredData)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response= HttpClientUtil.HTTP_CLIENT.newCall(request).execute();
            String responseBody = response.body().string();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(responseBody);
            alert.showAndWait();
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("An error occurred reasons: "+e.getMessage());
            alert.showAndWait();
        }
    }


    public List<EnvironmentDTO> getEnvironmentDTOList()
    {
        return environmentDTOList;
    }

    @FXML
    void startSimulation(ActionEvent event)
    {
        try
        {
            UUID executedSimulationId=sendHttpRequestAndGetExecutionID(requestId).get();
            //executedSimulationId
            this.mainAppController.initExecutionTracker(requestId,executedSimulationId);
            this.mainAppController.switchToResultsPage();

        }
        catch (Exception e)
        {

        }

    }



    private  CompletableFuture<UUID> sendHttpRequestAndGetExecutionID(UUID requestId)
    {
        // Replace this URL with the actual URL of your server endpoint
        String serverUrl = "http://localhost:8080/init_execute_simulation?id="+requestId.toString(); // Example URL
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(serverUrl).method("POST",body)
                .build();

        Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
        CompletableFuture<UUID> future = new CompletableFuture<>();
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {

                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                try
                {
                    String rawBody = response.body().string();
                    UUID executionId= UUID.fromString(rawBody);
                    future.complete(executionId);
                }
                catch (Exception e)
                {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }


    public void clearOnAction(ActionEvent event)
    {
        // Replace this URL with the actual URL of your server endpoint
        String serverUrl = "http://localhost:8080/clear_btn?id="+requestId.toString(); // Example URL

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
                    Platform.runLater(()->
                    {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Cleared .");
                    });

                }
                catch (Exception e)
                {
                    Platform.runLater(()->
                    {
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(e.getMessage());
                    });
                }
            }
        });

    }

    private CompletableFuture<WorldDTO> fetchWorldDTOFromServer(UUID uuid)
    {
        // Replace this URL with the actual URL of your server endpoint
        String serverUrl = "http://localhost:8080/get_world_dto?id="+uuid.toString(); // Example URL

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

}