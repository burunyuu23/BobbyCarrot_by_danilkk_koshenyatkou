package ru.vsu.cs.course1.game;

import java.lang.reflect.Array;
import java.util.Arrays;

public enum CellType {
    BLANK,
    PLAYER,
    EMPTY_CELL,
    FILLED_CELL,
    NONACTIVE_FINAL,
    ACTIVE_FINAL,
    ANGULAR_ROTATE_PASS_DOWN_LEFT,
    ANGULAR_ROTATE_PASS_DOWN_RIGHT,
    ANGULAR_ROTATE_PASS_UP_LEFT,
    ANGULAR_ROTATE_PASS_UP_RIGHT,
    PASS_LEFT,
    PASS_RIGHT,
    NONACTIVE_SPIKES,
    ACTIVE_SPIKES,
    WALL,
    NON_PRESSURE_SPIKES,
    PRESSURE_SPIKES,
    NON_PRESSURE_BUTTON,
    PRESSURE_BUTTON;

    private static final CellType[] list = CellType.values();

    public static CellType getCellType(int i){
        return list[i];
    }

    public static boolean cellTypeCheck(int i){
        return (i > 5 && i < 12);
    }
}
