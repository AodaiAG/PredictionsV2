package pSystem.engine;

import Grid.EntityInstancesCircularGrid;
import pEntity.Coordinate;
import pEntity.EntityInstance;
import pEnvironment.EnvironmentInstance;
import pRules.Rule;
import pEntity.Entity;

import java.lang.reflect.Field;
import java.util.*;

public class World implements Cloneable
{
    private int terminationTicks;

    private int terminationSeconds;

    private boolean terminationByUser;

    public int ticksCounter;

    private int secondMeasurement;

    private List<Entity> entities;

    private Map<String, EnvironmentInstance> name2Env;

    private List<Rule> rules;

   private EntityInstancesCircularGrid grid;

    public int getCurrentPopulationAmount() {
        return currentPopulationAmount;
    }

    public void setCurrentPopulationAmount(int currentPopulationAmount) {
        this.currentPopulationAmount = currentPopulationAmount;
    }

    private int currentPopulationAmount = 0;

    public World() {
        name2Env = new HashMap<>();
        rules = new ArrayList<>();
        entities = new ArrayList<Entity>();
        grid = new EntityInstancesCircularGrid();
    }

    // Implement clone method to create a deep copy of the World
    @Override
    public World clone() {
        try {
            World clonedWorld = (World) super.clone();

            // Clone primitive fields
            clonedWorld.terminationTicks = this.terminationTicks;
            clonedWorld.terminationSeconds = this.terminationSeconds;
            clonedWorld.terminationByUser = this.terminationByUser;
            clonedWorld.ticksCounter = this.ticksCounter;
            clonedWorld.secondMeasurement = this.secondMeasurement;

            // Clone the entities list
            List<Entity> clonedEntities = new ArrayList<>();
            for (Entity entity : this.entities) {
                Entity clonedEntity = (Entity) entity.clone(); // Make sure Entity class also implements Cloneable
                clonedEntities.add(clonedEntity);
            }
            clonedWorld.entities = clonedEntities;

            // Clone the rules list
            List<Rule> clonedRules = new ArrayList<>(this.rules);
            clonedWorld.rules = clonedRules;

            // Clone the name2Env map (deep copy of EnvironmentInstances not shown here)
            Map<String, EnvironmentInstance> clonedName2Env = new HashMap<>();

            for (Map.Entry<String, EnvironmentInstance> entry : this.name2Env.entrySet()) {
                String key = entry.getKey();
                EnvironmentInstance originalInstance = entry.getValue();

                // Perform a deep copy of the EnvironmentInstance
                EnvironmentInstance clonedInstance = originalInstance.clone();

                // Add the cloned instance to the new map
                clonedName2Env.put(key, clonedInstance);
            }

            clonedWorld.name2Env = clonedName2Env;


            // Clone the EntityInstancesCircularGrid (if it's cloneable)
            clonedWorld.grid = (EntityInstancesCircularGrid) this.grid.clone(); // Make sure EntityInstancesCircularGrid class implements Cloneable

            return clonedWorld;
        } catch (CloneNotSupportedException e)
        {
            throw new AssertionError(e);
        }
    }

    public boolean isTerminationByUser() {
        return terminationByUser;
    }

    public EntityInstancesCircularGrid getGrid()
    {
        return grid;
    }

    public void setTerminationByUser(boolean terminationByUser) {
        this.terminationByUser = terminationByUser;
    }

    public Map<String, EnvironmentInstance> getName2Env() {
        return name2Env;
    }

    public void setName2Env(Map<String, EnvironmentInstance> name2Env) {
        this.name2Env = name2Env;
    }

    public int getTerminationTicks() {
        return terminationTicks;
    }

    public void setTerminationTicks(int terminationTicks) {
        this.terminationTicks = terminationTicks;
    }

    public int getTerminationSeconds() {
        return terminationSeconds;
    }

    public void setTerminationSeconds(int terminationSeconds) {
        this.terminationSeconds = terminationSeconds;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public String random(String arg) {
        try {
            int maxRange = Integer.parseInt(arg);
            if (maxRange < 0) {
                throw new IllegalArgumentException("Argument must be a non-negative integer");
            }

            Random random = new Random();
            int randomValue = random.nextInt(maxRange + 1);
            return String.valueOf(randomValue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Argument must be a numeric value");
        }
    }

    public void moveAllInstances()
    {
        try
        {
            for (Entity entity : entities)

            {
                for (EntityInstance entityInstance : entity.getEntities()) {
                    grid.generateMove(entityInstance);
                }
            }
        }
        catch (Exception e)
        {

        }
    }

    public void removeKilledInstances(Entity entity)
    {
        boolean hasRemoved = entity.getEntities().removeIf(EntityInstance::getTobeKilled);
    }

    public static String getTypeOfEntity(EntityInstance e)
    {
        Field resField;
        try
        {
            resField = e.getClass().getField("Type");
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
        String typeOfField;
        typeOfField = resField.getType().getSimpleName();
        return typeOfField;
    }

    protected void initCoordinates()
    {
       try
       {
           for (Entity entity : entities)
           {
               for (EntityInstance instance : entity.getEntities())
               {
                   Coordinate emptyCoordinate = grid.getRandomEmptyCoordinate();
                   instance.setCoordinate(emptyCoordinate);
                   grid.setEntityInstanceInCell(instance, emptyCoordinate);
               }
           }
       }

       catch (Exception e)
       {

       }
    }
}