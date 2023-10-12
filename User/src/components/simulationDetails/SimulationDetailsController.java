package components.simulationDetails;

import components.mainApp.UserMainAppController;
import javafx.fxml.FXML;
import javafx.scene.control.TreeView;

import java.util.Timer;
import java.util.TimerTask;

public class SimulationDetailsController
{
    UserMainAppController mainAppController;
    @FXML
    TreeView treeView;

    public void initialize()
    {
        startSimulationDetailsRefresher();
    }

    public void setAppMainController(UserMainAppController mainAppController)
    {
        this.mainAppController = mainAppController;
    }

    public void startSimulationDetailsRefresher()
    {
        Timer timer = new Timer();
        TimerTask task = new SimulationTreeViewRefresher(treeView);
        long delay = 0; // Initial delay (0 milliseconds)
        long period = 2000; // Repeat every 2 seconds (2000 milliseconds)
        timer.scheduleAtFixedRate(task, delay, period);

    }
}
