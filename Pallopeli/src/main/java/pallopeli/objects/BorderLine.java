package pallopeli.objects;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import pallopeli.CompassDirection;

public class BorderLine extends Border {

    /**
     * Constructor for a new Border that can de described as Line2D.
     * @param piece
     * @param directionOfEdgeToGuard
     * @param radiusOfBall 
     */
    public BorderLine(Piece piece, CompassDirection directionOfEdgeToGuard, int radiusOfBall) {
        super(piece, directionOfEdgeToGuard, radiusOfBall);

        Point firstCorner = piece.getCornerPoint(directionOfEdgeToGuard.getFirstSideOfMainDirection());
        Point secondCorner = piece.getCornerPoint(directionOfEdgeToGuard.getSecondSideOfMainDirection());

        int firstX = firstCorner.x + directionOfEdgeToGuard.getX() * radiusOfBall;
        int firstY = firstCorner.y + directionOfEdgeToGuard.getY() * radiusOfBall;      
        int secondX = secondCorner.x + directionOfEdgeToGuard.getX() * radiusOfBall;
        int secondY = secondCorner.y + directionOfEdgeToGuard.getY() * radiusOfBall;
        this.borderShape = new Line2D.Float(firstX, firstY, secondX, secondY);

    }
    
    @Override
    public Line2D getBorderShape() {
        return (Line2D) this.borderShape;
    }
    /**
     * Helper method to extend BorderLine to cover the corners of Piece.
     * Used because I don't have time to implement BorderArc.
     * Game works quite well without BorderArcs, however.
     */
    public void extendLine() {
        Point2D firstEnd = this.getBorderShape().getP1();
        Point2D secondEnd = this.getBorderShape().getP2();
        int coefficientX = Math.abs(this.directionOfEdgeToGuard.getY());
        int coefficientY = Math.abs(this.directionOfEdgeToGuard.getX());
        int newFirstEndX = (int) firstEnd.getX() - coefficientX * this.radiusOfBall;
        int newFirstEndY = (int) firstEnd.getY() - coefficientY * this.radiusOfBall;
        int newSecondEndX = (int) secondEnd.getX() + coefficientX * this.radiusOfBall;
        int newSecondEndY = (int) secondEnd.getY() + coefficientY * this.radiusOfBall;
        this.borderShape = new Line2D.Float(newFirstEndX, newFirstEndY, newSecondEndX, newSecondEndY);
    }
     
    /**
     * Method used to eliminate copy paste in CollisionDetector.
     * @return Integer to indicate which direction Ball must bounce.
     */
    public int getBouncingDirection() {
        if (this.directionOfEdgeToGuard == CompassDirection.NORTH || this.directionOfEdgeToGuard == CompassDirection.WEST) {
            return -1;
        } else if (this.directionOfEdgeToGuard == CompassDirection.SOUTH || this.directionOfEdgeToGuard == CompassDirection.EAST) {
            return 1;
        }
        return 0;
    }
    /**
     * Returns the x-coordinate of vertical BorderLine and y-coordinate of horizontal BorderLine.
     * @return Coordinate as integer.
     */
    public int getLineCoordinate() {
        Line2D line = (Line2D) this.borderShape;
        int coefX = Math.abs(this.directionOfEdgeToGuard.getX());
        int coefY = Math.abs(this.directionOfEdgeToGuard.getY());        
        return (int) (coefX * line.getX1() + coefY * line.getY1());
    }

}
