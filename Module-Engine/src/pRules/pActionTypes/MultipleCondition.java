package pRules.pActionTypes;

import pEntity.EntityInstance;
import pEntity.Entity;
import pExceptionHandler.ActionExceptionHandler;
import pRules.Rule;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import pSystem.World;

public class MultipleCondition extends ConditionAction
{
    private Boolean conditionResult;
    private String logical;
    private List<ConditionAction> listOfConditions;


    @Override
    public Boolean getConditionResult() {
        return conditionResult;
    }

    @Override
    public void setConditionResult(Boolean conditionResult) {
        this.conditionResult = conditionResult;
    }


    public MultipleCondition createMultipleCondition(Node nodeList) throws Exception {
        try {
            World world = functions.getWorld();
            ActionExceptionHandler actionExceptionHandler = new ActionExceptionHandler();
            MultipleCondition res = new MultipleCondition();
            res.setFunctions(functions);
            res.setLogical(nodeList.getAttributes().getNamedItem("logical").getTextContent());
            actionExceptionHandler.conditionCheckOperator(nodeList.getAttributes().getNamedItem("logical").getTextContent());

            Element el = (Element) nodeList;
            NodeList wanted = nodeList.getChildNodes();
            Rule justToCallFunction = new Rule();
            justToCallFunction.setFunctions(functions);


            for (int i = 0; i < wanted.getLength(); i++) {
                if (wanted.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Node node = wanted.item(i);
                    String conditionType = node.getAttributes().getNamedItem("singularity").getTextContent();
                    actionExceptionHandler.conditionCheckSingularity(conditionType);
                    if (conditionType.equals("single")) {
                        SingleCondition single = new SingleCondition();
                        single.setFunctions(getFunctions());
                        single.setNameofEntity(node.getAttributes().getNamedItem("entity").getTextContent());

                        actionExceptionHandler.checkIfEntityExists(world.getEntities(), node.getAttributes().getNamedItem("entity").getTextContent());
                        Entity toBeChecked = getEntityAccordingToName(world, node.getAttributes().getNamedItem("entity").getTextContent());

                        single.setNameofProperty(node.getAttributes().getNamedItem("property").getTextContent());
                        actionExceptionHandler.checkIfPropertyExists(toBeChecked.getPropertiesOfTheEntity(), node.getAttributes().getNamedItem("property").getTextContent());

                        single.setOperator(node.getAttributes().getNamedItem("operator").getTextContent());
                        actionExceptionHandler.conditionSingleCheckOperator(node.getAttributes().getNamedItem("operator").getTextContent());

                        actionExceptionHandler.isExpressionValid(node.getAttributes().getNamedItem("value").getTextContent(), node.getAttributes().getNamedItem("entity").getTextContent(), world, "condition");
                        single.setValue(node.getAttributes().getNamedItem("value").getTextContent());
                        res.getListOfConditions().add(single);
                    }
                    if (conditionType.equals("multiple")) {
                        res.getListOfConditions().add(createMultipleCondition(node));
                    }
                }
            }

            // then

            Element thenNodesF = (Element) (((Element) nodeList).getElementsByTagName("PRD-then").item(0));
            if (thenNodesF != null) {
                NodeList thenNodes = thenNodesF.getElementsByTagName("PRD-action");
                for (int p = 0; p < thenNodes.getLength(); p++) {
                    res.getActionsToDoIfTrue().add(p, justToCallFunction.CreateAction(thenNodes.item(p)));
                }
            }

            Element elseNodesF = (Element) (((Element) nodeList).getElementsByTagName("PRD-else").item(0));
            if (elseNodesF != null) {
                NodeList elseNodes = elseNodesF.getElementsByTagName("PRD-action");

                for (int p = 0; p < elseNodes.getLength(); p++) {
                    res.getActionsToDoIfFalse().add(p, justToCallFunction.CreateAction(elseNodes.item(p)));
                }
            }
            return res;
        } catch (Exception e) {
            throw e;
        }
    }

    Entity getEntityAccordingToName(World world, String entityWorksOn) throws Exception {
        for (Entity entity : world.getEntities()) {
            if (entity.getNameOfEntity().equals(entityWorksOn))
            {
                return entity;
            }
        }
        throw new Exception("entity name: " + entityWorksOn + " not found!");
    }

    public String getLogical() {
        return logical;
    }

    public void setLogical(String logical) {
        this.logical = logical;
    }

    public List<ConditionAction> getListOfConditions() {
        return listOfConditions;
    }

    public void setListOfConditions(List<ConditionAction> listOfConditions) {
        this.listOfConditions = listOfConditions;
    }

    @Override
    public String getNameOfAction() {
        return "multipleCondition";
    }

    @Override
    public void ActivateAction(EntityInstance e) throws Exception
    {
        switch (logical) {
            case "and":
            {
                conditionResult = true;
                for (ConditionAction c : listOfConditions) {
                    c.ActivateAction(e);
                    Boolean result = c.getConditionResult();
                    if (!result) {
                        conditionResult = false;
                        break;
                    }
                }
                break;
            }

            case "or":
            {
                conditionResult = false;
                for (ConditionAction c : listOfConditions) {
                    c.ActivateAction(e);
                    Boolean result = c.getConditionResult();
                    if (result) {
                        conditionResult = true;
                        break;
                    }
                }
                break;
            }
        }
    }

    public MultipleCondition() {
        logical = "";
        listOfConditions = new ArrayList<>();
        conditionResult = true;
    }
}