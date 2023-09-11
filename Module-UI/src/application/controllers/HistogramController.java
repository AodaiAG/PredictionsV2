package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pDTOS.EntityDTO;
import pDTOS.EntityInstancesDTO;
import pDTOS.PropertyDTO;
import pDTOS.WorldDTO;
import pSystem.Simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HistogramController
{
    @FXML
    private ComboBox<String> entityComboBox;

    @FXML
    private ComboBox<String> propertyComboBox;
    @FXML
    private VBox histogramContent;
    private Simulation simulation;

    // Initialize the ComboBoxes with entity and property data
    public void initialize(Simulation simulation)
    {
        this.simulation=simulation;
        // Populate entityComboBox with entity names
        // You can retrieve this data from your simulation or wherever it's stored
        entityComboBox.getItems().addAll("Entity1", "Entity2", "Entity3");

        // Populate propertyComboBox with property names based on the selected entity
        entityComboBox.setOnAction(event ->
        {
            String selectedEntity = entityComboBox.getValue();
            // Retrieve the properties for the selected entity
            // Update propertyComboBox with property names
            propertyComboBox.getItems().clear(); // Clear previous items
            propertyComboBox.getItems().addAll("Property1", "Property2", "Property3"); // Replace with actual properties
        });
    }

    @FXML
    private void showHistogramContent(ActionEvent event)
    {
        String selectedEntity = entityComboBox.getValue();
        String selectedProperty = propertyComboBox.getValue();

        // Perform your histogram analysis based on selectedEntity and selectedProperty
        // Update the histogramContent VBox with the results
        histogramContent.getChildren().clear(); // Clear previous content

        // Create and display histogram content based on analysis results
        Label resultLabel = new Label("Histogram for " + selectedEntity + " - " + selectedProperty);
        histogramContent.getChildren().add(resultLabel);
        // Add the histogram data or other analysis results as needed
    }

    private void showHistogramContent()
    {
        // System.out.println("Entities in the simulation:" + '\n');
        WorldDTO wordAfterSimulation = simulation.getWordAfterSimulation();
        List<EntityDTO> entities = wordAfterSimulation.getEntityDTOSet();
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < entities.size(); i++)
        {
            System.out.println((i + 1) + " - " + entities.get(i).getName());
        }
        System.out.println("Please, Choose an entity:");
        //int userChoice = getUserChoice(sc, 1, entities.size());
        int userChoice = 3;
        EntityDTO wantedEntity = entities.get(userChoice - 1);
        Map<String, Map<String, Integer>> map = setHistogramToAllPropertyInEntity(wantedEntity);

        System.out.println("Properties of Entity:" + wantedEntity.getName() + '\n');
        for (int i = 0; i < wantedEntity.getProperties().size(); i++)
        {
            System.out.println((i + 1) + "- " + wantedEntity.getProperties().get(i).getNameOfProperty() + '\n');
        }

        System.out.println("Please, choose a property of which you would like to get the Histogram");
        //userChoice = getUserChoice(sc, 1, wantedEntity.getProperties().size());
        PropertyDTO wantedProperty = wantedEntity.getProperties().get(userChoice - 1);

        Map<String, Integer> value2count = map.get(wantedProperty.getNameOfProperty());

        for (Map.Entry<String, Integer> entry : value2count.entrySet())
        {
            System.out.println("Value-Count: \"" + entry.getKey() + "\" --> " + entry.getValue());
        }
    }

    Map<String, Map<String, Integer>> setHistogramToAllPropertyInEntity(EntityDTO entity)
    {

        Map<String, Map<String, Integer>> pName2pCount = new HashMap<>();

        List<PropertyDTO> propertyDTOList = entity.getProperties();
        for (PropertyDTO propertyDTO : propertyDTOList) {
            pName2pCount.put(propertyDTO.getNameOfProperty(), setHistogramToOneProperty(propertyDTO, entity.getInstancesDTOS()));
        }
        return pName2pCount;
    }
    Map<String, Integer> setHistogramToOneProperty(PropertyDTO property, List<EntityInstancesDTO> instancesDTOS)
    {
        // value // count
        Map<String, Integer> value2count = new HashMap<>();
        String propertyName = property.getNameOfProperty();

        for (EntityInstancesDTO entityInstancesDTO : instancesDTOS) {
            for (PropertyDTO propertyDTO : entityInstancesDTO.getProperties()) {
                if (propertyName.equals(propertyDTO.getNameOfProperty())) {
                    Integer count = value2count.get(propertyDTO.getDataString());
                    if (count == null) {
                        value2count.put(propertyDTO.getDataString(), 1);
                    } else {
                        count++;
                        value2count.put(propertyDTO.getDataString(), count);
                    }
                }
            }
        }
        return value2count;
    }
}
