package Entity;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class EntityInstance
{

    private String NameOfEntity;
    private Set<Properties> propertiesOfTheEnitiy;
    public EntityInstance()
    {

     propertiesOfTheEnitiy=new HashSet<Properties>();

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
