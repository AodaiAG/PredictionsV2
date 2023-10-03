package Requests;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pDTOS.TerminationDTO;

import java.util.Objects;
import java.util.UUID;

public class SimulationRequest
{
    private UUID id;
    private String executionsRunningAmount;
    private String executionsFinishedAmount;
    private String simulationName;
    private int numOfExecutions;
    private TerminationDTO terminationConditions;
    private SimpleStringProperty requestStatus = new SimpleStringProperty("unhandled");
    private String userName;

    public String getExecutionsRunningAmount()
    {
        return executionsRunningAmount;

    }

    public String executionsRunningAmountProperty()
    {
        return executionsRunningAmount;
    }

    public void setExecutionsRunningAmount(String executionsRunningAmount)
    {
        this.executionsRunningAmount=executionsRunningAmount;
    }

    public String getExecutionsFinishedAmount() {
        return executionsFinishedAmount;
    }

    public String executionsFinishedAmountProperty() {
        return executionsFinishedAmount;
    }

    public void setExecutionsFinishedAmount(String executionsFinishedAmount)
    {
        this.executionsFinishedAmount=executionsFinishedAmount;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationRequest that = (SimulationRequest) o;
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


    public SimulationRequest(UUID id, String simulationName, int numOfExecutions, TerminationDTO terminationConditions, String userName)
    {
        this.id = id;
        this.simulationName = simulationName;
        this.numOfExecutions = numOfExecutions;
        this.terminationConditions = terminationConditions;
        this.userName = userName;
    }


    public String getRequestStatus()
    {
        return requestStatus.getValue();
    }

    public void setRequestStatus(String requestStatus)
    {
        this.requestStatus.setValue(requestStatus);
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
