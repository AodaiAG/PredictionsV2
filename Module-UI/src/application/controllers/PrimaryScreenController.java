package application.controllers;
import application.manager.UserInterfaceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrimaryScreenController
{
    private UserInterfaceManager uiManager;

    public PrimaryScreenController() {
        uiManager = UserInterfaceManager.INSTANCE;
    }

    @FXML
    public void switchToDetailsScene(ActionEvent event) {
        uiManager.switchToDetailsScene();
    }

    @FXML
    public void switchToResultsScene(ActionEvent event) {
        uiManager.switchToResultsScene();
    }
    @FXML
    public void switchToNewExecutionScene(ActionEvent event) {
        uiManager.switchToNewExecutionScene();
    }

}
