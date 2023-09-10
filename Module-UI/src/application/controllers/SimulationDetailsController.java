package application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import pDTOS.EntityDTO;
import pEntity.Entity;
import pSystem.Simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SimulationDetailsController
{
    @FXML
    private AnchorPane rightAnchorPane;
    private Simulation simulation;
    @FXML
    TextField simulationIdText;

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
    private NumberAxis customYAxis = new NumberAxis(); // Create a custom NumberAxis





    private ToggleGroup toggleGroup = new ToggleGroup();
    public void initialize(Simulation Simulation)
    {
        this.simulation=Simulation;

        populateLineChart();// graph init

        simulationIdText.setText(simulation.getSimulationId());
        populationInfoRadioButton.setToggleGroup(toggleGroup);
        histogramRadioButton.setToggleGroup(toggleGroup);

        // Add a listener to handle radio button selection
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == populationInfoRadioButton)
            {
                showPopulationInfo();
            } else if (newValue == histogramRadioButton)
            {
                showHistogramContent();
            } else
            {
                // Handle deselection or other actions
                rightAnchorPane.getChildren().clear();
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
        Map<String, List<Integer>> entityPopulationHistory = simulation.getEntityPopulationHistory();

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
                entitySeries.getData().add(new XYChart.Data<>(ticksCounter, populationHistory.get(ticksCounter)));
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
        List<EntityDTO> entityDTOList = simulation.getWordBeforeSimulation().getEntityDTOSet();
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
                    setText(String.valueOf(simulation.getNumberOfEntityInstancesBefore(entityDTO) ));
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
                    setText(String.valueOf(simulation.getNumberOfEntityInstancesAfter(entityDTO) ));
                }
            }
        });


    }


    private void showHistogramContent()
    {
        // Set the histogram content as the content of the rightAnchorPane
        rightAnchorPane.getChildren().clear();
        //rightAnchorPane.getChildren().add(histogramContent);
    }

}
