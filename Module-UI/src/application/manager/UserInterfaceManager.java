package application.manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import pSystem.Engine;
import pSystem.IEngine;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pSystem.Engine;

import java.io.File;

import java.io.IOException;
import java.util.Optional;

public enum UserInterfaceManager
{
    INSTANCE;

    private Stage stage;
    private Scene detailsScene;
    private Scene newExecutionScene;
    private Scene resultsScene;
    private Scene primaryScene;
    private IEngine  engine=new Engine();

    public Scene getPrimaryScene() {
        return primaryScene;
    }

    public void setPrimaryScene(Scene primaryScene) {
        this.primaryScene = primaryScene;
    }
    public void initApplication()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/scenePrimary.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            primaryScene = new Scene(root);
            stage.setScene(primaryScene);
            stage.setTitle("Main Application");
            stage.show();
        } catch (IOException e) {
            System.out.println("failed to load scenePrimary.fxml");
        }
    }
    public void loadXmlFile(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null)
        {
            // Prompt the user to enter the name of the simulation
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Simulation Name");
            dialog.setHeaderText("Please enter the name of the simulation:");
            dialog.setContentText("Name:");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent())
            {
                String simulationName = result.get();

                // Load and process the XML file with the simulation name
                try {
                    engine.ParseXmlAndLoadWorld(selectedFile);

                    // Notify the user of successful loading
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("File Loaded Successfully");
                    alert.setHeaderText("The XML file was loaded successfully.");
                    alert.setContentText("Simulation Name: " + simulationName);
                    alert.showAndWait();

                    // Optionally, switch to a different scene or update UI components here
                } catch (Exception e)
                {
                    // Handle any exceptions that may occur during XML parsing
                    e.printStackTrace();

                    // Notify the user of the error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Loading File");
                    alert.setHeaderText("An error occurred while loading the XML file.");
                    alert.setContentText("Reason: " + e.getMessage());
                    alert.showAndWait();
                }
            } else {
                // User canceled the input dialog
                // Handle this case as needed
            }
        }
    }
    public Stage getStage()
    {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getDetailsScene() {
        return detailsScene;
    }

    public void setDetailsScene(Scene detailsScene) {
        this.detailsScene = detailsScene;
    }

    public Scene getNewExecutionScene() {
        return newExecutionScene;
    }

    public void setNewExecutionScene(Scene newExecutionScene)
    {
        this.newExecutionScene = newExecutionScene;
    }

    public Scene getResultsScene() {
        return resultsScene;
    }

    public void setResultsScene(Scene resultsScene) {
        this.resultsScene = resultsScene;
    }



    public void switchToDetailsScene()
    {
        if(detailsScene == null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/application/resources/detailsScene.fxml"));
                detailsScene = new Scene(root);
            }
            catch (IOException e)
            {
                System.out.println("failed to load detailsScene.fxml");
            }

        }
        stage.setScene(detailsScene);
        stage.show();
    }


    public void switchToNewExecutionScene(){
        if(newExecutionScene == null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/application/resources/newExecutionScene.fxml"));
                newExecutionScene = new Scene(root);
            }
            catch (IOException e)
            {
                System.out.println("failed to load newExecutionScene.fxml");
            }

        }
        stage.setScene(newExecutionScene);
        stage.show();
    }

    public void switchToResultsScene() {
        if(resultsScene == null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/application/resources/resultsScene.fxml"));
                resultsScene = new Scene(root);
            }catch(IOException e)
            {
                System.out.println("failed to resultsScene.fxml");
            }
        }
        stage.setScene(resultsScene);
        stage.show();
    }
}
