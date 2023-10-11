package pSystem.ThreadPoolManager;

import Requests.SimulationRequestDetails;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager
{
    private ExecutorService threadPool=Executors.newFixedThreadPool(1);;
    int waitingSimulations=0;
    int executingSimulations=0;
    int completedSimulations=0;

    public void initThreadPool(int numThreads )
    {
        threadPool = Executors.newFixedThreadPool(numThreads);
    }
    public void submitThreadTask(SimulationTask simulationTask)
    {
        try
        {
            SimulationRequestDetails simulationRequestDetails= simulationTask.engine.getRequestManager().getRequestFromId(simulationTask.simulationRequestExecuter.getRequestID());

            waitingSimulations++;
            threadPool.submit(() ->
            {
                // When the task starts, update the counts
                waitingSimulations--;
                executingSimulations++;
                simulationRequestDetails.increaseExecutingAmount();
                simulationTask.run();
                // After the task is done, update the counts
                simulationRequestDetails.decreaseExecutingAmount();
                executingSimulations--;
                completedSimulations++;
                simulationRequestDetails.increaseFinishedAmount();
                simulationRequestDetails.decreaseLeftAmount();


            });


        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public int getWaitingSimulations()
    {
        return waitingSimulations;
    }

    public int getExecutingSimulations()
    {
        return executingSimulations;
    }

    public int getCompletedSimulations()
    {
        return completedSimulations;
    }
}
