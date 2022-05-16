package com.example.checkers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.checkers.PieceType.*;

public class CheckersApp extends Application {

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private int turn;
    private Tile[][] board = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    private Piece mustStrike = null;
    public Controller controller = new Controller();
    private Parent createContent() throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("Board.fxml"));
        root.getChildren().addAll(tileGroup, pieceGroup);
        root.getChildren().add(controller.redLabel);
        root.getChildren().add(controller.whiteLabel);
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
        /*for (int i =0 ; i<HEIGHT;i++){
            for (int j = 0; j< HEIGHT;j++){
                System.out.println(board[j][i].hasPiece());
            }
        }*/
        return root;
    }

    private MoveResult tryMove(Piece piece, int newX, int newY) {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0 || ((piece.getType()==WHITE || piece.getType()==WHITEKING) && turn%2 ==1) ||
                ((piece.getType()==RED || piece.getType()==REDKING) && turn%2 ==0)) {
            return new MoveResult(MoveType.NONE);
        }
        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        mustStrike = checkIfAnyCheckerIsStrikable(piece.getType());
        if (Math.abs(newX - x0) == 1 && (newY - y0 == piece.getType().moveDir || newY - y0 == piece.getType().isKing) && mustStrike == null) { //sprawdzanie czy ktoś nie chce sie ruszyc o wiecej niz 1 pole
            turn++;
            return new MoveResult(MoveType.NORMAL);
        } else if ((Math.abs(newX - x0) == 2 && (newY - y0 == piece.getType().moveDir * 2 || newY - y0 == piece.getType().isKing * 2))) {
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;
            if (board[x1][y1].hasPiece() && (((board[x1][y1].getPiece().getType()==WHITE || board[x1][y1].getPiece().getType()==WHITEKING) &&
                    (piece.getType() == RED || piece.getType() == REDKING)) ||
                    ((board[x1][y1].getPiece().getType()==RED || board[x1][y1].getPiece().getType()==REDKING) &&
                            (piece.getType() == WHITE || piece.getType() == WHITEKING)))) {
                boolean canAttackAgain = canStrikeMore(piece.getType(), newX, newY);
                if (canAttackAgain) {
                    mustStrike = piece;
                    if (piece.getType() == RED || piece.getType() == REDKING){
                        controller.setRedCounter(controller.getRedCounter()+1);
                    }else
                        controller.setRedCounter(controller.getWhiteCounter()+1);
                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
                } else {
                    turn++;
                    mustStrike = null;
                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
                }

            }
        }
        return new MoveResult(MoveType.NONE);
    }

    boolean canStrikeMore(PieceType type, int x, int y) {
        if ((type == PieceType.WHITE && y < 2) || (type == PieceType.RED && y > 5))
            return false;
        boolean isStrikable = false;
        if (type == PieceType.WHITE) {
            if (x >= 2 && x <= 7) {
                if (board[x - 1][y - 1].hasPiece() && (board[x - 1][y - 1].getPiece().getType() == PieceType.RED ||
                        board[x - 1][y - 1].getPiece().getType() == REDKING) &&
                        !board[x - 2][y - 2].hasPiece())
                    isStrikable = true;
            }
            if (x >= 0 && x < 6) {
                if (board[x + 1][y - 1].hasPiece() && (board[x + 1][y - 1].getPiece().getType() == PieceType.RED ||
                        board[x + 1][y - 1].getPiece().getType() == REDKING) &&
                        !board[x + 2][y - 2].hasPiece()) {
                    isStrikable = true;
                }
            }
        } else if (type == PieceType.RED) {
            if (x > 1 && x < 7) {
                if (board[x - 1][y + 1].hasPiece() && (board[x - 1][y + 1].getPiece().getType() == PieceType.WHITE ||
                        board[x - 1][y + 1].getPiece().getType() == WHITEKING) &&
                        !board[x - 2][y + 2].hasPiece())
                    isStrikable = true;
            }
            if (x >= 0 && x < 6) {
                if (board[x + 1][y + 1].hasPiece() && (board[x + 1][y + 1].getPiece().getType() == PieceType.WHITE ||
                        board[x + 1][y + 1].getPiece().getType() == WHITEKING) &&
                        !board[x + 2][y + 2].hasPiece())
                    isStrikable = true;
            }
        } else if (type == WHITEKING) {
            if (x >= 2 && x <= 7 && y >= 2) {
                if (board[x - 1][y - 1].hasPiece() && (board[x - 1][y - 1].getPiece().getType() == PieceType.RED ||
                        board[x - 1][y - 1].getPiece().getType() == REDKING) &&
                        !board[x - 2][y - 2].hasPiece())
                    isStrikable = true;
            }
            if (x >= 0 && x < 6 && y >= 2) {
                if (board[x + 1][y - 1].hasPiece() && (board[x + 1][y - 1].getPiece().getType() == PieceType.RED ||
                        board[x + 1][y - 1].getPiece().getType() == REDKING) &&
                        !board[x + 2][y - 2].hasPiece()) {
                    isStrikable = true;
                }
            }
            if (x > 1 && x < 7 && y < 6) {
                if (board[x - 1][y + 1].hasPiece() && (board[x - 1][y + 1].getPiece().getType() == PieceType.RED ||
                        board[x - 1][y + 1].getPiece().getType() == REDKING) &&
                        !board[x - 2][y + 2].hasPiece())
                    isStrikable = true;
            }
            if (x >= 0 && x < 6 && y < 6) {
                if (board[x + 1][y + 1].hasPiece() && (board[x + 1][y + 1].getPiece().getType() == PieceType.RED ||
                        board[x + 1][y + 1].getPiece().getType() == REDKING) &&
                        !board[x + 2][y + 2].hasPiece())
                    isStrikable = true;
            }
        } else if (type == REDKING) {
            if (x >= 2 && x <= 7 && y >= 2) {
                if (board[x - 1][y - 1].hasPiece() && (board[x - 1][y - 1].getPiece().getType() == WHITE ||
                        board[x - 1][y - 1].getPiece().getType() == WHITEKING) &&
                        !board[x - 2][y - 2].hasPiece())
                    isStrikable = true;
            }
            if (x >= 0 && x < 6 && y >= 2) {
                if (board[x + 1][y - 1].hasPiece() && (board[x + 1][y - 1].getPiece().getType() == PieceType.WHITE ||
                        board[x + 1][y - 1].getPiece().getType() == WHITEKING) &&
                        !board[x + 2][y - 2].hasPiece()) {
                    isStrikable = true;
                }
            }
            if (x > 1 && x < 7 && y < 6) {
                if (board[x - 1][y + 1].hasPiece() && (board[x - 1][y + 1].getPiece().getType() == PieceType.WHITE ||
                        board[x - 1][y + 1].getPiece().getType() == WHITEKING) &&
                        !board[x - 2][y + 2].hasPiece())
                    isStrikable = true;
            }
            if (x >= 0 && x < 6 && y < 6) {
                if (board[x + 1][y + 1].hasPiece() && (board[x + 1][y + 1].getPiece().getType() == PieceType.WHITE ||
                        board[x + 1][y + 1].getPiece().getType() == WHITEKING) &&
                        !board[x + 2][y + 2].hasPiece())
                    isStrikable = true;
            }
        }
        return isStrikable;
    }

    private int toBoard(double pixel) {
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();
    }

    private Piece makePiece(PieceType type, int x, int y) throws IOException {
        Piece piece = new Piece(type, x, y);
        piece.setOnMouseReleased(e -> {
            mustStrike = checkIfAnyCheckerIsStrikable(piece.getType());
            int newX = toBoard(piece.getLayoutX()); //moving mouse changes layout
            int newY = toBoard(piece.getLayoutY());
            try {
                changeToKing(piece,newX,newY);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            MoveResult result = tryMove(piece, newX, newY);
            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            switch (result.getType()) {

                case NONE -> {
                    piece.abortMove();
                }
                case NORMAL -> {
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    System.out.println(board[newX][newY].getPiece().getType());
                }
                case KILL -> {
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    System.out.println(board[newX][newY].getPiece().getType());
                    controller.setRedLabel(6);
                }
            }
        });
        return piece;
    }

    private Piece checkIfAnyCheckerIsStrikable(PieceType type) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (board[x][y].hasPiece() && board[x][y].getPiece().getType() == type && canStrikeMore(type, x, y)) {
                    return board[x][y].getPiece();
                }
            }
        }
        return null;
    }

    private void changeToKing(Piece piece, int x, int y) throws IOException {
        if (piece.getType() == PieceType.RED && y == 7) {
            piece.setType(REDKING);
            for (int i = 0; i < pieceGroup.getChildren().size(); i++) {
                if (pieceGroup.getChildren().get(i) == piece){
                    Ellipse bp = FXMLLoader.load(getClass().getResource("RedKingPiece.fxml"));
                    piece.getChildren().set(0, bp);
                    return;
                }

            }
        } else if (piece.getType() == PieceType.WHITE && y == 0) {
            piece.setType(PieceType.WHITEKING);
            for (int i = 0; i < pieceGroup.getChildren().size(); i++) {
                if (pieceGroup.getChildren().get(i) == piece) {
                    Ellipse bp = FXMLLoader.load(getClass().getResource("WhiteKingPiece.fxml"));
                    piece.getChildren().set(0, bp);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
