

package components.mainApp;

import components.Configuration.Configuration;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import okhttp3.*;

import java.io.File;

public class MainAppController
{

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
                        .url(Configuration.BASE_URL + RESOURCE)
                        .post(body)
                        .build();

                Call call = Configuration.HTTP_CLIENT.newCall(request);

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

    @FXML
    void switchToAllocationsScreen(ActionEvent event) {

    }

    @FXML
    void switchToExecutionsScene(ActionEvent event) {

    }

    @FXML
    void switchToManagmentScreen(ActionEvent event)
    {

    }

}
