package Requests.SimulationRequestExecuter;

import Requests.SimulationRequestDetails;
import pSystem.engine.SimulationResult;
import pSystem.engine.aSimulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SimulationRequestExecuter
{
    private UUID requestID;
    private aSimulation originalSimulationToBeExecuted;
    private aSimulation currSimulation;

    private List<UUID> simulationResultUUID=new ArrayList<>();

    public SimulationRequestExecuter(UUID requestID, aSimulation simulationToBeExecuted)
    {
        this.requestID=requestID;
        this.originalSimulationToBeExecuted=simulationToBeExecuted;
        currSimulation=simulationToBeExecuted.clone();
    }
    public SimulationRequestExecuter()
    {

    }

    public aSimulation getCurrSimulation() {
        return currSimulation;
    }

    public void setCurrSimulation(aSimulation currSimulation) {
        this.currSimulation = currSimulation;
    }

    public aSimulation getSimulationToBeExecuted()
    {
        return originalSimulationToBeExecuted;
    }

    public void setSimulationToBeExecuted(aSimulation simulationToBeExecuted) {
        this.originalSimulationToBeExecuted = simulationToBeExecuted;
    }

    public UUID getRequestID()
    {
        return requestID;
    }


    public void setRequestID(UUID requestID)
    {
        this.requestID = requestID;
    }




    public List<UUID> getSimulationResultUUID()
    {
        return simulationResultUUID;
    }

    public void setSimulationResultUUID(List<UUID> simulationResultUUID)
    {
        this.simulationResultUUID = simulationResultUUID;
    }
}
