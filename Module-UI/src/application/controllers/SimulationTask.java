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

public class SimulationTask  extends Task<Void>
{
    UserInterfaceManager uiManger;
    IEngine engine;
    Tab simulationTab;
    SimulationDetailsTabController simulationDetailsTabController;

    int counter=0;
//
    @Override
    protected Void call() throws Exception
    {
        // this is the function that start the simulation logic
        UUID simulationId = engine.startSimulation();

        // when the simulation ends
        try
        {
            Thread.sleep(4000);

        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        Simulation simulation =engine.getSimulations().get(simulationId);
        Platform.runLater(() ->
        {
            this.simulationDetailsTabController.setSimulationResultsPane(simulation);
        });
        return null;
    }
    public SimulationTask(IEngine engine, Tab tab, UserInterfaceManager uiManger,SimulationDetailsTabController ct)
    {
        this.engine = engine;
        simulationTab=tab;
        this.uiManger=uiManger;
        this.simulationDetailsTabController=ct;

    }


    public void pauseSimulation()
    {
        engine.pauseSimulation();

    }

    public void resumeSimulation()
    {
        engine.resumeSimulation();

    }


    public void stopSimulation(ActionEvent event)
    {
        engine.stopSimulation();

    }

}




