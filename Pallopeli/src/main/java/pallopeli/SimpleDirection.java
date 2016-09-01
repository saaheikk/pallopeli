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
    /**
     * Method used to eliminate copy paste in Wallbuilder.
     * @param i Closer / further.
     * @return CompassDirection closer to / further from origin.
     */

    public CompassDirection getAssociatedCompassDirection(int i) {
        if (i == 1) {
            return first;
        }
        if (i == 2) {
            return second;
        }
        return null;
    }
    /**
     * Method used to eliminate copy paste in Wallbuilder.
     * @param i Closer / further.
     * @return Perpendicular CompassDirection closer to / further from origin.
     */    
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
