package Requests;

import java.util.*;

public class RequestManager
{
    private final Map<String,Set<SimulationRequest>> userRequestMap;

    public RequestManager()
    {
        this.userRequestMap = new HashMap<>();

    }
    public synchronized void addRequest(String username, SimulationRequest simulationRequest)
    {
        if (userRequestMap.containsKey(username))
        {
            // Username exists in the map, retrieve the set and add the request
            Set<SimulationRequest> existingRequests = userRequestMap.get(username);
            existingRequests.add(simulationRequest);
        } else
        {
            // Username doesn't exist in the map, create a new set, add the request, and put it into the map
            Set<SimulationRequest> newRequests = new HashSet<>();
            newRequests.add(simulationRequest);
            userRequestMap.put(username, newRequests);
        }
    }

    public synchronized void removeRequest(String username, SimulationRequest simulationRequest)
    {
        userRequestMap.get(username).remove(simulationRequest);
    }

    public synchronized Set<SimulationRequest> getRequests()
    {

        Set<SimulationRequest> allRequests = new HashSet<>();
        for (Set<SimulationRequest> requestSet : userRequestMap.values())
        {
            allRequests.addAll(requestSet);
        }

        return Collections.unmodifiableSet(allRequests);

    }

    public synchronized Set<SimulationRequest> getUserRequests(String userName)
    {
        return Collections.unmodifiableSet(this.userRequestMap.get(userName));
    }

    public boolean isRequestExists(String username, SimulationRequest simulationRequest)
    {
       return userRequestMap.get(username).contains(simulationRequest);
    }

}
