package Requests;

import pDTOS.TerminationDTO;

import java.util.Objects;
import java.util.UUID;

public class SimulationRequestDetails
{
    private UUID id;
    private int executionsRunningAmount=0;
    private int executionsFinishedAmount=0;
    private int executionsLeftAmount;
    private String simulationName;
    private int numOfExecutions;
    private TerminationDTO terminationConditions;
    private String requestStatus = "unhandled";
    private String userName;

    public int getRequestCounter() {
        return requestCounter;
    }

    public void setRequestCounter(int requestCounter) {
        this.requestCounter = requestCounter;
    }

    private int requestCounter;

    public SimulationRequestDetails(UUID id, String simulationName, int numOfExecutions, TerminationDTO terminationConditions, String userName)
    {
        this.id = id;
        this.simulationName = simulationName;
        this.numOfExecutions = numOfExecutions;
        this.terminationConditions = terminationConditions;
        this.userName = userName;
    }


    public String getExecutionsLeftAmount()
    {
        return String.valueOf(numOfExecutions-executionsFinishedAmount);
    }

    public void setExecutionsLeftAmount(String executionsLeftAmount)
    {
        this.executionsLeftAmount = Integer.parseInt(executionsLeftAmount);
    }

    public String getExecutionsRunningAmount()
    {
        return String.valueOf(executionsRunningAmount);
    }

    public String executionsRunningAmountProperty()
    {
        return String.valueOf(executionsRunningAmount);
    }

    public void setExecutionsRunningAmount(String executionsRunningAmount)
    {
        this.executionsRunningAmount= Integer.parseInt(executionsRunningAmount);
    }


    public String getExecutionsFinishedAmount() {
        return String.valueOf(executionsFinishedAmount);
    }

    public String executionsFinishedAmountProperty() {
        return String.valueOf(executionsFinishedAmount);
    }

    public void setExecutionsFinishedAmount(String executionsFinishedAmount)
    {
        this.executionsFinishedAmount= Integer.parseInt(executionsFinishedAmount);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationRequestDetails that = (SimulationRequestDetails) o;
        return numOfExecutions == that.numOfExecutions &&
                Objects.equals(id, that.id) &&
                Objects.equals(executionsRunningAmount, that.executionsRunningAmount) &&
                Objects.equals(executionsFinishedAmount, that.executionsFinishedAmount) &&
                Objects.equals(simulationName, that.simulationName) &&
                Objects.equals(terminationConditions, that.terminationConditions) &&
                Objects.equals(requestStatus, that.requestStatus) &&
                Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, executionsRunningAmount, executionsFinishedAmount, simulationName, numOfExecutions, terminationConditions, requestStatus, userName);
    }

    public TerminationDTO getTerminationConditions() {
        return terminationConditions;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequestStatus()
    {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus)
    {
        this.requestStatus=requestStatus;
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
