package application.controllers;

public class SimulationConditions
{
    private  Boolean simulationRunning = true; // Add this flag
    private  Boolean pauseSimulation = false;

    public Boolean getSimulationRunning()
    {
        return simulationRunning;
    }

    public synchronized void setSimulationRunning(Boolean simulationRunning)
    {
        this.simulationRunning = simulationRunning;
    }

    public Boolean getPauseSimulation()
    {
        return pauseSimulation;
    }

    public synchronized void setPauseSimulation(Boolean pauseSimulation)
    {
        this.pauseSimulation = pauseSimulation;
    }

}
