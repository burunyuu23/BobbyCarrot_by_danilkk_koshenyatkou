package ru.vsu.cs.course1.game;

public class Player {
    private Cell curCell;

    public Player(int row, int col) {
        this.curCell = new Cell(row, col);
    }

    public Cell getCurCell(){
        return this.curCell;
    }

    public void setCurCell(Cell newCell){
        this.curCell = newCell;
    }
}
