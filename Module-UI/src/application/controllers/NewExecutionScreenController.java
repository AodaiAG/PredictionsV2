package application.controllers;

import application.manager.UserInterfaceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pDTOS.EntityDTO;
import pDTOS.EnvironmentDTO;
import pDTOS.PropertyDTO;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

public class NewExecutionScreenController
{

    public VBox environmentDetailsVBox;
    @FXML
    public ListView<String> entitesList;
    private UserInterfaceManager uiManager;
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
    private TextField dataTextField,populationText;

    private EnvironmentDTO selectedEnvironmentDTO;
    private EntityDTO SelectedentityDTO;



    public void initialize()
    {

        ObservableList<String> entitiesList = FXCollections.observableArrayList();
        List<EntityDTO> entities=uiManager.getEntityDto();

        for (EntityDTO entityDTO : entities)
        {
            String envName = entityDTO.getName();
            entitiesList.add(envName);
        }
//
        entitesList.setItems(entitiesList);

        // Handle selection change in the list view
        entitesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                {
                    if (newValue != null)
                    {
                         SelectedentityDTO=entities.get(entitesList.getSelectionModel().getSelectedIndex());
                    } else
                    {
                        // Disable the populationText field when no entity is selected
                    }
                });

        dataTextField=new TextField();
        List<EnvironmentDTO> enDTO = uiManager.getEnvironmentsDTO();
        ObservableList<String> detailsList = FXCollections.observableArrayList();

        for (EnvironmentDTO environmentDTO : enDTO)
        {
            String envName = environmentDTO.getEnProperty().getNameOfProperty();
            detailsList.add(envName);
        }
//

        // Set the detailsList as the items in the ListView
        environmentVariableListView.setItems(detailsList);


        // Listen for selection changes in the ListView
        environmentVariableListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue != null)
            {
                // Get the selected EnvironmentDTO
                EnvironmentDTO selectedEnvironment = enDTO.get(environmentVariableListView.getSelectionModel().getSelectedIndex());
                updateDetailsPane(selectedEnvironment);
            }
        });
    }
    @FXML
    private void handleAddButtonClick()
    {
        String popString=populationText.getText();
        try
        {
            int popn=Integer.parseInt(popString);
            uiManager.generatePopulation(SelectedentityDTO,popn);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Population added successfully!");
            alert.showAndWait();

        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Population should be numeric!");
            alert.showAndWait();
        }



    }
    public void updateDetailsPane(EnvironmentDTO selectedEnvironment)
    {
        // Clear any existing content in the detailsPane
        detailsPane.getChildren().clear();

        // Create and add UI elements to detailsPane based on selectedEnvironment
        Label nameLabel = new Label("Name: " + selectedEnvironment.getEnProperty().getNameOfProperty());
        Label type = new Label("Type: " + selectedEnvironment.getEnProperty().getNameOfDataType());
        Label range = new Label("Range: " + selectedEnvironment.getEnProperty().getFrom()+" - "+selectedEnvironment.getEnProperty().getTo());
        Label datavalue = new Label("Data value: " + selectedEnvironment.getEnProperty().getDataString());
        // Add other labels for range, data, etc.

        Button modifyButton = new Button("Modify data");
        modifyButton.setOnAction(event ->
        {
            // Handle the "Modify" button click here
            handleModifyButtonClick(selectedEnvironment);
        });
        // Define the layout of elements within detailsPane
        VBox vbox = new VBox();
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
        System.out.println("haha");
        try
        {
            String dataEnterd=dataTextField.getText();
            uiManager.setDataToEnvironmentVar(selectedEnvironment,dataEnterd);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Modification completed successfully!");
            alert.showAndWait();

        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("An error occurred reasons: "+e.getMessage());
            alert.showAndWait();
        }


    }


    // Modify the EnvironmentDTO's data


    public NewExecutionScreenController() {
        this.uiManager = UserInterfaceManager.INSTANCE;
    }

    public List<EnvironmentDTO> getEnvironmentDTOList() {
        return environmentDTOList;
    }




}
