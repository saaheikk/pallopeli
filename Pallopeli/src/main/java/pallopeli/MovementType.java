package pallopeli;

/**
 * MovementType is used to describe Collision's type: is Ball moving into Piece or away from it.
 * MovementType is very helpful in the case where Ball has to bounce twice over one time step.
 * @author saara
 */
public enum MovementType {
    IN, OUT;
}
