package components.ExecutionsHistory.userNamesExecutions;

import components.ExecutionsHistory.userNamesExecutions.Refreshers.UserNameRefresher;
import components.ExecutionsHistory.userNamesExecutions.Refreshers.SimulationIdRefresher;
import components.mainApp.MainAppController;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.Timer;
import java.util.TimerTask;

public class showUsersAndSimulationIDController
{
    @FXML
    private ChoiceBox<String> usernameChoiceBox;

    @FXML
    private ChoiceBox<String> simulationIdChoiceBox;
    Timer simulationIdTimer = new Timer();

    public void initialize()
    {
        usernameChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
            {
                // Populate the simulationIdChoiceBox based on the selected username
                startSimulationIdRefresher(newValue);
                // Call the function you want
                //simulationIdTimer.cancel();
            }
        });
        startUserNameRefresher();


    }
    MainAppController mainAppController;
    public void setAppMainController(MainAppController mainAppController)
    {

    }

    private void startUserNameRefresher()
    {
        Timer timer = new Timer();
        TimerTask task = new UserNameRefresher(usernameChoiceBox);
        long delay = 0; // Initial delay (0 milliseconds)
        long period = 2000; // Repeat every 2 seconds (2000 milliseconds)
        timer.scheduleAtFixedRate(task, delay, period);
    }
    private void startSimulationIdRefresher(String userName)
    {
        TimerTask task = new SimulationIdRefresher(simulationIdChoiceBox,userName);
        long delay = 0; // Initial delay (0 milliseconds)
        long period = 2000; // Repeat every 2 seconds (2000 milliseconds)
        simulationIdTimer.scheduleAtFixedRate(task, delay, period);
    }

}
