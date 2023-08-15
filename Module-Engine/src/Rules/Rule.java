package Rules;

import Entity.Entity;
import Expression.AuxiliaryMethods;
import Rules.ActionTypes.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;
import Entity.EntityInstance;


public class Rule {
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

    public Action CreateAction(Node ActionNode)
    {
        String whichEntityActionWork = ActionNode.getAttributes().getNamedItem("entity").getTextContent();
        String typeOfAction = ActionNode.getAttributes().getNamedItem("type").getTextContent();

        switch (typeOfAction)
        {
            case "condition":
            {
                ConditionAction conditionA = new ConditionAction();
                conditionA.setFunctions(this.functions);
                conditionA.setEntityName(whichEntityActionWork);

                Node c = ((Element) ActionNode).getElementsByTagName("PRD-condition").item(0);
                String typeOfCondition = c.getAttributes().getNamedItem("singularity").getTextContent();
                if (typeOfCondition.equals("single"))
                {
                    SingleCondition single = new SingleCondition();
                    single.setFunctions(this.functions);
                    single.setNameofEntity(c.getAttributes().getNamedItem("entity").getTextContent());
                    single.setNameofProperty(c.getAttributes().getNamedItem("property").getTextContent());
                    single.setOperator(c.getAttributes().getNamedItem("operator").getTextContent());
                    single.setValue(c.getAttributes().getNamedItem("value").getTextContent());
                    conditionA.setCondition(single);
                }
                if (typeOfCondition.equals("multiple"))
                {

                    MultipleCondition tocallfunc = new MultipleCondition();
                    tocallfunc.setFunctions(this.functions);
                    conditionA.setCondition(tocallfunc.createMultipleCondition(c));
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

                    for (int p = 0; p < elseNodes.getLength(); p++)
                    {
                        conditionA.getActionsToDoIfFalse().add(p, CreateAction(elseNodes.item(p)));

                    }
                }
                return conditionA;
            }

            case "increase": {
                IncreaseAction action = new IncreaseAction();
                action.setFunctions(this.functions);
                action.setEntityName(whichEntityActionWork);
                action.setPropertyName(ActionNode.getAttributes().getNamedItem("property").getTextContent());
                action.setExpression(ActionNode.getAttributes().getNamedItem("by").getTextContent());

                return action;
            }

            case "decrease": {
                DecreaseAction action = new DecreaseAction();
                action.setFunctions(this.functions);
                action.setEntityName(whichEntityActionWork);
                action.setPropertyName(ActionNode.getAttributes().getNamedItem("property").getTextContent());
                action.setExpressionStr(ActionNode.getAttributes().getNamedItem("by").getTextContent());
                return action;
            }

            case "calculation": {
                CalculationAction action = new CalculationAction();
                action.setFunctions(this.functions);
                NodeList mul = ((Element) ActionNode).getElementsByTagName("PRD-multiply");
                NodeList div = ((Element) ActionNode).getElementsByTagName("PRD-divide");
                if (mul.item(0) != null) {
                    action.setExpression1(mul.item(0).getAttributes().getNamedItem("arg1").getTextContent());
                    action.setExpression2(mul.item(0).getAttributes().getNamedItem("arg2").getTextContent());
                    action.setCalType("multiply");
                    action.setResultProp(ActionNode.getAttributes().getNamedItem("result-prop").getTextContent());
                    return action;
                }
                if (div.item(0) != null) {
                    action.setExpression1(div.item(0).getAttributes().getNamedItem("arg1").getTextContent());
                    ;
                    action.setExpression2(div.item(0).getAttributes().getNamedItem("arg2").getTextContent());
                    ;
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
                action.setExpression(ActionNode.getAttributes().getNamedItem("value").getTextContent());
                return action;
            }

            case "kill": {
                KillAction action = new KillAction();
                action.setFunctions(this.functions);
                action.setEntityToKill(ActionNode.getAttributes().getNamedItem("entity").getTextContent());
                return action;
            }
        }
        throw new RuntimeException("emptyList");
    }

    public void isActivated(List<Entity> entities, int ticks, double generatedProbability)
    {
        if (ticks % activation.getTicks() == 0 || generatedProbability < activation.getProbability())
        {
            for (Action action : this.actions)
            {
                String currentEntityName = action.getNameOfEntity();
                Entity currentEntity = null;
                try
                {
                    currentEntity = action.findEntityAccordingName(entities, currentEntityName);
                } catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    continue;
                    //throw new RuntimeException(e);
                }
                for (EntityInstance eI : currentEntity.getEntities())
                {
                    try
                    {
                        action.ActivateAction(eI);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}