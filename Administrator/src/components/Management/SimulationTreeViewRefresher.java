package components.Management;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import components.Configuration.Configuration;
import javafx.scene.control.TreeView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import pDTOS.SimulationDTO;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

public class SimulationTreeViewRefresher extends TimerTask
{

    @Override
    public void run()
    {

        try
        {

            String RESOURCE = "/simulations";

            HttpClientUtil.runAsync(Configuration.BASE_URL + RESOURCE, new Callback()
            {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e)
                {
                    // httpRequestLoggerConsumer.accept("Something went wrong with Chat Request # " + finalRequestNumber);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                {
                    if (response.isSuccessful())
                    {
                        String rawBody = response.body().string();
                        Gson gson = new Gson();
                        TypeToken<List<SimulationDTO>> typeToken = new TypeToken<List<SimulationDTO>>() {};
                        List<SimulationDTO> simulationDTOList = gson.fromJson(rawBody, typeToken.getType());
                        System.out.println("hmmm");
                    }
                    else
                    {
                        // httpRequestLoggerConsumer.accept("Something went wrong with Request # " + finalRequestNumber + ". Code is " + response.code());
                    }
                }
            });


            // System.out.println(response.body().string()+"From client Admin");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
