package Rules.ActionTypes;

import Entity.EntityInstance;

public abstract class Action
{
     private String nameOfEntity;

     abstract public void ActivateAction(EntityInstance e) throws Exception;

     abstract public String getNameOfAction();

     public String getNameOfEntity() {
          return nameOfEntity;
     }

     public void setNameOfEntity(String nameOfEntity) {
          this.nameOfEntity = nameOfEntity;
     }
}
