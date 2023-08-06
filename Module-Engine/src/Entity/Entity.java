package Entity;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import Entity.Properties;

public class Entity
{
   private   int numberOfEntity;
    private String NameOfEntity;
    private Set<Properties> propertiesOfTheEnitiy;
    public Entity()
    {

     propertiesOfTheEnitiy=new HashSet<Properties>();

    }

 public int getNumberOfEntity()
 {
  return numberOfEntity;
 }

 public void setNumberOfEntity(int numberOfEntity)
 {
  this.numberOfEntity = numberOfEntity;
 }

 public String getNameOfEntity()
 {
  return NameOfEntity;
 }

 public void setNameOfEntity(String nameOfEntity)
 {
  NameOfEntity = nameOfEntity;
 }

 public Set<Properties> getPropertiesOfTheEnitiy()
 {
  return propertiesOfTheEnitiy;
 }

 public void setPropertiesOfTheEnitiy(Set<Properties> propertiesOfTheEnitiy)
 {
  this.propertiesOfTheEnitiy = propertiesOfTheEnitiy;
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
}
