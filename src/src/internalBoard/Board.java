package internalBoard;

import com.sun.xml.internal.bind.v2.TODO;
import notationConverter.Converter;
import pieces.*;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private String turn = "white";
    private String fen;
    private Piece[][] internalBoard = new Piece[8][8];
    private ArrayList<Piece> captured = new ArrayList<>(32);
    private ArrayList<Piece> whitePieces = new ArrayList<>(16);
    private ArrayList<Piece> blackPieces = new ArrayList<>(16);
    private Converter converter = new Converter();

    private King wK, bK;
    private Rook wR1, wR2, bR1, bR2;

    public Board() {
        createPieces();
        initializePieces();
    }

    public Board(Piece[][] internalBoard, String turn){
        this.internalBoard = internalBoard;
        this.turn = turn;
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
            if (internalBoard[destRank][destFile] != null) {
                captured.add(internalBoard[destRank][destFile]);
            }
            internalBoard[destRank][destFile] = piece;
            internalBoard[oldRank][oldFile] = null;
            if (turn.equals("white"))
                turn = "black";
            else
                turn = "white";

            piece.addMoveCount();
        }
    }

    /**
     * Creates the set of pieces needed for the game.
     */
    private void createPieces() {
        wK = new King("white", "e1");
        bK = new King("black", "e8");
        wR1 = new Rook("white", "a1");
        wR2 = new Rook("white", "h1");
        bR1 = new Rook("black", "a8");
        bR2 = new Rook("black", "h8");

        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        for (int i = 0; i < 8; i++) {
            whitePieces.add(new Pawn("white", letters[i] + "2"));
            blackPieces.add(new Pawn("black", letters[i] + "7"));
        }
        whitePieces.add(wR1);
        whitePieces.add(wR2);
        blackPieces.add(bR1);
        blackPieces.add(bR2);
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
        whitePieces.add(wK);
        blackPieces.add(bK);
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

    /**
     * Gets all available moves for a piece
     * @param piece the piece for all available moves
     * @return ArrayList of possible moves for the piece
     */
    public ArrayList<String> getPieceAvailMoves(Piece piece){

        ArrayList<String> availMoves = new ArrayList<>();

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                String move = converter.coordToNotation(row, col);
                if(canMove(piece, move)){
                    availMoves.add(move);
                }
            }
        }

//        availMoves = checkFilter(availMoves, piece);
//        for(String move:availMoves){
//            System.out.print(move + " ");
//        }
        return availMoves;
    }

    private ArrayList<String> checkFilter(ArrayList<String> moves, Piece piece){
        for(int i = moves.size() - 1; i >= 0; i--){
            if(isCheckViolation(piece, moves.get(i))){
                moves.remove(moves.get(i));
            }
        }
        return moves;
    }

    /**
     * Checks to see if a piece is threatened by a piece from the opposite color
     * @param color that is or is not threatened
     * @param tile that is or is not threatened
     * @return true if threatened or false if not threatened
     */
    public boolean isThreatened(String color, String tile){
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if(!isEmpty(converter.coordToNotation(row, col))){
                    Piece piece = internalBoard[row][col];
                    if(!(piece.getColor().equals(color)) && canMove(piece, tile)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check to see if a given color is in check
     * @param side The color we are check to see if in check
     * @return true if in check false if not in check
     */
    public boolean inCheck(String side){
        return((side.equals("white") && isThreatened("white", wK.getPos())) || (side.equals("black") && isThreatened("black", bK.getPos())));
    }



    //TODO: finish isCheckViolation
    public boolean isCheckViolation(Piece piece, String move){
        boolean p = false;
        int[] coord = converter.notationToCoord(piece.getPos());

        if((piece.equals(bK) || piece.equals(wK))) {
            if(piece.getColor().equals("white")){
                p = placePiece(piece, move);
                internalBoard[coord[0]][coord[1]] = piece;
            }
        }
        else{
            Piece tester = new Piece(piece.getColor(), piece.getPos());
            internalBoard[coord[0]][coord[1]] = tester;

            p = placePiece(tester, move);

            internalBoard[coord[0]][coord[1]] = piece;


        }
        return p;
    }

    private boolean placePiece(Piece piece, String move){
        Piece tempHolder;
        int[] origCoord = converter.notationToCoord(piece.getPos());
        internalBoard[origCoord[0]][origCoord[1]] = null;
        int[] coord = converter.notationToCoord(move);
        tempHolder = internalBoard[coord[0]][coord[1]];
        internalBoard[coord[0]][coord[1]] = piece;

        printBoard();

        if(inCheck(turn)){
            internalBoard[coord[0]][coord[1]] = tempHolder;
            return true;
        }
        else{

            internalBoard[coord[0]][coord[1]] = tempHolder;
            return false;
        }
    }

    public boolean checkSquares(String[] squareList){

        for(String tile: squareList){
            if(!(isEmpty(tile)) || (tile.charAt(1) == '1' && isCheckViolation(wK, tile)) ||
                    (tile.charAt(1) == '8' && isCheckViolation(bK, tile)))
                return false;
        }
        return true;
    }



    public void castleKingside(String side){
        if(side.equals("white")){
            move(wK, "g1");
            move(wR2, "f1");
        }
        else{
            move(bK, "g8");
            move(bR2, "f8");
        }
    }

    public void castleQueenside(String side){
        if(side.equals("white")){
            move(wK, "c1");
            move(wR1, "d1");
        }
        else{
            move(bK, "c8");
            move(bR1, "d8");
        }
    }



    public Piece[][] getInternalBoard() {
        return internalBoard;
    }

    public String getTurn() {
        return turn;
    }

    public ArrayList<Piece> getCaptured() {
        ArrayList<Piece> holder = new ArrayList<>(captured.size());
        String[] types = {"P", "N", "B", "R", "Q"};
        for (int i = 0; i < 5; i++) {
            for (Piece p : captured) {
                if(p.getType().equals(types[i])){
                    holder.add(p);
                }
            }
        }
        captured = holder;
        return captured;
    }

    public void printBoard() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (internalBoard[x][y] != null)
                    System.out.print(internalBoard[x][y] + " ");
                else
                    System.out.print("   ");
            }
            System.out.println();
        }
    }








    public ArrayList<Piece> createPromotionOptions(String col){
        ArrayList<Piece> promotionOptions = new ArrayList<>(4);
        promotionOptions.add(new Queen(col, "c4"));
        promotionOptions.add(new Rook(col, "d4"));
        promotionOptions.add(new Bishop(col, "e4"));
        promotionOptions.add(new Knight(col, "f4"));
        return promotionOptions;
    }

    public void promoted(Piece p, String newPiece) {
        if(p.getType().equals("P")) {
            int[] coord = converter.notationToCoord(p.getPos());
            switch (newPiece) {
                case "Q":
                    Queen proQueen = new Queen(p.getColor(), p.getPos());
                    proQueen.setCapImage(p.getCapImageFile());
                    internalBoard[coord[0]][coord[1]] = proQueen;
                    break;
                case "R":
                    Rook proRook = new Rook(p.getColor(), p.getPos());
                    proRook.setCapImage(p.getCapImageFile());
                    internalBoard[coord[0]][coord[1]] = proRook;
                    break;
                case "B":
                    Bishop proBish = new Bishop(p.getColor(), p.getPos());
                    proBish.setCapImage(p.getCapImageFile());
                    internalBoard[coord[0]][coord[1]] = proBish;
                    break;
                case "N":
                    Knight proKnight = new Knight(p.getColor(), p.getPos());
                    proKnight.setCapImage(p.getCapImageFile());
                    internalBoard[coord[0]][coord[1]] = proKnight;
                    break;
            }
        }
    }


}

