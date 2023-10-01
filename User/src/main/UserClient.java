//package main;
//
//import components.mainApp.MainAppController;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.net.URL;
//
//import static util.Constants.MAIN_PAGE_FXML_RESOURCE_LOCATION;
//
//public class UserClient extends Application
//{
//    private MainAppController mainAppController;
//    @Override
//    public void start(Stage primaryStage)
//    {
//
//        primaryStage.setTitle("User");
//        URL loginPage = getClass().getResource(MAIN_PAGE_FXML_RESOURCE_LOCATION);
//        try
//        {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(loginPage);
//            Parent root = fxmlLoader.load();
//            mainAppController = fxmlLoader.getController();
//            mainAppController.initApplication();
//            Scene scene = new Scene(root);
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//
//}
