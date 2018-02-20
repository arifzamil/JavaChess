package pieces;

import comp124graphics.Image;

public class Bishop extends Piece {


    public Bishop(String color, String pos) {
        super(color, pos);
        this.type = "B";
        if(color.equals("white")) {
            this.capImageFile = "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/wB.png";
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/wB.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/wB.png");
        }
        else {
            this.capImageFile = "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/bB.png";
            this.dispImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/images/bB.png");
            this.capImage = new Image(0, 0, "/Users/azamil/Desktop/ChessPart2/JavaChess/src/captured/bB.png");
        }
    }
}
