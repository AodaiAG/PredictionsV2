package application.controllers;
import application.manager.UserInterfaceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class PrimaryScreenController
{

    private UserInterfaceManager uiManager;
    @FXML
    private TextField filePathLabel;
    @FXML
    BorderPane mainBoard;
    @FXML
    Button results,newExecution,details;

    @FXML
    public void loadXmlButton(ActionEvent event)
    {
        uiManager.loadXmlFile(event,filePathLabel);

    }
    @FXML
    public void buttonswitchToDetailsScene()
    {
            try
            {
                Node details = FXMLLoader.load(getClass().getResource("/application/resources/detailsScene.fxml"));
                 mainBoard.setCenter(details);

            } catch (IOException e)
            {
                System.out.println("failed to load detailsScene.fxml");
            }

    }

    public PrimaryScreenController()
    {
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
