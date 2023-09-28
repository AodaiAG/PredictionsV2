package pRules.pActionTypes;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.ActionsDTO.ProximityActionDTO;
import pEntity.Coordinate;
import pEntity.Entity;
import pEntity.EntityInstance;
import pExceptionHandler.ActionExceptionHandler;
import pExpression.AuxiliaryMethods;
import pExpression.Expression;
import pRules.Rule;
import Grid.EntityInstancesCircularGrid;

import java.util.ArrayList;
import java.util.List;

public class ProximityAction extends Action
{
     private String sourceEntityName;

    private String targetEntityName;

    String of;

    List<Action> actionList = new ArrayList<>();

    public ActionDTO convertToDTO()
    {
        ProximityActionDTO proximityActionDTO=new ProximityActionDTO();
        proximityActionDTO.setNameOfAction("proximity");
        if (this.prDsecondaryEntity != null) {
            proximityActionDTO.setSecondaryEntityNameActionWorksOn(getPrDsecondaryEntity().getNameOfSecondEntity());

        }
        proximityActionDTO.setSourceEntity(sourceEntityName);
        proximityActionDTO.setTargetEntity(targetEntityName);
        proximityActionDTO.setOf(of);
        proximityActionDTO.setNumberOfActions(this.actionList.size());
        return proximityActionDTO;
    }


    public void setFunctions(AuxiliaryMethods functions)
    {
        super.functions = functions;
    }

    @Override
    public void ActivateAction(int currTick, EntityInstance ... args) throws Exception {
        EntityInstance entityInstanceToActivateActionOn = null;
        EntityInstance entityInstanceNeighbor = null;
        EntityInstancesCircularGrid grid = this.functions.getWorld().getGrid();
        Entity targetEntity = this.findEntityAccordingName(this.functions.getWorld().getEntities(), targetEntityName);
        Expression expression = new Expression(getFunctions(), args[0]);
        String valAndDataType = expression.evaluateExpression(of);
        int indexOfPeriod = valAndDataType.indexOf(".");
        String ofVal = valAndDataType.substring(indexOfPeriod + 1);
        int depth = (int) Float.parseFloat(ofVal);
        List<EntityInstance> neighbors = getNeighborsAtDepth(grid, args[0].getCoordinate(), depth, targetEntityName);

        if(!neighbors.isEmpty()) {
            EntityInstance entityInstanceFromTarget = neighbors.get(0);
            String primaryInstanceName;
            for (Action action : actionList) {
                primaryInstanceName = action.getNameOfEntity();
                if (primaryInstanceName.equals(args[0].getNameOfEntity())) {
                    entityInstanceToActivateActionOn = args[0];
                    entityInstanceNeighbor = entityInstanceFromTarget;

                } else if (primaryInstanceName.equals(entityInstanceFromTarget.getNameOfEntity())) {

                    entityInstanceToActivateActionOn = entityInstanceFromTarget;
                    entityInstanceNeighbor = args[0];
                }
                action.ActivateAction(currTick, entityInstanceToActivateActionOn, entityInstanceNeighbor);
            }
        }
    }

    public static List<EntityInstance> getNeighborsAtDepth(EntityInstancesCircularGrid grid, Coordinate coordinate, int depth, String targetEntityName) {
        List<EntityInstance> neighbors = new ArrayList<>();

        int x = coordinate.getRow();
        int y = coordinate.getCol();

        for (int i = -depth; i <= depth; i++)
        {
            for (int j = -depth; j <= depth; j++)
            {
                if (i == 0 && j == 0)
                {
                    // Skip the current cell (the center)
                    continue;
                }

                int neighborX = (x + i) % grid.getNumRows();
                int neighborY = (y + j) % grid.getNumCols();

                // Ensure the coordinates are non-negative
                neighborX = (neighborX + grid.getNumRows()) % grid.getNumRows();
                neighborY = (neighborY + grid.getNumCols()) % grid.getNumCols();

                EntityInstance neighbor = grid.getEntityInstanceByCoordinate(new Coordinate(neighborX, neighborY));
                if (neighbor != null && neighbor.getNameOfEntity().equals(targetEntityName)) {
                    neighbors.add(neighbor);
                }
            }
        }

        return neighbors;
    }

    public void initFromXML(Node ActionNode) throws Exception
    {
       try
       {
           Element actionElement=(Element) ActionNode;

           Element betweenElement = (Element)(actionElement).getElementsByTagName("PRD-between").item(0);

           ActionExceptionHandler actionExceptionHandler = new ActionExceptionHandler();
           actionExceptionHandler.checkIfEntityExists(this.functions.getWorld().getEntities(), betweenElement.getAttribute("source-entity"));
           actionExceptionHandler.checkIfEntityExists(this.functions.getWorld().getEntities(), betweenElement.getAttribute("target-entity"));

           this.sourceEntityName = betweenElement.getAttribute("source-entity");
           this.targetEntityName = betweenElement.getAttribute("target-entity");

           Element envDepthElement = (Element) actionElement.getElementsByTagName("PRD-env-depth").item(0);
           actionExceptionHandler.isExpressionValid( envDepthElement.getAttribute("of"), sourceEntityName, this.functions.getWorld(), "proximity");
           this.of = envDepthElement.getAttribute("of");
           // adding actions
           NodeList actionListNodes = actionElement.getElementsByTagName("PRD-action");
           for (int p = 0; p < actionListNodes.getLength(); p++)
           {
               if (actionListNodes.item(p).getNodeType() == Node.ELEMENT_NODE)
               {
                   Rule justToCallFunction = new Rule();
                   justToCallFunction.setFunctions(this.getFunctions());
                   this.actionList.add(p,justToCallFunction.createAction(actionListNodes.item(p)));
               }
           }
       }
       catch(IllegalArgumentException iAE)
       {
           throw new IllegalArgumentException("Error at action: Proximity. of is not an Integer");
       }
       catch (Exception e)
       {
           throw  e ;
       }
    }

    @Override
    public String getNameOfAction() {
        return "proximity";
    }

    @Override
    public String getNameOfEntity() {
        return sourceEntityName;
    }
}