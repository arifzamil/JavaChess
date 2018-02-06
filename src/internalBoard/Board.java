package internalBoard;

import notationConverter.Converter;
import pieces.*;

import java.util.ArrayList;

public class Board {

    private String fen;
    private Piece[][] internalBoard = new Piece[8][8];
    private ArrayList<Piece> whitePieces = new ArrayList<>(16);
    private ArrayList<Piece> blackPieces = new ArrayList<>(16);
    private Converter converter = new Converter();

    public Board() {
        createPieces();
        initializePieces();
    }

    /**
     * Initializes the pieces by setting their positions and adding them to the board.
     */
    private void initializePieces() {
        int wPawnCount = 0;
        int wRookCount = 0;
        int wKnightCount = 0;
        int wBishopCount = 0;
        int bPawnCount = 0;
        int bRookCount = 0;
        int bKnightCount = 0;
        int bBishopCount = 0;

        for (Piece piece : whitePieces) {
            switch (piece.getType()) {
                case "P":
                    piece.setPos(converter.coordToNotation(6, wPawnCount));
                    internalBoard[6][wPawnCount] = piece;
                    wPawnCount++;
                    break;
                case "R":
                    if (wRookCount == 0) {
                        piece.setPos("a1");
                        internalBoard[7][0] = piece;
                        wRookCount++;
                    } else {
                        piece.setPos("h1");
                        internalBoard[7][7] = piece;
                    }
                    break;
                case "N":
                    if (wKnightCount == 0) {
                        piece.setPos("b1");
                        internalBoard[7][1] = piece;
                        wKnightCount++;
                    } else {
                        piece.setPos("g1");
                        internalBoard[7][6] = piece;
                    }
                    break;
                case "B":
                    if (wBishopCount == 0) {
                        piece.setPos("c1");
                        internalBoard[7][2] = piece;
                        wBishopCount++;
                    } else {
                        piece.setPos("f1");
                        internalBoard[7][5] = piece;
                    }
                    break;
                case "Q":
                    piece.setPos("d1");
                    internalBoard[7][3] = piece;
                    break;
                case "K":
                    piece.setPos("e1");
                    internalBoard[7][4] = piece;
                    break;
            }
        }

        for (Piece piece : blackPieces) {
            switch (piece.getType()) {
                case "P":
                    piece.setPos(converter.coordToNotation(1, bPawnCount));
                    internalBoard[1][bPawnCount] = piece;
                    bPawnCount++;
                    break;
                case "R":
                    if (bRookCount == 0) {
                        piece.setPos("a8");
                        internalBoard[0][0] = piece;
                        bRookCount++;
                    } else {
                        piece.setPos("h8");
                        internalBoard[0][7] = piece;
                    }
                    break;
                case "N":
                    if (bKnightCount == 0) {
                        piece.setPos("b8");
                        internalBoard[0][1] = piece;
                        bKnightCount++;
                    } else {
                        piece.setPos("g8");
                        internalBoard[0][6] = piece;
                    }
                    break;
                case "B":
                    if (bBishopCount == 0) {
                        piece.setPos("c8");
                        internalBoard[0][2] = piece;
                        bBishopCount++;
                    } else {
                        piece.setPos("f8");
                        internalBoard[0][5] = piece;
                    }
                    break;
                case "Q":
                    piece.setPos("d8");
                    internalBoard[0][3] = piece;
                    break;
                case "K":
                    piece.setPos("e8");
                    internalBoard[0][4] = piece;
                    break;
            }
        }
    }

    /**
     * Checks to see if a given move is legal.
     *
     * @param piece A chess piece.
     * @param move  A target square.
     * @return <code>True</code> if <code>move</code> is legal, <code>false</code> if not.
     */
    public boolean canMove(Piece piece, String move) {
        int[] moveCoord = converter.notationToCoord(move);
        int[] posCoord = converter.notationToCoord(piece.getPos());
        double startRank = (double) posCoord[0];
        double startFile = (double) posCoord[1];
        double destRank = (double) moveCoord[0];
        double destFile = (double) moveCoord[1];
        double rankDiff = Math.abs(destRank - startRank);
        double fileDiff = Math.abs(destFile - startFile);
        double m = rankDiff / fileDiff;
        if (move.equals(piece.getPos()) || piece.isRuleViolation((int) destRank, (int) destFile)) {
            return false;
        }
        if (m != 1.0 && m != 0.0 && m != Double.NEGATIVE_INFINITY && m != Double.POSITIVE_INFINITY && m != 0.5 && m != 2.0) {
            return false;
        }
        if (!piece.getType().equals("N")) {
            if (m == 0.0) {
                if (startFile < destFile) {
                    for (int i = (int) startFile + 1; i < destFile; i++) {
                        if (!isEmpty(converter.coordToNotation((int) destRank, i))) {
                            return false;
                        }
                    }
                } else {
                    for (int i = (int) destFile + 1; i < startFile; i++) {
                        if (!isEmpty(converter.coordToNotation((int) destRank, i))) {
                            return false;
                        }
                    }
                }
            } else if (Math.abs(m) == 1.0) {
                if (startRank > destRank && startFile > destFile) {
                    for (int i = 0; i < rankDiff - 1; i++) {
                        if (!isEmpty(converter.coordToNotation((int) destRank + 1 + i, (int) destFile + 1 + i))) {
                            return false;
                        }
                    }
                } else if (startRank > destRank && startFile < destFile) {
                    for (int i = 0; i < rankDiff - 1; i++) {
                        if (!isEmpty(converter.coordToNotation((int) destRank + 1 + i, (int) destFile - 1 - i))) {
                            return false;
                        }
                    }
                } else if (startRank < destRank && startFile > destFile) {
                    for (int i = 0; i < rankDiff - 1; i++) {
                        if (!isEmpty(converter.coordToNotation((int) destRank - 1 - i, (int) destFile + 1 + i))) {
                            return false;
                        }
                    }
                } else {
                    for (int i = 0; i < rankDiff - 1; i++) {
                        if (!isEmpty(converter.coordToNotation((int) destRank - 1 - i, (int) destFile - 1 - i))) {
                            return false;
                        }
                    }
                }
            } else if (m == Double.POSITIVE_INFINITY || m == Double.NEGATIVE_INFINITY) {
                if (startRank < destRank) {
                    for (int i = (int) startRank + 1; i < destRank; i++) {
                        if (!isEmpty(converter.coordToNotation(i, (int) destFile))) {
                            return false;
                        }
                    }
                } else if (startRank > destRank) {
                    for (int i = (int) destRank + 1; i < startRank; i++) {
                        if (!isEmpty(converter.coordToNotation(i, (int) destFile))) {
                            return false;
                        }
                    }
                }
            }
        }
        if (!isEmpty(move)) {
            if (piece.getColor() == internalBoard[(int) destRank][(int) destFile].getColor()) {
                return false;
            }
        }
        if (piece.getType().equals("P")) {
            if (rankDiff == 1 && fileDiff == 1 && !isEmpty(move)) {
                return true;
            } else if (rankDiff == 1 && fileDiff == 1 && isEmpty(move)) {
                return false;
            } else if (!isEmpty(move)) {
                return false;
            }
            return (!(Math.abs(fileDiff) != 0));
        }
        return true;
    }

    /**
     * Moves a piece to a target square (if legal), and adjusts its position accordingly.
     *
     * @param piece A chess piece.
     * @param move  A target square.
     */
    public void move(Piece piece, String move) {
        if (canMove(piece, move)) {
            int[] oldPos = converter.notationToCoord(piece.getPos());
            int[] destPos = converter.notationToCoord(move);
            int oldRank = oldPos[0];
            int oldFile = oldPos[1];
            int destRank = destPos[0];
            int destFile = destPos[1];
            piece.setPos(move);
            internalBoard[destRank][destFile] = piece;
            internalBoard[oldRank][oldFile] = null;
        }
    }

    /**
     * Creates the set of pieces needed for the game.
     */
    private void createPieces() {
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        for (int i = 0; i < 8; i++) {
            whitePieces.add(new Pawn("white", letters[i] + "2"));
            blackPieces.add(new Pawn("black", letters[i] + "7"));
        }
        whitePieces.add(new Rook("white", "a1"));
        whitePieces.add(new Rook("white", "h1"));
        blackPieces.add(new Rook("black", "a8"));
        blackPieces.add(new Rook("black", "h8"));
        whitePieces.add(new Knight("white", "b1"));
        whitePieces.add(new Knight("white", "g1"));
        blackPieces.add(new Knight("black", "b8"));
        blackPieces.add(new Knight("black", "g8"));
        whitePieces.add(new Bishop("white", "c1"));
        whitePieces.add(new Bishop("white", "f1"));
        blackPieces.add(new Bishop("black", "c8"));
        blackPieces.add(new Bishop("black", "f8"));
        whitePieces.add(new Queen("white", "d1"));
        blackPieces.add(new Queen("black", "d8"));
        whitePieces.add(new King("white", "e1"));
        blackPieces.add(new King("black", "e8"));
    }

    /**
     * Checks to see if a target square on the board is empty.
     *
     * @param coord A target square.
     * @return <code>True</code> if this square is empty, <code>false</code> if not.
     */
    private boolean isEmpty(String coord) {
        int[] boardCoord = converter.notationToCoord(coord);
        return internalBoard[boardCoord[0]][boardCoord[1]] == null;
    }

    public Piece[][] getInternalBoard() {
        return internalBoard;
    }

    public void printBoard() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if(internalBoard[x][y] != null)
                    System.out.print(internalBoard[x][y].getType());
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
}

