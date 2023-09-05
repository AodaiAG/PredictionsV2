package application.controllers;

import application.manager.UserInterfaceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pDTOS.EnvironmentDTO;
import pDTOS.PropertyDTO;

import java.io.IOException;
import java.util.List;

public class NewExecutionScreenController {

    public VBox environmentDetailsVBox;
    private UserInterfaceManager uiManager;

    @FXML
    private ListView<String> environmentVariableListView;
    private List<EnvironmentDTO> environmentDTOList;

    public NewExecutionScreenController() {
        this.uiManager = UserInterfaceManager.INSTANCE;
    }

    public List<EnvironmentDTO> getEnvironmentDTOList() {
        return environmentDTOList;
    }

    public void setEnvironmentDTOList(List<EnvironmentDTO> environmentDTOList) {
        this.environmentDTOList = environmentDTOList;
    }

  /*  public void initialize() {
        List<EnvironmentDTO> enDTO = uiManager.getEnvironmentsDTO();
        for (EnvironmentDTO environmentDTO : enDTO) {
            Button button = new Button(environmentDTO.getEnProperty().getNameOfProperty());

            // Create a local variable that is effectively final
            EnvironmentDTO currentEnvironmentDTO = environmentDTO;
            button.setOnAction(event -> {
                // Clear previous details
                environmentDetailsVBox.getChildren().clear();

                // Populate environment details using the currentEnvironmentDTO
                PropertyDTO currentEnvProperty = environmentDTO.getEnProperty();
                String dataType = "Data Type: " + currentEnvProperty.getNameOfDataType(); // Replace with your logic for the first string
                String range = "Range:  from= " + currentEnvProperty.getFrom() + " to= " + currentEnvProperty.getTo(); // Replace with your logic for the second string

                Text detailsText1 = new Text(dataType);
                Text detailsText2 = new Text(range);

                // Add the Text nodes to the environmentDetailsVBox
                environmentDetailsVBox.getChildren().addAll(detailsText1, detailsText2);
            });
            environmentVariableVBox.getChildren().add(button);
        }*/
    //}

    public void initialize() {
        List<EnvironmentDTO> enDTO = uiManager.getEnvironmentsDTO();

        // Create an ObservableList to hold environment details for the ListView
        ObservableList<String> detailsList = FXCollections.observableArrayList();

        for (EnvironmentDTO environmentDTO : enDTO) {
            String envName = environmentDTO.getEnProperty().getNameOfProperty();
            detailsList.add(envName);
        }

        // Set the detailsList as the items in the ListView
        environmentVariableListView.setItems(detailsList);

        // Add a selection listener to the ListView
        environmentVariableListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Get the selected EnvironmentDTO
                EnvironmentDTO selectedEnvironment = enDTO.get(environmentVariableListView.getSelectionModel().getSelectedIndex());

                // Create a new Stage for the popup window
                Stage popupStage = new Stage();
                popupStage.setTitle("Environment Details");

                // Load the FXML for the popup window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/EnvironmentDetailsPopup.fxml"));
                Parent root;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                // Access the elements in the popup window
                Label environmentNameLabel = (Label) loader.getNamespace().get("environmentNameLabel");
                Label rangeLabel = (Label) loader.getNamespace().get("rangeLabel");
                Label dataTypeLabel = (Label) loader.getNamespace().get("dataTypeLabel");
                TextField dataTextField = (TextField) loader.getNamespace().get("dataTextField");

                // Set the environment details in the popup window
                PropertyDTO currentEnvProperty = selectedEnvironment.getEnProperty();
                String name = currentEnvProperty.getNameOfProperty();
                String range = "from= " + currentEnvProperty.getFrom() + " to= " + currentEnvProperty.getTo();
                String dataType = currentEnvProperty.getNameOfDataType();
                environmentNameLabel.setText("Name: " + name);
                rangeLabel.setText("Range: " + range);
                dataTypeLabel.setText("Data Type: " + dataType);

                // Add an event handler to handle data input
                popupStage.setOnCloseRequest(event -> {
                    String enteredData = dataTextField.getText();
                    // Do something with the entered data (store it in enteredData variable)
                    System.out.println("Entered Data: " + enteredData);
                });

                // Create a Scene for the popup window
                Scene scene = new Scene(root);

                // Set the scene and show the popup window
                popupStage.setScene(scene);
                popupStage.show();
            }
        });
    }
}