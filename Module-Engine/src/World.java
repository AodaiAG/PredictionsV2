import Entity.EntityInstance;
import Environment.EnvironmentInstance;
import Rules.Rules;
import Entity.Entity;

import java.lang.reflect.Field;
import java.util.*;

public class World
{
    private int terminationTicks;
    private int terminationSeconds;
    private List<Entity> entities;
    private Set<EnvironmentInstance> environmentVariables;
    private Set<Rules> rules;

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

    public Set<EnvironmentInstance> getEnvironmentVariables() {
        return environmentVariables;
    }

    public void setEnvironmentVariables(Set<EnvironmentInstance> environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public Set<Rules> getRules() {
        return rules;
    }

    public void setRules(Set<Rules> rules) {
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

        environmentVariables=new HashSet<EnvironmentInstance>();
        rules=new HashSet<Rules>();
        entities=new ArrayList<Entity>();
    }



    public static Object RandomFun(Properties e, int upperbound)
    {

        Random r=new Random();
        try {
            Field resField=e.getClass().getField("Type");
            String typeOfField=resField.getType().getSimpleName();
            switch (typeOfField)

            {
                case "Integer":
                    return r.nextInt(upperbound);

                case "Float":
                    return  0 + (upperbound - 0) * r.nextFloat();

                case "Boolean":
                    return r.nextBoolean();

                case "String":
                {
                    int leftLimit = 97; // letter 'a'
                    int rightLimit = 122; // letter 'z'
                    int targetStringLength = 50;
                    Random random = new Random();
                    StringBuilder buffer = new StringBuilder(targetStringLength);
                    for (int i = 0; i < targetStringLength; i++) {
                        int randomLimitedInt = leftLimit + (int)
                                (random.nextFloat() * (rightLimit - leftLimit + 1));
                        buffer.append((char) randomLimitedInt);
                    }
                    String generatedString = buffer.toString();
                    return generatedString;
                }
            }
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
        return 5;
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

    public Object environment(String envName)
    {
        for(EnvironmentInstance eI :environmentVariables)
        {
            if(eI.getEnvironmentProperty().getNameOfProperty().equals(envName))
            {
                return eI.getEnvironmentProperty().getEdata().getData();
            }
        }
        throw new RuntimeException("environment name not found");
    }
}