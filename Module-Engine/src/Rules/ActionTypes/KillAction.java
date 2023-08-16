package Rules.ActionTypes;
import Entity.Entity;
import Entity.EntityInstance;
import Expression.AuxiliaryMethods;


public class KillAction extends Action
{
    private String entityName;

    private String entityToKill;
        public KillAction()
        {
            entityName = "";
            entityToKill=new String();
        }
    @Override
    public void setFunctions(AuxiliaryMethods functions) {
        super.functions = functions;
    }
    @Override
    public String getNameOfEntity() {
        return entityName;
    }
    public String getEntityToKill() {
        return entityToKill;
    }

    public void setEntityToKill(String entityToKill) {
        this.entityToKill = entityToKill;
    }

    @Override
    public void ActivateAction(EntityInstance e) throws Exception
    {
        try
        {
            Entity entityToKill=findEntityAccordingName(functions.getWorld().getEntities(),e.getNameOfEntity());
            Boolean isKilled=entityToKill.getEntities().remove(e);
            if(!isKilled)
            {
                throw new Exception("Entity instance Not Found!");
            }

        }
        catch (Exception exception)
        {
            throw  exception;
        }

    }
    @Override
    public String getNameOfAction()
    {
        return "kill";
    }

}
