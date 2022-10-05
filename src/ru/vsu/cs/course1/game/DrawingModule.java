package ru.vsu.cs.course1.game;

import java.awt.*;
import java.util.Random;

public class DrawingModule {

    public static void drawPlayer(Graphics2D g2d, int cellWidth, int cellHeight) {
        switch (Game.getSkin()) {
            case SKIN_UKRAINE -> SkinManager.skinUkraine(g2d, cellWidth, cellHeight);
            case SKIN_JUST_BLUE -> SkinManager.skinBlue(g2d, cellWidth, cellHeight);
            case SKIN_PIG -> SkinManager.skinPig(g2d, cellWidth, cellHeight);
            case SKIN_CHINA -> SkinManager.skinChina(g2d, cellWidth, cellHeight);
            case SKIN_FRIED_EGG -> SkinManager.skinFriedEgg(g2d, cellWidth, cellHeight);
            case SKIN_24_MARK -> SkinManager.skin24Mark(g2d, cellWidth, cellHeight);
            case SKIN_45_MARK -> SkinManager.skin45Mark(g2d, cellWidth, cellHeight);
            case SKIN_SMILE -> SkinManager.skinSmiley(g2d, cellWidth, cellHeight);
        }

    }

    public static void drawWall(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, size, size);
    }

    public static void drawNonSpike(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(size / 10, size, size - size / 5, size / 5);
    }

    public static void drawSpike(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(size / 10, size, size - size / 5, size / 5);
        g2d.setColor(Color.GRAY);
        int[] xInts = new int[]{size / 10 + size / 50, (size - size / 10) / 2, size - size / 7};
        int[] yInts = new int[]{size - 1, (size - size / 10) / 2, size - 1};
        g2d.fillPolygon(xInts, yInts, 3);
    }

    public static void drawEmptyCell(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);

        g2d.setColor(Color.GREEN);
        g2d.fillRect(size / 10, size - size / 9, size - size / 5, size);

    }

    public static void drawFilledCell(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        Random rnd = new Random();

        Color[] addClr = new Color[]{Color.PINK, Color.MAGENTA, Color.WHITE};

        int i = rnd.nextInt(addClr.length);

        g2d.setColor(Color.GRAY);
        g2d.fillRoundRect(limit, (int) Math.round(limit * 1.5), size - 2 * limit, (size - 2) * limit, limit * 3, limit * 4);
        g2d.setColor(addClr[i]);
        limit = (int) Math.round(size * 0.42);
        g2d.fillRoundRect(limit - 4, limit + 5, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
        limit = (int) Math.round(size * 0.47);
        g2d.fillRoundRect(limit + 4, limit + 9, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(size / 10, size - size / 9, size - size / 5, size);
    }

    public static  void drawNonGoal(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.RED);
        g2d.fillRoundRect(cellWidth / 2 - (10 * size / limit) / 2, cellHeight / 2 - (10 * size / limit) / 2, 10 * size / limit, 10 * size / limit, limit * 3, limit * 3);
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(cellWidth / 2 - (7 * size / limit) / 2, cellHeight / 2 - (7 * size / limit) / 2, 7 * size / limit, 7 * size / limit, limit * 3, limit * 3);
        g2d.setColor(Color.RED);
        g2d.fillRoundRect(cellWidth / 2 - (3 * size / limit) / 2, cellHeight / 2 - (3 * size / limit) / 2, 3 * size / limit, 3 * size / limit, limit * 3, limit * 3);
        g2d.setColor(Color.BLACK);
        g2d.drawArc(cellWidth / 2 - (14 * size / limit) / 2, cellHeight / 2 - (14 * size / limit) / 2, 14 * size / limit, 14 * size / limit, 0, 360);
    }

    public static  void drawGoal(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.RED);
        g2d.fillArc(cellWidth / 2 - (14 * size / limit) / 2, cellHeight / 2 - (14 * size / limit) / 2, 14 * size / limit, 14 * size / limit, 0, 360);
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(cellWidth / 2 - (10 * size / limit) / 2, cellHeight / 2 - (10 * size / limit) / 2, 10 * size / limit, 10 * size / limit, limit * 3, limit * 3);
        g2d.setColor(Color.RED);
        g2d.fillRoundRect(cellWidth / 2 - (7 * size / limit) / 2, cellHeight / 2 - (7 * size / limit) / 2, 7 * size / limit, 7 * size / limit, limit * 3, limit * 3);
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(cellWidth / 2 - (3 * size / limit) / 2, cellHeight / 2 - (3 * size / limit) / 2, 3 * size / limit, 3 * size / limit, limit * 3, limit * 3);
        g2d.setColor(Color.BLACK);
        g2d.drawArc(cellWidth / 2 - (14 * size / limit) / 2, cellHeight / 2 - (14 * size / limit) / 2, 14 * size / limit, 14 * size / limit, 0, 360);
    }

    public static  void drawPassLeft(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.YELLOW);
        g2d.fillRect(size / 2 - (int) (limit * 1.3), (int) (size / 1.33) - limit, size - limit, size - limit * 3);
        int[] xInts = new int[]{size - size / 4, size, size - size / 4};
        int[] yInts = new int[]{size - limit * 3, (int) (size / 1.33) - limit + (size - limit * 3) / 2, limit * 3};
        g2d.fillPolygon(xInts, yInts, 3);
        g2d.setColor(Color.BLACK);
        xInts = new int[]{size / 2 - (int) (limit * 1.3), size - size / 4, size - size / 4, size,
                size - size / 4, size - size / 4, size / 2 - (int) (limit * 1.3), size / 2 - (int) (limit * 1.3)};
        yInts = new int[]{(int) (size / 1.33) - limit, (int) (size / 1.33) - limit, size - limit * 3,
                (int) (size / 1.33) - limit + (size - limit * 3) / 2, limit * 3,
                (int) (size / 1.33) - limit + size - limit * 3, (int) (size / 1.33) - limit + size - limit * 3,
                (int) (size / 1.33) - limit};

        g2d.drawPolygon(xInts, yInts, 8);
    }

    public static void drawPassRight(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.YELLOW);
        g2d.fillRect(size / 2 - (int) (limit * 1.3), (int) (size / 1.33) - limit, size - limit, size - limit * 3);
        int[] xInts = new int[]{size / 4, 0, size / 4};
        int[] yInts = new int[]{size - limit * 3, (int) (size / 1.33) - limit + (size - limit * 3) / 2, limit * 3};
        g2d.fillPolygon(xInts, yInts, 3);
        g2d.setColor(Color.BLACK);
        xInts = new int[]{size / 4, size / 4, 0, size / 4, size / 4,
                size / 2 - (int) (limit * 1.3) + size - limit, size / 2 - (int) (limit * 1.3) + size - limit,
                size / 4, size / 4};
        yInts = new int[]{(int) (size / 1.33) - limit, size - limit * 3, (int) (size / 1.33) - limit + (size - limit * 3) / 2,
                limit * 3, (int) (size / 1.33) - limit + (size - limit * 3), (int) (size / 1.33) - limit + (size - limit * 3),
                (int) (size / 1.33) - limit, (int) (size / 1.33) - limit,
                (int) (size / 1.33) - limit};

        g2d.drawPolygon(xInts, yInts, 8);
    }

    public static void drawNonPressureSpikes(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect((int) (limit / 1.3), (int) (limit * 1.9), limit * 2, (int) (limit * 1.3));
    }

    public static void drawPressureSpikes(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect((int) (limit / 1.3), (int) (limit * 1.9), limit * 2, (int) (limit * 1.3));
        g2d.setColor(Color.GRAY);
        int[] xInts = new int[]{size / 10 + size / 6, (size - size / 3) / 2, size - size / 2};
        int[] yInts = new int[]{size - size / 6, (size - size / 10) / 2, size - size / 6};
        g2d.fillPolygon(xInts, yInts, 3);
        xInts = new int[]{size - (int) (limit * 1.7), (int) ((size - limit * 2) * 1.514), size - (int) (limit / 1.2)};
        yInts = new int[]{size - size / 6, (size - size / 10) / 2, size - size / 6};
        g2d.fillPolygon(xInts, yInts, 3);
    }

    public static void drawNonPressureButton(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect((int) (limit / 1.3), (int) (limit * 1.9), limit * 2, (int) (limit * 1.3));
        g2d.setColor(Color.ORANGE);
        g2d.fillRect((int) (limit / 1.3) + limit / 2, (int) (limit * 1.66), limit, (int) (limit * 0.9));
    }

    public static void drawPressureButton(Graphics2D g2d, int cellWidth, int cellHeight) {
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect((int) (limit / 1.3), (int) (limit * 1.9), limit * 2, (int) (limit * 1.3));
        g2d.setColor(Color.ORANGE);
        g2d.fillRect((int) (limit / 1.3) + limit / 2, (int) (limit * 2.1), limit, (int) (limit * 0.5));
    }

}
