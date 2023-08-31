package pSystem;

import pEntity.Coordinate;

public enum Direction {
    UP {
        @Override
        Coordinate getNextCoordinate(Coordinate cor, int gridRows, int gridCols) {
            Coordinate coordinate = new Coordinate();
            coordinate.setCol(cor.getCol());
            coordinate.setRow((cor.getRow()-1) % gridRows);
            return coordinate;
        }
    },
    DOWN {
        @Override
        Coordinate getNextCoordinate(Coordinate cor, int gridRows, int gridCols) {
            Coordinate coordinate = new Coordinate();
            coordinate.setCol(cor.getCol());
            coordinate.setRow((cor.getRow() + 1) % gridRows);
            return coordinate;
        }
    },
    LEFT{
        @Override
        Coordinate getNextCoordinate(Coordinate cor, int gridRows, int gridCols) {
            Coordinate coordinate = new Coordinate();
            coordinate.setRow(cor.getRow());
            coordinate.setCol((cor.getCol()-1) % gridCols);
            return coordinate;
        }
    },
    RIGHT{
        @Override
        Coordinate getNextCoordinate(Coordinate cor, int gridRows, int gridCols) {
            Coordinate coordinate = new Coordinate();
            coordinate.setRow(cor.getRow());
            coordinate.setCol((cor.getCol()+1) % gridCols);
            return coordinate;
        }
    };

    abstract Coordinate getNextCoordinate(Coordinate cor, int gridRows, int gridCols);
}