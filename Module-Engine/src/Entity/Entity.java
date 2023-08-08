package Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Entity
{
    private String nameOfEntity;
    private int numberOfInstances;
    private List<EntityInstance> entityInstances;
    private Set<Properties> propertiesOfTheEnitiy;

    public int getNumberOfInstances() {
        return numberOfInstances;
    }

    public void setNumberOfInstances(int numberOfInstances) {
        this.numberOfInstances = numberOfInstances;
    }

    public void setEntities(List<EntityInstance> entities)
    {
        this.entityInstances = entities;
    }

    public Set<Properties> getPropertiesOfTheEnitiy() {
        return propertiesOfTheEnitiy;
    }

    public void setPropertiesOfTheEnitiy(Set<Properties> propertiesOfTheEnitiy) {
        this.propertiesOfTheEnitiy = propertiesOfTheEnitiy;
    }

    public Entity()
    {
        this.nameOfEntity = new String();
        this.entityInstances = new ArrayList<EntityInstance>();
        propertiesOfTheEnitiy=new HashSet<>();
    }
    public int getNumberOfEntites()
    {
        numberOfInstances= entityInstances.size();
        return numberOfInstances;
    }
    public String getNameOfEntity() {
        return nameOfEntity;
    }

    public void setNameOfEntity(String nameOfEntity)
    {
        this.nameOfEntity = nameOfEntity;
    }

    public List<EntityInstance> getEntities() {
        return entityInstances;
    }


}
