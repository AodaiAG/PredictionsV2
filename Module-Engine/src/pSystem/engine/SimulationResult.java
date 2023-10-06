package pSystem.engine;

import pDTOS.EntityDTO;
import pDTOS.WorldDTO;
import pEntity.Entity;
import pEntity.EntityInstance;
import pEntity.Property;

import java.util.*;

public class SimulationResult
{
    private static boolean programRunning;
    private WorldDTO wordAfterSimulation;
    private WorldDTO wordBeforeSimulation;
    private World worldTobeExecuted;
    private UUID simulationId;
    Map<String, List<Integer>> entityPopulationHistory = new HashMap<>();

    public World getWorldTobeExecuted()
    {
        return worldTobeExecuted;
    }

    public void setWorldTobeExecuted(World worldTobeExecuted)
    {
        this.worldTobeExecuted = worldTobeExecuted;
    }

    private final Map<String, Map<String, Integer>> propertyValueCounts = new HashMap<>(); //<entityName <property, instancesAmount>>
    Date runningDate;
    public boolean isCurrentlyRunning;

    public Map<String, List<Integer>> getEntityPopulationHistory()
    {
        return entityPopulationHistory;
    }

    public void setEntityPopulationHistory(Map<String, List<Integer>> entityPopulationHistory) {
        this.entityPopulationHistory = entityPopulationHistory;
    }

    public String getReasonForTermination() {
        return reasonForTermination;
    }

    public void setReasonForTermination(String reasonForTermination)
    {
        this.reasonForTermination = reasonForTermination;
    }

    public void setRunningDate(Date runningDate) {
        this.runningDate = runningDate;
    }

    public Date getRunningDate() {
        return runningDate;
    }

    private String reasonForTermination;

    private final Map<String, Integer> initialQuantities = new HashMap<>();

    public SimulationResult(WorldDTO worldBefore, WorldDTO worldAfter, UUID simulationId)
    {
        this.wordAfterSimulation = worldAfter;
        wordBeforeSimulation = worldBefore;
        programRunning = true;
        this.simulationId=simulationId;
    }

    public String getSimulationId()
    {
        return simulationId.toString();
    }

    public Map<String, Map<String, Integer>> getPropertyValueCounts() {
        return propertyValueCounts;
    }

    public Map<String, Integer> getInitialQuantities() {
        return initialQuantities;
    }

    public int getNumberOfEntityInstancesBefore(EntityDTO entityDTO)
    {
        for(EntityDTO entityDTO1:this.wordBeforeSimulation.getEntityDTOSet())
        {
            if(entityDTO1.getName().equals(entityDTO.getName()))
            {
                return entityDTO1.getInstancesDTOS().size();
            }
        }
        return 0;
    }
    public int getNumberOfEntityInstancesAfter(EntityDTO entityDTO)
    {

        for(EntityDTO entityDTO1:this.wordAfterSimulation.getEntityDTOSet())
        {
            if(entityDTO1.getName().equals(entityDTO.getName()))
            {
                return entityDTO1.getInstancesDTOS().size();
            }
        }
        return 0;
    }

    public WorldDTO getWordAfterSimulation() {
        return wordAfterSimulation;
    }

    public void setWordAfterSimulation(WorldDTO wordAfterSimulation) {
        this.wordAfterSimulation = wordAfterSimulation;
    }

    public WorldDTO getWordBeforeSimulation() {
        return wordBeforeSimulation;
    }

    public void setWordBeforeSimulation(WorldDTO wordBeforeSimulation) {
        this.wordBeforeSimulation = wordBeforeSimulation;
    }

    public Map<String, Integer> initPropertyHistogramAndReturnValueCounts(Entity entity, String propertyName) //<nameOfProperty, map<valueOfProperty, amountOfInstancesWithThisValue>
    {
        for (EntityInstance instance : entity.getEntities()) {
            for (Property property : instance.getPropertiesOfTheEntity()) {
                String localPropertyName = property.getNameOfProperty();
                String localPropertyValue = property.getData().getDataString();

                propertyValueCounts.putIfAbsent(localPropertyName, new HashMap<>());
                Map<String, Integer> valueCounts = propertyValueCounts.get(localPropertyName);
                valueCounts.put(localPropertyValue, valueCounts.getOrDefault(localPropertyValue, 0) + 1);
            }
        }
        return propertyValueCounts.get(propertyName);
    }

    public void initQuantities()
    {
        for (EntityDTO entityDTO : this.wordBeforeSimulation.getEntityDTOSet()) {
            initialQuantities.put(entityDTO.getName(), entityDTO.getNumberOfInstances());
        }
    }

    public void showEntitiesAmount() {
        initQuantities();
    }
}