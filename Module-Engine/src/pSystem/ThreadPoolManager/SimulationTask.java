package pSystem.ThreadPoolManager;

import Requests.SimulationRequestExecuter.SimulationRequestExecuter;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import pSystem.engine.Engine;
import pSystem.engine.IEngine;
import pSystem.engine.SimulationResult;

import java.util.UUID;
import java.util.function.Consumer;

public class SimulationTask extends Task<Void>
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



    //
    @Override
    protected Void call() throws Exception
    {

        engine.executeSimulation(simulationRequestExecuter,executionId);
        return null;
    }




}