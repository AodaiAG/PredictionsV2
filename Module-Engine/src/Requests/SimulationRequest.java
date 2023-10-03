package Requests;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pDTOS.TerminationDTO;

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
