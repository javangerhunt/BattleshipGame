package ch.muehlemann.schiffversenken;

import java.util.ArrayList;
import java.util.List;

// class battleship for the determination of its length
public class Battleship {
    // each battleship has a start...
    private Position start;
    // ... and an end
    private Position end;

    // a List generated to record/save the hit positions
    private List<Position> hittedPositions = new ArrayList<>();

    public Battleship(Position start, Position end) {
        this.start = start;
        this.end = end;
    }
    // to determine whether a ship sunk or not. If all coordinates of the ship were hit
    public boolean isSunk() {
        for (Position shipCoordinate : getShipCoordinates()) {
            if (!hittedPositions.contains(shipCoordinate)) {
                return false;
            }
        }
        return true;
    }
    /*
     to get the ship coordinates the end coordinate of the ship (i.e. the higher number of y or x) gets subtracted by
     the start coordinate and saved into a list
     */
    public List<Position> getShipCoordinates() {
        int differenceX = end.getX() - start.getX();
        int differenceY = end.getY() - start.getY();
        List<Position> shipCoordinates = new ArrayList<>();
        if (differenceX != 0) {
            for (int i = 0; i <= differenceX; i++) {
                shipCoordinates.add(new Position(i + start.getX(), start.getY()));
            }
            return shipCoordinates;
        } else if (differenceY != 0) {
            for (int i = 0; i <= differenceY; i++) {
                shipCoordinates.add(new Position(start.getX(), i + start.getY()));
            }
            return shipCoordinates;
        } else {
            shipCoordinates.add(start);
            return shipCoordinates;
        }
    }
    // the ship is hit if the coordinates of the ship comply with the field the player hit, i.e. pos.getX()/pos.getY()
    public boolean isHit(Position pos) {
        if (start.getX() <= pos.getX() && pos.getX() <= end.getX() && start.getY() <= pos.getY() && pos.getY() <= end.getY()) {
            hittedPositions.add(pos);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Battleship{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
