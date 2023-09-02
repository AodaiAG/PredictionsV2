package application.controllers;
import application.manager.UserInterfaceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;

public class NewExecutionScreenController
{

    private UserInterfaceManager uiManager;



    public NewExecutionScreenController() {
        uiManager = UserInterfaceManager.INSTANCE;
    }
    @FXML
    public void loadXmlFile(ActionEvent event)
    {
        uiManager.loadXmlFile(event);
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
