package pSystem.ThreadPoolManager;

import Requests.SimulationRequestExecuter.SimulationRequestExecuter;
import pSystem.engine.Engine;
import java.util.UUID;

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