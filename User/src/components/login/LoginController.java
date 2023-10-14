package components.login;

import components.mainApp.UserMainAppController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.net.URL;

import static util.Constants.MAIN_PAGE_FXML_RESOURCE_LOCATION;

public class LoginController
{
    @FXML
    public TextField userNameTextField;

    @FXML
    public Label errorMessageLabel;

   private UserMainAppController mainAppController;

    private final StringProperty errorMessageProperty = new SimpleStringProperty();

    @FXML
    public void initialize()
    {
        errorMessageLabel.textProperty().bind(errorMessageProperty);
//        HttpClientUtil.setCookieManagerLoggingFacility(line ->
//                Platform.runLater(() ->
//                        updateHttpStatusLine(line)));
    }


    @FXML
    private void loginButtonClicked(ActionEvent event)
    {
        String userName = userNameTextField.getText();
        if (userName.isEmpty())
        {
            errorMessageProperty.set("User name is empty. You can't login with empty user name");
            return;
        }
        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                        .parse(Constants.LOGIN_PAGE)
                        .newBuilder()
                        .addQueryParameter("username", userName)
                        .build()
                        .toString();

//        updateHttpStatusLine("New request is launched for: " + finalUrl);

        HttpClientUtil.runAsync(finalUrl, new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                if (response.code() != 200) {

                    Platform.runLater(() -> {
                        if (response.code() == 400) {
                            // User already exists (or other client-side error)
                            errorMessageProperty.set("User already exists");
                        } else {
                            // Handle other server-side errors
                            errorMessageProperty.set("Something went wrong");
                        }
                    });
                } else
                {
                    Platform.runLater(() ->
                    {
                        switchToMainAppPage(event);
                        mainAppController.updateUserName(userName);
                    });
                }
            }
        });
    }

    public void switchToMainAppPage(ActionEvent event) {
        // Load the FXML file for the main app
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_PAGE_FXML_RESOURCE_LOCATION));
        Parent mainAppRoot;
        try
        {
            mainAppRoot = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Create a new stage for the main app
        Stage newStage = new Stage();
        newStage.setTitle("User");
        Scene mainAppScene = new Scene(mainAppRoot);
        this.mainAppController = loader.getController();
        mainAppController.initApplication();
        // Set the scene for the new stage
        newStage.setScene(mainAppScene);

        // Close the previous stage if needed
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Show the new stage
        newStage.show();
    }

    @FXML
    private void userNameKeyTyped(KeyEvent event) {
        errorMessageProperty.set("");
    }

    @FXML
    private void quitButtonClicked(ActionEvent e) {
        Platform.exit();
    }

    public void setAppMainController(UserMainAppController mainAppController) {
        this.mainAppController = mainAppController;
    }
}
