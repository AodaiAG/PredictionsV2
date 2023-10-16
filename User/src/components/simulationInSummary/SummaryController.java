package components.simulationInSummary;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class SummaryController {
    public void fetchTreeViewData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8080/get_simulations_summary")
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String treeViewData = response.body().string();
                // Parse treeViewData and update the UI TreeView with the received data
                updateTreeView(treeViewData);
            } else {
                // Handle non-successful response
                System.err.println("Failed to fetch tree view data.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

}
