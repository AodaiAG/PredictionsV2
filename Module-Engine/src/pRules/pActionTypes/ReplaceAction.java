package pRules.pActionTypes;

import org.w3c.dom.Node;
import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.ActionsDTO.ReplaceActionDTO;
import pEntity.Entity;
import pEntity.EntityInstance;
import pEntity.Property;
import pExpression.AuxiliaryMethods;

public class ReplaceAction extends Action
{

    private String nameEntityToKill;
    private String nameEntityToCreate;
    private String mode;

    public ActionDTO convertToDTO() {

        ReplaceActionDTO replaceActionDTO = new ReplaceActionDTO();
        replaceActionDTO.setMainEntityNameActionWorksOn(nameEntityToCreate);
        replaceActionDTO.setNameOfAction("replace");
        if (this.prDsecondaryEntity != null) {
            replaceActionDTO.setSecondaryEntityNameActionWorksOn(getPrDsecondaryEntity().getNameOfSecondEntity());

        }
        replaceActionDTO.setMode(mode);
        replaceActionDTO.setEntityToCreate(nameEntityToCreate);

        return replaceActionDTO;
    }


    public void setFunctions(AuxiliaryMethods functions) {
        super.functions = functions;
    }

    @Override
    public void ActivateAction(int currTick, EntityInstance... args) throws Exception
    {
        EntityInstance entityInstanceToKill = args[0];
        EntityInstance createdEntityInstance = null;

        entityInstanceToKill.setTobeKilled(true);
        functions.getWorld().getGrid().removeInstanceFromCell(entityInstanceToKill);
        Entity entityToKill = findEntityAccordingName(this.functions.getWorld().getEntities(), nameEntityToKill);
        Entity entityToCreate = findEntityAccordingName(this.functions.getWorld().getEntities(), nameEntityToCreate);

        switch (mode) {
            case "scratch": {
                createdEntityInstance = entityToCreate.createNewInstance();
                createdEntityInstance.setCoordinate(entityInstanceToKill.getCoordinate());
                this.functions.getWorld().getGrid().setEntityInstanceInCell(createdEntityInstance, entityInstanceToKill.getCoordinate());
                entityToCreate.getEntities().add(createdEntityInstance);
            }

            case "derived": {
                createdEntityInstance = entityToCreate.createNewInstance();
                createdEntityInstance.setCoordinate(entityInstanceToKill.getCoordinate());
                this.functions.getWorld().getGrid().setEntityInstanceInCell(createdEntityInstance, entityInstanceToKill.getCoordinate());

                for (Property propertyKilled : entityInstanceToKill.getPropertiesOfTheEntity()) {//property at kill
                    for (Property propertyAdded : createdEntityInstance.getPropertiesOfTheEntity()) { //property at create
                        if (propertyKilled.getNameOfProperty().equals(propertyAdded.getNameOfProperty()) && propertyKilled.getTypeString().equals(propertyAdded.getTypeString())) {
                            propertyAdded = propertyKilled.clone();
                        }
                    }
                }
                entityToCreate.getEntities().add(createdEntityInstance);
            }
        }
        entityToKill.updateNumberOfInstances();
        entityToCreate.updateNumberOfInstances();
    }

    public void initFromXML(Node ActionNode)
    {

        this.nameEntityToKill = ActionNode.getAttributes().getNamedItem("kill").getTextContent();
        this.nameEntityToCreate = ActionNode.getAttributes().getNamedItem("create").getTextContent();
        this.mode = ActionNode.getAttributes().getNamedItem("mode").getTextContent();
    }

    public String getNameEntityToKill() {
        return nameEntityToKill;
    }

    public void setNameEntityToKill(String nameEntityToKill) {
        this.nameEntityToKill = nameEntityToKill;
    }

    public String getNameEntityToCreate() {
        return nameEntityToCreate;
    }

    public void setNameEntityToCreate(String nameEntityToCreate) {
        this.nameEntityToCreate = nameEntityToCreate;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String getNameOfAction() {
        return "replace";
    }

    @Override
    public String getNameOfEntity() {

        return nameEntityToKill;
    }
}