package application.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public enum UserInterfaceManager {
    INSTANCE;

    private Stage stage;
    private Scene detailsScene;
    private Scene newExecutionScene;
    private Scene resultsScene;
    private Scene primaryScene;

    public Scene getPrimaryScene() {
        return primaryScene;
    }

    public void setPrimaryScene(Scene primaryScene) {
        this.primaryScene = primaryScene;
    }


    public Stage getStage() {
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

    public void setNewExecutionScene(Scene newExecutionScene) {
        this.newExecutionScene = newExecutionScene;
    }

    public Scene getResultsScene() {
        return resultsScene;
    }

    public void setResultsScene(Scene resultsScene) {
        this.resultsScene = resultsScene;
    }

    public void initApplication(){
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

    public void switchToDetailsScene() {
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
