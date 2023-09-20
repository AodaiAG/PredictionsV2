package application.controllers;

import application.controllers.ResultsScreenController.SimulationDetailsTabController;
import application.manager.UserInterfaceManager;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Tab;
import pSystem.IEngine;
import pSystem.Simulation;

import java.util.UUID;
import java.util.function.Consumer;

public class SimulationTask extends Task<Void>
{
    public SimulationConditions simulationConditions = new SimulationConditions();
    UserInterfaceManager uiManger;
    IEngine engine;
    private Simulation simulation;
    SimulationDetailsTabController simulationDetailsTabController;

    public SimulationTask(IEngine engine, UserInterfaceManager uiManger, SimulationDetailsTabController ct)
    {
        this.engine = engine;
        this.uiManger = uiManger;
        this.simulationDetailsTabController = ct;
    }

    public void returnButton()
    {

    }

    //
    @Override
    protected Void call() throws Exception
    {
        // this is the function that start the simulation logic
        Consumer<String> consumer = this::updateMessage;
        simulationDetailsTabController.enableProgressNode();
        Platform.runLater(() ->
        {
            uiManger.decrementWaitingSimulations();
            uiManger.incrementExecutingSimulations();
        });


        UUID simulationId = engine.startSimulation(simulationConditions, consumer);
        simulationDetailsTabController.disableProgressNode();
        this.simulation = engine.getSimulations().get(simulationId);
        Platform.runLater(() ->
        {
            simulationDetailsTabController.setStatusLabel("Finished");
            uiManger.incrementCompletedSimulations();
            uiManger.decrementExecutingSimulations();
            this.simulationDetailsTabController.setSimulationResultsPane(simulation);
        });
        return null;
    }
    public void returnButtonPressed()
    {
       engine.setWorldFromExecution(simulation);
       uiManger.switchToNewExecutionScreen(new ActionEvent());
    }

    public void pauseSimulation()
    {
        simulationDetailsTabController.setStatusLabel("Paused");
        simulationConditions.setPauseSimulation(true);
        System.out.println("setting pause to true");
    }

    public void bindComponentsToTask() {
        simulationDetailsTabController.bindComponentsToTask();
    }

    public void resumeSimulation()
    {

        simulationConditions.setPauseSimulation(false);
        simulationDetailsTabController.setStatusLabel("Running");

    }

    public void stopSimulation()
    {
        simulationConditions.setSimulationRunning(false);

    }
}