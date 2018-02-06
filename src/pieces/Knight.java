package pieces;

public class Knight extends Piece {

    public Knight(String color, String pos) {
        super(color, pos);
        this.type = "N";
    }

    public boolean isRuleViolation(int destRank, int destFile) {
        int[] coord = converter.notationToCoord(this.getPos());
        double startRank = (double) coord[0];
        double startFile = (double) coord[1];
        double rankDiff = Math.abs(destRank - startRank);
        double fileDiff = Math.abs(destFile - startFile);
        return ((rankDiff != 2 && rankDiff !=1) || (fileDiff != 2 && fileDiff !=1) || (rankDiff == fileDiff));
    }
}
