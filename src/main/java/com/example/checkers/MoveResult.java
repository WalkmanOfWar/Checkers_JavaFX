package com.example.checkers;

public class MoveResult {
    private final MoveType type;
    private final Piece piece;

    public MoveType getType() {
        return type;
    }


    public Piece getPiece() {
        return piece;
    }

    public MoveResult(MoveType type) {
        this(type, null);
    }

    public MoveResult(MoveType type, Piece piece) {
        this.type = type;
        this.piece = piece;
    }

}
