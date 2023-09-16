package pRules.pActionTypes;

import org.w3c.dom.Node;
import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.ActionsDTO.ProximityActionDTO;
import pDTOS.ActionsDTO.ReplaceActionDTO;
import pEntity.Entity;
import pEntity.EntityInstance;
import pEntity.Property;
import pExpression.AuxiliaryMethods;

public class ReplaceAction extends Action {

    private String entityToKill;
    private String entityToCreate;
    private String mode;

    public ActionDTO convertToDTO() {

        ReplaceActionDTO replaceActionDTO = new ReplaceActionDTO();
        replaceActionDTO.setMainEntityNameActionWorksOn(entityToCreate);
        replaceActionDTO.setNameOfAction("replace");
        if (this.prDsecondaryEntity != null) {
            replaceActionDTO.setSecondaryEntityNameActionWorksOn(getPrDsecondaryEntity().getNameOfSecondEntity());

        }
        replaceActionDTO.setMode(mode);
        replaceActionDTO.setEntityToCreate(entityToCreate);

        return replaceActionDTO;
    }


    public void setFunctions(AuxiliaryMethods functions) {
        super.functions = functions;
    }

    @Override
    public void ActivateAction(int currTick, EntityInstance... args) throws Exception
    {
        EntityInstance entityToKill = args[0];
        EntityInstance entityToCreate = args[1];
        Entity EntitytoKill = null;
        Entity EntityToCreate = null;
        EntityInstance addedEntityInstance = new EntityInstance();

        entityToKill.setTobeKilled(true);

        for (Entity entity : this.functions.getWorld().getEntities()) {
            if (entity.getNameOfEntity().equals(entityToKill)) {
                EntitytoKill = entity;
            }
            if (entity.getNameOfEntity().equals(entityToCreate)) {
                EntityToCreate = entity;
            }
        }


        switch (mode) {
            case "scratch": {
                addedEntityInstance = entityToKill.clone();

                // genereate random values

                EntityToCreate.getEntities().add(addedEntityInstance);

            }

            case "derived": {
                addedEntityInstance = entityToCreate;
                for (Property propertykilled : entityToKill.getPropertiesOfTheEntity()) {
                    for (Property propertyadded : addedEntityInstance.getPropertiesOfTheEntity()) {
                        if (propertykilled.getNameOfProperty().equals(propertyadded.getNameOfProperty()) && propertykilled.getTypeString().equals(propertyadded.getTypeString())) {
                            propertyadded = propertykilled.clone();
                        }
                    }
                }
                EntityToCreate.getEntities().add(addedEntityInstance);

            }

        }

    }

    public void initFromXML(Node ActionNode) {

        this.entityToKill = ActionNode.getAttributes().getNamedItem("kill").getTextContent();
        this.entityToCreate = ActionNode.getAttributes().getNamedItem("create").getTextContent();
        this.mode = ActionNode.getAttributes().getNamedItem("mode").getTextContent();


    }

    public String getEntityToKill() {
        return entityToKill;
    }

    public void setEntityToKill(String entityToKill) {
        this.entityToKill = entityToKill;
    }

    public String getEntityToCreate() {
        return entityToCreate;
    }

    public void setEntityToCreate(String entityToCreate) {
        this.entityToCreate = entityToCreate;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String getNameOfAction() {
        return null;
    }

    @Override
    public String getNameOfEntity() {

        return null;
    }
}