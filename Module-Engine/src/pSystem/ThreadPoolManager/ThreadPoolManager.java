package pSystem.ThreadPoolManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager
{
    private ExecutorService threadPool;

    void initThreadPool(int numThreads )
    {
        threadPool = Executors.newFixedThreadPool(numThreads);
    }
    void submitThreadTask(SimulationTask simulationTask)
    {
        threadPool.submit(simulationTask);
    }

}
