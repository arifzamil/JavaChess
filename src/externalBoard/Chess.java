package externalBoard;

import notationConverter.Converter;
import internalBoard.Board;
import pieces.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import comp124graphics.*;


public class Chess {
    public static void main(String[] args) {
        new BoardSetup("Chess", 800, 800);
    }


    public static class Tile extends Rectangle {
        private Color tileColor;

        public Tile(int x, int y, int width, int height) {
            super(x, y, width, height);
        }

        public void setTileColor(Color col) {
            tileColor = col;
        }

        public Color getTileColor() {
            return tileColor;
        }
    }

    public static class BoardSetup extends CanvasWindow implements MouseListener, MouseMotionListener {
        private Board internalBoard;
        private Converter convo;
        private Tile[][] tiles;
        private ArrayList<Image> captured;
        private Tile highlighted = null;
        private Piece sPicece = null;
        private int sPX;
        private int sPY;
        private final int BOARD_SIZE = 80;
        private final int ALIGN_PIECE = 8;
        private final Color[] BOARD_COLORS = {new Color(255, 253, 208), Color.GRAY};

        private BoardSetup(String title, int width, int height) {
            super(title, width, height);
            internalBoard = new Board();
            convo = new Converter();
            tiles = new Tile[8][8];
            sPX = 0;
            sPY = 0;
            addMouseListener(this);
            addMouseMotionListener(this);
            GUIBoard();
            redraw();
        }


        private void redraw(){
            for(int row = 0; row < 8; row++){
                for(int col = 0; col < 8; col++){
                    int y_ = row * BOARD_SIZE + ALIGN_PIECE;
                    int x_ = col * BOARD_SIZE + ALIGN_PIECE;
                    if(internalBoard.getInternalBoard()[row][col] != null){
                        internalBoard.getInternalBoard()[row][col].getDispImage().setPosition(x_, y_);
                        add(internalBoard.getInternalBoard()[row][col].getDispImage());
                    }
                }
            }
        }


        private void GUIBoard() {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    int x = col * BOARD_SIZE;
                    int y = row * BOARD_SIZE;
                    Tile rect = new Tile(x, y, BOARD_SIZE, BOARD_SIZE);
                    if ((row % 2) == (col % 2)) {
                        rect.setFillColor(BOARD_COLORS[0]);
                        rect.setTileColor(BOARD_COLORS[0]);
                    } else {
                        rect.setFillColor(BOARD_COLORS[1]);
                        rect.setTileColor(BOARD_COLORS[1]);
                    }
                    rect.setFilled(true);
                    this.add(rect);
                    tiles[row][col] = rect;
                }
            }
        }

        private void selectTile(int x, int y) {
            if (x < 8 && y < 8) {
                deselectTile();
                highlightTile(x, y);
            }
        }

        private void deselectTile() {
            if (highlighted != null)
                highlighted.setFillColor(highlighted.getTileColor());
        }

        private void highlightTile(int x, int y) {
            Tile rect = tiles[y][x];
            rect.setFillColor(Color.RED);
            highlighted = rect;
        }



        @Override
        public void mousePressed(MouseEvent e) {
            double x_ = e.getX();
            double y_ = e.getY();
            int x = (int) (x_ / BOARD_SIZE);
            int y = (int) (y_ / BOARD_SIZE);

            if (x < 8 && y < 8) {
                if (sPicece == null) {
                    sPicece = internalBoard.getInternalBoard()[y][x];
                    if (sPicece != null) {
                        remove(sPicece.getDispImage());
                        add(sPicece.getDispImage());
                        sPX = x;
                        sPY = y;
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            double x_ = e.getX();
            double y_ = e.getY();
            int x = (int) (x_ / BOARD_SIZE);
            int y = (int) (y_ / BOARD_SIZE);

            if (x < 8 && y < 8) {
                if (sPicece != null) {
                    internalBoard.move(sPicece, convo.coordToNotation(y, x));
                    System.out.println(convo.coordToNotation(y, x) + sPicece.getType());
                    internalBoard.printBoard();
                }
                sPicece = null;
            }
            else if(sPicece != null)  {
                sPicece.getDispImage().setPosition(sPX * BOARD_SIZE + ALIGN_PIECE, sPY * BOARD_SIZE + ALIGN_PIECE);
                sPicece = null;
                deselectTile();
            }
            redraw();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            double x_ = e.getX();
            double y_ = e.getY();
            int x = (int) (x_ / BOARD_SIZE);
            int y = (int) (y_ / BOARD_SIZE);
            selectTile(x, y);


            if (x < 8 && y < 8) {
                if (sPicece != null) {
                    sPicece.getDispImage().setPosition(x_ - 32, y_ - 32);
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}

