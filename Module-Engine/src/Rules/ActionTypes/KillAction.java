package Rules.ActionTypes;

import Entity.Entity;

import java.util.Collection;

public class KillAction extends Action
{

String entityToKill;
    public KillAction()
    {
        super("kill");
        entityToKill=new String();
    }
    @Override
    void ActivateAction(Entity e)
    {



    }
    void ActivateAction(Collection<Entity> list)
    {

        for(Entity e : list)
        {
            if(e.NameOfEntity.equals(entityToKill))
            {
                Entity wanted=e;
                boolean response=list.remove(wanted);
            }
        }



    }



}
