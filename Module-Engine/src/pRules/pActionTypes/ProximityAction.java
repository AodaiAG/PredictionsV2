package pRules.pActionTypes;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pDTOS.ActionsDTO.ActionDTO;
import pDTOS.ActionsDTO.ProximityActionDTO;
import pEntity.Coordinate;
import pEntity.Entity;
import pEntity.EntityInstance;
import pExpression.AuxiliaryMethods;
import pExpression.Expression;
import pRules.Rule;
import pSystem.EntityInstancesCircularGrid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void ActivateAction(int currTick, EntityInstance ... args) throws Exception
    {
        EntityInstancesCircularGrid grid = this.functions.getWorld().getGrid();
        Entity targetEntity = this.findEntityAccordingName(this.functions.getWorld().getEntities(), targetEntityName);
        EntityInstance instanceFromTargetEntity = targetEntity.getEntities().get(1);
        Expression expression = new Expression(getFunctions(), args[0]);
        String valAndDataType = expression.evaluateExpression(of);
        int indexOfPeriod = valAndDataType.indexOf(".");
        String ofVal = valAndDataType.substring(indexOfPeriod);
        if(isInCycleAtDepth(args[0], instanceFromTargetEntity, grid, Integer.parseInt(ofVal)))
        {
            for (Action action: actionList)
            {
                action.ActivateAction(currTick, args[0], instanceFromTargetEntity);
            }
        }
    }

    public boolean isInCycleAtDepth(EntityInstance entityInstance1, EntityInstance entityInstance2, EntityInstancesCircularGrid grid, int depth)
    {
        if (depth < 1)
        {
            throw new IllegalArgumentException("Depth must be greater than or equal to 1.");
        }

        Set<EntityInstance> neighborsAtDepth = new HashSet<>();
        Set<EntityInstance> neighborsAtPreviousDepth = new HashSet<>();
        neighborsAtPreviousDepth.add(entityInstance1);

        for (int d = 1; d <= depth; d++)
        {
            Set<EntityInstance> currentDepthNeighbors = new HashSet<>();

            for (EntityInstance neighborEntity : neighborsAtPreviousDepth) {
                List<EntityInstance> immediateNeighbors = getImmediateNeighbors(neighborEntity, grid);
                currentDepthNeighbors.addAll(immediateNeighbors);
            }

            neighborsAtPreviousDepth = currentDepthNeighbors;

            if (d == depth && neighborsAtPreviousDepth.contains(entityInstance2)) {
                return true;
            }
        }

        return false;
    }

    public List<EntityInstance> getImmediateNeighbors(EntityInstance primaryEntityInstance, EntityInstancesCircularGrid grid) {
        List<EntityInstance> neighborEntities = new ArrayList<>();
        Coordinate currentCoordinate = primaryEntityInstance.getCoordinate();
        int numRows = grid.getNumRows();
        int numCols = grid.getNumCols();

        // Define relative offsets for 8 neighbors (including diagonals)
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++)
        {
            int newRow = (currentCoordinate.getRow() + rowOffsets[i] + numRows) % numRows;
            int newCol = (currentCoordinate.getCol() + colOffsets[i] + numCols) % numCols;
            Coordinate newCoordinate = new Coordinate(newRow, newCol);
            EntityInstance neighborEntity = grid.getEntityInstanceByCoordinate(newCoordinate) ;
            if (neighborEntity != null) {
                neighborEntities.add(neighborEntity);
            }
        }

        return neighborEntities;
    }


    public void initFromXML(Node ActionNode) throws Exception
    {
       try
       {
           Element actionElement=(Element) ActionNode;

           Element betweenElement = (Element)(actionElement).getElementsByTagName("PRD-between").item(0);
           this.sourceEntityName = betweenElement.getAttribute("source-entity");
           this.targetEntityName = betweenElement.getAttribute("target-entity");

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
                   this.actionList.add(p,justToCallFunction.createAction(actionListNodes.item(p)));
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
        return "proximity";
    }

    @Override
    public String getNameOfEntity() {
        return sourceEntityName;
    }
}