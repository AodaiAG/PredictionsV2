package application.controllers;

import application.manager.UserInterfaceManager;
import javafx.fxml.FXML;
import javafx.scene.control.TreeView;

public class DetailsScreenController
{

    private UserInterfaceManager uiManager;

    public DetailsScreenController() {
        uiManager = UserInterfaceManager.INSTANCE;
    }
    @FXML
    private TreeView<String> worldTreeView; // Matches the fx:id in your FXML

    public void initialize()
    {
        // Load the WorldDTO here (replace with your code)


        // Generate the TreeView
        TreeView<String> generatedTreeView = uiManager.generateWorldDetails();

        // Set the generated TreeView to the one defined in your FXML
        worldTreeView.setRoot(generatedTreeView.getRoot());
    }

}
