package pSystem;

import pEntity.Coordinate;
import pEntity.EntityInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityInstancesCircularGrid
{
    private  int numRows;
    private  int numCols;
    private EntityInstance[][] grid;


    public void initEntityInstancesCircularGrid(int numRows, int numCols )
    {
        this.numRows = numRows;
        this.numCols = numCols;
        this.grid = new EntityInstance[numRows][numCols];
    }

    boolean isCellEmpty(Coordinate c)
    {
        return grid[c.getRow()][c.getCol()] == null;
    }

    public boolean setEntityInstanceInCell(EntityInstance entityInstance, Coordinate c) {
        if(isCellEmpty(c))
        {
            entityInstance.setCoordinate(c);
            int circularRow = c.getRow();
            int circularCol = c.getCol();
            grid[circularRow][circularCol] = entityInstance;
            return true;
        }
        else {
            return false;
        }
    }

    public EntityInstance getEntityInstanceByCoordinate(Coordinate c) {
        return grid[c.getRow() % numRows][c.getCol() % numCols];
    }

    public void generateMove(EntityInstance entityInstance)
    {
        List<Direction> availableDirections = getAvailableDirections(entityInstance.getCoordinate());

        if (!availableDirections.isEmpty()) {
            Random r = new Random();
            int pick = r.nextInt(availableDirections.size());
            Direction randomDirection = availableDirections.get(pick);
            Coordinate newCoordinate = randomDirection.getNextCoordinate(entityInstance.getCoordinate(), this.numRows, this.numCols);
            entityInstance.setCoordinate(newCoordinate);
            setEntityInstanceInCell(entityInstance, newCoordinate);
        }
    }

    public List<Direction> getAvailableDirections(Coordinate coordinate) {
        List<Direction> availableDirections = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            Coordinate newCoordinate = direction.getNextCoordinate(coordinate, this.numRows, this.numCols);
            if (isWithinBounds(newCoordinate) && isCellEmpty(newCoordinate)) {
                availableDirections.add(direction);
            }
        }

        return availableDirections;
    }

    public boolean isWithinBounds(Coordinate coordinate) {
        int row = coordinate.getRow();
        int col = coordinate.getCol();
        return row >= 0 && row < this.numRows && col >= 0 && col < this.numCols;
    }
}