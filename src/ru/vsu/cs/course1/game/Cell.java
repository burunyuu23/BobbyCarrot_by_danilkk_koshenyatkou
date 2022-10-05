package ru.vsu.cs.course1.game;

public class Cell {
    private int row;
    private int col;
    private CellType type;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Cell(int row, int col, CellType type){
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

    public CellType getType(){
        return this.type;
    }

    public Cell addRow(int d){
        this.row += d;
        return this;
    }

    public Cell addCol(int d){
        this.col += d;
        return this;
    }

    public Cell setType(CellType type){
        this.type = type;
        return this;
    }
}
