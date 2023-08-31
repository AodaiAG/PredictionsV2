package pEntity;

import pSystem.EntityInstancesCircularGrid;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class EntityInstance
{
    private String NameOfEntity;
    private Set<Property> propertiesOfTheEntity;
    private Boolean isTobeKilled = false;
    private Coordinate coordinate;

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Boolean getTobeKilled() {
        return isTobeKilled;
    }

    public void setTobeKilled(Boolean tobeKilled) {
        isTobeKilled = tobeKilled;
    }


    @Override
    public EntityInstance clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        EntityInstance res = new EntityInstance();
        res.setNameOfEntity(this.NameOfEntity);
        res.setTobeKilled(isTobeKilled);
        Set<Property> pSetRes = new HashSet<>();
        for (Property p : this.propertiesOfTheEntity) {
            pSetRes.add(p.clone());
        }
        res.setPropertiesOfTheEntity(pSetRes);
        return res;
    }

    public EntityInstance() {
        propertiesOfTheEntity = new HashSet<>();
    }

    public String getNameOfEntity() {
        return NameOfEntity;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void setNameOfEntity(String nameOfEntity) {
        NameOfEntity = nameOfEntity;
    }

    public Set<Property> getPropertiesOfTheEntity() {
        return propertiesOfTheEntity;
    }

    public void setPropertiesOfTheEntity(Set<Property> propertiesOfTheEntity) {
        this.propertiesOfTheEntity = propertiesOfTheEntity;
    }

    public static String getTypeOfEntity(EntityInstance e) {
        Field resField;
        try {
            resField = e.getClass().getField("Type");
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
        String typeOfField;
        typeOfField = resField.getType().getSimpleName();
        return typeOfField;
    }
}