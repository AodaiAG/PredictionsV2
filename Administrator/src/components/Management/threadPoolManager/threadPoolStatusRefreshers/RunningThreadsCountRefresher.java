package components.Management.threadPoolManager.threadPoolStatusRefreshers;

import javafx.application.Platform;
import javafx.scene.control.Label;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.TimerTask;

public class RunningThreadsCountRefresher extends TimerTask
{

    Label completedLabel;

    public RunningThreadsCountRefresher(Label completedLabel) {
        this.completedLabel = completedLabel;
    }

    @Override
    public void run()
    {
        String serverUrl = "http://localhost:8080/get_ru_threads_count"; // Example URL
        Request request = new Request.Builder()
                .url(serverUrl)
                .build();
        Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("i'm in onFailure/user");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseString = response.body().string();
                Platform.runLater(() -> {
                    completedLabel.setText(responseString);
                });
            }
        });


    }
}
