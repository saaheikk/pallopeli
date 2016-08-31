/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pallopeli.objects;

import java.awt.Shape;
import pallopeli.CompassDirection;

/**
 *
 * @author saara
 */
public abstract class Border {
    protected Piece pieceToGuard;
    protected CompassDirection directionOfEdgeToGuard;
    protected int radiusOfBall;
    
   
    
    protected boolean active;

    protected Shape borderShape;
    
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
}
