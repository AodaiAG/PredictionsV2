package application.controllers;
import application.manager.UserInterfaceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrimaryScreenController {
    private UserInterfaceManager uiManager;

    public PrimaryScreenController() {
        uiManager = UserInterfaceManager.INSTANCE;
    }

    @FXML
    public void switchToFirstScene(ActionEvent event) {
        uiManager.switchToDetailsScene();
    }

    @FXML
    public void switchToThirdScene(ActionEvent event) {
        uiManager.switchToResultsScene();
    }
}
