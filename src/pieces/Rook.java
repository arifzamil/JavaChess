package pieces;

import comp124graphics.Image;

public class Rook extends Piece {

    public Rook(String color, String pos) {
        super(color, pos);
        this.type = "R";

        if(color == "white") {
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/wR.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/wR.png");
        }
        else {
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/bR.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/bR.png");
        }
    }
}
