package pSystem;

import pEntity.Coordinate;

public enum Direction
{
    UP {
        @Override
        Coordinate getNextCoordinate(Coordinate cor, int gridRows, int gridCols) {
            return new Coordinate((cor.getRow() - 1 + gridRows) % gridRows, cor.getCol());
        }
    },
    DOWN {
        @Override
        Coordinate getNextCoordinate(Coordinate cor, int gridRows, int gridCols) {
            return new Coordinate((cor.getRow() + 1) % gridRows, cor.getCol());
        }
    },
    LEFT{
        @Override
        Coordinate getNextCoordinate(Coordinate cor, int gridRows, int gridCols) {
            return new Coordinate(cor.getRow(), (cor.getCol() - 1 + gridCols) % gridCols);
        }
    },
    RIGHT{
        @Override
        Coordinate getNextCoordinate(Coordinate cor, int gridRows, int gridCols) {
            return new Coordinate(cor.getRow(), (cor.getCol() + 1) % gridCols);
        }
    };

    abstract Coordinate getNextCoordinate(Coordinate cor, int gridRows, int gridCols);
}