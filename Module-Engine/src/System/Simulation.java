package System;

import DTOS.EntityDTO;
import DTOS.WorldDTO;
import Entity.Entity;
import Entity.EntityInstance;
import Entity.Property;
import java.util.*;

public class Simulation
{
    private static boolean programRunning;
    private WorldDTO wordAfterSimulation;
    private WorldDTO wordBeforeSimulation;
    private final Map<String, Map<String, Integer>> propertyValueCounts = new HashMap<>(); //<entityName <property, instancesAmount>>

    private Map<String, Integer> initialQuantities = new HashMap<>();

    public Map<String, Map<String, Integer>> getPropertyValueCounts() {
        return propertyValueCounts;
    }

    public Map<String, Integer> getInitialQuantities() {
        return initialQuantities;
    }


    public Simulation(WorldDTO worldBefore, WorldDTO worldAfter)
    {
        this.wordAfterSimulation = worldAfter;
        wordBeforeSimulation = worldBefore;
        programRunning = true;
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
        for (EntityInstance instance:entity.getEntities()) {
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

    public void showEntitiesAmount()
    {
        initQuantities();

    }
}