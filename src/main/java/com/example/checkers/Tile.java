package com.example.checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.example.checkers.CheckersApp.TILE_SIZE;

public class Tile extends Rectangle {
    private Piece piece;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Tile(boolean colour, int x, int y) {
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);
        relocate(x * TILE_SIZE, y * TILE_SIZE);

        setFill(colour ? Color.valueOf("#feb") : Color.valueOf("#582"));
    }
}
