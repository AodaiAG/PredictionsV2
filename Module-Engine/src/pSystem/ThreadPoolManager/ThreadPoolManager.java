package pSystem.ThreadPoolManager;

import Requests.SimulationRequestDetails;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
    public int calculateSystemLoad()
    {
        int queueSize = getQueueSize();
        int activeThreads = getActiveThreadCount();
        // Calculate a load score based on queue size and active threads
        int loadScore = Math.min(queueSize + activeThreads, 10); // Adjust based on your system
        // Normalize the load score to the range 1-10
        return (loadScore * 10) / Math.max(1, Math.min(queueSize + activeThreads, 10));
    }

    // ...

    // Replace these methods with your actual metric retrieval logic

    public void increaseThreadCount(int additionalThreads)
    {
        int currentThreads = ((ThreadPoolExecutor) threadPool).getMaximumPoolSize();
        ((ThreadPoolExecutor) threadPool).setMaximumPoolSize(currentThreads + additionalThreads);
    }

    public void decreaseThreadCount(int reductionThreads)
    {
        int currentThreads = ((ThreadPoolExecutor) threadPool).getMaximumPoolSize();
        int newThreadCount = Math.max(1, currentThreads - reductionThreads); // Ensure there's at least 1 thread
        ((ThreadPoolExecutor) threadPool).setMaximumPoolSize(newThreadCount);
    }
    public int getActiveThreadCount()
    {
        return ((ThreadPoolExecutor) threadPool).getActiveCount();
    }

    public int getQueueSize()
    {
        return ((ThreadPoolExecutor) threadPool).getQueue().size();
    }

    public int getCompletedTaskCount()
    {
        return (int) ((ThreadPoolExecutor) threadPool).getCompletedTaskCount();
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
