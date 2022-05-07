package com.example.checkers;

public enum PieceType {
    RED(1), WHITE(-1);
    final int moveDir;
    PieceType(int moveDIR){
        this.moveDir = moveDIR;
    }
}
