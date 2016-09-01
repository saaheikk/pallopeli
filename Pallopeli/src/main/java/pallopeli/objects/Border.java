package pallopeli.objects;

import java.awt.Shape;
import pallopeli.CompassDirection;

public abstract class Border {
    protected Piece pieceToGuard;
    protected CompassDirection directionOfEdgeToGuard;
    protected int radiusOfBall;
    protected boolean active;
    protected Shape borderShape;
    /**
     * Constructor for new Border attached to given Piece.
     * @param piece
     * @param directionOfEdgeToGuard
     * @param radiusOfBall 
     */
    public Border(Piece piece, CompassDirection directionOfEdgeToGuard, int radiusOfBall) {
        this.pieceToGuard =  piece;
        this.directionOfEdgeToGuard = directionOfEdgeToGuard;
        this.radiusOfBall = radiusOfBall;
        this.active = false;
    }
    
    public boolean isActive() {
        return this.active;
    }
    public void setActivity(boolean activity) {
        this.active = activity;
    }

    public void setBorderShape(Shape borderShape) {
        this.borderShape = borderShape;
    }

    public Shape getBorderShape() {
        return this.borderShape;
    }
    @Override
    public String toString() {
        return "I am guarding " + this.directionOfEdgeToGuard;
    }
}
