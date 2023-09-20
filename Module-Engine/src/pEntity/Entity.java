package pEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Entity implements Cloneable
{
    private String nameOfEntity;
    private int numberOfInstances;
    private int numberOfInstancesBefore;
    private List<EntityInstance> entityInstances;
    private Set<Property> propertiesOfTheEntity;


    public Entity() {
        this.nameOfEntity = "";
        this.entityInstances = new ArrayList<>();
        propertiesOfTheEntity = new HashSet<>();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Entity clonedEntity = (Entity) super.clone();

        // Create a new list for cloned entity instances
        List<EntityInstance> clonedEntityInstances = new ArrayList<>();
        for (EntityInstance entityInstance : this.entityInstances) {
            EntityInstance clonedInstance = (EntityInstance) entityInstance.clone(); // Assuming EntityInstance is Cloneable
            clonedEntityInstances.add(clonedInstance);
        }
        clonedEntity.entityInstances = clonedEntityInstances;

        // Clone propertiesOfTheEntity set using your Property class's clone method (assuming Property is Cloneable)
        Set<Property> clonedProperties = new HashSet<>();
        for (Property property : this.propertiesOfTheEntity) {
            Property clonedProperty = (Property) property.clone(); // Use your Property class's clone method
            clonedProperties.add(clonedProperty);
        }
        clonedEntity.propertiesOfTheEntity = clonedProperties;

        return clonedEntity;
    }

    public void updateNumberOfInstances()
    {
        numberOfInstances = entityInstances.size();
    }

    public int getNumberOfInstances() {
        return numberOfInstances;
    }

    public void setNumberOfInstances(int numberOfInstances) {
        this.numberOfInstances = numberOfInstances;
    }

    public void setEntities(List<EntityInstance> entities) {
        this.entityInstances = entities;
    }

    public Set<Property> getPropertiesOfTheEntity() {
        return propertiesOfTheEntity;
    }

    public void setPropertiesOfTheEntity(Set<Property> propertiesOfTheEntity) {
        this.propertiesOfTheEntity = propertiesOfTheEntity;
    }

    public String getNameOfEntity() {
        return nameOfEntity;
    }

    public void setNameOfEntity(String nameOfEntity) {
        this.nameOfEntity = nameOfEntity;
    }

    public List<EntityInstance> getEntities() {
        return entityInstances;
    }

    public EntityInstance createNewInstance()
    {
        EntityInstance newEntityInstance = new EntityInstance();
        newEntityInstance.setTobeKilled(false);
        newEntityInstance.setNameOfEntity(nameOfEntity);
        Set<Property> propOfEntity = new HashSet<>();
        for (Property p : propertiesOfTheEntity)
        {
            propOfEntity.add(p.clone());
        }
        newEntityInstance.setPropertiesOfTheEntity(propOfEntity);
        for (Property p : getPropertiesOfTheEntity())
        {
            String initVal = p.getData().getDataString();
            if (p.isRandomInitialize())
            {
                p.getData().calculateNewVal(initVal, true);
            }
        }
        return newEntityInstance;
    }
}
