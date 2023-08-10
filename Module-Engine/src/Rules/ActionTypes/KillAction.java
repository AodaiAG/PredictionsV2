package Rules.ActionTypes;

import Entity.Entity;

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
    public void ActivateAction(Entity e) throws Exception
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
