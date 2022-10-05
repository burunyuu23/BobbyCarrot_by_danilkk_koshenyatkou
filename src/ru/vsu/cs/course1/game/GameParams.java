package ru.vsu.cs.course1.game;

/**
 * Класс для хранения параметров игры
 */
public record GameParams(int rowCount, int colCount) {

    public int getColCount() {
        return colCount;
    }

    public int getRowCount() {
        return rowCount;
    }
}
