package Rules.ActionTypes;

import Entity.EntityInstance;

import java.util.Collection;

public class KillAction implements Action
{

private String entityToKill;
    public KillAction()
    {

        entityToKill=new String();
    }

    public String getEntityToKill() {
        return entityToKill;
    }

    public void setEntityToKill(String entityToKill) {
        this.entityToKill = entityToKill;
    }

    @Override
    public void ActivateAction(EntityInstance e)
    {



    }
    void ActivateAction(Collection<EntityInstance> list)
    {

        for(EntityInstance e : list)
        {
            if(e.getNameOfEntity().equals(entityToKill))
            {
                EntityInstance wanted=e;
                boolean response=list.remove(wanted);
            }
        }



    }



}
