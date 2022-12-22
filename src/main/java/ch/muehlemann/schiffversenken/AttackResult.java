package ch.muehlemann.schiffversenken;

import java.util.List;

// wrapper class for the result
public class AttackResult {
    private AttackResultEnum result;
    private List<Position> coordinates;

    // constructor
    public AttackResult(AttackResultEnum result, List<Position> coordinates) {
        this.result = result;
        this.coordinates = coordinates;
    }

    // constructor
    public AttackResult(AttackResultEnum result) {
        this.result = result;
    }

    public AttackResultEnum getResult() {
        return result;
    }

    // gets the coordinates of the hit button out of the list and returns them
    public List<Position> getCoordinates() {
        return coordinates;
    }

    // writes the attack result into the console
    @Override
    public String toString() {
        return "AttackResult{" +
                "result=" + result +
                ", coordinates=" + coordinates +
                '}';
    }

    // different kind of attack results are possible
    public enum AttackResultEnum {
        HIT,
        SUNK,
        MISS;
    }
}


