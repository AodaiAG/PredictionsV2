package main;

import components.login.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static util.Constants.LOGIN_PAGE_FXML_RESOURCE_LOCATION;

public class UserClient extends Application
{
    private LoginController loginController;
    private GridPane loginComponent;

    @Override
    public void start(Stage primaryStage)
    {

        primaryStage.setTitle("User");

        try
        {
            URL loginPageUrl = getClass().getResource(LOGIN_PAGE_FXML_RESOURCE_LOCATION);
            FXMLLoader fxmlLoader = new FXMLLoader(loginPageUrl);
            loginComponent = fxmlLoader.load();
            loginController = fxmlLoader.getController();
            Scene loginScene = new Scene(loginComponent);
            primaryStage.setScene(loginScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void stop() throws Exception
    {
        System.exit(5);
        //chatAppMainController.close();
    }
}
