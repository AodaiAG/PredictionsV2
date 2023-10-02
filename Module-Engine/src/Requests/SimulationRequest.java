package Requests;

import javafx.beans.property.StringProperty;
import pDTOS.TerminationDTO;

import java.util.UUID;

public class SimulationRequest
{
    private UUID id;
    private String simulationName;
    private int numOfExecutions;
    private String requestStatus="Unhandled";
    private TerminationDTO terminationConditions;


    public SimulationRequest(UUID id, String simulationName, int numOfExecutions, TerminationDTO terminationConditions)
    {
        this.id = id;
        this.simulationName = simulationName;
        this.numOfExecutions = numOfExecutions;
        this.terminationConditions = terminationConditions;
    }


    public String getRequestStatus()
    {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus)
    {
        this.requestStatus = requestStatus;
    }

    public UUID getId()
    {
        return id;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public int getNumOfExecutions()
    {
        return numOfExecutions;
    }
}
