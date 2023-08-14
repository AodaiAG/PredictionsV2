package Rules.ActionTypes;
import Entity.EntityInstance;
import Expression.AuxiliaryMethods;
import sun.security.pkcs11.wrapper.Functions;


public abstract class Action
{
     private String nameOfEntity;
     private AuxiliaryMethods functions;
     public AuxiliaryMethods getFunctions()
     {
          return functions;
     }

     public void setFunctions(AuxiliaryMethods functions)
     {
          this.functions = functions;
     }

     abstract public void ActivateAction(EntityInstance e) throws Exception;

     abstract public String getNameOfAction();

     public String getNameOfEntity()
     {
          return nameOfEntity;
     }

     public void setNameOfEntity(String nameOfEntity) {
          this.nameOfEntity = nameOfEntity;
     }
}
