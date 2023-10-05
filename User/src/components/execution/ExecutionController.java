//package components.execution;
//
//import Requests.SimulationRequest;
//import Requests.SimulationRequestDetails;
//import application.manager.UserInterfaceManager;
//import components.mainApp.UserMainAppController;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.VBox;
//import pDTOS.EntityDTO;
//import pDTOS.EnvironmentDTO;
//import pDTOS.WorldDTO;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//public class NewExecutionScreenController
//{
//    UserMainAppController mainAppController;
//    public VBox environmentDetailsVBox;
//    @FXML
//    public ListView<String> entitesList;
//    private UserInterfaceManager uiManager;
//    @FXML
//    private ListView<String> environmentVariableListView;
//    private List<EnvironmentDTO> environmentDTOList;
//    @FXML
//    private AnchorPane detailsPane;
//    @FXML
//    private AnchorPane listPane;
//    @FXML
//    private SplitPane splitPane;
//    @FXML
//    private VBox vbox;
//    @FXML
//    private TextField dataTextField,populationText;
//
//    private EnvironmentDTO selectedEnvironmentDTO;
//    private EntityDTO SelectedentityDTO;
//    @FXML
//    Button clearBtn;
//
//
//
//    public void initializeController(SimulationRequestDetails simulationRequest)
//    {
//            WorldDTO worldDTO=fetchWorldDTOFromServer(simulationRequest.getId());
//            populationText.setDisable(true);
//            List<EntityDTO> entities = worldDTO.getEntityDTOSet();
//            ObservableList<String> entitiesList = FXCollections.observableArrayList
//                (
//                        entities.stream()
//                        .map(entityDTO -> entityDTO.getName()) // Replace with the appropriate method to get the string representation of your EntityDTO
//                        .collect(Collectors.toList())
//                );
//
//            entitesList.setItems(entitiesList);
//            // Handle selection change in the list view
//            entitesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
//            {
//                if (newValue != null)
//                {
//                    populationText.setDisable(false);
//                    SelectedentityDTO = entities.get(entitesList.getSelectionModel().getSelectedIndex());
//                    populationText.clear();
//                }
//                else
//                {
//
//                }
//            });
//
//            dataTextField = new TextField();
//            List<EnvironmentDTO> enDTO = worldDTO.getEnvironmentDTOS();
//            ObservableList<String> detailsList = FXCollections.observableArrayList();
//
//            for (EnvironmentDTO environmentDTO : enDTO)
//            {
//                String envName = environmentDTO.getEnProperty().getNameOfProperty();
//                detailsList.add(envName);
//            }
//
//            // Set the detailsList as the items in the ListView
//            environmentVariableListView.setItems(detailsList);
//
//            // Listen for selection changes in the ListView
//            environmentVariableListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
//            {
//                if (newValue != null)
//                {
//                    // Get the selected EnvironmentDTO
//                    EnvironmentDTO selectedEnvironment = enDTO.get(environmentVariableListView.getSelectionModel().getSelectedIndex());
//                    selectedEnvironment = uiManager.updateEnvironment(selectedEnvironment);
//                    updateDetailsPane(selectedEnvironment);
//                    environmentVariableListView.refresh();
//                }
//            });
//
//        }
//
//    }
//    private WorldDTO fetchWorldDTOFromServer(UUID id)
//    {
//
//
//    }
//    public void setAppMainController(UserMainAppController mainAppController)
//    {
//        this.mainAppController = mainAppController;
//    }
//
//    public void refreshScreen()
//    {
//        this.initializeController();
//    }
//
//    @FXML
//    private void handleAddButtonClick()
//    {
//        String popString = populationText.getText();
//        try
//        {
//            int popn = Integer.parseInt(popString);
//            uiManager.generatePopulation(SelectedentityDTO, popn);
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setHeaderText("Population added successfully!");
//            alert.showAndWait();
//        }
//        catch (IllegalArgumentException e)
//        {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Population should be numeric!");
//            alert.showAndWait();
//        }
//        catch(Exception exception)
//        {
//            // Create and configure an alert
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("An error occurred");
//            alert.setContentText("Unable to generate population: " + exception.getMessage());
//            // Show the alert
//            alert.showAndWait();
//        }
//    }
//
//    public void updateDetailsPane(EnvironmentDTO selectedEnvironment)
//    {
//        // Clear any existing content in the detailsPane
//        detailsPane.getChildren().clear();
//        Button modifyButton = new Button("Modify data");
//        // Create and add UI elements to detailsPane based on selectedEnvironment
//        Label nameLabel = new Label("Name: " + selectedEnvironment.getEnProperty().getNameOfProperty());
//        Label type = new Label("Type: " + selectedEnvironment.getEnProperty().getNameOfDataType());
//        Label range = new Label("Range: " + selectedEnvironment.getEnProperty().getFrom()+" - "+selectedEnvironment.getEnProperty().getTo());
//        Label datavalue = new Label("Data value: " + selectedEnvironment.getEnProperty().getDataString());
//        // Add other labels for range, data, etc.
//        modifyButton.setOnAction(event ->
//        {
//            // Handle the "Modify" button click here
//            handleModifyButtonClick(selectedEnvironment);
//        });
//
//        // Define the layout of elements within detailsPane
//        vbox.getChildren().clear();
//        vbox.getChildren().addAll(nameLabel,type,range,datavalue, dataTextField, modifyButton);
//
//        // Add the layout to detailsPane
//        detailsPane.getChildren().add(vbox);
//
//
//        // Set the SplitPane divider position to control the width of list and details
//        SplitPane.setResizableWithParent(listPane, Boolean.FALSE);
//        SplitPane.setResizableWithParent(detailsPane, Boolean.FALSE);
//        splitPane.setDividerPositions(0.2); // Adjust this value as needed
//    }
//
//    private void handleModifyButtonClick(EnvironmentDTO selectedEnvironment)
//    {
//        try
//        {
//            String dataEnterd=dataTextField.getText();
//            uiManager.setDataToEnvironmentVar(selectedEnvironment,dataEnterd);
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setHeaderText("Modification completed successfully!");
//            selectedEnvironment=uiManager.updateEnvironment(selectedEnvironment);
//            alert.showAndWait();
//            updateDetailsPane(selectedEnvironment);
//        }
//
//        catch (Exception e)
//        {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error!");
//            alert.setHeaderText("An error occurred reasons: "+e.getMessage());
//            alert.showAndWait();
//        }
//    }
//
//
//    public List<EnvironmentDTO> getEnvironmentDTOList()
//    {
//        return environmentDTOList;
//    }
//
//    @FXML
//    void startSimulation(ActionEvent event)
//    {
//        uiManager.runSimulation();
//        uiManager.switchToResultsScreen(event);
//    }
//
//    public void clearOnAction(ActionEvent event)
//    {
//        uiManager.clearPressed();
//    }
//}