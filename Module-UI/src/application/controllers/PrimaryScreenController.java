package application.controllers;
import application.manager.UserInterfaceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    public PrimaryScreenController()
    {
        uiManager = UserInterfaceManager.INSTANCE;
    }

    @FXML
    public void buttonSwitchToDetailsScene(ActionEvent event)
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

    @FXML
    void buttonSwitchToNewExecutionScene(ActionEvent event)
    {
        try
        {
            Node newEx = FXMLLoader.load(getClass().getResource("/application/resources/newExecutionScene.fxml"));
            mainBoard.setCenter(newEx);
        } catch (IOException e)
        {
            System.out.println("failed to load newExecutionScene here.fxml");
        }
    }

    @FXML
    public void buttonSwitchToResultsScene(ActionEvent event) {
        try
        {
            Node newEx = FXMLLoader.load(getClass().getResource("/application/resources/resultsScene.fxml"));
            mainBoard.setCenter(newEx);
        } catch (IOException e)
        {
            System.out.println("failed to load resultsScene here.fxml");
        }
    }
}
