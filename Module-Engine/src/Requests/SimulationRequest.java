package Requests;

import pDTOS.TerminationDTO;

import java.util.UUID;

public class SimulationRequest
{
    UUID id;
    private String simulationName;
    private int numOfExecutions;
    private TerminationDTO terminationConditions;


    public SimulationRequest(UUID id, String simulationName, int numOfExecutions, TerminationDTO terminationConditions)
    {
        this.id = id;
        this.simulationName = simulationName;
        this.numOfExecutions = numOfExecutions;
        this.terminationConditions = terminationConditions;
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
