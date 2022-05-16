package com.example.checkers;

public enum PieceType {
    RED(1, 0), WHITE(-1, 0), REDKING(1,-1), WHITEKING(-1, 1), ;
    final int moveDir;
    public int isKing;

    PieceType(int moveDIR, int isKing){
        this.moveDir = moveDIR;
        this.isKing = isKing;
    }

}
