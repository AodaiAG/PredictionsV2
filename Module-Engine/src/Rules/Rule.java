package Rules;

import Entity.Entity;
import ExceptionHandler.ActionExceptionHandler;
import Expression.AuxiliaryMethods;
import Rules.ActionTypes.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import Entity.EntityInstance;
import System.World;

public class Rule
{
    private String nameOfRule;

    private String whenActivated;

    private Activation activation;

    private List<Action> actions;

    private AuxiliaryMethods functions;

    public AuxiliaryMethods getFunctions() {
        return functions;
    }

    public void setFunctions(AuxiliaryMethods functions) {
        this.functions = functions;
    }

    public String getNameOfRule() {
        return nameOfRule;
    }

    public void setNameOfRule(String nameOfRule) {
        this.nameOfRule = nameOfRule;
    }

    public String getWhenActivated() {
        return whenActivated;
    }

    public void setWhenActivated(String whenActivated) {
        this.whenActivated = whenActivated;
    }

    public Activation getActivation() {
        return activation;
    }

    public void setActivation(Activation activation) {
        this.activation = activation;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Rule() {
        actions = new ArrayList<>();
    }

    public Action CreateAction(Node ActionNode) throws Exception {
        try {
            World world = functions.getWorld();
            ActionExceptionHandler actionExceptionHandler = new ActionExceptionHandler();

            String whichEntityActionWork = ActionNode.getAttributes().getNamedItem("entity").getTextContent();
            actionExceptionHandler.checkIfEntityExists(world.getEntities(), whichEntityActionWork);

            String typeOfAction = ActionNode.getAttributes().getNamedItem("type").getTextContent();
            actionExceptionHandler.checkIfActionTypeValid(typeOfAction);


            switch (typeOfAction) {
                case "condition": {
                    ConditionAction conditionA = new ConditionAction();
                    conditionA.setFunctions(this.functions);
                    conditionA.setEntityName(whichEntityActionWork);
                    Node c = ((Element) ActionNode).getElementsByTagName("PRD-condition").item(0);
                    String typeOfCondition = c.getAttributes().getNamedItem("singularity").getTextContent();
                    actionExceptionHandler.conditionCheckSingularity(typeOfCondition);


                    if (typeOfCondition.equals("single")) {
                        SingleCondition single = new SingleCondition();
                        single.setFunctions(this.functions);

                        actionExceptionHandler.checkIfEntityExists(world.getEntities(), c.getAttributes().getNamedItem("entity").getTextContent());
                        single.setNameofEntity(c.getAttributes().getNamedItem("entity").getTextContent());

                        Entity toBeChecked = getEntityAccordingToName(world, c.getAttributes().getNamedItem("entity").getTextContent());
                        single.setNameofProperty(c.getAttributes().getNamedItem("property").getTextContent());
                        Boolean isProperty = actionExceptionHandler.checkIfPropertyExists(toBeChecked.getPropertiesOfTheEntity(), c.getAttributes().getNamedItem("property").getTextContent());
                        actionExceptionHandler.throwableCheckIfPropertyExists(isProperty, whichEntityActionWork, c.getAttributes().getNamedItem("property").getTextContent());

                        actionExceptionHandler.conditionSingleCheckOperator(c.getAttributes().getNamedItem("operator").getTextContent());
                        single.setOperator(c.getAttributes().getNamedItem("operator").getTextContent());

                        actionExceptionHandler.isExpressionValid(c.getAttributes().getNamedItem("value").getTextContent(), c.getAttributes().getNamedItem("entity").getTextContent(), world, "condition");
                        single.setValue(c.getAttributes().getNamedItem("value").getTextContent());


                        conditionA.setCondition(single);
                    }
                    if (typeOfCondition.equals("multiple")) {

                        MultipleCondition toCallFunc = new MultipleCondition();
                        toCallFunc.setFunctions(this.functions);
                        conditionA.setCondition(toCallFunc.createMultipleCondition(c));
                    }

                    Element thenNodesF = (Element) (((Element) ActionNode).getElementsByTagName("PRD-then").item(0));
                    if (thenNodesF != null) {
                        NodeList thenNodes = thenNodesF.getElementsByTagName("PRD-action");
                        for (int p = 0; p < thenNodes.getLength(); p++) {
                            conditionA.getActionsToDoIfTrue().add(p, CreateAction(thenNodes.item(p)));
                        }
                    }

                    Element elseNodesF = (Element) (((Element) ActionNode).getElementsByTagName("PRD-else").item(0));
                    if (elseNodesF != null) {
                        NodeList elseNodes = elseNodesF.getElementsByTagName("PRD-action");

                        for (int p = 0; p < elseNodes.getLength(); p++) {
                            conditionA.getActionsToDoIfFalse().add(p, CreateAction(elseNodes.item(p)));

                        }
                    }
                    return conditionA;
                }

                case "increase": {
                    IncreaseAction action = new IncreaseAction();
                    action.setFunctions(this.functions);
                    action.setEntityName(whichEntityActionWork);
                    Entity toBeChecked = getEntityAccordingToName(world, whichEntityActionWork);
                    String nameofProperty = ActionNode.getAttributes().getNamedItem("property").getTextContent();
                    action.setPropertyName(ActionNode.getAttributes().getNamedItem("property").getTextContent());
                    Boolean isProperty = actionExceptionHandler.checkIfPropertyExists(toBeChecked.getPropertiesOfTheEntity(), ActionNode.getAttributes().getNamedItem("property").getTextContent());
                    actionExceptionHandler.throwableCheckIfPropertyExists(isProperty, whichEntityActionWork, nameofProperty);

                    action.setExpression(ActionNode.getAttributes().getNamedItem("by").getTextContent());
                    actionExceptionHandler.isExpressionValid(ActionNode.getAttributes().getNamedItem("by").getTextContent(), whichEntityActionWork, world, "increase");
                    return action;
                }

                case "decrease": {
                    DecreaseAction action = new DecreaseAction();
                    action.setFunctions(this.functions);
                    action.setEntityName(whichEntityActionWork);
                    Entity toBeChecked = getEntityAccordingToName(world, whichEntityActionWork);

                    action.setPropertyName(ActionNode.getAttributes().getNamedItem("property").getTextContent());
                    Boolean isProperty = actionExceptionHandler.checkIfPropertyExists(toBeChecked.getPropertiesOfTheEntity(), ActionNode.getAttributes().getNamedItem("property").getTextContent());
                    actionExceptionHandler.throwableCheckIfPropertyExists(isProperty, whichEntityActionWork, ActionNode.getAttributes().getNamedItem("property").getTextContent());
                    action.setExpressionStr(ActionNode.getAttributes().getNamedItem("by").getTextContent());
                    actionExceptionHandler.isExpressionValid(ActionNode.getAttributes().getNamedItem("by").getTextContent(), whichEntityActionWork, world, "decrease");
                    return action;
                }

                case "calculation": {

                    CalculationAction action = new CalculationAction();
                    action.setFunctions(this.functions);
                    action.setEntityName(whichEntityActionWork);
                    NodeList mul = ((Element) ActionNode).getElementsByTagName("PRD-multiply");
                    NodeList div = ((Element) ActionNode).getElementsByTagName("PRD-divide");
                    if (mul.item(0) != null) {

                        action.setExpression1(mul.item(0).getAttributes().getNamedItem("arg1").getTextContent());
                        action.setExpression2(mul.item(0).getAttributes().getNamedItem("arg2").getTextContent());
                        actionExceptionHandler.isExpressionValid(mul.item(0).getAttributes().getNamedItem("arg1").getTextContent(), whichEntityActionWork, world, "calculation");
                        actionExceptionHandler.isExpressionValid(mul.item(0).getAttributes().getNamedItem("arg2").getTextContent(), whichEntityActionWork, world, "calculation");
                        action.setCalType("multiply");
                        action.setResultProp(ActionNode.getAttributes().getNamedItem("result-prop").getTextContent());
                        return action;
                    }
                    if (div.item(0) != null) {
                        actionExceptionHandler.isExpressionValid(div.item(0).getAttributes().getNamedItem("arg1").getTextContent(), whichEntityActionWork, world, "calculation");
                        actionExceptionHandler.isExpressionValid(div.item(0).getAttributes().getNamedItem("arg2").getTextContent(), whichEntityActionWork, world, "calculation");
                        action.setExpression1(div.item(0).getAttributes().getNamedItem("arg1").getTextContent());
                        action.setExpression2(div.item(0).getAttributes().getNamedItem("arg2").getTextContent());
                        action.setCalType("divide");
                        action.setResultProp(ActionNode.getAttributes().getNamedItem("result-prop").getTextContent());
                        return action;
                    }
                    break;
                }

                case "set": {
                    SetAction action = new SetAction();
                    action.setFunctions(this.functions);
                    action.setEntityName(whichEntityActionWork);
                    action.setPropertyName(ActionNode.getAttributes().getNamedItem("property").getTextContent());
                    Entity toBeChecked = getEntityAccordingToName(world, whichEntityActionWork);
                    Boolean isProperty = actionExceptionHandler.checkIfPropertyExists(toBeChecked.getPropertiesOfTheEntity(), ActionNode.getAttributes().getNamedItem("property").getTextContent());
                    actionExceptionHandler.throwableCheckIfPropertyExists(isProperty, whichEntityActionWork, ActionNode.getAttributes().getNamedItem("property").getTextContent());
                    action.setExpression(ActionNode.getAttributes().getNamedItem("value").getTextContent());
                    return action;
                }

                case "kill": {
                    KillAction action = new KillAction();
                    action.setFunctions(this.functions);
                    Entity toBeChecked = getEntityAccordingToName(world, ActionNode.getAttributes().getNamedItem("entity").getTextContent());
                    action.setEntityToKill(ActionNode.getAttributes().getNamedItem("entity").getTextContent());
                    return action;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        throw new RuntimeException("emptyList");
    }

    Entity getEntityAccordingToName(World world, String entityWorksOn) throws Exception {
        for (Entity entity : world.getEntities()) {
            if (entity.getNameOfEntity().equals(entityWorksOn)) {
                return entity;
            }
        }
        throw new Exception("entity name: " + entityWorksOn + " not found!");
    }

    public void isActivated(List<Entity> entities, int ticks, double generatedProbability) {
        if (ticks % activation.getTicks() == 0 && generatedProbability < activation.getProbability()) {
            for (Action action : this.actions) {
                String currentEntityName = action.getNameOfEntity();
                Entity currentEntity;
                try {
                    currentEntity = action.findEntityAccordingName(entities, currentEntityName);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                if (!currentEntity.getEntities().isEmpty()) {
                    for (EntityInstance eI : currentEntity.getEntities()) {
                        try {
                            if (eI != null) {
                                action.ActivateAction(eI);
                            }
                        } catch (Exception e) {

                        }
                    }
                    boolean hasRemoved = currentEntity.getEntities().removeIf(entityInstance -> entityInstance.getTobeKilled());
                }
            }
        }
    }
}