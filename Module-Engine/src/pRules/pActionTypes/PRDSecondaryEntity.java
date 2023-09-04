package pRules.pActionTypes;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pEntity.EntityInstance;
import pEntity.Entity;
import pExpression.AuxiliaryMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PRDSecondaryEntity {

    protected AuxiliaryMethods functions;
    String count;
    ConditionAction condition;
    private String nameOfSecondEntity;
    private List<EntityInstance> listOfInstancesToFetch;

    public PRDSecondaryEntity() {
        nameOfSecondEntity = "";
        count = "";
        condition = new ConditionAction();
        listOfInstancesToFetch = new ArrayList<>();
    }

    public String getNameOfSecondEntity() {
        return nameOfSecondEntity;
    }

    public void setNameOfSecondEntity(String nameOfSecondEntity) {
        this.nameOfSecondEntity = nameOfSecondEntity;
    }

    public List<EntityInstance> getListOfInstancesToFetch() {
        return listOfInstancesToFetch;
    }

    public void setListOfInstancesToFetch(List<EntityInstance> listOfInstancesToFetch) {
        this.listOfInstancesToFetch = listOfInstancesToFetch;
    }

    public int initFromXML(Node ActionNode) {
        int times;
        try {
            NodeList secondaryEntityNodes = ((Element) (ActionNode)).getElementsByTagName("PRD-secondary-entity");
            for (int i = 0; i < secondaryEntityNodes.getLength(); i++) {
                times = secondaryEntityNodes.getLength();
                Element secondaryEntityElement = (Element) secondaryEntityNodes.item(i);

                // Get entity attribute of PRD-secondary-entity
                nameOfSecondEntity = secondaryEntityElement.getAttribute("entity");

                // Get PRD-selection element within PRD-secondary-entity
                Element selectionElement = (Element) secondaryEntityElement.getElementsByTagName("PRD-selection").item(0);

                // Get count attribute of PRD-selection
                count = selectionElement.getAttribute("count");

                // Get PRD-condition element within PRD-selection
                Element conditionElement = (Element) selectionElement.getElementsByTagName("PRD-condition").item(0);

                // Get attributes of PRD-condition
                String singularityType = conditionElement.getAttribute("singularity");

                if (singularityType.equals("single")) {
                    SingleCondition single = new SingleCondition();
                    single.setFunctions(this.functions);

                    single.setNameofEntity(conditionElement.getAttributes().getNamedItem("entity").getTextContent());

                    single.setNameofProperty(conditionElement.getAttributes().getNamedItem("property").getTextContent());

                    single.setOperator(conditionElement.getAttributes().getNamedItem("operator").getTextContent());

                    single.setValue(conditionElement.getAttributes().getNamedItem("value").getTextContent());

                    this.condition = single;
                    return times;
                }

                if (singularityType.equals("multiple")) {
                    MultipleCondition toCallFunc = new MultipleCondition();
                    toCallFunc.setFunctions(this.functions);
                    condition = toCallFunc.createMultipleCondition(conditionElement);
                    return times;
                }
            }
        } catch (Exception e) {
            System.out.println("my name is");
        }

        return 0;
    }

    public AuxiliaryMethods getFunctions() {
        return functions;
    }

    public void setFunctions(AuxiliaryMethods functions) {
        this.functions = functions;
    }

    public void calcInstancesToFetch(Action action, Entity secondaryEntity)
    {
        listOfInstancesToFetch = null;
        List<EntityInstance> filteredEntities = new ArrayList<>();
        if (condition != null) {
            for (EntityInstance eI : secondaryEntity.getEntities()) {
                try {
                    condition.ActivateAction(eI);
                    if (condition.getConditionResult()) {
                        filteredEntities.add(eI);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            filteredEntities = secondaryEntity.getEntities();
        }

        boolean isNumber = count.matches("\\d+");
        int numericCount = 0;
        if (isNumber) {
            numericCount = Integer.parseInt(count);
        } else {
            if (count.equals("ALL")) {
                numericCount = filteredEntities.size();
            }
        }

        if (numericCount < filteredEntities.size()) {
            Random random = new Random();
            while (listOfInstancesToFetch.size() < numericCount) {
                int randomIndex = random.nextInt(filteredEntities.size());
                EntityInstance randomEntity = filteredEntities.get(randomIndex);
                listOfInstancesToFetch.add(randomEntity);
            }
        } else {
            listOfInstancesToFetch = filteredEntities;
        }
    }
}