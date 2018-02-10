package pieces;

import comp124graphics.Image;

public class Knight extends Piece {

    public Knight(String color, String pos) {
        super(color, pos);
        this.type = "N";
        if(color.equals("white")) {
            this.capImageFile = "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/wN.png";
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/wN.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/wN.png");
        }
        else {
            this.capImageFile = "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/bN.png";
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/bN.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/bN.png");
        }
    }
}
