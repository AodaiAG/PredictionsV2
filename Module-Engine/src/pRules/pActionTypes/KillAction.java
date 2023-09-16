package pRules.pActionTypes;

import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.ActionsDTO.DecreaseActionDTO;
import pDTOS.ActionsDTO.KillActionDTO;
import pEntity.Entity;
import pEntity.EntityInstance;
import pExpression.AuxiliaryMethods;

import pEntity.Property;

public class KillAction extends Action
{
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
    public ActionDTO convertToDTO()
    {
        KillActionDTO killActionDTO=new KillActionDTO();
        killActionDTO.setNameOfAction("kill");
        if (this.prDsecondaryEntity != null) {
            killActionDTO.setSecondaryEntityNameActionWorksOn(getPrDsecondaryEntity().getNameOfSecondEntity());

        }              killActionDTO.setMainEntityNameActionWorksOn(entityName);
        killActionDTO.setEntityToKill(entityToKill);
        return killActionDTO;
    }

    @Override
    public void ActivateAction(int currTick, EntityInstance... args) throws Exception
    {
        System.out.println("im am in kill action");
        EntityInstance e=args[0];
        for(EntityInstance eI:args)
        {
            if(eI.getNameOfEntity().equals(this.entityName))
            {
                e=eI;
                break;
            }
        }
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