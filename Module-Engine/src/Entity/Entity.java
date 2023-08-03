package Entity;

import java.util.HashSet;
import java.util.Set;

public class Entity
{
   public  int numberOfEntity;
    public String NameOfEntity;
    public Set<Properties> propertiesOfTheEnitiy;
    public Entity()
    {

     propertiesOfTheEnitiy=new HashSet<Properties>();

    }



}
