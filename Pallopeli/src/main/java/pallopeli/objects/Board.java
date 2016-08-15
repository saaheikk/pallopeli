package pallopeli.objects;

import java.awt.Point;
import java.util.ArrayList;
import pallopeli.BuildingDirection;
import pallopeli.CompassDirection;

public class Board {
    private int width; // how many pieces
    private int height; // how many pieces
    private int sizeOfPieces; // pixels
    
    private Piece[][] pieces; // matrix containing all the pieces


    public Board(int width, int height, int sizeOfPieces) {
        if (width > 4 && height > 4 && width <= 30 && height <= 30) { 
            this.width = width;
            this.height = height;
            this.sizeOfPieces = sizeOfPieces;            
            this.pieces = new Piece[height][width];
            this.initializePieces();
            this.setNeighbors();  
        }
    }
    
   
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
    
    protected void setNeighbors() {
        for (int h = 0; h < this.height; h++) {                       
            for (int w = 0; w < this.width; w++) {
                try { 
                    this.pieces[h][w].setNorthNeighbor(this.pieces[h - 1][w]);                    
                } catch (Exception e) {
                    this.pieces[h][w].setNorthNeighbor(null);
                }
                try {
                    this.pieces[h][w].setEastNeighbor(this.pieces[h][w + 1]);   
                } catch (Exception e) {
                    this.pieces[h][w].setEastNeighbor(null);
                }
                try {
                    this.pieces[h][w].setSouthNeighbor(this.pieces[h + 1][w]); 
                } catch (Exception e) {
                    this.pieces[h][w].setSouthNeighbor(null);
                }
                try {
                    this.pieces[h][w].setWestNeighbor(this.pieces[h][w - 1]);
                } catch (Exception e) {
                    this.pieces[h][w].setWestNeighbor(null);
                    
                }
            }
        }                   
    }
    
    // this is a test version!!
    public void buildWall(int x, int y, BuildingDirection direction) {
        this.pieces[y][x].turnIntoWall();
        if (direction == BuildingDirection.HORIZONTAL) {
            this.buildWall(x, y, CompassDirection.EAST);
            this.buildWall(x, y, CompassDirection.WEST);
        } else if (direction == BuildingDirection.VERTICAL) {
            this.buildWall(x, y, CompassDirection.NORTH);
            this.buildWall(x, y, CompassDirection.SOUTH);
        }
        
    }
    // this is a test version!!    
    protected void buildWall(int x, int y, CompassDirection compassDirection) {
        boolean continues = this.pieces[y][x].turnNeighborIntoWall(compassDirection);
        while (continues) {
            continues = this.pieces[y][x].turnNeighborIntoWall(compassDirection);
            y--;        
        }
    }
  


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
        return this.pieces[y][x];
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
    

    
//    public ArrayList<Piece> getAlarmedWallPieces(Ball ball) {
//        ArrayList<Piece> alarmedWallPieces = new ArrayList<>();
//        for (int h = 0; h < this.height; h++) {                       
//            for (int w = 0; w < this.width; w++) {
//                if (this.getPiece(w, h).isWall()) {
//                    if (this.getPiece(w, h).hasBall(ball)) {
//                        alarmedWallPieces.add(this.getPiece(w, h));
//                    }                    
//                }
//            }
//        }  
//        return alarmedWallPieces;
//    }
    
    
    
    
    
    
    
    
    // helper method
    public ArrayList<Piece> getWallPiecesAsList() {
        ArrayList<Piece> wallPieces = new ArrayList<>();
        for (int h = 0; h < this.height; h++) {                       
            for (int w = 0; w < this.width; w++) {
                if (this.getPiece(w, h).isWall()) {
                    wallPieces.add(this.getPiece(w, h));
                }
            }
        }  
        return wallPieces;        
    }

}
