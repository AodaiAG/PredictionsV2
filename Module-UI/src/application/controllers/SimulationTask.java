package application.controllers;

import application.controllers.ResultsScreenController.ResultsScreenController;
import application.controllers.ResultsScreenController.SimulationDetailsTabController;
import application.manager.UserInterfaceManager;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;
import pSystem.Engine;
import pSystem.IEngine;
import pSystem.Simulation;
import pSystem.World;
import java.util.UUID;

public class SimulationTask  extends Task<Void>
{
    UserInterfaceManager uiManger;
    IEngine engine;
    Tab simulationTab;
    SimulationDetailsTabController simulationDetailsTabController;
//
    @Override
    protected Void call() throws Exception
    {

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
}




