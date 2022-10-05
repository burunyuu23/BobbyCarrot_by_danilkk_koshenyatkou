package ru.vsu.cs.course1.game;

import ru.vsu.cs.util.ArrayUtils;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 * Класс, реализующий логику игры
 */
public class Game {
    private static int currentLevel = 1;
    private int maxLevel;
    private static Skins skin = Skins.SKIN_JUST_BLUE;
    private String name = "unnamed";
    private final List<String> levelRank = new ArrayList<>();
    private final List<Integer> rankSteps = new ArrayList<>();
    private static GameStatus gs = GameStatus.NOT_STARTED;
    private Move move;

    private final String[] cheats = new String[]{"openLVL", "skinCHANGES", "deleteALL", "re:name"};

    private static Path levelsPathFile = BuildFilePathLevel();

    private static Path BuildFilePathLevel() {
        String strTemp = "lvl_" + getCurLevel() + ".txt";
        return Paths.get(SaveManager.getUserDirectory(), "levels", strTemp);
    }

    public static int[][] intS = ArrayUtils.readIntArray2FromFile(levelsPathFile.toString());
    public static int[][] ints = ArrayUtils.readIntArray2FromFile(levelsPathFile.toString());

    private int rows = Objects.requireNonNull(ints).length;
    private int cols = ints[0].length;

    private Cell[][] cells = new Cell[rows][cols];

    private int numOfCells = 0;

    public static Skins getSkin(){
        return skin;
    }

    public static void setSkin(Skins skin){
        Game.skin = skin;
    }

    /**
     * двумерный массив для хранения игрового поля
     * (в данном случае цветов, 0 - пусто; создается / пересоздается при старте игры)
     */
    private int[][] field = null;

    public Game() {
        currentLevel = 1;
        maxLevel = 1;
    }

    public void checkSaveAndLoad() throws FileNotFoundException {
        SaveManager.checkAndSave(this);
        SaveManager.load(this);
    }

    public void writeRecords(JTable tableRecords){
        SaveManager.writeRecords(tableRecords, this);
    }

    public void save() throws FileNotFoundException {
        SaveManager.save(this);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        if (name.equals("What's your name?")){
            return;
        }
        this.name = name;
    }

    public void exit() throws FileNotFoundException {
        SaveManager.save(this);
        System.exit(0);
    }

    public static int getCurLevel(){
        return currentLevel;
    }

    public void setCurLevel(int lvl){
        currentLevel = lvl;
        if (currentLevel >= maxLevel){
            maxLevel = currentLevel;
        }

    }

    public static GameStatus getGs() {
        return gs;
    }

    public static void setGs(GameStatus gs) {
        Game.gs = gs;
    }

    public void activate(){
        setGs(GameStatus.PLAYING);
    }

    public void deactivate(){
        setGs(GameStatus.NOT_STARTED);
    }

    public void win(JTable tableRecords) throws FileNotFoundException {
        setGs(GameStatus.WIN);
        if (getCurLevel() < 10) {
            this.setCurLevel(getCurLevel() + 1);
        }
        this.setCurLevel(getCurLevel());
        writeRecords(tableRecords);
        this.save();
    }

    public boolean isActivated(){
        return getGs().equals(GameStatus.PLAYING);
    }

    public void gpDelete(){

    }

    public void setMaxLevel(int lvl){
        maxLevel = lvl;
    }

    public int getMaxLevel(){
        return maxLevel;
    }

    public void newGame(int rowCount, int colCount) {
        // создаем поле
        field = new int[rowCount][colCount];
    }

    public void setLevelRank(String rank) {
        if (levelRank.size() < currentLevel) {
            levelRank.add(rank);
        } else {
            levelRank.set(currentLevel - 1, rank);
        }
        System.out.println(levelRank);
    }

    public List<String> getRanks() {
        return levelRank;
    }

    public void setRankSteps(Integer rank) {
        if (rankSteps.size() < currentLevel) {
            rankSteps.add(rank);
        } else {
            rankSteps.set(currentLevel - 1, rank);
        }
        System.out.println(rankSteps);
    }

    public List<Integer> getRankSteps() {
        return rankSteps;
    }

    public int getRowCount() {
        return field == null ? 0 : field.length;
    }

    public int getColCount() {
        return field == null ? 0 : field[0].length;
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    public void reGame(){
        Move.moves = 0;
        levelsPathFile = BuildFilePathLevel();
        intS = ArrayUtils.readIntArray2FromFile(levelsPathFile.toString());
        ints = ArrayUtils.readIntArray2FromFile(levelsPathFile.toString());
        rows = Objects.requireNonNull(ints).length;
        cols = ints[0].length;

        setNumOfCells(0);
        setCells(new Cell[getRows()][getCols()]);

        activate();
        countNum();
    }

    public int[][] getIntS(){
        return intS;
    }

    public void setNumOfCells(int i){
        numOfCells = i;
    }

    public void setCells(Cell[][] a){
        cells = a;
    }

    public int getNumOfCells(){
        return numOfCells;
    }

    public Cell getCell(int i, int j){
        return cells[i][j];
    }

    public String[] getCheats(){
        return cheats;
    }

    public void refill(JLabel labelStatus, JLabel numOfCells) {
        labelStatus.setText("Количество шагов " + Move.moves);
        numOfCells.setText("Количество оставшихся яиц " + this.numOfCells);
        boolean flagButton = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                CellType type = CellType.getCellType(ints[i][j]);
                if (type == CellType.NONACTIVE_SPIKES && Move.moves % 2 == 0) {
                    type = CellType.ACTIVE_SPIKES;
                    ints[i][j] = 13;
                } else if (type == CellType.ACTIVE_SPIKES && Move.moves % 2 != 0) {
                    type = CellType.NONACTIVE_SPIKES;
                    ints[i][j] = 12;
                }
                if (this.numOfCells == 0 && CellType.getCellType(intS[i][j]) == CellType.NONACTIVE_FINAL) {
                    intS[i][j] = 5;
                }
                if (type == CellType.PASS_LEFT && flagButton) {
                    ints[i][j] = 11;
                }
                if (type == CellType.PASS_RIGHT && flagButton) {
                    ints[i][j] = 10;
                }
                cells[i][j] = new Cell(i, j, type);

                if (type == CellType.PLAYER) {
                    this.move = createMove(i, j);
                    if (CellType.getCellType(intS[i][j]) == CellType.PLAYER) {
                        intS[i][j] = 0;
                    } else if (CellType.getCellType(intS[i][j]) == CellType.EMPTY_CELL) {
                        intS[i][j] = 3;
                    } else if (CellType.getCellType(intS[i][j]) == CellType.NON_PRESSURE_SPIKES) {
                        intS[i][j] = 16;
                    } else if (CellType.getCellType(intS[i][j]) == CellType.NON_PRESSURE_BUTTON) {
                        intS[i][j] = 18;
                        flagButton = true;
                        i = 0;
                        j = 0;
                    }
                }
                type = CellType.getCellType(ints[i][j]);
                cells[i][j] = new Cell(i, j, type);
            }
        }
    }

    public Move createMove(int i, int j){
        return new Move(i, j);
    }

    public void countNum(){
        for (int[] i : intS) {
            for (int j : i) {
                if (j == 2) {
                    numOfCells++;
                }
            }
        }
    }

    public Move getMove() {
        return move;
    }

}
