package application.controllers;

import application.controllers.ResultsScreenController.SimulationDetailsTabController;
import application.manager.UserInterfaceManager;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;
import pSystem.IEngine;
import pSystem.Simulation;

import java.awt.event.ActionEvent;
import java.util.UUID;
import java.util.function.Consumer;

public class SimulationTask extends Task<Void> {
    public SimulationConditions simulationConditions = new SimulationConditions();
    UserInterfaceManager uiManger;
    IEngine engine;
    SimulationDetailsTabController simulationDetailsTabController;

    public SimulationTask(IEngine engine, UserInterfaceManager uiManger, SimulationDetailsTabController ct) {
        this.engine = engine;
        this.uiManger = uiManger;
        this.simulationDetailsTabController = ct;
    }

    //
    @Override
    protected Void call() throws Exception {
        // this is the function that start the simulation logic
        Consumer<String> consumer = this::updateMessage;
        simulationDetailsTabController.enableProgressNode();
        UUID simulationId = engine.startSimulation(simulationConditions, consumer);
        simulationDetailsTabController.disableProgressNode();

        // when the simulation ends
//        try
//        {
//            Thread.sleep(4000);
//
//        } catch (InterruptedException e)
//        {
//            throw new RuntimeException(e);
//        }
        Simulation simulation = engine.getSimulations().get(simulationId);
        Platform.runLater(() ->
        {
            this.simulationDetailsTabController.setSimulationResultsPane(simulation);
        });
        return null;
    }

    public void pauseSimulation() {
        simulationConditions.setPauseSimulation(true);
        System.out.println("setting pause to true");
    }

    public void bindComponentsToTask() {
        simulationDetailsTabController.bindComponentsToTask();
    }

    public void resumeSimulation() {
        simulationConditions.setPauseSimulation(false);

    }

    public void stopSimulation() {
        simulationConditions.setSimulationRunning(false);
    }
}