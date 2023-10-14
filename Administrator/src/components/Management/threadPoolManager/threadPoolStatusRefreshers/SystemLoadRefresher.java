package components.Management.threadPoolManager.threadPoolStatusRefreshers;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.TimerTask;

public class SystemLoadRefresher extends TimerTask
{
    private ProgressBar sLprogressBar;

    public SystemLoadRefresher(ProgressBar sLprogressBar)
    {
        this.sLprogressBar = sLprogressBar;
    }

    @Override
    public void run()
    {
        String serverUrl = "http://localhost:8080/get_system_load"; // Example URL
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
                String responseString=response.body().string();
                Platform.runLater(()->
                {
                    // Parse the responseString into an integer representing system load
                    int systemLoad = Integer.parseInt(responseString);

                    // Map system load (1-10) to progress bar value (0.1 - 1.0)
                    double progress = systemLoad / 10.0;

                    // Update the progress bar value
                    sLprogressBar.setProgress(progress);
                    setProgressColor();

                });
            }
        });


    }

    private void setProgressColor()
    {
        double progress = sLprogressBar.getProgress();
        if (progress >= 0.1 && progress < 0.5)
        {
            sLprogressBar.setStyle("-fx-accent: green;");
        } else if (progress >= 0.5 && progress < 0.8) {
            sLprogressBar.setStyle("-fx-accent: orange;");
        } else if (progress >= 0.8) {
            sLprogressBar.setStyle("-fx-accent: red;");
        }
    }
}
