package application.program;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import application.manager.UserInterfaceManager;

public class Program extends Application {
    public static void main(String[] args) {
        launch(args);
        System.out.println("hello world");
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        UserInterfaceManager uiManager = UserInterfaceManager.INSTANCE;
        uiManager.setStage(primaryStage);
        uiManager.initApplication();
    }
}