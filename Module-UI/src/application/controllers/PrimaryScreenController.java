package application.controllers;
import application.manager.UserInterfaceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javax.xml.soap.Text;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class PrimaryScreenController
{
    private UserInterfaceManager uiManager;
    @FXML
    private TextField filePathLabel;
    @FXML
    BorderPane mainBoard;
    @FXML
    Button results,newExecution,details,btnLoad,btnHome,btnSettings,btnTheme;
    @FXML
    Pane buttonsPane;
    @FXML
    Text coomingSoonT;
    @FXML
    private FontAwesomeIconView exiticon;
    @FXML
    private AnchorPane mainAnchorProgram;

    @FXML
    private Pane pnlStatus;

    @FXML
    private Label pnlsMessage;
    @FXML
    private VBox Leftvbox;

    @FXML
    public void loadXmlButton(ActionEvent event)
    {
        uiManager.loadXmlFile(event);
    }
    public PrimaryScreenController()
    {
        uiManager = UserInterfaceManager.INSTANCE;
    }


    @FXML
    public void buttonSwitchToDetailsScene(ActionEvent event)
    {
        try
        {
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlsMessage.setText("Details");
            Node details = FXMLLoader.load(getClass().getResource("/application/resources/detailsScene.fxml"));
            mainBoard.setCenter(details);
        } catch (IOException e)
        {
            System.out.println("failed to load detailsScene.fxml");
        }
    }

    @FXML
    void buttonSwitchToNewExecutionScene(ActionEvent event)
    {

        try
        {

            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlsMessage.setText("New Execution");
            Node newEx = FXMLLoader.load(getClass().getResource("/application/resources/newExecutionScene.fxml"));
            mainBoard.setCenter(newEx);
        } catch (IOException e)
        {
            System.out.println("failed to load newExecutionScene here.fxml");
        }
    }

    @FXML
    void Homebttonhandle(ActionEvent event)
    {
        buttonsPane.setVisible(true);
        pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
        pnlsMessage.setText("Home");
    }
    @FXML
    void settingsBttonhandle(ActionEvent event)
    {
        try
        {
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlsMessage.setText("Settings");
            Node newEx = FXMLLoader.load(getClass().getResource("/application/resources/settings.fxml"));
            mainBoard.setCenter(newEx);
            buttonsPane.setVisible(false);
        } catch (IOException e)
        {
            System.out.println("failed to settings.fxml");
        }

    }
    @FXML
    void themeBrown(ActionEvent event)
    {

        //mainAnchorProgram.getScene().getRoot().getStylesheets().add("/application/resources/theme2.css");
        //mainAnchorProgram.getScene().getRoot().getStylesheets().add("/application/resources/theme2.css");
        Leftvbox.setBackground(new Background(new BackgroundFill(Color.rgb(48,28,28), CornerRadii.EMPTY, Insets.EMPTY)));
        pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(48,28,28), CornerRadii.EMPTY, Insets.EMPTY)));
        details.setBackground(new Background(new BackgroundFill(Color.rgb(48,28,28), CornerRadii.EMPTY, Insets.EMPTY)));
        newExecution.setBackground(new Background(new BackgroundFill(Color.rgb(48,28,28), CornerRadii.EMPTY, Insets.EMPTY)));
        results.setBackground(new Background(new BackgroundFill(Color.rgb(48,28,28), CornerRadii.EMPTY, Insets.EMPTY)));
        btnHome.setBackground(new Background(new BackgroundFill(Color.rgb(48,28,28), CornerRadii.EMPTY, Insets.EMPTY)));
        btnSettings.setBackground(new Background(new BackgroundFill(Color.rgb(48,28,28), CornerRadii.EMPTY, Insets.EMPTY)));
        btnLoad.setBackground(new Background(new BackgroundFill(Color.rgb(48,28,28), CornerRadii.EMPTY, Insets.EMPTY)));


    }

    @FXML
    void btnThemeHandle(ActionEvent event)
    {
        Leftvbox.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
        pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
        details.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
        newExecution.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
        results.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
        btnHome.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
        btnSettings.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
        btnLoad.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));


    }




    @FXML
    public void switchToResultsScene(ActionEvent event) {
        uiManager.switchToResultsScene();
    }

    public void handleClose(javafx.scene.input.MouseEvent mouseEvent)
    {
        System.exit(0);
    }
}
