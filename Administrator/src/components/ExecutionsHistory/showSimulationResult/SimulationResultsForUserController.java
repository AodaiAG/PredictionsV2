package components.ExecutionsHistory.showSimulationResult;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import components.mainApp.MainAppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import pDTOS.ActionsDTO.*;
import pDTOS.EntityDTO;
import pDTOS.WorldDTO;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SimulationResultsForUserController
{
    UUID requestId;
    UUID executionId;
    private  MainAppController mainAppController;
    @FXML
    private AnchorPane resultsAnchor;

    @FXML
    private RadioButton populationInfoRadioButton;

    @FXML
    private RadioButton histogramRadioButton;

    @FXML
    private TextField simulationIdText;

    @FXML
    private LineChart<Integer, Integer> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private AnchorPane hisoPopPane;

    @FXML
    TableView<EntityDTO> populationInfoTable;

    @FXML
    TableColumn<EntityDTO, String> entityNameColumn;
    WorldDTO worldDTOBefore;
    WorldDTO worldDTOAfter;
    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML

    TableColumn<EntityDTO, Integer> populationBeforeColumn,populationAfterColumn;

    @FXML
    private Button reExecuteBTN;
    public void setAppMainController(MainAppController mainAppController)
    {

    }


    private void setWorldDTOsBeforeAndAfter()
    {
        // Clear the table before populating it
        try
        {
            this.worldDTOBefore=getWorldDtoBeforeFromServer().get();
            this.worldDTOAfter=getWorldDtoAfterFromServer().get();
        }
        catch (Exception e)
        {

        }
    }
    private CompletableFuture<WorldDTO> getWorldDtoAfterFromServer()
    {
        // Replace this URL with the actual URL of your server endpoint
        String serverUrl = "http://localhost:8080/get_dto_after?r_id="+requestId.toString()+"&e_id="+executionId.toString(); // Example URL
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
    private CompletableFuture<WorldDTO> getWorldDtoBeforeFromServer()
    {
        // Replace this URL with the actual URL of your server endpoint
        String serverUrl = "http://localhost:8080/get_before?r_id="+requestId.toString()+"&e_id="+executionId.toString(); // Example URL
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
    private CompletableFuture<String> getSimulationResultId()
    {
        String serverUrl = "http://localhost:8080/get_simulation_result_id?r_id="+requestId.toString()+"&e_id="+executionId.toString(); // Example URL
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
    @FXML
    private void showPopulationInfo()
    {

        try
        {

            populationInfoTable.getItems().clear();

            List<EntityDTO> entityDTOList = worldDTOBefore.getEntityDTOSet();

            ObservableList<EntityDTO> observableEntityDTOList = FXCollections.observableArrayList(entityDTOList);

            populationInfoTable.setItems(observableEntityDTOList);

            entityNameColumn.setCellFactory(param -> new TableCell<EntityDTO, String>()

            {

                @Override

                protected void updateItem(String item, boolean empty)

                {

                    super.updateItem(item, empty);


                    if (empty)

                    {

                        setText(null);

                    } else

                    {

                        EntityDTO entityDTO = getTableView().getItems().get(getIndex());

                        setText(entityDTO.getName());

                    }

                }

            });

            populationBeforeColumn .setCellFactory(param -> new TableCell<EntityDTO, Integer>()
            {

                @Override

                protected void updateItem(Integer item, boolean empty)

                {

                    super.updateItem(item, empty);


                    if (empty)

                    {

                        setText(null);

                    } else

                    {

                        EntityDTO entityDTO = getTableView().getItems().get(getIndex());

                        setText(String.valueOf(worldDTOBefore.getNumberOfEntityInstancesBefore(entityDTO) ));

                    }

                }

            });

            populationAfterColumn .setCellFactory(param -> new TableCell<EntityDTO, Integer>()

            {

                @Override

                protected void updateItem(Integer item, boolean empty)

                {

                    super.updateItem(item, empty);


                    if (empty)

                    {

                        setText(null);

                    } else

                    {

                        EntityDTO entityDTO = getTableView().getItems().get(getIndex());

                        setText(String.valueOf(worldDTOAfter.getNumberOfEntityInstancesAfter(entityDTO) ));

                    }

                }

            });

            populationInfoTable.setVisible(true);
        }
        catch (Exception e)
        {

        }

    }



    public void setSimulationResultsPane(UUID requestId,UUID executionId)
    {
        try
        {
            this.requestId=requestId;
            this.executionId = executionId;
            setWorldDTOsBeforeAndAfter();
            resultsAnchor.setDisable(false);
            populateLineChart();// graph init
            populationInfoTable.setVisible(false);

            simulationIdText.setText(getSimulationResultId().get()); /// http

            populationInfoRadioButton.setToggleGroup(toggleGroup);

            histogramRadioButton.setToggleGroup(toggleGroup);


            // Add a listener to handle radio button selection

            toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) ->

            {

                if (newValue == populationInfoRadioButton)

                {

                    hisoPopPane.getChildren().clear();

                    showPopulationInfo();

                    populationInfoTable.setVisible(true);

                    hisoPopPane.getChildren().add(populationInfoTable);

                } else if (newValue == histogramRadioButton)

                {


                    showHistogramContent();

                } else

                {

                    // Handle deselection or other actions

                    resultsAnchor.getChildren().clear();

                }

            });

        }

        catch (Exception e)
        {

        }


    }
    private void showHistogramContent()

    {

        try

        {

            populationInfoTable.setVisible(false);

            // Load the SimulationDetails.fxml

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/ExecutionsHistory/showSimulationResult/histogramUI.fxml"));

            AnchorPane histogram = loader.load();

            // Set the controller for the simulation details

            HistogramController histogramController = loader.getController();

            histogramController.initialize(worldDTOAfter); // Pass the simulation data to the controller
            hisoPopPane.getChildren().setAll(histogram);

        }


        catch (Exception e)

        {


        }


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
    private  CompletableFuture< Map<String, List<Integer>> >getEntityPopulationHistory()
    {

        // Replace this URL with the actual URL of your server endpoint
        String serverUrl = "http://localhost:8080/population_history?r_id="+requestId.toString()+"&e_id="+executionId.toString(); // Example URL
        Request request = new Request.Builder()
                .url(serverUrl)
                .build();

        Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
        CompletableFuture<Map<String, List<Integer>>> future = new CompletableFuture<>();
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
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    TypeToken<Map<String, List<Integer>>> typeToken = new TypeToken<Map<String, List<Integer>>>() {};
                    Map<String, List<Integer>> worldDTO = gson.fromJson(rawBody, typeToken.getType());
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

    private void populateLineChart()
    {

        try
        {
            lineChart.getData().clear();

            lineChart.setTitle("Entity Populations Over Time");

            xAxis.setLabel("Ticks");

            yAxis.setLabel("Population");


            // Retrieve entity population history from the simulation

            Map<String, List<Integer>> entityPopulationHistory = getEntityPopulationHistory().get();

            // Create a map to store the Y-values for each entity

            Map<String, List<Integer>> entityData = new HashMap<>();


            // Iterate over each entry in the entityPopulationHistory map

            for (Map.Entry<String, List<Integer>> entry : entityPopulationHistory.entrySet())

            {

                String entityName = entry.getKey(); // Get the entity name
                List<Integer> populationHistory = entry.getValue();
                // Store the Y-values (population history) for the entity
                entityData.put(entityName, populationHistory);
                // Create a data series for the entity
                XYChart.Series<Integer, Integer> entitySeries = new XYChart.Series<>();
                entitySeries.setName(entityName); // Set the entity name as the series name
                // Populate the data series with data points
                for (int ticksCounter = 0; ticksCounter < populationHistory.size(); ticksCounter++)
                {
                    entitySeries.getData().add(new XYChart.Data<>(ticksCounter*500, populationHistory.get(ticksCounter)));
                }
                // Add the entity data series to the LineChart
                lineChart.getData().add(entitySeries);
            }

            // Set Y-axis categories based on entity names
        }

        catch (Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }




    }
}
