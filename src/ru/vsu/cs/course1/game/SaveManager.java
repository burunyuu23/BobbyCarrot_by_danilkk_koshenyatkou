package ru.vsu.cs.course1.game;

import ru.vsu.cs.util.JTableUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class SaveManager {

    private static final String userDirectory = new File("").getAbsolutePath();

    public static String getUserDirectory(){
        return userDirectory;
    }

    private static final Path savedLevelsDir = Paths.get(userDirectory, "levels","saved_levels.txt");
    private static final File savedLevelsFile = savedLevelsDir.toFile();

    public static void checkAndSave(Game game) throws FileNotFoundException {
        if (!savedLevelsFile.exists()){
            save(game);
        }
    }

    static void writeRecords(JTable jt, Game game) {
        String[] records = new String[game.getRanks().size()];
        for (int i = 0; i < records.length; i++) {
            records[i] = "Level " + (i + 1) + " passed on " + game.getRanks().get(i) + " with " + game.getRankSteps().get(i) + " steps.";
            System.out.println(records[i]);
        }
        System.out.println(game.getMaxLevel() + " " + Game.getCurLevel());
        System.out.println(game.getRanks() + " " + game.getRankSteps());
        JTableUtils.writeArrayToJTable(jt, records);
    }

    public static void delete(){
        boolean bool = savedLevelsFile.delete();
        System.out.println(bool);
    }

    public static void save(Game game) throws FileNotFoundException {
        PrintStream out = new PrintStream(savedLevelsFile);

        out.println(game.getMaxLevel());
        for (int i = 0; i < game.getRanks().size(); i++) {
            out.println(game.getRanks().get(i) + " " + game.getRankSteps().get(i));
        }
        out.println("name");
        out.println(game.getName());
        out.println("skin");
        out.println(Game.getSkin());

        out.close();
    }

    public static void load(Game gm) throws FileNotFoundException {
        Scanner scanner = new Scanner(savedLevelsFile);
        while (scanner.hasNextInt()) {
            gm.setMaxLevel(scanner.nextInt());
            gm.setCurLevel(gm.getMaxLevel());
        }
        scanner.nextLine();
        while (scanner.hasNext()) {
            String temp = scanner.next();
            System.out.println(temp);
            if (!temp.equals("name") && !temp.equals("skin")) {
                gm.setLevelRank(temp);
                gm.setRankSteps(Integer.valueOf(scanner.next()));
            }
            else if (temp.equals("name")){
                gm.setName(scanner.next());
            }
            else{
                Game.setSkin(Skins.valueOf(scanner.next()));
            }
        }
        System.out.println(gm.getRankSteps());
        System.out.println(gm.getRanks());
        scanner.close();
    }
}
