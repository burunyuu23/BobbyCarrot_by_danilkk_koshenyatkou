package ru.vsu.cs.course1.game;

import java.io.FileNotFoundException;
import java.util.Locale;

/**
 * Класс с методом main
 */
public class Program {

    /**
     * Основная функция программы
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);

        java.awt.EventQueue.invokeLater(() -> {
            try {
                new MainForm().setVisible(true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
