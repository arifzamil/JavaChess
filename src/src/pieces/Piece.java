package pieces;

import notationConverter.Converter;
import comp124graphics.*;

public class Piece {

    protected String capImageFile;
    protected Image dispImage, capImage;
    protected int moveCount;
    private String pos;
    private final String COLOR;
    protected String type;
    private Converter converter = new Converter();

    public Piece(String color, String pos) {
        this.pos = pos;
        this.COLOR = color;
        this.moveCount = 0;
    }

    public boolean isRuleViolation(int destRank, int destFile) {
        int[] coord = converter.notationToCoord(this.getPos());
        double startRank = (double) coord[0];
        double startFile = (double) coord[1];
        double rankDiff = Math.abs(destRank - startRank);
        double fileDiff = Math.abs(destFile - startFile);
        double m = rankDiff / fileDiff;
        switch (type) {
            case "R":
                return !(m == 0.0 || m == Double.POSITIVE_INFINITY || m == Double.NEGATIVE_INFINITY);
            case "N":
                return ((rankDiff != 2.0 && rankDiff != 1.0) || (fileDiff != 2.0 && fileDiff != 1.0) || (rankDiff == fileDiff));
            case "B":
                return !(Math.abs(m) == 1.0);
            case "Q":
                return !(m == 0.0 || m == Double.POSITIVE_INFINITY || m == Double.NEGATIVE_INFINITY || Math.abs(m) == 1.0);
            case "K":
                return !((m == 0.0 || m == Double.POSITIVE_INFINITY || m == Double.NEGATIVE_INFINITY || Math.abs(m) == 1.0) &&
                        (rankDiff == 1.0 && fileDiff == 1.0) || (rankDiff + fileDiff == 1.0));
            case "P":
                if (getColor().equals("white")) {
                    if (destRank - startRank > 0) {
                        return true;
                    } else if (startRank == 6) {
                        return (rankDiff > 2);
                    }
                    return (destRank - startRank != -1);
                } else {
                    if (destRank - startRank <= 0) {
                        return true;
                    } else if (startRank == 1) {
                        return (rankDiff > 2);
                    }
                    return destRank - startRank != 1;
                }
        }
        return false;
    }



    public void addMoveCount(){
        moveCount++;
    }

    public void subMoveCount(){
        moveCount--;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getColor() {
        return COLOR;
    }

    public String getType() {
        return type;
    }

    public void setCapImage(String capImageFile) {
        this.capImage = new Image(0, 0, capImageFile);
    }

    public Image getDispImage() {
        return dispImage;
    }

    public Image getCapImage() {
        return capImage;
    }

    public String getCapImageFile() {
        return capImageFile;
    }


    @Override
    public String toString() {
        return COLOR.substring(0, 1) + type;
    }
}
