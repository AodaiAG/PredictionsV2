package pSystem;

import pDTOS.EntityDTO;
import pDTOS.WorldDTO;
import pEntity.Entity;
import pEntity.EntityInstance;
import pEntity.Property;

import java.util.*;

public class Simulation
{
    private static boolean programRunning;
    private WorldDTO wordAfterSimulation;
    private WorldDTO wordBeforeSimulation;
    private final Map<String, Map<String, Integer>> propertyValueCounts = new HashMap<>(); //<entityName <property, instancesAmount>>
    Date runningDate;

    public String getReasonForTermination() {
        return reasonForTermination;
    }

    public void setReasonForTermination(String reasonForTermination) {
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

    public Simulation(WorldDTO worldBefore, WorldDTO worldAfter) {
        this.wordAfterSimulation = worldAfter;
        wordBeforeSimulation = worldBefore;
        programRunning = true;
    }

    public Map<String, Map<String, Integer>> getPropertyValueCounts() {
        return propertyValueCounts;
    }

    public Map<String, Integer> getInitialQuantities() {
        return initialQuantities;
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

    public void initQuantities() {
        for (EntityDTO entityDTO : this.wordBeforeSimulation.getEntityDTOSet()) {
            initialQuantities.put(entityDTO.getName(), entityDTO.getNumberOfInstances());
        }
    }

    public void showEntitiesAmount() {
        initQuantities();
    }
}