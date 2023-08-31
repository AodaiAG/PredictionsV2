package pRules.pActionTypes;

import pEntity.Entity;
import pEntity.EntityInstance;
import pExpression.AuxiliaryMethods;

import pEntity.Property;

public class KillAction extends Action {
    private String entityName;

    private String entityToKill;

    public KillAction()
    {
        entityName = "";
        entityToKill = "";
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
    public void ActivateAction(EntityInstance... args) throws Exception
    {
        EntityInstance e=args[0];
        e.setTobeKilled(true);

        for (Entity entity : functions.getWorld().getEntities()) {
            if (e.getNameOfEntity().equals(entity.getNameOfEntity())) {
                int numbOfInstance = entity.getNumberOfInstances() - 1;
                entity.setNumberOfInstances(numbOfInstance);
            }
        }
    }

    @Override
    public String getNameOfAction() {
        return "kill";
    }
}