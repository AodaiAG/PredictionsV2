package System;

import Entity.EntityInstance;
import Environment.EnvironmentInstance;
import Rules.Rule;
import Entity.Entity;

import java.lang.reflect.Field;
import java.util.*;

public class World implements IWorld
{
    private int terminationTicks;
    private int terminationSeconds;
    private int ticksCounter;
    private int secondMeasurment;
    private List<Entity> entities;
    private Map<String,EnvironmentInstance> name2Env;
    private Set<Rule> rules;

    public Map<String, EnvironmentInstance> getName2Env()
    {
        return name2Env;
    }

    public void setName2Env(Map<String, EnvironmentInstance> name2Env) {
        this.name2Env = name2Env;
    }


    public int getTerminationTicks()
    {
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



    public Set<Rule> getRules() {
        return rules;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }

    public List<EntityInstance> CreateEntityWithPopulation(String name, int popNumber)
    {
        List<EntityInstance> res=new ArrayList<>();
        for(int i=0;i<popNumber;i++)
        {
            EntityInstance e=new EntityInstance();
            e.setNameOfEntity(name);
            res.add(e);

        }
        return res;
    }
    public World()
    {
        // init

        name2Env=new HashMap<>();
        rules=new HashSet<Rule>();
        entities=new ArrayList<Entity>();
    }


    public String random(String arg)
    {
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

    public  String environment(String nameOfEnvironmentVariable)
    {
        EnvironmentInstance en=getName2Env().get(nameOfEnvironmentVariable);
        return en.getEnvironmentProperty().getData().getDataString();
    }
    public static String getTypeOfEntity(EntityInstance e)
    {
        Field resField= null;
        try {
            resField = e.getClass().getField("Type");
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
        String typeOfField=new String();
        typeOfField=resField.getType().getSimpleName();

        return typeOfField;
    }
}