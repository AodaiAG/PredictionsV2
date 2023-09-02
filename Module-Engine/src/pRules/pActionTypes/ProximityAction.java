package pRules.pActionTypes;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pEntity.EntityInstance;
import pExpression.AuxiliaryMethods;
import pRules.Rule;
import pSystem.EntityInstancesCircularGrid;

import java.util.ArrayList;
import java.util.List;

public class ProximityAction extends Action
{

    String sourceEntity;
    String targetEntity;
    String of;
    List<Action> actionList=new ArrayList<>();


    public void setFunctions(AuxiliaryMethods functions)
    {
        super.functions = functions;
    }

    @Override
    public void ActivateAction(EntityInstance... args) throws Exception
    {

        EntityInstancesCircularGrid grid =this.functions.getWorld().getGrid();
        //boolean iScircual=checkCircual(args,grid);







    }


    public void initFromXML(Node ActionNode) throws Exception
    {
       try
       {
           Element actionElement=(Element) ActionNode;

           Element betweenElement = (Element)(actionElement).getElementsByTagName("PRD-between").item(0);
           this.sourceEntity = betweenElement.getAttribute("source-entity");
           this.targetEntity = betweenElement.getAttribute("target-entity");

           Element envDepthElement = (Element) actionElement.getElementsByTagName("PRD-env-depth").item(0);
           this.of = envDepthElement.getAttribute("of");
           // adding actions
           NodeList actionListNodes = actionElement.getElementsByTagName("PRD-action");
           for (int p = 0; p < actionListNodes.getLength(); p++)
           {
               if (actionListNodes.item(p).getNodeType() == Node.ELEMENT_NODE)
               {
                   Rule justToCallFunction = new Rule();
                   justToCallFunction.setFunctions(this.getFunctions());
                   this.actionList.add(p,justToCallFunction.CreateAction(actionListNodes.item(p)));
               }
           }


       }
       catch (Exception e)
       {
           throw  e ;
       }

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
