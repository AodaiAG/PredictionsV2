package application.controllers;

import application.manager.UserInterfaceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pDTOS.EntityDTO;
import pSystem.Simulation;

public class ResultsScreenController {

    @FXML
    private ListView<UUID> simulationListView;

    @FXML
    private AnchorPane changesAP;

    @FXML
    private AnchorPane radioButtonsShowResultsAP;

    @FXML
    private RadioButton progressDetailsRB;

    @FXML
    private RadioButton entInfoRB;

    @FXML
    private RadioButton analysisRB;

    @FXML
    private TextArea progressDetsilsText;

    @FXML
    private Text initText;

    @FXML
    private TableView<EntityDTO> entitiesTB;

    @FXML
    private TableColumn<EntityDTO, String> entityNameCol;

    @FXML
    private TableColumn<EntityDTO, Integer> quantityCol;

    @FXML
    private Label numOfEntitiesLabel;

    private Simulation selectedSimulation;

    private UserInterfaceManager uiManager;

    private ToggleGroup toggleGroup; // Add this field

    public ResultsScreenController() {
        uiManager = UserInterfaceManager.INSTANCE;
    }

    public void initialize() {
        initText.setVisible(true);
        List<UUID> uuidList = new ArrayList<>(uiManager.getSimulations().keySet());
        simulationListView.getItems().addAll(uuidList);

        simulationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // newValue contains the selected UUID
            if (newValue != null) {
                System.out.println("Selected UUID: " + newValue);
                selectedSimulation = uiManager.getSimulations().get(newValue);
                initText.setVisible(false);
                radioButtonsShowResultsAP.setVisible(true);
                // Perform actions or update your UI based on the selected simulation
                // ...
            }
        });
    }

    @FXML
    void showEntitiesInfo(ActionEvent event) {
        List<EntityDTO> entityDTOList = selectedSimulation.getWordBeforeSimulation().getEntityDTOSet();

        ObservableList<EntityDTO> observableEntityDTOList = FXCollections.observableArrayList(entityDTOList);

        // Set the ObservableList as the data source for the TableView
        entitiesTB.setItems(observableEntityDTOList);

        // Create a custom cell factory for the entityNameCol column
        entityNameCol.setCellFactory(param -> new TableCell<EntityDTO, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    EntityDTO entityDTO = getTableView().getItems().get(getIndex());
                    setText(entityDTO.getName());
                }
            }
        });

        // Create a custom cell factory for the quantityCol column
        quantityCol.setCellFactory(param -> new TableCell<EntityDTO, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    EntityDTO entityDTO = getTableView().getItems().get(getIndex());
                    setText(String.valueOf(entityDTO.getNumberOfInstances()));
                }
            }
        });

        entitiesTB.setVisible(true);

        // Assuming you have a method to get the number of entities in your selectedSimulation
        int numberOfEntities = selectedSimulation.getWordBeforeSimulation().getEntityDTOSet().size(); // Replace with your actual method

        // Set the text of the numOfEntitiesLabel
        numOfEntitiesLabel.setText("Number Of Entities: " + numberOfEntities);
        numOfEntitiesLabel.setVisible(true);
    }

}
