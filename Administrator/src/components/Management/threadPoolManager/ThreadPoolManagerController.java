package components.Management.threadPoolManager;

import components.Management.simulationDetails.SimulationTreeViewRefresher;
import components.Management.threadPoolManager.threadPoolStatusRefreshers.SystemLoadRefresher;
import components.Management.threadPoolManager.threadPoolStatusRefreshers.completedRefresher;
import components.Management.threadPoolManager.threadPoolStatusRefreshers.executingRefresher;
import components.Management.threadPoolManager.threadPoolStatusRefreshers.waitingRefreshers;
import components.mainApp.MainAppController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ThreadPoolManagerController
{
    private MainAppController mainAppController;
    @FXML
    private TextField threadText;
    @FXML
    private ProgressBar sLprogressBar;


    @FXML
    private Button setBTN;

    @FXML
    private Label waitingLabel;

    @FXML
    private Label exeLabel;

    @FXML
    private Label compLabel;
    @FXML
    private TextField inText;

    @FXML
    private TextField deTxt;




    public void initialize()
    {
        sLprogressBar.setStyle("-fx-accent: red;");
        sLprogressBar.getProgress();
        startRefreshers();
    }
    public void setAppMainController(MainAppController mainAppController)
    {
        this.mainAppController = mainAppController;
    }
    private void startRefreshers()
    {

        startCompletedThreadCountTask();
        startExecutingThreadCountTask();
        startWaitingThreadCountTask();
        startSystemLoadTask();
    }

    private void startCompletedThreadCountTask()
    {
        Timer timer = new Timer();
        TimerTask task = new completedRefresher(compLabel);
        long delay = 0; // Initial delay (0 milliseconds)
        long period = 1; // Repeat every 2 seconds (2000 milliseconds)
        timer.scheduleAtFixedRate(task, delay, period);

    }
    private void startExecutingThreadCountTask()
    {
        Timer timer = new Timer();
        TimerTask task = new executingRefresher(exeLabel);
        long delay = 0; // Initial delay (0 milliseconds)
        long period = 1; // Repeat every 2 seconds (2000 milliseconds)
        timer.scheduleAtFixedRate(task, delay, period);

    }
    private void startSystemLoadTask()
    {
        Timer timer = new Timer();
        TimerTask task = new SystemLoadRefresher(sLprogressBar);
        long delay = 0; // Initial delay (0 milliseconds)
        long period = 1; // Repeat every 2 seconds (2000 milliseconds)
        timer.scheduleAtFixedRate(task, delay, period);

    }

    private void startWaitingThreadCountTask()
    {
        Timer timer = new Timer();
        TimerTask task = new waitingRefreshers(waitingLabel);
        long delay = 0; // Initial delay (0 milliseconds)
        long period = 1; // Repeat every 2 seconds (2000 milliseconds)
        timer.scheduleAtFixedRate(task, delay, period);

    }

    @FXML
    void setThreadPoolCount(ActionEvent event)
    {

        try
        {

            String serverUrl = "http://localhost:8080/set_thread_count?value="+threadText.getText(); // Example URL
            Request request = new Request.Builder()
                    .url(serverUrl)
                    .build();
            Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
            call.enqueue(new Callback()
            { @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                System.out.println("i'm in onFailure/user");
            }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                {
                    Platform.runLater(()->
                    {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        try
                        {
                            String responsee=response.body().string();
                            alert.setContentText(responsee);
                            setBTN.setDisable(true);
                            threadText.clear();
                            threadText.setDisable(true);

                        } catch (IOException e)
                        {
                            alert.setContentText(e.getMessage());
                        }
                        alert.showAndWait();
                    });
                }
            });


        }
        catch (Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Error : "+e.getMessage());
            alert.showAndWait();
        }
    }



    @FXML
    void decreaseThreadCount(ActionEvent event)
    {
        try
        {

            String serverUrl = "http://localhost:8080/de_thread_count?value="+deTxt.getText(); // Example URL
            Request request = new Request.Builder()
                    .url(serverUrl)
                    .build();
            Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
            call.enqueue(new Callback()
            { @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                System.out.println("i'm in onFailure/user");
            }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                {
                    Platform.runLater(()->
                    {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        try
                        {
                            String responsee=response.body().string();
                            alert.setContentText(responsee);


                        } catch (IOException e)
                        {
                            alert.setContentText(e.getMessage());
                        }
                        alert.showAndWait();
                    });
                }
            });


        }
        catch (Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Error : "+e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void increaseThreadCount(ActionEvent event)
    {
        try
        {

            String serverUrl = "http://localhost:8080/in_thread_count?value="+inText.getText(); // Example URL
            Request request = new Request.Builder()
                    .url(serverUrl)
                    .build();
            Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
            call.enqueue(new Callback()
            { @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                System.out.println("i'm in onFailure/user");
            }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                {
                    Platform.runLater(()->
                    {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        try
                        {
                            String responsee=response.body().string();
                            alert.setContentText(responsee);


                        } catch (IOException e)
                        {
                            alert.setContentText(e.getMessage());
                        }
                        alert.showAndWait();
                    });
                }
            });


        }
        catch (Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Error : "+e.getMessage());
            alert.showAndWait();
        }

    }
}
