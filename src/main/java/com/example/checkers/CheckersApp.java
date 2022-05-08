package com.example.checkers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckersApp extends Application {

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private int turn;
    private Tile[][] board = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Parent createContent() throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("Board.fxml"));
        root.getChildren().addAll(tileGroup, pieceGroup);
        turn = 0;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.RED, x, y);
                }

                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.WHITE, x, y);
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

        return root;
    }
    private MoveResult tryMove(Piece piece, int newX, int newY){
        if (board[newX][newY].hasPiece() || (newX+ newY)%2 == 0 || piece.getType() == (turn %2 == 0 ? PieceType.WHITE : PieceType.RED)){
            return new MoveResult(MoveType.NONE);
        }
        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());
        if (Math.abs(newX - x0) == 1 && newY -y0 == piece.getType().moveDir){
            turn++;
            return new MoveResult(MoveType.NORMAL);
        }else if(Math.abs(newX - x0) == 2 && newY -y0 == piece.getType().moveDir*2){
            int x1 = x0+ (newX - x0)/2;
            int y1 = y0 + (newY - y0)/2;
            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()){
                turn++;
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            }
        }
        return new MoveResult(MoveType.NONE);
    }
    private int toBoard(double pixel){
        return (int)(pixel+TILE_SIZE/2)/TILE_SIZE;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();
    }
    private Piece makePiece(PieceType type, int x, int y) throws IOException {
        Piece piece = new Piece(type,x,y);
        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX()); //moving mouse changes layout
            int newY = toBoard(piece.getLayoutY());
            MoveResult result = tryMove(piece, newX, newY);
            int x0  = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());
            switch (result.getType()){

                case NONE -> {
                    piece.abortMove();
                }
                case NORMAL -> {
                    piece.move(newX,newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                }
                case KILL -> {
                    piece.move(newX,newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                }
            }
        });
        return piece;
    }
    public static void main(String[] args) {
        launch();
    }
}