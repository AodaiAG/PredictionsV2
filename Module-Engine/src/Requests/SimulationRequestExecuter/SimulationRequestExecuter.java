package Requests.SimulationRequestExecuter;

import Requests.SimulationRequestExecuter.SimulationTaskHelper.SimulationExecutionHelper;
import pSystem.engine.aSimulation;

import java.util.*;

public class SimulationRequestExecuter
{
    private String userName;
    private UUID requestID;
    private aSimulation originalSimulationToBeExecuted;
    private aSimulation currSimulation;
    private SimulationExecutionHelper simulationExecutionHelper;
    private Map<UUID,SimulationReadyForExecution> uuidSimulationReadyForExecutionMap=new HashMap<>();
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

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<UUID, SimulationReadyForExecution> getUuidSimulationReadyForExecutionMap()
    {
        return uuidSimulationReadyForExecutionMap;
    }

    public void setUuidSimulationReadyForExecutionMap(Map<UUID, SimulationReadyForExecution> uuidSimulationReadyForExecutionMap)
    {
        this.uuidSimulationReadyForExecutionMap = uuidSimulationReadyForExecutionMap;
    }

    public aSimulation getOriginalSimulationToBeExecuted() {
        return originalSimulationToBeExecuted;
    }

    public void setOriginalSimulationToBeExecuted(aSimulation originalSimulationToBeExecuted) {
        this.originalSimulationToBeExecuted = originalSimulationToBeExecuted;
    }

    public SimulationExecutionHelper getAndInitSimulationExecutionHelper()
    {
        this.simulationExecutionHelper=new SimulationExecutionHelper();
        return simulationExecutionHelper;
    }

    public SimulationExecutionHelper getSimulationExecutionHelper() {
        return simulationExecutionHelper;
    }

    public void setSimulationExecutionHelper(SimulationExecutionHelper simulationExecutionHelper) {
        this.simulationExecutionHelper = simulationExecutionHelper;
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
