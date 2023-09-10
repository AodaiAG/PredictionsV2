package application.controllers;

import application.manager.UserInterfaceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeView;

public class DetailsScreenController
{
    private UserInterfaceManager uiManager;

    public DetailsScreenController()
    {
        uiManager = UserInterfaceManager.INSTANCE;
    }

    @FXML
    private TreeView<String> worldTreeView; // Matches the fx:id in your FXML

    public void initialize()
    {
       if(uiManager.isThereASimulation())
       {
           // Load the WorldDTO here (replace with your code)
           // Generate the TreeView
           TreeView<String> generatedTreeView = uiManager.generateWorldDetails();
           generatedTreeView.getRoot().setValue(uiManager.getSimulationName());
           worldTreeView.setRoot(generatedTreeView.getRoot());
       }
       else
       {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setHeaderText("Please,load a file first!");
           alert.showAndWait();
       }
    }
}