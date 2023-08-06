import Entity.Entity;
import Environment.EnvironmentInstance;
import Rules.Rules;

import java.lang.reflect.Field;
import java.util.*;

public class World

{
    public int terminationTicks;
    public int terminationSeconds;
    public List<List<Entity>> entities;
    public Set<EnvironmentInstance> environmentVariables;
    public Set<Rules> rules;
    public List<Entity> CreateEnityWithPopulation(String name,int popNumber)
    {
         List<Entity> res=new ArrayList<>();
        for(int i=0;i<popNumber;i++)
        {
            Entity e=new Entity();
            e.setNameOfEntity(name);
            res.add(e);

        }
        return res;
    }
    public World()
    {
        // init
        entities=new ArrayList<List<Entity>>();
        for(List<Entity> e:entities)
        {
            e=new ArrayList<Entity>();
        }
        environmentVariables=new HashSet<EnvironmentInstance>();
        rules=new HashSet<Rules>();
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

    public static String getTypeOfEntity(Entity e)
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

    public  Object  environment(String envName)
    {
        for(EnvironmentInstance e :environmentVariables)
        {
            if(e.getNameOfProperty().equals(envName))
            {
                return e.getType();
            }
        }

        throw new RuntimeException("enviorment name not found");

    }


}




