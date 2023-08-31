package pRules.pActionTypes;

import org.w3c.dom.Node;
import pEntity.Entity;
import pEntity.EntityInstance;
import pEntity.Property;
import pExpression.AuxiliaryMethods;

public class ReplaceAction extends Action

{

 private String entityToKill;
 private String entityToCreate;
 private String mode;



    @Override
    public void setFunctions(AuxiliaryMethods functions)
    {
        super.functions = functions;
    }

    @Override
    public void ActivateAction(EntityInstance ...args) throws Exception
    {
        EntityInstance e=args[0];
        Entity EntitytoKill=null;
        Entity EntityToCreate = null;
        EntityInstance addedEntityInstance=new EntityInstance();
        e.setTobeKilled(true);
       for(Entity entity: this.functions.getWorld().getEntities())
       {
           if(entity.getNameOfEntity().equals(entityToKill))
           {
               EntitytoKill=entity;
           }
           if(entity.getNameOfEntity().equals(entityToCreate))
           {
               EntityToCreate=entity;
           }
       }


        switch (mode)
        {
            case"scratch":
            {
                addedEntityInstance =EntityToCreate.getEntities().get(0).clone();
                // genereate random values
                EntityToCreate.getEntities().add(addedEntityInstance);

            }

            case "derived":
            {
                addedEntityInstance=EntityToCreate.getEntities().get(0).clone();
                for(Property propertykilled:e.getPropertiesOfTheEntity())
                {
                    for(Property propertyadded:addedEntityInstance.getPropertiesOfTheEntity())
                    {
                        if(propertykilled.getNameOfProperty().equals(propertyadded.getNameOfProperty()) && propertykilled.getTypeString().equals(propertyadded.getTypeString()) )
                        {

                            propertyadded=propertykilled;

                        }
                    }
                }

                EntityToCreate.getEntities().add(addedEntityInstance);
            }

        }

    }

   public ReplaceAction initFromXML(Node ActionNode)
    {

        this.entityToKill=ActionNode.getAttributes().getNamedItem("kill").getTextContent();
        this.entityToCreate=ActionNode.getAttributes().getNamedItem("create").getTextContent();
        this.mode=ActionNode.getAttributes().getNamedItem("mode").getTextContent();
        return this;


    }

    public String getEntityToKill()
    {
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
    public String getNameOfAction()
    {
        return null;
    }

    @Override
    public String getNameOfEntity()
    {

        return null;
    }
}
