package Rules.ActionTypes;

import Entity.Entity;
import Entity.EntityInstance;
import Expression.AuxiliaryMethods;

public class KillAction extends Action {
    private String entityName;

    private String entityToKill;

    public KillAction() {
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
    public void ActivateAction(EntityInstance e) throws Exception {
        e.setTobeKilled(true);
        for (Entity entity : functions.getWorld().getEntities()) {
            if (e.getNameOfEntity().equals(entity.getNameOfEntity())) {
                int numberOfInstances = entity.getNumberOfInstances() - 1;
                entity.setNumberOfInstances(numberOfInstances);
            }
        }

    }

    @Override
    public String getNameOfAction() {
        return "kill";
    }
}
