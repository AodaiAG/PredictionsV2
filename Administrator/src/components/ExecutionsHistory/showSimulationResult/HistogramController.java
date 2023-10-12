package components.ExecutionsHistory.showSimulationResult;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import pDTOS.EntityDTO;
import pDTOS.EntityInstancesDTO;
import pDTOS.PropertyDTO;
import pDTOS.WorldDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistogramController
{
    @FXML
    TableView histogramTableView;
    @FXML
    TableColumn<Map.Entry<String, Integer>, String> valueColumn;
    @FXML
    TableColumn<Map.Entry<String, Integer>, String> countColumn;
    @FXML
    private ComboBox<String> entityComboBox;
    @FXML
    private ComboBox<String> propertyComboBox;
    private VBox histogramContent;

    @FXML
    private Label averageLabel;
    @FXML
    private TextField averageText, consText1;
    private WorldDTO worldDTOAfterSimulation;

    // Initialize the ComboBoxes with entity and property data
    public void initialize(WorldDTO worldDTOAfter)
    {
         worldDTOAfterSimulation=worldDTOAfter;
        // Populate entityComboBox with entity names
        // You can retrieve this data from your simulation or wherever it's stored
        for (EntityDTO entityDTO : worldDTOAfterSimulation.getEntityDTOSet())
        {
           entityComboBox.getItems().add(entityDTO.getName());
        }
        // Populate propertyComboBox with property names based on the selected entity
        entityComboBox.setOnAction(event ->
        {
            String selectedEntity = entityComboBox.getValue();
            EntityDTO selectedEntityDTO = null;
            for (EntityDTO entityDTO : worldDTOAfterSimulation.getEntityDTOSet()) {
                if (entityDTO.getName().equals(selectedEntity)) {
                    selectedEntityDTO = entityDTO;
                    break;
                }
            }
            propertyComboBox.getItems().clear(); // Clear previous items

            for (PropertyDTO propertyDTO : selectedEntityDTO.getProperties()) {
                propertyComboBox.getItems().add(propertyDTO.getNameOfProperty());
            }

        });

        propertyComboBox.setOnAction(event ->
        {
            showHistogramContent(event);
        });
    }

    @FXML
    private void showHistogramContent(ActionEvent event)
    {
        String selectedEntity = entityComboBox.getValue();
        String selectedProperty = propertyComboBox.getValue();
        histogramTableView.getItems().clear();
        EntityDTO selectedEntityDTO = null;
        for (EntityDTO entityDTO : worldDTOAfterSimulation.getEntityDTOSet()) {
            if (entityDTO.getName().equals(selectedEntity)) {
                selectedEntityDTO = entityDTO;
                break;
            }
        }

        if (selectedEntityDTO != null)
        {
            PropertyDTO selectedPropertyDTO = null;
            for (PropertyDTO propertyDTO : selectedEntityDTO.getProperties()) {
                if (propertyDTO.getNameOfProperty().equals(selectedProperty)) {
                    selectedPropertyDTO = propertyDTO;
                    break;
                }
            }

            if (selectedPropertyDTO != null) {
                Map<String, Integer> value2count = setHistogramToOneProperty(selectedPropertyDTO, selectedEntityDTO.getInstancesDTOS());
                int consis = setConsistencyToOneProperty(selectedPropertyDTO, selectedEntityDTO.getInstancesDTOS(), worldDTOAfterSimulation.tickCounter);
                ObservableList<Map.Entry<String, Integer>> observableEntityDTOList = FXCollections.observableArrayList(value2count.entrySet());
                this.histogramTableView.setItems(observableEntityDTOList);
                valueColumn.setCellFactory(param -> new TableCell<Map.Entry<String, Integer>, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                        } else {
                            Map.Entry<String, Integer> entry = getTableView().getItems().get(getIndex());
                            setText(entry.getKey().toString());
                        }
                    }
                });
                countColumn.setCellFactory(param -> new TableCell<Map.Entry<String, Integer>, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            Map.Entry<String, Integer> entry = getTableView().getItems().get(getIndex());
                            setText(entry.getValue().toString());
                        }
                    }
                });


                if (selectedPropertyDTO.getNameOfDataType().equals("Float") || selectedPropertyDTO.getNameOfDataType().equals("Decimal")) {
                    float sumData = 0;
                    int sumCount = 0;
                    for (Map.Entry<String, Integer> entry : value2count.entrySet()) {
                        String dataString = entry.getKey();
                        int count = entry.getValue();
                        float data = Float.parseFloat(dataString);
                        sumData = (count * data) + sumData;
                        sumCount += count;
                    }

                    float average = 0;
                    // average
                    if (sumCount != 0) {
                        average = sumData / sumCount;
                    }

                    averageLabel.setDisable(false);
                    averageText.setDisable(false);
                    averageText.setText(Float.toString(average));

                } else {
                    averageLabel.setDisable(true);
                    averageText.clear();
                    averageText.setDisable(true);
                }

                consText1.clear();
                consText1.setDisable(false);
                this.consText1.setText(Integer.toString(consis));

            } else {
                consText1.clear();
                consText1.setDisable(true);
                averageText.clear();
                averageText.setDisable(true);

            }
        } else {
            // Handle the case where selectedEntityDTO is null
        }
    }


    int setConsistencyToOneProperty(PropertyDTO property, List<EntityInstancesDTO> instancesDTOS, int lastTick) {
        // value // count

        String propertyName = property.getNameOfProperty();
        int consistency = 0;
        int instancesAverageSum = 0;
        int populationCounter = 0;

        for (EntityInstancesDTO entityInstancesDTO : instancesDTOS) {
            for (PropertyDTO propertyDTO : entityInstancesDTO.getProperties()) {
                if (propertyName.equals(propertyDTO.getNameOfProperty())) {
                    //last consistency time for property
                    int lastConsistTime = lastTick - propertyDTO.getLastUnchangedTicks();
                    int totalSumForSingleInstance = propertyDTO.getSumTicksNoChange() + lastConsistTime;
                    if(propertyDTO.getNumOfTimesHasChanged() != 0)
                    {
                    //calculate average for one instance and add it to the total sum
                        instancesAverageSum += totalSumForSingleInstance / propertyDTO.getNumOfTimesHasChanged();
                    }
                    else {
                        instancesAverageSum += totalSumForSingleInstance;
                    }
                    populationCounter++;
                    break;
                }
            }
        }

        //calculate the average
        if (populationCounter != 0) {
            consistency = instancesAverageSum / populationCounter;

        }
        return consistency;

    }

    Map<String, Integer> setHistogramToOneProperty(PropertyDTO property, List<EntityInstancesDTO> instancesDTOS) {
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