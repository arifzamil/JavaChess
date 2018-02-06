package pieces;

import comp124graphics.Image;

public class Pawn extends Piece {

    public Pawn(String color, String pos) {
        super(color, pos);
        this.type = "P";
        if(color == "white") {
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/wP.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/wP.png");
        }
        else {
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/bP.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/bP.png");
        }
    }
}
