package pallopeli;

/**
 * SimpleDirection is mainly used to make Ball bounce (three different ways to reset speed).
 * @author saara
 */

public enum SimpleDirection {
    
    HORIZONTAL(CompassDirection.WEST, CompassDirection.EAST), 
    VERTICAL(CompassDirection.NORTH, CompassDirection.SOUTH);
    
    CompassDirection first;
    CompassDirection second;
    
    SimpleDirection(CompassDirection first, CompassDirection second) {
        this.first = first;
        this.second = second;
    }

    public CompassDirection getFirst() {
        return first;
    }

    public void setFirst(CompassDirection first) {
        this.first = first;
    }

    public CompassDirection getSecond() {
        return second;
    }

    public void setSecond(CompassDirection second) {
        this.second = second;
    }
    
    
}
