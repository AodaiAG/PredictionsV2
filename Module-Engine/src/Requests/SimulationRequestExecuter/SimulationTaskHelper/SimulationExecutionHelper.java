package Requests.SimulationRequestExecuter.SimulationTaskHelper;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SimulationExecutionHelper
{
   private EntityWrapper entityWrapper=new EntityWrapper();
   private Consumer<String> consumer;
   private SimulationConditions simulationConditions;
   private Map<String, List<Integer>> entityPopulationHistory = new HashMap<>();
    public Map<String, List<Integer>> getEntityPopulationHistory()
    {
        return entityPopulationHistory;
    }
    private String popAndTicks="Not started Yet";

    public void setEntityPopulationHistory(Map<String, List<Integer>> entityPopulationHistory) {
        this.entityPopulationHistory = entityPopulationHistory;
    }

    public EntityWrapper getEntityWrapper()
    {

        return entityWrapper;
    }

    public String getPopAndTicks()
    {
        return popAndTicks;
    }

    public void setPopAndTicks(String popAndTicks) {
        this.popAndTicks = popAndTicks;
    }

    public void setEntityWrapper(EntityWrapper entityWrapper) {
        this.entityWrapper = entityWrapper;
    }

    public Consumer<String> getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    public SimulationConditions getSimulationConditions() {
        return simulationConditions;
    }

    public void setSimulationConditions(SimulationConditions simulationConditions) {
        this.simulationConditions = simulationConditions;
    }
}
