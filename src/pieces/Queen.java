package pieces;

import comp124graphics.*;

public class Queen extends Piece {

    public Queen(String color, String pos) {
        super(color, pos);
        this.type = "Q";
        if(color.equals("white")) {
            this.capImageFile = "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/wQ.png";
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/wQ.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/wQ.png");
        }
        else {
            this.capImageFile = "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/bQ.png";
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/bQ.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/bQ.png");
        }
    }
}
