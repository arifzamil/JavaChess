package pieces;

import notationConverter.Converter;

public class Piece {

    private String pos;
    private final String COLOR;
    String type;
    Converter converter = new Converter();

    Piece(String color, String pos) {
        this.pos = pos;
        this.COLOR = color;
    }

    public boolean isRuleViolation(int destRank, int destFile) {
        int[] coord = converter.notationToCoord(this.getPos());
        double startRank = (double) coord[0];
        double startFile = (double) coord[1];
        double rankDiff = Math.abs(destRank - startRank);
        double fileDiff = Math.abs(destFile - startFile);
        double m = (destRank - startRank) / (destFile - startFile);
        switch (type) {
            case "R":
                return !(m == 0.0 || m == Double.POSITIVE_INFINITY || m == Double.NEGATIVE_INFINITY);
            case "N":
                return ((rankDiff != 2 && rankDiff != 1) || (fileDiff != 2 && fileDiff != 1) || (rankDiff == fileDiff));
            case "B":
                return !(Math.abs(m) == 1.0);
            case "Q":
                return !(m == 0.0 || m == Double.POSITIVE_INFINITY || m == Double.NEGATIVE_INFINITY || Math.abs(m) == 1.0);
            case "K":
                return !((m == 0.0 || m == Double.POSITIVE_INFINITY || m == Double.NEGATIVE_INFINITY || Math.abs(m) == 1.0) &&
                        rankDiff == 1.0 && fileDiff == 1.0);
            case "P":
                if (getColor().equals("white")) {
                    if (rankDiff > 0) {
                        return true;
                    } else if (startRank == 6) {
                        return (Math.abs(rankDiff) > 2);
                    }
                    return (rankDiff != -1);
                } else {
                    if (rankDiff <= 0) {
                        return true;
                    } else if (startRank == 1) {
                        return (Math.abs(rankDiff) > 2);
                    }
                    return rankDiff != 1;
                }
        }
        return false;
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
}
