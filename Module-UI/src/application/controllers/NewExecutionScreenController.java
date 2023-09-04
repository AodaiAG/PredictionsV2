package application.controllers;
import application.manager.UserInterfaceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.awt.*;

public class NewExecutionScreenController
{

    private UserInterfaceManager uiManager;




    public NewExecutionScreenController() {
        uiManager = UserInterfaceManager.INSTANCE;
    }


    @FXML
    void switchToThirdScene(ActionEvent event) {
        uiManager.switchToResultsScene();
    }

    @FXML
    public void switchToDetails(ActionEvent event)
    {
        uiManager.switchToDetailsScene();
    }





}
