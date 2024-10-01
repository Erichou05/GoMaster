package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Game;
import model.GameHistory;

public class GameBoardUI extends JPanel {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int BOARD_SIZE = 19;
    private static final int STONE_RADIUS = 15;
    private int[][] board;
    private List<Move> moveList;
    private Map<Integer, String> annotations;
    private int currentPlayer;
    private boolean gameEnded;
    private JPanel boardPanel;
    private GameHistory gameHistory;
    private Game game;

    /*
     * EFFECTS: initializes the user interface for the game board, moves, and
     * annotations
     */
    public GameBoardUI(GameHistory gameHistory, Game game) {
        this.gameHistory = gameHistory;
        this.game = game;
        board = new int[BOARD_SIZE][BOARD_SIZE];
        moveList = new ArrayList<>();
        annotations = new HashMap<>();
        currentPlayer = 1; // Start with player 1 (black), 2 represent player 2 (white)
        gameEnded = false;
        initializeBoard();
        setupUI();
    }

    /*
     * EFFECTS: initializes an empty game board
     */
    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = 0; // 0 represents an empty intersection
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the user interface of recording games
     */
    private void setupUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setupBoardPanel();
        setupControlPanel();
        add(boardPanel, BorderLayout.CENTER);
        add(setupControlPanel(), BorderLayout.SOUTH);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the user interface of the game board
     */
    private void setupBoardPanel() {
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        boardPanel.setPreferredSize(new Dimension(400, 400));
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the buttons for recording games
     */
    private JPanel setupControlPanel() {
        JPanel controlPanel = new JPanel();
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> undoMove());
        JButton addAnnotationButton = new JButton("Add Annotation");
        addAnnotationButton.addActionListener(e -> handleAddAnnotation());
        JButton endGameButton = new JButton("End Game");
        endGameButton.addActionListener(e -> endGame());
        controlPanel.add(undoButton);
        controlPanel.add(addAnnotationButton);
        controlPanel.add(endGameButton);
        return controlPanel;
    }


    /*
     * MODIFIES: this
     * EFFECTS: handles adding moves by clicking on the game board
     */
    private void handleMouseClick(MouseEvent e) {
        if (!gameEnded) {
            int panelWidth = boardPanel.getWidth();
            int panelHeight = boardPanel.getHeight();
            int gridSize = Math.min(panelWidth, panelHeight) / (BOARD_SIZE + 1);
            int offset = gridSize / 2;
            int x = (e.getX() - offset + gridSize / 2) / gridSize; // get nearest x grid
            int y = (e.getY() - offset + gridSize / 2) / gridSize; // get nearest y grid
            if (isValidMove(x, y)) {
                if (addMove(x, y, currentPlayer)) {
                    nextPlayer();
                }
            }
        }
    }

    /*
     * EFFECTS: returns true if the move is valid (on an empty intersection), false otherwise
     */
    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board[x][y] == 0;
    }

    /*
     * MODIFIES: this
     * EFFECTS: records annotation of a specific move
     */
    private void handleAddAnnotation() {
        String annotation = JOptionPane.showInputDialog("Enter annotation:");
        if (!annotation.isEmpty()) {
            if (!moveList.isEmpty()) {
                addAnnotation(annotation, moveList.size() - 1);
            } else {
                JOptionPane.showMessageDialog(null, "No moves to annotate.");
            }
        }
    }

    /*
     * MODIFIES：this
     * EFFECTS: updates the board with the new move
     */
    private boolean addMove(int x, int y, int player) {
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE || board[x][y] != 0) {
            return false;
        }
        board[x][y] = player;
        moveList.add(new Move(x, y));
        removeCapturedStones(x, y, player);
        boardPanel.repaint();
        return true;
    }

    /*
     * MODIFIES：this
     * EFFECTS: updates the board with the last move removed
     */
    private void undoMove() {
        if (!moveList.isEmpty()) {
            Move lastMove = moveList.remove(moveList.size() - 1);
            board[lastMove.getCoordX()][lastMove.getCoordY()] = 0;
            nextPlayer();
            boardPanel.repaint();
        }
    }

    /*
     * MODIFIES：this
     * EFFECTS: updates the next player
     */
    private int nextPlayer() {
        currentPlayer = 3 - currentPlayer; // player 1 -> player 2 / player 2 -> player 1
        return currentPlayer;
    }

    /*
     * MODIFIES：this
     * EFFECTS: records the annotation
     */
    private void addAnnotation(String annotation, int moveIndex) {
        annotations.put(moveIndex, annotation);
    }

    /*
     * MODIFIES：this
     * EFFECTS: removes stones captured if there is any by the last stones placed on
     * the board
     */
    private void removeCapturedStones(int x, int y, int player) {
        int opponent = 3 - player;
        boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        for (Move move : getAdjacentIntersections(x, y)) {
            if (board[move.getCoordX()][move.getCoordY()] == opponent
                    && isGroupCaptured(move.getCoordX(), move.getCoordY(), opponent, visited)) {
                for (Move capturedStone : getGroupStones(move.getCoordX(), move.getCoordY())) {
                    board[capturedStone.getCoordX()][capturedStone.getCoordY()] = 0;
                }
            }
        }
    }

    /*
     * EFFECTS: return true if the given stone has no liberty (captured), false
     * otherwise
     */
    private boolean isGroupCaptured(int x, int y, int color, boolean[][] visited) {
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) {
            return true;
        }
        if (board[x][y] == 0) {
            return false;
        }
        if (visited[x][y] || board[x][y] != color) {
            return true;
        }
        visited[x][y] = true;
        return isGroupCaptured(x + 1, y, color, visited)
                && isGroupCaptured(x - 1, y, color, visited)
                && isGroupCaptured(x, y + 1, color, visited)
                && isGroupCaptured(x, y - 1, color, visited);
    }

    /*
     * EFFECTS: returns a list of stones connected to the given stone with the same
     * color
     */
    private List<Move> getGroupStones(int x, int y) {
        List<Move> group = new ArrayList<>();
        boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        collectGroupStones(x, y, board[x][y], visited, group);
        return group;
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds adjacent moves with the same color to the group
     */
    private void collectGroupStones(int x, int y, int color, boolean[][] visited, List<Move> group) {
        if (!(x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) && !(board[x][y] != color || visited[x][y])) {
            visited[x][y] = true;
            group.add(new Move(x, y));
            collectGroupStones(x + 1, y, color, visited, group);
            collectGroupStones(x - 1, y, color, visited, group);
            collectGroupStones(x, y + 1, color, visited, group);
            collectGroupStones(x, y - 1, color, visited, group);
        }
    }

    /*
     * EFFECTS: returns the list of positions adjacent to the given position
     */
    private List<Move> getAdjacentIntersections(int x, int y) {
        List<Move> adjacentMoves = new ArrayList<>();
        if (x > 0) {
            adjacentMoves.add(new Move(x - 1, y));
        }
        if (x < BOARD_SIZE - 1) {
            adjacentMoves.add(new Move(x + 1, y));
        }
        if (y > 0) {
            adjacentMoves.add(new Move(x, y - 1));
        }
        if (y < BOARD_SIZE - 1) {
            adjacentMoves.add(new Move(x, y + 1));
        }
        return adjacentMoves;
    }

    /*
     * EFFECTS: draws the grid lines and the stones
     */
    private void drawBoard(Graphics g) {
        int panelWidth = boardPanel.getWidth();
        int panelHeight = boardPanel.getHeight();
        int gridSize = Math.min(panelWidth, panelHeight) / (BOARD_SIZE + 1);
        int offset = gridSize / 2;
        g.setColor(Color.BLACK);
        for (int i = 0; i < BOARD_SIZE; i++) {
            int pos = offset + i * gridSize;
            g.drawLine(offset, pos, offset + (BOARD_SIZE - 1) * gridSize, pos);
            g.drawLine(pos, offset, pos, offset + (BOARD_SIZE - 1) * gridSize);
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != 0) {
                    if (board[i][j] == 1) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillOval(offset + i * gridSize - STONE_RADIUS, offset + j * gridSize - STONE_RADIUS,
                            2 * STONE_RADIUS, 2 * STONE_RADIUS);
                }
            }
        }
    }

    /*
     * MODIFIES: GameHistory
     * EFFECTS: records game data to game history
     */
    private void endGame() {
        List<Integer> moves = new ArrayList<>();
        for (Move move : moveList) {
            moves.add(move.getCoordX() * 100 + move.getCoordY());
        }
        List<String> annotation = new ArrayList<>();
        for (Map.Entry<Integer, String> a : annotations.entrySet()) {
            annotation.add("Move " + (a.getKey() + 1) + ": " + a.getValue());
        }
        game.setMoves(moves);
        game.setAnnotation(annotation);
        ;
        gameHistory.addGame(game);
        gameEnded = true;
        JOptionPane.showMessageDialog(null, "Game ended.");
    }

    // helper class to handle move
    private class Move {
        private final int coordX;
        private final int coordY;

        public Move(int x, int y) {
            this.coordX = x;
            this.coordY = y;
        }

        public int getCoordX() {
            return coordX;
        }

        public int getCoordY() {
            return coordY;
        }
    }
}
