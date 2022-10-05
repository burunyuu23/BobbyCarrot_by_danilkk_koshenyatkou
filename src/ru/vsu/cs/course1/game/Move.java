package ru.vsu.cs.course1.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Move extends Player {
    private static boolean flag = false;

    public static int moves = 0;

    public static void setFlag(boolean bool){
        flag = bool;
    }

    public static boolean getFlag(){
        return flag;
    }

    public Move(int row, int col) {
        super(row, col);
    }

    public  void moveUp(){
        swap(getCurCell().getRow(), getCurCell().getCol(), 0, -1, Direction.UP);
    }

    public  void moveDown(){
        swap(getCurCell().getRow(), getCurCell().getCol(), 0, 1, Direction.DOWN);
    }

    public  void moveLeft(){
        swap(getCurCell().getRow(), getCurCell().getCol(), -1, 0, Direction.LEFT);
    }

    public  void moveRight(){
        swap(getCurCell().getRow(), getCurCell().getCol(), 1, 0, Direction.RIGHT);
    }

    public boolean isEqual(CellType ct, int a){
        return CellType.getCellType(a) == ct;
    }

    public void swap(int row , int col, int dy, int dx, Direction dir) {
        int a = Game.ints[row + dx][col + dy];
        if (!isEqual(CellType.WALL, a) && !isEqual(CellType.NONACTIVE_SPIKES, a) &&
                !isEqual(CellType.FILLED_CELL, a) && !isEqual(CellType.PRESSURE_SPIKES, a)) {
            if (isEqual(CellType.PASS_LEFT, a) && dir == Direction.RIGHT) {
                skip(row, col, dx, dy, dir);
            } else if (isEqual(CellType.PASS_RIGHT, a) &&
                    dir == Direction.LEFT) {
                skip(row, col, dx, dy, dir);
            } else if (isEqual(CellType.ANGULAR_ROTATE_PASS_UP_RIGHT, a) || isEqual(CellType.ANGULAR_ROTATE_PASS_UP_LEFT, a) &&
                    dir == Direction.DOWN) {
                skip(row, col, dx, dy, dir);
            } else if (isEqual(CellType.ANGULAR_ROTATE_PASS_DOWN_LEFT, a) || isEqual(CellType.ANGULAR_ROTATE_PASS_DOWN_RIGHT, a) &&
                    dir == Direction.UP) {
                skip(row, col, dx, dy, dir);
            } else if (!CellType.cellTypeCheck(a)){
                moves++;
                int temp = Game.ints[row][col];
                Game.ints[row][col] = Game.intS[row][col];
                Game.ints[row + dx][col + dy] = temp;
                Move.setFlag(true);
            }
        }
    }

    public void skip(int row, int col, int dx, int dy, Direction dir) {
        int a = Game.ints[row + dx][col + dy];
        if (CellType.cellTypeCheck(a)) {
            if (dir == Direction.DOWN) {
                dx += 1;
                skip(row, col, dx, dy, dir);
                return;
            } else if (dir == Direction.LEFT) {
                dy -= 1;
                skip(row, col, dx, dy, dir);
                return;
            } else if (dir == Direction.UP) {
                dx = 1;
                skip(row, col, dx, dy, dir);
                return;
            } else if (dir == Direction.RIGHT) {
                dy += 1;
                skip(row, col, dx, dy, dir);
                return;
            }
        }
        if (!isEqual(CellType.WALL, a) && !isEqual(CellType.NONACTIVE_SPIKES, a) &&
                !isEqual(CellType.FILLED_CELL, a) && !isEqual(CellType.PRESSURE_SPIKES, a)) {
            moves++;
            int temp = Game.ints[row][col];
            Game.ints[row][col] = Game.intS[row][col];
            Game.ints[row + dx][col + dy] = temp;
            Move.setFlag(true);
        }
    }
}