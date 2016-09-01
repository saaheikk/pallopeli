package pallopeli.objects;

import java.awt.Point;
import java.util.ArrayList;
import pallopeli.SimpleDirection;
import pallopeli.CompassDirection;

/**
 * Board contains all the pieces of the game as a matrix and its main task is to build walls according to commands from user and ball.
 * @author saara
 */

public class Board {
    private int width; // how many pieces
    private int height; // how many pieces
    private int sizeOfPieces; // pixels
    private Piece[][] pieces; // matrix containing all the pieces

    /**
     * Constructor for a new Board.
     * Sets all the neighbors.
     * Sets Borders for all Pieces on Board by using the parameter sizeOfPieces.
     * Resets active borders according to the default starting state of board.
     * @param width
     * @param height
     * @param sizeOfPieces 
     */
    public Board(int width, int height, int sizeOfPieces) {
        if (width > 4 && height > 4 && width <= 30 && height <= 30) { 
            this.width = width;
            this.height = height;
            this.sizeOfPieces = sizeOfPieces;            
            this.pieces = new Piece[height][width];
            this.initializePieces();
            this.setNeighbors();
            this.setMainBordersForAllPieces(sizeOfPieces / 2);
            this.resetActiveBordersOfWallpieces();
        }
    }
    
    /**
     * Helper method to initialize pieces so that wall pieces surround the board.
     */
    protected void initializePieces() {
        // top row (all pieces are wall)
        for (int w = 0; w < this.width; w++) {
            this.pieces[0][w] = new Piece(w, 0, true, sizeOfPieces);
        }        
        // rows between top and bottom (wall - normal - wall)
        for (int h = 1; h < this.height - 1; h++) {
            this.pieces[h][0] = new Piece(0, h, true, sizeOfPieces);
            for (int w = 1; w < this.width - 1; w++) {
                this.pieces[h][w] = new Piece(w, h, false, sizeOfPieces);
            }
            this.pieces[h][this.width - 1] = new Piece((this.width - 1), h, true, sizeOfPieces);
        }
        // bottom row (all pieces are wall)
        for (int w = 0; w < this.width; w++) {
            this.pieces[this.height - 1][w] = new Piece(w, (this.height - 1), true, sizeOfPieces);
        }        
    }
    
    /**
     * Helper method to make Piece know its neighbors.
     */
    public void setNeighbors() {
        for (int h = 0; h < this.height; h++) {                       
            for (int w = 0; w < this.width; w++) {
                for (CompassDirection direction : CompassDirection.values()) {
                    int neighborX = w + direction.getX();
                    int neighborY = h + direction.getY();
                    if (this.positionIsInBounds(neighborX, neighborY)) {
                        this.pieces[h][w].setNeighbor(direction, this.pieces[neighborY][neighborX]);
                        
                    }
                }
                System.out.println("");
            }
        }                   
    }
    /**
     * Helper method for detecting pieces that are at risk to collide with Ball.
     * @param point Ball's current location.
     * @param howMuchIsNearby 
     * @return List of pieces that are close to the given point.
     */
    public ArrayList<Piece> getWallPiecesNearby(Point point, int howMuchIsNearby) {
        ArrayList<Piece> wallPiecesNearby = new ArrayList<>();
        for (int h = 0; h < this.height; h++) {                       
            for (int w = 0; w < this.width; w++) {
                Piece piece = this.getPiece(w, h);
                if (piece.isWall()) {                    
                    if (point.x - howMuchIsNearby < piece.getCenterCoordinateX() && piece.getCenterCoordinateX() < point.x + howMuchIsNearby) {
                        if (point.y - howMuchIsNearby < piece.getCenterCoordinateY() && piece.getCenterCoordinateY() < point.y + howMuchIsNearby) {
                            wallPiecesNearby.add(piece);
                        }
                    }
                }
            }
        }   
        return wallPiecesNearby;
    }
    /**
     * Helper method to check if given position is located within the boundaries of Board.
     * @param x Tested coordinate x.
     * @param y Tested coordinate y.
     * @return True if the given position is allowed and false if not.
     */
    public boolean positionIsInBounds(int x, int y) {
        if (0 <= x && x < this.width && 0 <= y && y < this.height) {
            return true;
        }
        return false;
    }

    // methods for setting and resetting borders for pieces on board
    /**
     * Sets borders to guard in NORTH, WEST, SOUTH and EAST.
     * @param radiusOfBall 
     */
    public void setMainBordersForAllPieces(int radiusOfBall) {
        for (int h = 0; h < this.height; h++) {                       
            for (int w = 0; w < this.width; w++) {
                this.getPiece(w, h).setMainBorders(radiusOfBall);
            }
        }          
    }
    /**
     * Resets the activity of borders according to the current state of Board.
     */
    public void resetActiveBordersOfWallpieces() {
        // reset borders of the wallpieces after creating the board or turning pieces into wall
        for (int h = 0; h < this.height; h++) {                       
            for (int w = 0; w < this.width; w++) {
                this.getPiece(w, h).resetActivityOfBorders();
            }
        }           
    }
    /**
     * Returns the Piece that fully encloses given Point.
     * @param p
     * @return Piece or null if given Point is between Pieces.
     */
    public Piece getPieceThatEnclosesPoint(Point p) {
        if (p.x % this.sizeOfPieces == 0 || p.y % this.sizeOfPieces == 0) {
            return null; // check for lines between the pieces
        }
        int x = p.x / this.sizeOfPieces;
        int y = p.y / this.sizeOfPieces; 
        return this.getPiece(x, y);
    }
    /**
     * Calculates all the Pieces on Board.
     * @return Integer.
     */
    public int numberOfAllPieces() {
        return this.height * this.width;
    }
    /**
     * Calculates all the wall Pieces on Board.
     * @return Integer.
     */
    public int numberOfWallPieces() {
        int n = 0;
        for (int h = 0; h < this.height; h++) {                       
            for (int w = 0; w < this.width; w++) {
                if (this.getPiece(w, h).isWall()) {
                    n++;
                }
            }
        }
        return n;
    }  
    // getter & setters

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Piece[][] getPieces() {
        return pieces;
    }
    
    public Piece getPiece(int x, int y) {
        if (this.positionIsInBounds(x, y)) {
            return this.pieces[y][x];
        }
        return null;
    }

    public int getSizeOfPieces() {
        return sizeOfPieces;
    }
    
  
    
    @Override
    public String toString() {
        String boardToString = "";
        for (int h = 0; h < this.height; h++) {                       
            for (int w = 0; w < this.width; w++) {
                boardToString += this.getPiece(w, h).toString() + " ";
            }
            boardToString += "\n";
        }
        return boardToString;
    }
 
}
