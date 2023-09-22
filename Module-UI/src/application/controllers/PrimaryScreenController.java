package application.controllers;
import application.manager.UserInterfaceManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.ScrollPane;


import javax.xml.soap.Text;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class PrimaryScreenController
{
    private UserInterfaceManager uiManager;
    @FXML
    public TextField filePathLabel;
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
    private AnchorPane mainAnchorpane;

    @FXML
    private Pane pnlStatus;

    @FXML
    private Label pnlsMessage;
    @FXML
    private VBox Leftvbox;
    @FXML
    private Label waitingLabel,exeLabel,compLabel;

    public IntegerProperty waitingSimulations = new SimpleIntegerProperty(0);
    public IntegerProperty executingSimulations = new SimpleIntegerProperty(0);
    public IntegerProperty completedSimulations = new SimpleIntegerProperty(0);


    @FXML
    public void loadXmlButton(ActionEvent event)
    {
        uiManager.loadXmlFile(event);
        filePathLabel.setText(uiManager.directoryPath);
        filePathLabel.setDisable(true);

    }

    public PrimaryScreenController()
    {
        uiManager = UserInterfaceManager.INSTANCE;
    }
    public void initialize()
    {

        waitingLabel.textProperty().bind(waitingSimulations.asString());
        exeLabel.textProperty().bind(executingSimulations.asString());
        compLabel.textProperty().bind(completedSimulations.asString());
    }

    @FXML
    public void buttonSwitchToDetailsScene(ActionEvent event)
    {
        try
        {
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlsMessage.setText("Details");
            Node details = FXMLLoader.load(getClass().getResource("/application/resources/detailsScene.fxml"));
            AnchorPane.setTopAnchor(details, 0.0);
            AnchorPane.setBottomAnchor(details, 0.0);
            AnchorPane.setLeftAnchor(details, 0.0);
            AnchorPane.setRightAnchor(details, 0.0);
            mainAnchorpane.getChildren().setAll(details);


        }
        catch (IOException e)
        {
            System.out.println("failed to load detailsScene.fxml");
        }
    }

    public IntegerProperty waitingSimulationsProperty() {
        return waitingSimulations;
    }

    public IntegerProperty executingSimulationsProperty() {
        return executingSimulations;
    }

    public IntegerProperty completedSimulationsProperty()
    {
        return completedSimulations;
    }


    @FXML
    public void buttonSwitchToNewExecutionScene(ActionEvent event)
    {
        try
        {
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlsMessage.setText("New Execution");
            Node newEx = FXMLLoader.load(getClass().getResource("/application/resources/newExecutionScene.fxml"));
            AnchorPane.setTopAnchor(newEx, 0.0);
            AnchorPane.setBottomAnchor(newEx, 0.0);
            AnchorPane.setLeftAnchor(newEx, 0.0);
            AnchorPane.setRightAnchor(newEx, 0.0);
            mainAnchorpane.getChildren().setAll(newEx);
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
            mainAnchorpane.getChildren().setAll(newEx);
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
    public void switchToResultsScene(ActionEvent event)
    {
        try
        {
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(63,43,99), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlsMessage.setText("Results");
            Node results = FXMLLoader.load(getClass().getResource("/application/resources/resultsScene.fxml"));
            AnchorPane.setTopAnchor(results, 0.0);
            AnchorPane.setBottomAnchor(results, 0.0);
            AnchorPane.setLeftAnchor(results, 0.0);
            AnchorPane.setRightAnchor(results, 0.0);
            mainAnchorpane.getChildren().setAll(results);
        } catch (IOException e)
        {
            System.out.println("failed to load results.fxml");
        }
    }

    public void handleClose(javafx.scene.input.MouseEvent mouseEvent)
    {
        System.exit(0);
    }


    public void refreshScreen()
    {
        mainAnchorpane.requestLayout();
    }
}
