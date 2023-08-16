package System;

import DTOS.EntityDTO;
import DTOS.WorldDTO;
import Entity.Entity;
import Rules.Rule;
import Entity.EntityInstance;
import Entity.Property;
import java.util.*;

public class Simulation
{
    private static boolean programRunning;
    private World worldRes;
    private WorldDTO oldWorldDTO;
    private final Map<String, Map<String, Integer>> propertyValueCounts = new HashMap<>(); //<entityName <property, instancesAmount>>

    private Map<String, Integer> initialQuantities = new HashMap<>();

    public Map<String, Map<String, Integer>> getPropertyValueCounts() {
        return propertyValueCounts;
    }

    public Map<String, Integer> getInitialQuantities() {
        return initialQuantities;
    }


    public Simulation(World world, WorldDTO worldDTO) {
        this.worldRes = world;
        oldWorldDTO = worldDTO;
        programRunning = true;
    }

    public void runSimulation() {
        Random random = new Random();
        double generatedProbability;
        generatedProbability = random.nextDouble();
        int ticksCounter = 0;
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                programRunning = false;
                System.out.println("Time's up");
            }
        };

        int ticksAmount = this.worldRes.getTerminationTicks();
        long delay =(long) this.worldRes.getTerminationSeconds() * 1000; // Delay in milliseconds (5 seconds)
        timer.schedule(task, delay);

        while (ticksCounter < ticksAmount && programRunning) {
            for (Rule rule : this.worldRes.getRules()) { //is it start over?
                rule.isActivated(worldRes.getEntities(), ticksCounter, generatedProbability);
                generatedProbability = random.nextDouble();
            }
            ticksCounter++;
        }
        timer.cancel(); // Cancel the timer when simulation is done
    }

    public Map<String, Integer> initPropertyHistogramAndReturnValueCounts(Entity entity,String propertyName) //<nameOfProperty, map<valueOfProperty, amountOfInstancesWithThisValue>
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
        for (EntityDTO entityDTO : this.oldWorldDTO.getEntityDTOSet()) {
            initialQuantities.put(entityDTO.getName(), entityDTO.getNumberOfInstances());
        }
    }

    public void showEntitiesAmount()
    {
        initQuantities();

    }
}