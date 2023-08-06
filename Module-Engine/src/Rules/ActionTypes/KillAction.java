package Rules.ActionTypes;

import Entity.Entity;

import java.util.Collection;

public class KillAction extends Action
{

private String entityToKill;
    public KillAction()
    {
        super("kill");
        entityToKill=new String();
    }

    public String getEntityToKill() {
        return entityToKill;
    }

    public void setEntityToKill(String entityToKill) {
        this.entityToKill = entityToKill;
    }

    @Override
    void ActivateAction(Entity e)
    {



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
