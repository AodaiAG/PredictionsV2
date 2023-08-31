package application.controllers;
import application.manager.UserInterfaceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class NewExecutionScreenController {

    private UserInterfaceManager uiManager;

    public NewExecutionScreenController() {
        uiManager = UserInterfaceManager.INSTANCE;
    }

    @FXML
    void switchToThirdScene(ActionEvent event) {
        uiManager.switchToResultsScene();
    }

}
