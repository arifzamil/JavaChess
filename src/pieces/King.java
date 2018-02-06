package pieces;

import comp124graphics.Image;

public class King extends Piece {

    public King(String color, String pos) {
        super(color, pos);
        this.type = "K";
        if(color == "white") {
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/wK.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/wK.png");
        }
        else {
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/bK.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/bK.png");
        }
    }
}
