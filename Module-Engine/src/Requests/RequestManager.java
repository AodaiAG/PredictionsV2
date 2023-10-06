package Requests;

import java.util.*;

public class RequestManager
{
    private  int requestsCounter = 1;
    private final Map<String,List<SimulationRequestDetails>> userRequestMap;

    public RequestManager()
    {
        this.userRequestMap =new HashMap<>();
    }

    public synchronized void addRequest(String username, SimulationRequestDetails simulationRequestDetails)
    {
        simulationRequestDetails.setRequestCounter(requestsCounter);
        if (userRequestMap.containsKey(username))
        {
            // Username exists in the map, retrieve the set and add the request
            List<SimulationRequestDetails> existingRequests = userRequestMap.get(username);
            existingRequests.add(simulationRequestDetails);
        }
        else
        {
            // Username doesn't exist in the map, create a new set, add the request, and put it into the map
            List<SimulationRequestDetails> newRequests = new ArrayList<>();
            newRequests.add(simulationRequestDetails);
            userRequestMap.put(username, newRequests);
        }
        requestsCounter++;
    }

    public synchronized void removeRequest(String username, SimulationRequestDetails simulationRequestDetails)
    {
        userRequestMap.get(username).remove(simulationRequestDetails);
    }

    public synchronized List<SimulationRequestDetails> getRequests()
    {

        List<SimulationRequestDetails> allRequests = new ArrayList<>();
        for (List<SimulationRequestDetails> requestSet : userRequestMap.values())
        {
            allRequests.addAll(requestSet);
        }

        allRequests.sort(Comparator.comparingInt(SimulationRequestDetails::getRequestCounter));
        return allRequests;

    }

    public synchronized SimulationRequestDetails getRequestUserTwoUUID(String userName, UUID uuid)
    {

        List<SimulationRequestDetails> userRequests=this.getUserRequests(userName);
        for(SimulationRequestDetails simulationRequestDetails :userRequests)
        {
           if( simulationRequestDetails.getId().equals(uuid))
           {
               return simulationRequestDetails;
           }
        }

        return null;
    }

    public synchronized List<SimulationRequestDetails> getUserRequests(String userName)
    {
        List<SimulationRequestDetails> set = this.userRequestMap.get(userName);
        return set!= null ? set: null;
    }

    public boolean isRequestExists(String username, SimulationRequestDetails simulationRequestDetails)
    {
       return userRequestMap.get(username).contains(simulationRequestDetails);
    }

}
