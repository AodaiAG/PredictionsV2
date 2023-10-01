

package components.mainApp;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import okhttp3.*;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainAppController
{
    AnchorPane executionsComponent;
    ExecutionsController executionsController;
    AnchorPane allocationsComponent;
    AllocationsController allocationsController;
    AnchorPane ManagmentComponent;
    ManagementController managementController;
    @FXML
    private AnchorPane mainAnchorProgram;
    @FXML
    private VBox Leftvbox;
    @FXML
    private Pane pnlStatus;
    @FXML
    private Label pnlsMessage;
    @FXML
    private Pane buttonsPane;
    @FXML
    private Button btnLoad;
    @FXML
    private TextField filePathLabel;
    @FXML
    private Button magnmentBtn;
    @FXML
    private Button AllocationsBtn;
    @FXML
    private Button ExecutionsBtn;
    @FXML
    private AnchorPane mainAnchorpane;

    public void initApplication()
    {
        loadManagment();
        loadAllocations();
        loadExecutionsHistory();
    }


    private void loadManagment()
    {
        URL loginPageUrl = getClass().getResource(MANAGMENT_PAGE);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            ManagmentComponent = fxmlLoader.load();
            managementController = fxmlLoader.getController();
            managementController.setChatAppMainController(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void loadAllocations()
    {
        URL loginPageUrl = getClass().getResource(ALLOCATIONS_FXML);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            allocationsComponent = fxmlLoader.load();
            allocationsController = fxmlLoader.getController();
            allocationsController.setChatAppMainController(this);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadExecutionsHistory()
    {
        URL loginPageUrl = getClass().getResource(EXECUTIONS_FXML);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            executionsComponent = fxmlLoader.load();
            executionsController = fxmlLoader.getController();
            executionsController.setChatAppMainController(this);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }




    private void setMainPanelTo(Parent pane)
    {
        mainAnchorpane.getChildren().clear();
        mainAnchorpane.getChildren().add(pane);
        AnchorPane.setBottomAnchor(pane, 1.0);
        AnchorPane.setTopAnchor(pane, 1.0);
        AnchorPane.setLeftAnchor(pane, 1.0);
        AnchorPane.setRightAnchor(pane, 1.0);
    }
    @FXML
    void loadXmlButton(ActionEvent event)
    {
        try
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open XML File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            File f = fileChooser.showOpenDialog(mainAnchorProgram.getScene().getWindow());
            if(f!=null)
            {
                String RESOURCE = "/upload-file";

                RequestBody body =
                        new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("file1", f.getName(), RequestBody.create(f, MediaType.parse("text/xml")))
                                .build();

                Request request = new Request.Builder()
                        .url(Constants.BASE_URL + RESOURCE)
                        .post(body)
                        .build();

                Call call = HttpClientUtil.HTTP_CLIENT.newCall(request);

                Response response = call.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Status");
                alert.setContentText(response.body().string());
                alert.showAndWait();
            }

           // System.out.println(response.body().string()+"From client Admin");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
//
    @FXML
    void switchToAllocationsScreen(ActionEvent event)
    {
        setMainPanelTo(allocationsComponent);
    }

    @FXML
    void switchToExecutionsScene(ActionEvent event)
    {
        setMainPanelTo(executionsComponent);
    }

    @FXML
    void switchToManagmentScreen(ActionEvent event)
    {
        setMainPanelTo(ManagmentComponent);
        managementController. startSimulationDetailsRefresher();
    }

}
