package main;

import components.mainApp.MainAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;

import static util.Constants.*;

public class AdministratorClient extends Application
{
    private MainAppController mainAppController;
    private boolean administratorWindowClosing;

    @Override
    public void start(Stage primaryStage)
    {
        if (isAdministratorActive()) {
            showAdministratorActiveDialog();
        }
        else {
            notifyAdministratorIsActivated();
            primaryStage.setTitle("Administrator");
            primaryStage.setOnCloseRequest(event -> {
                // Set the flag when the administrator window is being closed
                administratorWindowClosing = true;
            });
            URL loginPage = getClass().getResource(MAIN_PAGE_FXML_RESOURCE_LOCATION);
            try
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(loginPage);
                Parent root = fxmlLoader.load();
                mainAppController = fxmlLoader.getController();
                mainAppController.initApplication();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void showAdministratorActiveDialog()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Administrator Active");
        alert.setHeaderText(null);
        alert.setContentText("An administrator is already active. You cannot start a new instance.");
        alert.showAndWait();
    }

    @Override
    public void stop() throws Exception
    {
        if (administratorWindowClosing)
        {
            notifyAdministratorIsDeactivated();
        } else {
            System.out.println("i close dialog");
        }
        System.exit(5);
    }

    private boolean isAdministratorActive()
    {
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = HttpUrl.parse(ADMIN_ACTIVATION)
                .newBuilder()
                .addQueryParameter("mode", "check")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return Boolean.parseBoolean(responseBody);
            } else {
                // Handle non-successful response (e.g., log or throw an exception)
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
        return false; // Return false by default if there is an error
    }

    private void notifyAdministratorIsActivated() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = HttpUrl.parse(ADMIN_ACTIVATION)
                .newBuilder()
                .addQueryParameter("mode", "activated")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // The servlet has been notified of the administrator activation.
            } else {
                // Handle non-successful response (e.g., log or throw an exception)
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
    } private void notifyAdministratorIsDeactivated() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = HttpUrl.parse(ADMIN_ACTIVATION)
                .newBuilder()
                .addQueryParameter("mode", "deactivated")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // The servlet has been notified of the administrator activation.
            } else {
                // Handle non-successful response (e.g., log or throw an exception)
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
