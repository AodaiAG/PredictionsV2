package Grid;

import pEntity.Coordinate;
import pEntity.EntityInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityInstancesCircularGrid implements Cloneable
{
    private int numRows;

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    private int numCols;

    private EntityInstance[][] grid;

    public void initEntityInstancesCircularGrid(int numRows, int numCols)
    {
        this.numRows = numRows;
        this.numCols = numCols;
        this.grid = new EntityInstance[numRows][numCols];
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        EntityInstancesCircularGrid clonedGrid = (EntityInstancesCircularGrid) super.clone();

        // Clone the grid
        clonedGrid.grid = new EntityInstance[this.numRows][this.numCols];
        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                if (this.grid[row][col] != null) {
                    // Clone the EntityInstance objects within the grid
                    clonedGrid.grid[row][col] = (EntityInstance) this.grid[row][col].clone();
                }
            }
        }

        return clonedGrid;
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
            removeInstanceFromCell(entityInstance);
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

    public List<Coordinate> getEmptyCoordinates() {
    List<Coordinate> emptyCoordinates = new ArrayList<>();

    for (int x = 0; x < numRows; x++) {
        for (int y = 0; y < numCols; y++) {
            if (grid[x][y] == null) {
                emptyCoordinates.add(new Coordinate(x, y));
            }
        }
    }

    return emptyCoordinates;
}

    public void removeInstanceFromCell(EntityInstance entityInstance) {
        Coordinate c = entityInstance.getCoordinate();
        grid[c.getRow()][c.getCol()] = null;
    }

    public Coordinate getRandomEmptyCoordinate() {
        List<Coordinate> emptyCoordinates = getEmptyCoordinates();

        if (!emptyCoordinates.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(emptyCoordinates.size());
            return emptyCoordinates.get(randomIndex);
        } else {
            // Handle the case when there are no empty cells available
            throw new IllegalStateException("No empty cells available in the grid.");
        }
    }
}