import Entity.Entity;
import Environment.EnvironmentInstance;
import Rules.Rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            e.NameOfEntity=name;
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




}
