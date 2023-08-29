package application.newExecution;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.IOException;

public class NewExecutionScreenController
{

    @FXML
    private Button startSimulationButton;

    // ...

    @FXML
    private void initialize()
    {
        // Set up event handler for the Start Simulation button
        startSimulationButton.setOnAction(event -> handleStartSimulation());
    }
     @FXML
    private void handleStartSimulation()
{
        // Load the Results screen FXML
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("C:\\Users\\udaeig\\IdeaProjects\\PredictionsV1\\Module-UI\\src\\application\\results\\ResultsScreen.fxml"));
            Parent resultsScreenRoot = loader.load();

            // Create a new stage for the Results screen
            Stage resultsStage = new Stage();
            resultsStage.setTitle("Results Screen");
            resultsStage.setScene(new Scene(resultsScreenRoot));

            // Show the Results screen
            resultsStage.show();

            // Close the current New Execution screen
            startSimulationButton.getScene().getWindow().hide();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
