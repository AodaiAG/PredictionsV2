package components.Management;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import components.Management.simulationDetails.SimulationDetailsController;
import components.Management.threadPoolManager.ThreadPoolManagerController;
import components.mainApp.MainAppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import pDTOS.SimulationDTO;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static util.Constants.*;
import static util.Constants.SIMULATION_DETAILS_FXML_RESOURCE_LOCATION;

public class ManagementController
{
    MainAppController mainAppController;
    @FXML
    AnchorPane simulationDetailsAnchor;
    private SimulationDetailsController simulationDetailsController;
    @FXML
    AnchorPane threadPoolAnchor;
    private ThreadPoolManagerController threadPoolController;


    public void initialize()
    {
        loadSimulationDetailsPage();
        loadThreadPoolPage();
    }

    private void loadSimulationDetailsPage()
    {

        URL loginPageUrl = getClass().getResource(SIMULATION_DETAILS_FXML_RESOURCE_LOCATION);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            AnchorPane toBeSubmitted = fxmlLoader.load();
            simulationDetailsController = fxmlLoader.getController();
            simulationDetailsController.setAppMainController(this.mainAppController);
            //submitNewRequestController.initApplication();
            simulationDetailsAnchor.getChildren().clear();
            simulationDetailsAnchor.getChildren().add(toBeSubmitted);
            AnchorPane.setBottomAnchor(toBeSubmitted, 1.0);
            AnchorPane.setTopAnchor(toBeSubmitted, 1.0);
            AnchorPane.setLeftAnchor(toBeSubmitted, 1.0);
            AnchorPane.setRightAnchor(toBeSubmitted, 1.0);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadThreadPoolPage()
    {

        URL loginPageUrl = getClass().getResource(THREAD_POOL_LOCATION);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            AnchorPane toBeSubmitted = fxmlLoader.load();
            threadPoolController = fxmlLoader.getController();
            threadPoolController.setAppMainController(this.mainAppController);
            //submitNewRequestController.initApplication();
            threadPoolAnchor.getChildren().clear();
            threadPoolAnchor.getChildren().add(toBeSubmitted);
            AnchorPane.setBottomAnchor(toBeSubmitted, 1.0);
            AnchorPane.setTopAnchor(toBeSubmitted, 1.0);
            AnchorPane.setLeftAnchor(toBeSubmitted, 1.0);
            AnchorPane.setRightAnchor(toBeSubmitted, 1.0);


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setChatAppMainController(MainAppController mainAppController)
    {
        this.mainAppController=mainAppController;

    }
}




