package Rules.ActionTypes;
import Entity.Entity;
import Entity.EntityInstance;
import Expression.AuxiliaryMethods;

import java.util.ArrayList;
import java.util.List;
import Entity.Property;

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

        e.setTobeKilled(true);

        for(Property p :e.getPropertiesOfTheEntity())
        {
            if(p.getNameOfProperty().equals("age"))
            {
                System.out.println("Age: "+p.getData().getDataString());
            }
        }
         for(Entity entity: functions.getWorld().getEntities())
         {
             if(e.getNameOfEntity().equals(entity.getNameOfEntity()))
             {
                int numbOfinstance= entity.getNumberOfInstances()-1;
                entity.setNumberOfInstances(numbOfinstance);
             }
         }

    }




    @Override
    public String getNameOfAction()
    {
        return "kill";
    }

}
