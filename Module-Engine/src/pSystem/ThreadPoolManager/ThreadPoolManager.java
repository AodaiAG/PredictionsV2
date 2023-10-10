package pSystem.ThreadPoolManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager
{
    private ExecutorService threadPool=Executors.newFixedThreadPool(8);;


    public void initThreadPool(int numThreads )
    {
        threadPool = Executors.newFixedThreadPool(numThreads);
    }
    public void submitThreadTask(SimulationTask simulationTask)
    {
        try
        {
            System.out.println("is threadPool Shut down : "+ threadPool.isShutdown());
            System.out.println("About to be submitted to thread pool");
            threadPool.submit(simulationTask);
            System.out.println("Task submitted");
        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

}
