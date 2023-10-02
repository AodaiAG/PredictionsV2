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
        switchToMainAppPage(event);
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

                if (response.code() != 200)
                {
                    System.out.println("im not good");
                    String responseBody = response.body().string();
                    System.out.println("Response Body: " + responseBody);

                    Platform.runLater(() ->
                            errorMessageProperty.set("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() ->
                    {
                        System.out.println("im good");
                            mainAppController.updateUserName(userName);
                            switchToMainAppPage(event);
                    });
                }
            }
        });
    }

    public void switchToMainAppPage(ActionEvent event)
    {
        // Assuming you have a MainApp.fxml file and MainAppController for your main app
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_PAGE_FXML_RESOURCE_LOCATION));
        Parent mainAppRoot;
        try {
            mainAppRoot = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        UserMainAppController mainAppController = loader.getController();
        Scene mainAppScene = new Scene(mainAppRoot);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(mainAppScene);
        currentStage.setTitle("User");
        currentStage.show();
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
