package pRules.pActionTypes;

import pDTOS.ActionsDTO.ActionDTO;
import pEntity.Entity;
import pEntity.EntityInstance;
import pExpression.AuxiliaryMethods;

import java.util.List;

public abstract class Action
{
    protected AuxiliaryMethods functions;

    protected PRDSecondaryEntity prDsecondaryEntity;

    public abstract ActionDTO convertToDTO();

    public PRDSecondaryEntity getPrDsecondaryEntity()
    {
        return prDsecondaryEntity;
    }

    public void setPrDsecondaryEntity(PRDSecondaryEntity prDsecondaryEntity)
    {
        this.prDsecondaryEntity = prDsecondaryEntity;
    }

    public AuxiliaryMethods getFunctions() {
        return functions;
    }

    abstract public void setFunctions(AuxiliaryMethods functions);

    abstract public void ActivateAction(int currTick, EntityInstance... args) throws Exception;

    abstract public String getNameOfAction();

    public abstract String getNameOfEntity();

    public Entity findEntityAccordingName(List<Entity> entities, String currentEntityName) throws Exception {
        for (Entity entity : entities) {
            if (entity.getNameOfEntity().equals(currentEntityName)) {
                return entity;
            }
        }
        throw new Exception("Entity not found");
    }
}