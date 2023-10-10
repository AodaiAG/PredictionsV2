package pSystem.ThreadPoolManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager
{
    private ExecutorService threadPool=Executors.newFixedThreadPool(1);;


    public void initThreadPool(int numThreads )
    {
        threadPool = Executors.newFixedThreadPool(numThreads);
    }
    public void submitThreadTask(SimulationTask simulationTask)
    {

        threadPool.submit(simulationTask);
    }

}
