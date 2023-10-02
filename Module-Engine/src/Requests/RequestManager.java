package Requests;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestManager
{
    private final Map<String,Set<SimulationRequest>> userRequestMap;

    public RequestManager()
    {
        this.userRequestMap = new HashMap<>();

    }
    public synchronized void addRequest(String username, SimulationRequest simulationRequest)
    {
        userRequestMap.get(username).add(simulationRequest);
    }

    public synchronized void removeRequest(String username, SimulationRequest simulationRequest)
    {
        userRequestMap.get(username).remove(simulationRequest);
    }

    public synchronized Set<SimulationRequest> getRequests()
    {
        return Collections.unmodifiableSet((Set<? extends SimulationRequest>) userRequestMap.values());
    }

    public boolean isRequestExists(String username, SimulationRequest simulationRequest)
    {
       return userRequestMap.get(username).contains(simulationRequest);
    }
}
