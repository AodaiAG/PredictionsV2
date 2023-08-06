package Entity;

import java.util.ArrayList;
import java.util.List;

public class EntityCollection
{
    private String nameOfEntity;
   private List<Entity> entities;

   public int getNumberOfEntites()
   {
       return entities.size();
   }
    public EntityCollection()
    {
        this.nameOfEntity = new String();
        this.entities = new ArrayList<Entity>();
    }
    public String getNameOfEntity() {
        return nameOfEntity;
    }

    public void setNameOfEntity(String nameOfEntity)
    {
        this.nameOfEntity = nameOfEntity;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}
