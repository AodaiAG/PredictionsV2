package Rules.ActionTypes;

import Entity.Entity;

import java.util.Collection;
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



    }
    @Override
    public String getNameOfAction()
    {
        return "kill";
    }
    void ActivateAction(Collection<Entity> list)
    {

        for(Entity e : list)
        {
            if(e.getNameOfEntity().equals(entityToKill))
            {
                Entity wanted=e;
                boolean response=list.remove(wanted);
            }
        }



    }



}
