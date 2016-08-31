package pallopeli;

/**
 * SimpleDirection is mainly used to make Ball bounce (three different ways to reset speed).
 * @author saara
 */

public enum SimpleDirection {
    
    HORIZONTAL(CompassDirection.WEST, CompassDirection.EAST, CompassDirection.NORTH, CompassDirection.SOUTH), 
    VERTICAL(CompassDirection.NORTH, CompassDirection.SOUTH, CompassDirection.WEST, CompassDirection.EAST),
    DIAGONAL(null, null, null, null);
    
    CompassDirection first;
    CompassDirection second;
    CompassDirection firstPerpendicular;
    CompassDirection secondPerpendicular;
    
    SimpleDirection(CompassDirection first, CompassDirection second, CompassDirection firstPerpendicular, CompassDirection secondPerpendicular) {
        this.first = first;
        this.second = second;
        this.firstPerpendicular = firstPerpendicular;
        this.secondPerpendicular = secondPerpendicular;
    }

    public CompassDirection getAssociatedCompassDirection(int i) {
        if (i == 1) {
            return first;
        }
        if (i == 2) {
            return second;
        }
        return null;
    }
    public CompassDirection getPerpendicularCompassDirection(int i) {
        if (i == 1) {
            return firstPerpendicular;
        }
        if (i == 2) {
            return secondPerpendicular;
        }
        return null;
    }    

    
}
