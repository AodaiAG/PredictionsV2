package application.controllers.ResultsScreenController;

import application.controllers.EntityWrapper;
import application.controllers.ObservableEntity;
import application.controllers.SimulationTask;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import pDTOS.EntityDTO;
import pSystem.engine.SimulationResult;


import java.util.*;


public class SimulationDetailsTabController
{
    SimulationTask simulationTask;
    private SimulationResult simulationResult;
    @FXML

    private AnchorPane resultsAnchor,detailsMainAnchor,progressPane;
    @FXML

    TextField simulationIdText;

    @FXML

    AnchorPane hisoPopPane;

    @FXML

    private RadioButton populationInfoRadioButton,histogramRadioButton;

    @FXML

    TableColumn<EntityDTO, String> entityNameColumn;

    @FXML

    TableColumn<EntityDTO, Integer> populationBeforeColumn,populationAfterColumn;

    @FXML

    TableView<EntityDTO> populationInfoTable;

    @FXML

    private LineChart<Integer, Integer> lineChart;

    @FXML

    private NumberAxis xAxis;

    @FXML

    private NumberAxis yAxis;

    @FXML
    TextField countetext;
    @FXML
    private Button stopBtn,pauseBtn,resumeBtn,reExecuteBTN;
    @FXML
    private Label updateLabel,statusLabel;
    @FXML
    private AnchorPane progressAnchor;
    private NumberAxis customYAxis = new NumberAxis(); // Create a custom NumberAxis
    private ToggleGroup toggleGroup = new ToggleGroup();
    public StringProperty statusPropertyLabel = new SimpleStringProperty("Running");

    @FXML
    TableView<ObservableEntity> populationProgress;
    @FXML
    private TableColumn<ObservableEntity, String> entityNameColumnProgress;
    @FXML
    private TableColumn<ObservableEntity, String> populationCountColumnProgress;
    //entityNameColumnProgress
    EntityWrapper entityWrapper=new EntityWrapper();


    public void initialize()

    {
        entityNameColumnProgress.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        populationCountColumnProgress.setCellValueFactory(cellData -> cellData.getValue().populationProperty());
        populationProgress.setItems(entityWrapper.getEntityList());
        statusLabel.textProperty().bind(statusPropertyLabel);


    }

    public void setTEntityWrapper()
    {
        simulationTask.setEntityWrapper(entityWrapper);

    }


    public SimulationTask getSimulationTask()

    {

        return simulationTask;
    }

    public synchronized void setStatusLabel(String s)
    {
       this.statusPropertyLabel.set(s);
    }


    public void setSimulationTask(SimulationTask simulationTask)

    {

        this.simulationTask = simulationTask;

    }


    public void setSimulationResultsPane(SimulationResult SimulationResult)

    {

        resultsAnchor.setDisable(false);

        this.simulationResult = SimulationResult;

        populateLineChart();// graph init

        populationInfoTable.setVisible(false);

        simulationIdText.setText(simulationResult.getSimulationId());

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




    private void populateLineChart()

    {

        // Clear existing data from the chart

        lineChart.getData().clear();

        lineChart.setTitle("Entity Populations Over Time");

        xAxis.setLabel("Ticks");

        yAxis.setLabel("Population");


        // Retrieve entity population history from the simulation

        Map<String, List<Integer>> entityPopulationHistory = simulationResult.getEntityPopulationHistory();


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
                entitySeries.getData().add(new XYChart.Data<>(ticksCounter*100, populationHistory.get(ticksCounter)));
            }
            // Add the entity data series to the LineChart
            lineChart.getData().add(entitySeries);
        }


        // Set Y-axis categories based on entity names


    }






    @FXML

    private void showPopulationInfo()

    {

        // Clear the table before populating it

        populationInfoTable.getItems().clear();

        List<EntityDTO> entityDTOList = simulationResult.getWordBeforeSimulation().getEntityDTOSet();

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

                    setText(String.valueOf(simulationResult.getNumberOfEntityInstancesBefore(entityDTO) ));

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

                    setText(String.valueOf(simulationResult.getNumberOfEntityInstancesAfter(entityDTO) ));

                }

            }

        });


        populationInfoTable.setVisible(true);

    }



    private void showHistogramContent()

    {

        try

        {

            populationInfoTable.setVisible(false);

            // Load the SimulationDetails.fxml

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/histogramUI.fxml"));

            AnchorPane histogram = loader.load();

            // Set the controller for the simulation details

            HistogramController histogramController = loader.getController();

            histogramController.initialize(simulationResult); // Pass the simulation data to the controller

            hisoPopPane.getChildren().setAll(histogram);

        }


        catch (Exception e)

        {


        }


    }




    public void disableProgressNode()

    {

        progressAnchor.setDisable(true);

    }

    public void enableProgressNode()
    {

        resumeBtn.setDisable(true);

        progressAnchor.setDisable(false);

    }



    public void updateCounter(int counter)

    {

        countetext.setText(Integer.toString(counter));


    }


    public void pauseSimulation(javafx.event.ActionEvent actionEvent)

    {

        resumeBtn.setDisable(false);

        stopBtn.setDisable(false);

        pauseBtn.setDisable(true);

        simulationTask.pauseSimulation();

    }


    public void resumeSimulation(javafx.event.ActionEvent actionEvent)

    {

        simulationTask.resumeSimulation();

        resumeBtn.setDisable(true);

        stopBtn.setDisable(false);

        pauseBtn.setDisable(false);



    }


    public void stopSimulation(javafx.event.ActionEvent actionEvent)

    {

        resumeBtn.setDisable(true);

        stopBtn.setDisable(true);

        pauseBtn.setDisable(true);

        simulationTask.stopSimulation();

    }


    public void bindComponentsToTask()

    {

        updateLabel.textProperty().bind(simulationTask.messageProperty());

    }

    public void reExecuteOnAction(javafx.event.ActionEvent event)
    {
        this.simulationTask.returnButtonPressed();

    }
}