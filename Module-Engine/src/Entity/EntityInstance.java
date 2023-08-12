package Entity;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class EntityInstance
{
    private String NameOfEntity;
    private Set<Property> propertiesOfTheEntity;
    public EntityInstance()
    {
        propertiesOfTheEntity =new HashSet<Property>();
    }

    public String getNameOfEntity()
    {
        return NameOfEntity;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    public void setNameOfEntity(String nameOfEntity)
    {
        NameOfEntity = nameOfEntity;
    }

    public Set<Property> getPropertiesOfTheEntity()
    {
        return propertiesOfTheEntity;
    }

    public void setPropertiesOfTheEntity(Set<Property> propertiesOfTheEntity)
    {
        this.propertiesOfTheEntity = propertiesOfTheEntity;
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