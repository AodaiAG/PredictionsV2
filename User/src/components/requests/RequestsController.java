package components.requests;

import components.mainApp.UserMainAppController;
import components.requests.showUserRequests.UserRequestsController;
import components.requests.submitNewRequest.SubmitNewRequestController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

import static util.Constants.*;

public class RequestsController
{
    @FXML
    private AnchorPane submitNewAnchor;
    SubmitNewRequestController submitNewRequestController;

    @FXML
    private AnchorPane showRequestAnchor;

    private UserRequestsController userRequestsController;

    public void initApplication()
    {
        loadSubmitNewRequestPage();
        loadUserRequestsPage();
    }

    private void loadSubmitNewRequestPage()
    {

        URL loginPage = getClass().getResource(SUBMIT_NEW_REQUEST_LOCATION);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPage);
            AnchorPane toBeSubmitted = fxmlLoader.load();
            submitNewRequestController = fxmlLoader.getController();
            submitNewRequestController.setAppMainController(this.mainAppController);
            //submitNewRequestController.initApplication();
            submitNewAnchor.getChildren().clear();
            submitNewAnchor.getChildren().add(toBeSubmitted);
            AnchorPane.setBottomAnchor(toBeSubmitted, 1.0);
            AnchorPane.setTopAnchor(toBeSubmitted, 1.0);
            AnchorPane.setLeftAnchor(toBeSubmitted, 1.0);
            AnchorPane.setRightAnchor(toBeSubmitted, 1.0);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadUserRequestsPage()
    {
        URL loginPage = getClass().getResource(SHOW_USER_REQUESTS);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPage);
            AnchorPane toBeSubmitted = fxmlLoader.load();
            userRequestsController = fxmlLoader.getController();
            userRequestsController.setAppMainController(this.mainAppController);
            //submitNewRequestController.initApplication();
            showRequestAnchor.getChildren().clear();
            showRequestAnchor.getChildren().add(toBeSubmitted);
            AnchorPane.setBottomAnchor(toBeSubmitted, 1.0);
            AnchorPane.setTopAnchor(toBeSubmitted, 1.0);
            AnchorPane.setLeftAnchor(toBeSubmitted, 1.0);
            AnchorPane.setRightAnchor(toBeSubmitted, 1.0);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    UserMainAppController mainAppController;

    public void setAppMainController(UserMainAppController mainAppController)
    {
        this.mainAppController = mainAppController;
    }
}
