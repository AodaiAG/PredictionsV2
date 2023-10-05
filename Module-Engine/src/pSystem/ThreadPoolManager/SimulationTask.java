package pSystem.ThreadPoolManager;

import application.controllers.EntityWrapper;
import application.controllers.ResultsScreenController.SimulationDetailsTabController;
import application.controllers.SimulationConditions;
import application.manager.UserInterfaceManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import pSystem.engine.IEngine;
import pSystem.engine.SimulationResult;

import java.util.UUID;
import java.util.function.Consumer;

public class SimulationTask extends Task<Void>
{
    public SimulationConditions simulationConditions = new SimulationConditions();
    UserInterfaceManager uiManger;
    IEngine engine;
    application.controllers.EntityWrapper entityWrapper;
    private SimulationResult simulationResult;
    SimulationDetailsTabController simulationDetailsTabController;



    public SimulationTask(IEngine engine, UserInterfaceManager uiManger, SimulationDetailsTabController ct)
    {
        this.engine = engine;
        this.uiManger = uiManger;
        this.simulationDetailsTabController = ct;
    }

    public application.controllers.EntityWrapper getEntityWrapper()
    {
        return entityWrapper;
    }

    public void setEntityWrapper(EntityWrapper entityWrapper) {
        this.entityWrapper = entityWrapper;
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

        UUID simulationId = engine.startSimulation(simulationConditions, consumer,entityWrapper);
        simulationDetailsTabController.disableProgressNode();
        this.simulationResult = engine.getSimulationResults().get(simulationId);
        Platform.runLater(() ->
        {
            simulationDetailsTabController.setStatusLabel("Finished");
            uiManger.incrementCompletedSimulations();
            uiManger.decrementExecutingSimulations();
            this.simulationDetailsTabController.setSimulationResultsPane(simulationResult);
        });
        return null;
    }

    public void returnButtonPressed()
    {
       engine.setWorldFromExecution(simulationResult);
       uiManger.switchToNewExecutionScreen(new ActionEvent());
    }

    public void pauseSimulation()
    {
        simulationDetailsTabController.setStatusLabel("Paused");
        simulationConditions.setPauseSimulation(true);
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