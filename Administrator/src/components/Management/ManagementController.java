package components.Management;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import components.Configuration.Configuration;
import components.mainApp.MainAppController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import pDTOS.SimulationDTO;
import util.http.HttpClientUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static util.Constants.*;

public class ManagementController
{
MainAppController mainAppController;
    @FXML
    TreeView treeView;


//    public void initApplication()
//    {
//        startSimulationDetailsRefresher();
//    }

    public void initRef()
    {
        startSimulationDetailsRefresher();
    }


    void startSimulationDetailsRefresher()
    {
        Timer timer = new Timer();
        TimerTask task = new SimulationTreeViewRefresher(treeView);
        long delay = 2000; // Initial delay (0 milliseconds)
        long period = 2000; // Repeat every 2 seconds (2000 milliseconds)
        timer.scheduleAtFixedRate(task, delay, period);

    }

    public void setChatAppMainController(MainAppController mainAppController)
    {
        this.mainAppController=mainAppController;

    }
}




