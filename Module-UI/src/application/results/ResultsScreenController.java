package application.results;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;

public class ResultsScreenController
{

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TableView<SimulationResult> resultsTableView;

    @FXML
    private Button analyzeButton;

    // You can add initialization or event handling methods here

    // Inner class for simulation results
    public static class SimulationResult {
        // Define properties for simulation results
    }
}
