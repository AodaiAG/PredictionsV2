package Requests.SimulationRequestExecuter;

import Requests.SimulationRequestExecuter.SimulationTaskHelper.SimulationExecutionHelper;
import pSystem.engine.SimulationResult;
import pSystem.engine.World;

import java.util.UUID;

public class SimulationReadyForExecution
{
  private UUID ExecutionId;
  private  World world;
  private SimulationExecutionHelper simulationExecutionHelper=new SimulationExecutionHelper();
  private Boolean isExecutionFinshed=false;

  private SimulationResult simulationResult=null;


    public UUID getExecutionId()
    {
        return ExecutionId;
    }

    public void setExecutionId(UUID executionId)
    {
        ExecutionId = executionId;
    }

    public World getWorld() {
        return world;
    }

    public SimulationResult getSimulationResult() {
        return simulationResult;
    }

    public void setSimulationResult(SimulationResult simulationResult) {
        this.simulationResult = simulationResult;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public SimulationExecutionHelper getSimulationExecutionHelper() {
        return simulationExecutionHelper;
    }

    public void setSimulationExecutionHelper(SimulationExecutionHelper simulationExecutionHelper) {
        this.simulationExecutionHelper = simulationExecutionHelper;
    }

    public Boolean getExecutionFinshed() {
        return isExecutionFinshed;
    }

    public void setExecutionFinshed(Boolean executionFinshed) {
        isExecutionFinshed = executionFinshed;
    }
}
