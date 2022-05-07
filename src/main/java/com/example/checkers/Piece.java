package com.example.checkers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;

import java.io.IOException;

import static com.example.checkers.CheckersApp.TILE_SIZE;

public class Piece extends StackPane {
    private PieceType type;
    private  PieceType getType(){
        return type;
    }
    public Piece(PieceType type, int x, int y) throws IOException {
        this.type = type;
        relocate(x*TILE_SIZE, y*TILE_SIZE);
        Ellipse bg;
        if(type == PieceType.RED)
            bg = FXMLLoader.load(getClass().getResource("RedPiece.fxml"));
        else
            bg = FXMLLoader.load(getClass().getResource("WhitePiece.fxml"));

        getChildren().add(bg);
    }
}
