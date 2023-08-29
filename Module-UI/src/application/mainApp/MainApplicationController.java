package application.mainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class MainApplicationController
{


    @FXML
    private Pane mainContent;


    private void loadDetailsScreen()
    {
        loadScreen("../details/DetailsScreen.fxml");
    }

    private void loadNewExecutionScreen()
    {
        loadScreen("../newExecution/NewExecutionScreen.fxml");
    }

    private void loadResultsScreen()
    {
        loadScreen("../results/ResultsScreen.fxml");
    }

    private void loadScreen(String fxmlFileName)
    {
        try
        {
           URL resource= getClass().getResource(fxmlFileName);
            FXMLLoader loader = new FXMLLoader(resource);
            Pane screen = loader.load();
            mainContent.getChildren().clear();
            mainContent.getChildren().add(screen);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleDetailsButton(ActionEvent event)
    {
        loadDetailsScreen();
    }

    @FXML
    private void handleNewExecutionButton(ActionEvent event)
    {
        loadNewExecutionScreen();
    }


}
