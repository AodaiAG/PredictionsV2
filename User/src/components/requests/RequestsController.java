package components.requests;

import components.mainApp.UserMainAppController;
import components.requests.showUserRequests.UserRequestsController;
import components.requests.submitNewRequest.SubmitNewRequestController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

import static util.Constants.MAIN_PAGE_FXML_RESOURCE_LOCATION;
import static util.Constants.*;

public class RequestsController
{
    @FXML
    private AnchorPane submitnewAnchor;
    SubmitNewRequestController submitNewRequestController;

    @FXML
    private AnchorPane showRequestAnchor;

    private UserRequestsController userRequestsController;

    public void initApplication()
    {
        loadSubmitnewRequestPage();
        loadUserRequestsPage();
    }

    private void loadSubmitnewRequestPage()
    {

        URL loginPage = getClass().getResource(SUBMIT_NEW_REQUEST_LOCATION);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPage);
            AnchorPane tobeSubmited = fxmlLoader.load();
            submitNewRequestController = fxmlLoader.getController();
            submitNewRequestController.setAppMainController(this.mainAppController);
            //submitNewRequestController.initApplication();
            submitnewAnchor.getChildren().clear();
            submitnewAnchor.getChildren().add(tobeSubmited);
            AnchorPane.setBottomAnchor(submitnewAnchor, 1.0);
            AnchorPane.setTopAnchor(submitnewAnchor, 1.0);
            AnchorPane.setLeftAnchor(submitnewAnchor, 1.0);
            AnchorPane.setRightAnchor(submitnewAnchor, 1.0);

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
            AnchorPane tobeSubmited = fxmlLoader.load();
            userRequestsController = fxmlLoader.getController();
            userRequestsController.setAppMainController(this.mainAppController);
            //submitNewRequestController.initApplication();
            showRequestAnchor.getChildren().clear();
            showRequestAnchor.getChildren().add(tobeSubmited);
            AnchorPane.setBottomAnchor(showRequestAnchor, 1.0);
            AnchorPane.setTopAnchor(showRequestAnchor, 1.0);
            AnchorPane.setLeftAnchor(showRequestAnchor, 1.0);
            AnchorPane.setRightAnchor(showRequestAnchor, 1.0);

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
