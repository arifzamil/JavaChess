package pieces;

import comp124graphics.Image;
import externalBoard.Chess;

public class Bishop extends Piece {


    public Bishop(String color, String pos) {
        super(color, pos);
        this.type = "B";
        if(color == "white") {
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/wB.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/wB.png");
        }
        else {
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/bB.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/bB.png");
        }
    }
}
