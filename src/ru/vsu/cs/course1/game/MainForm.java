package ru.vsu.cs.course1.game;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.vsu.cs.util.JTableUtils;
import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.Locale;

public class MainForm extends JFrame {
    private JPanel panelMain;
    private JTable tableGameField;
    private JLabel labelStatus;
    private JLabel cellsNum;
    private JPanel panelGame;
    private JPanel panelStart;
    private JLabel titleText;
    private JButton startButton;
    private JButton helpButton;
    private JButton exitButton;
    private JButton creditsButton;
    private JButton recordsButton;
    private JPanel panelLevels;
    private JLabel textLevels;
    private JButton level1;
    private JButton level2;
    private JButton level3;
    private JButton level4;
    private JButton level5;
    private JButton level6;
    private JButton level7;
    private JButton level8;
    private JButton level9;
    private JButton level10;
    private JButton backButton;
    private JLabel cheatsText;
    private JTextField cheatsInput;
    private JButton getCheatCode;
    private JLabel versionText;
    private JPanel panelRecords;
    private JTable tableRecords;
    private JButton refreshGame;
    private JButton toLevelSelector;
    private JLabel nameRecords;
    private JButton backRecordButton;
    private JButton deleteAllRecords;
    private JPanel panelTitle;
    private JTextField nameField;
    private JButton nameButton;
    private JPanel panelSkins;
    private JButton nextSkin;
    private JButton previousSkin;
    private JButton skinChanger;
    private JButton chooseSkin;
    private JButton backButtonSkins;
    private JTable tableSkins;
    private JLabel titleTextTitle;

    private static Game game = new Game();

    private Move move;

    private int DEFAULT_COL_COUNT = game.getCols();
    private int DEFAULT_ROW_COUNT = game.getRows();

    private static int DEFAULT_GAP = 8;
    private static final int DEFAULT_CELL_SIZE = 70;

    private GameParams params = new GameParams(DEFAULT_ROW_COUNT, DEFAULT_COL_COUNT);

    private final ParamsDialog dialogParams;

    public static void goToLayout(JPanel jf, String name) {
        CardLayout cardLayout = (CardLayout) jf.getLayout();
        cardLayout.show(jf, name);
    }

    public void refill() {
        game.refill(labelStatus, cellsNum);
    }

    private void levelsButtons(JButton[] lvl, int end) {
        for (int i = 0; i < lvl.length; i++) {
            if (lvl[i] != null) {
                lvl[i].setText("Level " + (i + 1));
                lvl[i].setVisible(i <= end - 1);
            }
        }
    }

    public MainForm() throws FileNotFoundException {
        this.setTitle("Bobby Carrot");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JFrame jFrame = new JFrame();
        this.setBackground(Color.black);
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(MainForm.this,
                        "Save?", "Exit", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(1);
                    try {
                        game.save();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    jFrame.dispose();
                } else {
                    System.exit(1);
                }
            }
        });
        jFrame.setSize(300, 200);
        jFrame.setBackground(Color.RED);
        this.pack();

        setJMenuBar(createMenuBar());
        this.pack();

        game.checkSaveAndLoad();

        SwingUtils.setShowMessageDefaultErrorHandler();

        tableGameField.setRowHeight(DEFAULT_CELL_SIZE);
        tableRecords.setRowHeight(DEFAULT_CELL_SIZE);
        tableSkins.setRowHeight(DEFAULT_CELL_SIZE * 3);

        JTableUtils.initJTableForArray(tableGameField, DEFAULT_CELL_SIZE, false, false, false, false);
        JTableUtils.initJTableForArray(tableSkins, DEFAULT_CELL_SIZE * 3, false, false, false, false);
        JTableUtils.initJTableForArray(tableRecords, DEFAULT_CELL_SIZE * 5, false, false, false, false);
        tableGameField.setIntercellSpacing(new Dimension(0, 0));
        tableGameField.setEnabled(false);

        SwingUtils.setDefaultFont("Minecraft Rus", 16);
        titleText.setText("Bobby Carrot");
        startButton.setText("Start Game");
        recordsButton.setText("Records");
        helpButton.setText("Help");
        creditsButton.setText("Credits");
        exitButton.setText("Exit");
        versionText.setText("ver. 0.01a");
        updateWindowSize();

        backButton.setText("Back");
        cheatsText.setText("CheatCodes");
        getCheatCode.setText("Cheat!");
        textLevels.setText("Choose level");

        refreshGame.setText("Restart level");
        toLevelSelector.setText("Back to level select");

        deleteAllRecords.setText("Delete all records");
        backRecordButton.setText("Back");

        previousSkin.setText("Previous skin");
        nextSkin.setText("Next skin");
        skinChanger.setText("Change skin");

        nameField.setText("What's your name?");
        nameButton.setText("My name is ...");
        backButtonSkins.setText("Back");
        chooseSkin.setText("Choose");

        titleTextTitle.setText("Enter your name");

        JButton[] levels = new JButton[]{level1, level2, level3, level4, level5, level6, level7, level8, level9, level10};
        levelsButtons(levels, game.getMaxLevel());

        panelMain.add(panelTitle, "panelTitle");
        panelMain.add(panelStart, "panelStart");
        panelMain.add(panelLevels, "panelLevels");
        panelMain.add(panelGame, "panelGame");
        panelMain.add(panelRecords, "panelRecords");
        panelMain.add(panelSkins, "panelSkins");

        if (!game.getName().equals("unnamed")) {
            goToLayout(panelMain, "panelStart");
        }
        nameButton.addActionListener(e -> {
            game.setName(nameField.getText());
            goToLayout(panelMain, "panelStart");
        });
        startButton.addActionListener(e -> goToLayout(panelMain, "panelLevels"));
        exitButton.addActionListener(e -> {
            try {
                game.exit();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        backButton.addActionListener(e -> goToLayout(panelMain, "panelStart"));
        getCheatCode.addActionListener(e -> {
            for (String cheat : game.getCheats()) {
                if (cheatsInput.getText().equals(cheat)) {
                    switch (cheat) {
                        case "openLVL" -> levelsButtons(levels, levels.length);
                        case "deleteALL" -> {
                            SaveManager.delete();
                            game = new Game();
                            SaveManager.writeRecords(tableRecords, game);
                            levelsButtons(levels, game.getMaxLevel());
                        }
                        case "re:name" -> goToLayout(panelMain, "panelTitle");
                    }
                }
            }
        });
        for (int i = 0; i < levels.length; i++) {
            int finalI = i;
            levels[i].addActionListener(e -> {
                game.setCurLevel(finalI + 1);
                goToLayout(panelMain, "panelGame");
                newGame();
            });
        }
        helpButton.addActionListener(e -> SwingUtils.showInfoMessageBox("""
                Ваша задача разложить все пасхальные яйца по гнёздам и встать на финишный круг.
                Опасайтесь различного рода ловушек и попробуйте не забаррикадировать самого себя.
                Продумайте свой путь заранее. Удачи!""", "Правила"));
        creditsButton.addActionListener(e -> SwingUtils.showInfoMessageBox(
                """
                        Bobby Carrot

                        Автор: Шлыков Д.Г.
                        """,
                "О программе"
        ));
        refreshGame.addActionListener(e -> newGame());
        toLevelSelector.addActionListener(e -> {
            game.deactivate();
            goToLayout(panelMain, "panelLevels");
        });
        skinChanger.addActionListener(e -> goToLayout(panelMain, "panelSkins"));
        recordsButton.addActionListener(e -> {
            nameRecords.setText(game.getName() + "'s records");
            goToLayout(panelMain, "panelRecords");
        });
        backRecordButton.addActionListener(e -> goToLayout(panelMain, "panelStart"));
        deleteAllRecords.addActionListener(e -> {
            game.gpDelete();
            game = new Game();
            game.writeRecords(tableRecords);
            levelsButtons(levels, game.getMaxLevel());
        });
        backButtonSkins.addActionListener(e -> goToLayout(panelMain, "panelLevels"));
        nextSkin.addActionListener(e -> {
            Game.setSkin(Skins.getSkin(1));
            repaint();
        });
        previousSkin.addActionListener(e -> {
            Game.setSkin(Skins.getSkin(-1));
            repaint();
        });

        tableGameField.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            final class DrawComponent extends Component {
                private int row = 0, column = 0;

                @Override
                public void paint(Graphics gr) {
                    if (row < game.getRows() && column < game.getCols()) {
                        Graphics2D g2d = (Graphics2D) gr;
                        int width = getWidth() - 2;
                        int height = getHeight() - 2;
                        try {
                            paintCell(row, column, g2d, width, height);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            final DrawComponent comp = new DrawComponent();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                comp.row = row;
                comp.column = column;
                return comp;
            }
        });

        tableSkins.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            final class DrawComponent extends Component {
                private int row = 0, column = 0;

                @Override
                public void paint(Graphics gr) {
                    if (row < game.getRows() && column < game.getCols()) {
                        Graphics2D g2d = (Graphics2D) gr;
                        int width = getWidth() - 2;
                        int height = getHeight() - 2;
                        try {
                            paintCellSkin(g2d, width, height);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            final DrawComponent comp = new DrawComponent();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                comp.row = row;
                comp.column = column;
                return comp;
            }
        });

        newGame();
        updateWindowSize();
        updateView();

        if (game.getRanks().size() > 0) {
            game.writeRecords(tableRecords);
        }

        dialogParams = new ParamsDialog(params, tableGameField, e -> newGame());

        /*
            Обработка событий нажатия клавиш (если в вашей программе не нужно, удалить код ниже)
            сделано так, а не через addKeyListener, так в последнем случае события будет получать компонент с фокусом,
            т.е. если на форме есть, например, кнопка или поле ввода, то все события уйдут этому компоненту
         */
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(e -> {
            if (game.isActivated()) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (game.isActivated()) {
                        move = game.getMove();
                        if (e.getKeyCode() == KeyEvent.VK_W) {
                            move.moveUp();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_S) {
                            move.moveDown();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_A) {
                            move.moveLeft();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_D) {
                            move.moveRight();
                        }
                        refill();
                        repaint();
                    }
                }
            }
            return false;
        });
    }

    private JMenuItem createMenuItem(String text, String shortcut, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(listener);
        if (shortcut != null) {
            menuItem.setAccelerator(KeyStroke.getKeyStroke(shortcut.replace('+', ' ')));
        }
        return menuItem;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBarMain = new JMenuBar();

        JMenu menuGame = new JMenu("Игра");
        menuBarMain.add(menuGame);
        menuGame.add(createMenuItem("Новая", "ctrl+N", e -> newGame()));
        menuGame.add(createMenuItem("Параметры", "ctrl+P", e -> {
            dialogParams.updateView();
            dialogParams.setVisible(true);
        }));
        menuGame.addSeparator();
        menuGame.add(createMenuItem("Выход", "ctrl+X", e -> System.exit(0)));

        JMenu menuView = new JMenu("Вид");
        menuBarMain.add(menuView);
        menuView.add(createMenuItem("Подогнать размер окна", null, e -> updateWindowSize()));
        menuView.addSeparator();
        SwingUtils.initLookAndFeelMenu(menuView);

        JMenu menuHelp = new JMenu("Справка");
        menuBarMain.add(menuHelp);
        menuHelp.add(createMenuItem("Правила", "ctrl+R", e -> SwingUtils.showInfoMessageBox("""
                Ваша задача разложить все пасхальные яйца по гнёздам и встать на финишный круг.
                Опасайтесь различного рода ловушек и попробуйте не забаррикадировать самого себя.
                Продумайте свой путь заранее. Удачи!""", "Правила")));
        menuHelp.add(createMenuItem("О программе", "ctrl+A", e -> SwingUtils.showInfoMessageBox(
                "\nСОХРАНЕНИЯ РАБОТАЮТ ТОЛЬКО НА \"Exit\" НА ПАНЕЛЬКЕ СТАРТА",
                "О программе"
        )));

        return menuBarMain;
    }

    private void updateWindowSize() {
        int menuSize = this.getJMenuBar() != null ? this.getJMenuBar().getHeight() : 0;
        SwingUtils.setFixedSize(
                this,
                tableGameField.getWidth() + 2 * DEFAULT_GAP + 60,
                tableGameField.getHeight() + panelMain.getY() + labelStatus.getHeight() +
                        menuSize + DEFAULT_GAP + 2 * DEFAULT_GAP + 60
        );
        this.setMaximumSize(null);
        this.setMinimumSize(null);
    }

    private void updateView() {
        tableGameField.repaint();
        tableSkins.repaint();
    }

    private void paintCellSkin(Graphics2D g2d, int cellWidth, int cellHeight) throws FileNotFoundException {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        DrawingModule.drawPlayer(g2d, cellWidth, cellHeight);
    }

    private void paintCell(int row, int col, Graphics2D g2d, int cellWidth, int cellHeight) throws FileNotFoundException {
        Cell cell = game.getCell(row, col);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        switch (cell.getType()) {
            case WALL -> DrawingModule.drawWall(g2d, cellWidth, cellHeight);
            case PLAYER -> {
                switch (CellType.getCellType((game.getIntS()[row][col]))) {
                    case NONACTIVE_SPIKES -> DrawingModule.drawNonSpike(g2d, cellWidth, cellHeight);
                    case FILLED_CELL -> {
                        if (Move.getFlag()) {
                            game.setNumOfCells(game.getNumOfCells() - 1);
                            Move.setFlag(false);
                        }
                        DrawingModule.drawEmptyCell(g2d, cellWidth, cellHeight);
                    }
                    case NONACTIVE_FINAL -> DrawingModule.drawNonGoal(g2d, cellWidth, cellHeight);
                    case ACTIVE_FINAL -> {
                        DrawingModule.drawGoal(g2d, cellWidth, cellHeight);
                        winGame();
                    }
                    case PRESSURE_SPIKES -> DrawingModule.drawNonPressureSpikes(g2d, cellWidth, cellHeight);
                    case PRESSURE_BUTTON -> DrawingModule.drawPressureButton(g2d, cellWidth, cellHeight);
                }
                DrawingModule.drawPlayer(g2d, cellWidth, cellHeight);
            }
            case NONACTIVE_SPIKES -> DrawingModule.drawNonSpike(g2d, cellWidth, cellHeight);
            case ACTIVE_SPIKES -> DrawingModule.drawSpike(g2d, cellWidth, cellHeight);
            case EMPTY_CELL -> DrawingModule.drawEmptyCell(g2d, cellWidth, cellHeight);
            case FILLED_CELL -> DrawingModule.drawFilledCell(g2d, cellWidth, cellHeight);
            case NONACTIVE_FINAL -> DrawingModule.drawNonGoal(g2d, cellWidth, cellHeight);
            case ACTIVE_FINAL -> DrawingModule.drawGoal(g2d, cellWidth, cellHeight);
            case PASS_LEFT -> DrawingModule.drawPassLeft(g2d, cellWidth, cellHeight);
            case PASS_RIGHT -> DrawingModule.drawPassRight(g2d, cellWidth, cellHeight);
            case NON_PRESSURE_SPIKES -> DrawingModule.drawNonPressureSpikes(g2d, cellWidth, cellHeight);
            case PRESSURE_SPIKES -> DrawingModule.drawPressureSpikes(g2d, cellWidth, cellHeight);
            case NON_PRESSURE_BUTTON -> DrawingModule.drawNonPressureButton(g2d, cellWidth, cellHeight);
            case PRESSURE_BUTTON -> DrawingModule.drawPressureButton(g2d, cellWidth, cellHeight);
        }
    }

    private void createGame() {
        game.newGame(params.getRowCount(), params.getColCount());
        JTableUtils.resizeJTable(tableGameField,
                game.getRowCount(), game.getColCount(),
                tableGameField.getRowHeight(), tableGameField.getRowHeight()
        );
    }

    private void newGame() {
        game.reGame();

        DEFAULT_ROW_COUNT = game.getRows();
        DEFAULT_COL_COUNT = game.getCols();
        DEFAULT_GAP = game.getRows();

        params = new GameParams(DEFAULT_ROW_COUNT, DEFAULT_COL_COUNT);

        refill();

        createGame();

        updateView();
    }

    private void winGame() throws FileNotFoundException {
        WinForm winForm = new WinForm(Move.moves, panelMain, e -> newGame(), game);
        JButton[] levels = new JButton[]{level1, level2, level3, level4, level5, level6, level7, level8, level9, level10};
        game.win(tableRecords);
        levelsButtons(levels, game.getMaxLevel());
        winForm.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        panelMain = new JPanel();
        panelMain.setLayout(new CardLayout(0, 0));
        panel1.add(panelMain, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelStart = new JPanel();
        panelStart.setLayout(new GridLayoutManager(9, 4, new Insets(10, 10, 10, 55), -1, -1));
        panelMain.add(panelStart, "Card2");
        final Spacer spacer1 = new Spacer();
        panelStart.add(spacer1, new GridConstraints(2, 0, 6, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panelStart.add(spacer2, new GridConstraints(2, 1, 6, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panelStart.add(spacer3, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panelStart.add(spacer4, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        startButton = new JButton();
        startButton.setText("Button");
        panelStart.add(startButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(143, 30), null, 0, false));
        recordsButton = new JButton();
        recordsButton.setText("Button");
        panelStart.add(recordsButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(143, 30), null, 0, false));
        helpButton = new JButton();
        helpButton.setText("Button");
        panelStart.add(helpButton, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(143, 30), null, 0, false));
        creditsButton = new JButton();
        creditsButton.setText("Button");
        panelStart.add(creditsButton, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(143, 30), null, 0, false));
        exitButton = new JButton();
        exitButton.setText("Button");
        panelStart.add(exitButton, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(143, 30), null, 0, false));
        versionText = new JLabel();
        versionText.setText("Label");
        panelStart.add(versionText, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleText = new JLabel();
        Font titleTextFont = this.$$$getFont$$$("Minecraft Rus", Font.BOLD, 72, titleText.getFont());
        if (titleTextFont != null) titleText.setFont(titleTextFont);
        titleText.setText("Label");
        panelStart.add(titleText, new GridConstraints(0, 1, 1, 3, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panelStart.add(spacer5, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        panelLevels = new JPanel();
        panelLevels.setLayout(new GridLayoutManager(9, 12, new Insets(10, 10, 10, 55), -1, -1));
        panelMain.add(panelLevels, "Card3");
        final Spacer spacer6 = new Spacer();
        panelLevels.add(spacer6, new GridConstraints(1, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        panelLevels.add(spacer7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        level8 = new JButton();
        level8.setText("Button");
        panelLevels.add(level8, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 100), new Dimension(100, 100), new Dimension(100, 100), 0, false));
        level9 = new JButton();
        level9.setText("Button");
        panelLevels.add(level9, new GridConstraints(2, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 100), new Dimension(100, 100), new Dimension(100, 100), 0, false));
        level1 = new JButton();
        level1.setText("Button");
        panelLevels.add(level1, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 100), new Dimension(100, 100), new Dimension(100, 100), 0, false));
        level7 = new JButton();
        level7.setText("Button");
        panelLevels.add(level7, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 100), new Dimension(100, 100), new Dimension(100, 100), 0, false));
        level2 = new JButton();
        level2.setText("Button");
        panelLevels.add(level2, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 100), new Dimension(100, 100), new Dimension(100, 100), 0, false));
        level3 = new JButton();
        level3.setText("Button");
        panelLevels.add(level3, new GridConstraints(1, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 100), new Dimension(100, 100), new Dimension(100, 100), 0, false));
        level4 = new JButton();
        level4.setText("Button");
        panelLevels.add(level4, new GridConstraints(1, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 100), new Dimension(100, 100), new Dimension(100, 100), 0, false));
        level5 = new JButton();
        level5.setText("Button");
        panelLevels.add(level5, new GridConstraints(1, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 100), new Dimension(100, 100), new Dimension(100, 100), 0, false));
        level10 = new JButton();
        level10.setText("Button");
        panelLevels.add(level10, new GridConstraints(2, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 100), new Dimension(100, 100), new Dimension(100, 100), 0, false));
        level6 = new JButton();
        level6.setText("Button");
        panelLevels.add(level6, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 100), new Dimension(100, 100), new Dimension(100, 100), 0, false));
        final Spacer spacer8 = new Spacer();
        panelLevels.add(spacer8, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        panelLevels.add(spacer9, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        panelLevels.add(spacer10, new GridConstraints(1, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        panelLevels.add(spacer11, new GridConstraints(2, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        panelLevels.add(spacer12, new GridConstraints(4, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        cheatsInput = new JTextField();
        panelLevels.add(cheatsInput, new GridConstraints(6, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, -1), null, 0, false));
        cheatsText = new JLabel();
        cheatsText.setText("Label");
        panelLevels.add(cheatsText, new GridConstraints(5, 11, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        getCheatCode = new JButton();
        getCheatCode.setText("Button");
        panelLevels.add(getCheatCode, new GridConstraints(7, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setText("Button");
        panelLevels.add(backButton, new GridConstraints(7, 6, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textLevels = new JLabel();
        textLevels.setText("Label");
        panelLevels.add(textLevels, new GridConstraints(0, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        panelLevels.add(spacer13, new GridConstraints(4, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        skinChanger = new JButton();
        skinChanger.setText("Button");
        panelLevels.add(skinChanger, new GridConstraints(3, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelGame = new JPanel();
        panelGame.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelGame, "Card1");
        final JScrollPane scrollPane1 = new JScrollPane();
        panelGame.add(scrollPane1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableGameField = new JTable();
        scrollPane1.setViewportView(tableGameField);
        labelStatus = new JLabel();
        labelStatus.setText("Label");
        panelGame.add(labelStatus, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cellsNum = new JLabel();
        cellsNum.setText("Label");
        panelGame.add(cellsNum, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        refreshGame = new JButton();
        refreshGame.setText("Button");
        panelGame.add(refreshGame, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        toLevelSelector = new JButton();
        toLevelSelector.setText("Button");
        panelGame.add(toLevelSelector, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelRecords = new JPanel();
        panelRecords.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        panelMain.add(panelRecords, "Card4");
        tableRecords = new JTable();
        tableRecords.setAutoCreateRowSorter(true);
        tableRecords.setAutoResizeMode(3);
        tableRecords.setEnabled(false);
        Font tableRecordsFont = this.$$$getFont$$$("Certa Serif", Font.PLAIN, 22, tableRecords.getFont());
        if (tableRecordsFont != null) tableRecords.setFont(tableRecordsFont);
        panelRecords.add(tableRecords, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        nameRecords = new JLabel();
        Font nameRecordsFont = this.$$$getFont$$$("Unispace", Font.BOLD, 26, nameRecords.getFont());
        if (nameRecordsFont != null) nameRecords.setFont(nameRecordsFont);
        nameRecords.setText("Label");
        panelRecords.add(nameRecords, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelRecords.add(panel2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        backRecordButton = new JButton();
        backRecordButton.setText("Button");
        panel2.add(backRecordButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteAllRecords = new JButton();
        deleteAllRecords.setText("Button");
        panel2.add(deleteAllRecords, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelTitle = new JPanel();
        panelTitle.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        panelMain.add(panelTitle, "Card5");
        titleTextTitle = new JLabel();
        Font titleTextTitleFont = this.$$$getFont$$$("Minecraft Rus", Font.BOLD, 48, titleTextTitle.getFont());
        if (titleTextTitleFont != null) titleTextTitle.setFont(titleTextTitleFont);
        titleTextTitle.setText("Label");
        panelTitle.add(titleTextTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelTitle.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nameField = new JTextField();
        panel3.add(nameField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nameButton = new JButton();
        nameButton.setText("Button");
        panel3.add(nameButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        panel3.add(spacer14, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer15 = new Spacer();
        panel3.add(spacer15, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer16 = new Spacer();
        panel3.add(spacer16, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        panelSkins = new JPanel();
        panelSkins.setLayout(new GridLayoutManager(1, 2, new Insets(10, 10, 10, 10), -1, -1));
        panelMain.add(panelSkins, "Card6");
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelSkins.add(panel4, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(3, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        previousSkin = new JButton();
        previousSkin.setText("Button");
        panel6.add(previousSkin, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backButtonSkins = new JButton();
        backButtonSkins.setText("Button");
        panel6.add(backButtonSkins, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextSkin = new JButton();
        nextSkin.setText("Button");
        panel6.add(nextSkin, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chooseSkin = new JButton();
        chooseSkin.setText("Button");
        panel6.add(chooseSkin, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer17 = new Spacer();
        panel6.add(spacer17, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer18 = new Spacer();
        panel6.add(spacer18, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer19 = new Spacer();
        panel6.add(spacer19, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, new Dimension(1, 1), 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableSkins = new JTable();
        tableSkins.setEnabled(false);
        panel7.add(tableSkins, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
