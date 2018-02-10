package externalBoard;

import comp124graphics.CanvasWindow;
import comp124graphics.Rectangle;
import internalBoard.Board;
import notationConverter.Converter;
import pieces.Piece;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class BoardSetup extends CanvasWindow implements MouseListener, MouseMotionListener {
    private Board internalBoard;
    private Converter convo;
    private Tile[][] tiles;
    private Tile highlighted = null;
    private Piece sPicece = null;
    private int sPX;
    private int sPY;
    private final int BOARD_SIZE = 80;
    private final int ALIGN_PIECE = 8;
    private final int SHIFT_BOARD_RIGHT = 100;
    private final int SHIFT_BOARD_DOWN = 50;
    private final Color[] BOARD_COLORS = {new Color(255, 253, 208), Color.GRAY};


    public BoardSetup(String title, int width, int height) {
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

    private static class Tile extends Rectangle {
        private Color tileColor;

        private Tile(int x, int y, int width, int height) {
            super(x, y, width, height);
        }

        private void setTileColor(Color col) {
            tileColor = col;
        }

        private Color getTileColor() {
            return tileColor;
        }
    }

    private void redraw() {
        removeAll();
        GUIBoard();
        showCaps();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int y_ = row * BOARD_SIZE + ALIGN_PIECE + SHIFT_BOARD_DOWN;
                int x_ = col * BOARD_SIZE + ALIGN_PIECE + SHIFT_BOARD_RIGHT;
                if (internalBoard.getInternalBoard()[row][col] != null) {
                    add(internalBoard.getInternalBoard()[row][col].getDispImage(), x_, y_);
                }
            }
        }
    }


    private void GUIBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int x = col * BOARD_SIZE + SHIFT_BOARD_RIGHT;
                int y = row * BOARD_SIZE + SHIFT_BOARD_DOWN;
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


    private void showCaps() {
        ArrayList<Piece> caps = internalBoard.getCaptured();
        int x = 8 * BOARD_SIZE + ALIGN_PIECE + SHIFT_BOARD_RIGHT;
        int y = 200;
        int bSpace = 12;
        int wSpace = 12;
        for (Piece p : caps) {
            if (p.getType().equals("P")) {
                if (p.getColor().equals("white")) {
                    add(p.getCapImage(), x + wSpace, y - 50);
                    wSpace += 12;
                } else {
                    add(p.getCapImage(), x + bSpace, getHeight() - y - 50);
                    bSpace += 12;
                }
            }
        }
        wSpace = 12;
        bSpace = 12;
        for (Piece p : caps) {
            if (!p.getType().equals("P")) {
                if (p.getColor().equals("white")) {
                    add(p.getCapImage(), x + wSpace, y);
                    wSpace += 12;
                } else {
                    add(p.getCapImage(), x + bSpace, getHeight() - y);
                    bSpace += 12;
                }
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        double x_ = e.getX();
        double y_ = e.getY();
        int x = (int) ((x_ - SHIFT_BOARD_RIGHT) / BOARD_SIZE);
        int y = (int) ((y_ - SHIFT_BOARD_DOWN) / BOARD_SIZE);

        if (x < 8 && y < 8) {
            try {
                sPicece = internalBoard.getInternalBoard()[y][x];
                if (sPicece.getColor().equals(internalBoard.getTurn())) {
                    {
                        remove(sPicece.getDispImage());
                        add(sPicece.getDispImage());
                        sPX = x;
                        sPY = y;
                    }
                }
            } catch (NullPointerException error) {
                sPicece = null;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        double x_ = e.getX();
        double y_ = e.getY();
        int x = (int) ((x_ - SHIFT_BOARD_RIGHT) / BOARD_SIZE);
        int y = (int) ((y_ - SHIFT_BOARD_DOWN) / BOARD_SIZE);

        if (x < 8 && y < 8) {
            if (sPicece != null) {
                internalBoard.move(sPicece, convo.coordToNotation(y, x));
                internalBoard.printBoard();
                sPicece = null;
            }
        } else if (sPicece != null) {
            sPicece.getDispImage().setPosition(sPX * BOARD_SIZE + ALIGN_PIECE + SHIFT_BOARD_RIGHT, sPY * BOARD_SIZE + ALIGN_PIECE + SHIFT_BOARD_DOWN);
            sPicece = null;
            deselectTile();
        }
        redraw();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        double x_ = e.getX();
        double y_ = e.getY();
        int x = (int) ((x_ - SHIFT_BOARD_RIGHT) / BOARD_SIZE);
        int y = (int) ((y_ - SHIFT_BOARD_DOWN) / BOARD_SIZE);
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
        double x_ = e.getX();
        double y_ = e.getY();
        int x = (int) ((x_ - SHIFT_BOARD_RIGHT) / BOARD_SIZE);
        int y = (int) ((y_ - SHIFT_BOARD_DOWN) / BOARD_SIZE);

        selectTile(x, y);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


}
