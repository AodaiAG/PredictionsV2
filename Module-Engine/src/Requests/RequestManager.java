package Requests;

import java.util.*;

public class RequestManager
{
    private final Map<String,List<SimulationRequest>> userRequestMap;

    public RequestManager()
    {
        this.userRequestMap =new HashMap<>();

    }
    public synchronized void addRequest(String username, SimulationRequest simulationRequest)
    {
        if (userRequestMap.containsKey(username))
        {
            // Username exists in the map, retrieve the set and add the request
            List<SimulationRequest> existingRequests = userRequestMap.get(username);
            existingRequests.add(simulationRequest);
        }
        else
        {
            // Username doesn't exist in the map, create a new set, add the request, and put it into the map
            List<SimulationRequest> newRequests = new ArrayList<>();
            newRequests.add(simulationRequest);
            userRequestMap.put(username, newRequests);
        }
    }

    public synchronized void removeRequest(String username, SimulationRequest simulationRequest)
    {
        userRequestMap.get(username).remove(simulationRequest);
    }

    public synchronized List<SimulationRequest> getRequests()
    {

        List<SimulationRequest> allRequests = new ArrayList<>();
        for (List<SimulationRequest> requestSet : userRequestMap.values())
        {
            allRequests.addAll(requestSet);
        }

        return allRequests;

    }
    public synchronized SimulationRequest getRequestUserTwoUUID(String userName,UUID uuid)
    {

        List<SimulationRequest> userRequests=this.getUserRequests(userName);
        for(SimulationRequest simulationRequest:userRequests)
        {
           if( simulationRequest.getId().equals(uuid))
           {
               return simulationRequest;
           }
        }

        return null;
    }

    public synchronized List<SimulationRequest> getUserRequests(String userName)
    {
        List<SimulationRequest> set = this.userRequestMap.get(userName);
        return set!= null ? set: null;
    }

    public boolean isRequestExists(String username, SimulationRequest simulationRequest)
    {
       return userRequestMap.get(username).contains(simulationRequest);
    }

}
