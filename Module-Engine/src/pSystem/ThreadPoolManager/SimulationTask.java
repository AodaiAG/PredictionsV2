package pSystem.ThreadPoolManager;

import Requests.SimulationRequestExecuter.SimulationRequestExecuter;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import pSystem.engine.Engine;
import pSystem.engine.IEngine;
import pSystem.engine.SimulationResult;

import java.util.UUID;
import java.util.function.Consumer;

public class SimulationTask implements Runnable
{

    Engine engine;
    SimulationRequestExecuter simulationRequestExecuter;
    UUID executionId;


    public SimulationTask(Engine engine,SimulationRequestExecuter simulationRequestExecuter,UUID id)
    {
        this.engine = engine;
        this.simulationRequestExecuter= simulationRequestExecuter;
        executionId=id;
    }

    @Override
    public void run()
    {
        System.out.println("About to execute the simulation - in run ");
        engine.executeSimulation(simulationRequestExecuter, executionId);
    }







}