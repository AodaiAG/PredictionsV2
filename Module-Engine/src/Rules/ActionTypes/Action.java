package Rules.ActionTypes;
import Entity.Entity;
import Entity.EntityInstance;
import Expression.AuxiliaryMethods;
import sun.security.pkcs11.wrapper.Functions;
import java.util.List;

public abstract class Action
{
  //   private String nameOfEntity;

     protected AuxiliaryMethods functions;

     public AuxiliaryMethods getFunctions()
     {
          return functions;
     }

     abstract public void setFunctions(AuxiliaryMethods functions);

     abstract public void ActivateAction(EntityInstance e) throws Exception;

     abstract public String getNameOfAction();

     public abstract String getNameOfEntity();

//     public void setNameOfEntity(String nameOfEntity) {
//          this.nameOfEntity = nameOfEntity;
//     }

     public Entity findEntityAccordingName(List<Entity> entities, String currentEntityName) throws Exception {
          for (Entity entity : entities) {
               if (entity.getNameOfEntity().equals(currentEntityName)) {
                    return entity;
               } else {
                    throw new Exception("Entity not found");
               }
          }
          return null;
     }

}