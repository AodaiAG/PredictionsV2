package pSystem.ThreadPoolManager;

import Requests.SimulationRequestDetails;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolManager
{
    private ExecutorService threadPool=Executors.newFixedThreadPool(1);
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
        int activeThreads = getActiveThreadCount();
        int availableThreads = getQueueSize();

        // Calculate system load as a number from 1 to 10
        if (availableThreads > 0)
        {
            double loadRatio = (double) activeThreads / (activeThreads + availableThreads);
            int loadScale = (int) (loadRatio * 9) + 1;
            return loadScale;
        } else
        {
            // Handle the case where no threads are available
            return 10; // Maximum load (10)
        }
    }


    public void increaseThreadCount(int additionalThreads)
    {
        int currentThreads = ((ThreadPoolExecutor) threadPool).getMaximumPoolSize();
        int newThreadCount = currentThreads + additionalThreads;

        ThreadPoolExecutor newThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(newThreadCount);

        threadPool.shutdown();
        threadPool = newThreadPool;
    }

    public void decreaseThreadCount(int reductionThreads)
    {
        int currentThreads = ((ThreadPoolExecutor) threadPool).getMaximumPoolSize();
        int newThreadCount = currentThreads - reductionThreads;

        ThreadPoolExecutor newThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(newThreadCount);

        threadPool.shutdown();
        threadPool = newThreadPool;
    }

    public int getActiveThreadCount()
    {
        return ((ThreadPoolExecutor) threadPool).getActiveCount();
    }

    public int getQueueSize()
    {
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) threadPool;
        int poolSize = poolExecutor.getPoolSize();
        int corePoolSize = poolExecutor.getCorePoolSize();
        return corePoolSize - poolSize;
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
