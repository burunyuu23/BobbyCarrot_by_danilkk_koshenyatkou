package ru.vsu.cs.course1.game;

import ru.vsu.cs.util.DrawUtils;

import java.awt.*;

public class SkinManager {

    public static void skinUkraine(Graphics2D g2d, int cellWidth, int cellHeight){
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.BLUE);
        g2d.fillRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
        g2d.setColor(Color.YELLOW);
        g2d.fillArc(limit, limit, size - 2 * limit, size - 2 * limit, -180, 180);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
    }

    public static void skinBlue(Graphics2D g2d, int cellWidth, int cellHeight){
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.BLUE);
        g2d.fillRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
    }

    public static void skinPig(Graphics2D g2d, int cellWidth, int cellHeight){
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.PINK);
        int[] xInts = new int[]{limit / 2, (int)(limit * 1.1), (int)(limit * 1.3)};
        int[] yInts = new int[]{limit / 2, (int)(limit / 2), (int)(limit * 1.3)};
        g2d.fillPolygon(xInts, yInts, 3);
        xInts = new int[]{size - limit / 2, size - (int)(limit * 1.1), size - (int)(limit * 1.3)};
        yInts = new int[]{limit / 2, (limit / 2), (int)(limit * 1.3)};
        g2d.fillPolygon(xInts, yInts, 3);
        g2d.setColor(Color.BLACK);
        xInts = new int[]{limit / 2, (int)(limit * 1.1), (int)(limit * 1.3)};
        yInts = new int[]{limit / 2, (int)(limit / 2), (int)(limit * 1.3)};
        g2d.drawPolygon(xInts, yInts, 3);
        xInts = new int[]{size - limit / 2, size - (int)(limit * 1.1), size - (int)(limit * 1.3)};
        yInts = new int[]{limit / 2, (limit / 2), (int)(limit * 1.3)};
        g2d.drawPolygon(xInts, yInts, 3);

        g2d.setColor(Color.PINK);
        g2d.fillRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect((int) (limit * 1.5), (int) (limit * 1.5), size - 3 * limit, size - 3 * limit, limit * 3, limit * 3);

        g2d.drawRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
    }

    public static void skinChina(Graphics2D g2d, int cellWidth, int cellHeight){
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.RED);
        g2d.fillRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);

        g2d.setColor(Color.YELLOW);
        g2d.fillRoundRect((int)(limit * 1.3), (int)(limit * 1.3), size - 3 * limit, size - 3 * limit, limit * 3, limit * 3);

        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
    }

    public static void skinFriedEgg(Graphics2D g2d, int cellWidth, int cellHeight){
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);

        g2d.setColor(Color.YELLOW);
        g2d.fillRoundRect((int) (1.2 * limit), (int) (1.2 * limit), size - (int) (2.8 * limit), size - (int) (2.8 * limit), limit * 3, limit * 3);
        g2d.fillRoundRect((int) (1.666 * limit), (int) (1.666 * limit), size - (int) (2.9 * limit), size - (int) (2.9 * limit), limit * 3, limit * 3);

        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
    }

    public static void skin24Mark(Graphics2D g2d, int cellWidth, int cellHeight){
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.RED);
        g2d.fillRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);

        g2d.setColor(Color.WHITE);
        DrawUtils.drawStringInCenter(g2d, new Font("Comic Sans MS", Font.BOLD, limit / 2),"24", limit, limit, size - 2 * limit, size - 2 * limit);

        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
    }

    public static void skin45Mark(Graphics2D g2d, int cellWidth, int cellHeight){
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.GREEN);
        g2d.fillRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);

        g2d.setColor(Color.WHITE);
        DrawUtils.drawStringInCenter(g2d, new Font("Comic Sans MS", Font.BOLD, limit / 2),"45", limit, limit, size - 2 * limit, size - 2 * limit);

        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
    }

    public static void skinSmiley(Graphics2D g2d, int cellWidth, int cellHeight){
        int size = Math.min(cellWidth, cellHeight);
        int limit = (int) Math.round(size * 0.3);

        g2d.setColor(Color.ORANGE);
        g2d.fillRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);

        g2d.setColor(Color.BLACK);
        DrawUtils.drawStringInCenter(g2d, new Font("Comic Sans MS", Font.BOLD, limit / 2),"O O", limit, limit - limit / 9, size - 2 * limit, size - 2 * limit);
        DrawUtils.drawStringInCenter(g2d, new Font("Comic Sans MS", Font.BOLD, limit / 2)," _ ", limit, limit + limit/9, size - 2 * limit, size - 2 * limit);

        g2d.drawRoundRect(limit, limit, size - 2 * limit, size - 2 * limit, limit * 3, limit * 3);
    }
}
