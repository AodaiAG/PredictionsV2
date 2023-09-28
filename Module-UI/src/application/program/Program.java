package application.program;

import application.controllers.DetailsScreenController;
import application.controllers.NewExecutionScreenController;
import application.controllers.ResultsScreenController.ResultsScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import application.manager.UserInterfaceManager;

public class Program extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ResultsScreenController resultsScreenController = ResultsScreenController.getInstance();
        DetailsScreenController detailsScreenController=DetailsScreenController.getInstance();
        NewExecutionScreenController newExecutionScreenController=NewExecutionScreenController.getInstance();
        UserInterfaceManager uiManager = UserInterfaceManager.INSTANCE;
        uiManager.setResultsController(resultsScreenController);
        uiManager.setDetailsScreenController(detailsScreenController);
        uiManager.setNewExecutionController(newExecutionScreenController);
        uiManager.setStage(primaryStage);
        uiManager.initApplication();
    }
}