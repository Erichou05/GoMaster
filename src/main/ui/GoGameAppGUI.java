package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import model.Game;
import model.Event;
import model.EventLog;
import model.GameHistory;
import model.Player;
import model.PlayerList;
import persistence.JsonReader;
import persistence.JsonWriter;

public class GoGameAppGUI extends JFrame implements ActionListener {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private static final String GAMEHISTORY_JSON_STORE = "./data/gameHistory.json";
    private static final String PLAYERLIST_JSON_STORE = "./data/playerList.json";
    private JsonWriter gameJsonWriter;
    private JsonReader gameJsonReader;
    private JsonWriter playerJsonWriter;
    private JsonReader playerJsonReader;
    private GameHistory gameHistory = GameHistory.getInstance();
    private PlayerList playerList = PlayerList.getInstance();

    private JTextArea outputArea;
    private JButton addPlayerButton;
    private JButton removePlayerButton;
    private JButton viewPlayerDetailsButton;
    private JButton recordGameButton;
    private JButton overviewGameButton;
    private JButton viewGameButton;
    private JButton deleteGameButton;
    private JButton filterGameButton;
    private JButton quitButton;
    private JButton viewPlayerListButton;
    private JButton updatePlayerRatingButton;
    private JButton viewPlayerWinRateButton;
    private JButton viewPlayerPerformanceCurveButton;
    private JButton saveButton;
    private JButton loadButton;
    private JScrollPane scrollPane;

    /*
     * EFFECTS: initializes the user interface and Json reader and writer
     */
    public GoGameAppGUI() {
        setTitle("Game Management System");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        initializeJson();

        initializeButtons();
        initializeButtonPanels();
        setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes Json reader and Json writer
     */
    private void initializeJson() {
        gameJsonWriter = new JsonWriter(GAMEHISTORY_JSON_STORE);
        gameJsonReader = new JsonReader(GAMEHISTORY_JSON_STORE);
        playerJsonWriter = new JsonWriter(PLAYERLIST_JSON_STORE);
        playerJsonReader = new JsonReader(PLAYERLIST_JSON_STORE);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes buttons by associating with action listener
     */
    private void initializeButtons() {
        initializeGameButtons();
        initializePlayerButtons();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes game buttons by associating with action listener
     */
    private void initializeGameButtons() {
        recordGameButton = new JButton("Record New Game");
        overviewGameButton = new JButton("Overview Games");
        viewGameButton = new JButton("View Game");
        deleteGameButton = new JButton("Delete Game");
        filterGameButton = new JButton("Filter Games");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        quitButton = new JButton("Quit");

        recordGameButton.addActionListener(this);
        overviewGameButton.addActionListener(this);
        viewGameButton.addActionListener(this);
        deleteGameButton.addActionListener(this);
        filterGameButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        quitButton.addActionListener(this);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes player buttons by associating with action listener
     */
    private void initializePlayerButtons() {
        addPlayerButton = new JButton("Add Player");
        removePlayerButton = new JButton("Remove Player");
        viewPlayerDetailsButton = new JButton("View Player Details");
        viewPlayerListButton = new JButton("View Player List");
        updatePlayerRatingButton = new JButton("Update Player Rating");
        viewPlayerWinRateButton = new JButton("View Player Win Rate");
        viewPlayerPerformanceCurveButton = new JButton("View Performance Curve");

        addPlayerButton.addActionListener(this);
        removePlayerButton.addActionListener(this);
        viewPlayerDetailsButton.addActionListener(this);
        viewPlayerListButton.addActionListener(this);
        updatePlayerRatingButton.addActionListener(this);
        viewPlayerWinRateButton.addActionListener(this);
        viewPlayerPerformanceCurveButton.addActionListener(this);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds buttons to button panels
     */
    private void initializeButtonPanels() {
        // Button panel for game functions (on the left)
        JPanel gameButtonPanel = new JPanel();
        gameButtonPanel.setLayout(new BoxLayout(gameButtonPanel, BoxLayout.Y_AXIS));
        gameButtonPanel.add(recordGameButton);
        gameButtonPanel.add(overviewGameButton);
        gameButtonPanel.add(viewGameButton);
        gameButtonPanel.add(deleteGameButton);
        gameButtonPanel.add(filterGameButton);
        gameButtonPanel.add(saveButton);
        gameButtonPanel.add(loadButton);
        gameButtonPanel.add(quitButton);
        add(gameButtonPanel, BorderLayout.WEST);

        // Button panel for player functions (on the right)
        JPanel playerButtonPanel = new JPanel();
        playerButtonPanel.setLayout(new BoxLayout(playerButtonPanel, BoxLayout.Y_AXIS));
        playerButtonPanel.add(addPlayerButton);
        playerButtonPanel.add(removePlayerButton);
        playerButtonPanel.add(viewPlayerDetailsButton);
        playerButtonPanel.add(viewPlayerListButton);
        playerButtonPanel.add(updatePlayerRatingButton);
        playerButtonPanel.add(viewPlayerWinRateButton);
        playerButtonPanel.add(viewPlayerPerformanceCurveButton);
        add(playerButtonPanel, BorderLayout.EAST);
    }

    /*
     * EFFECTS: calls method when buttons are pressed
     */
    @SuppressWarnings("methodlength")
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Add Player")) {
            addPlayer();
        } else if (command.equals("Remove Player")) {
            removePlayer();
        } else if (command.equals("View Player Details")) {
            viewPlayerDetails();
        } else if (command.equals("View Player List")) {
            viewPlayerList();
        } else if (command.equals("Update Player Rating")) {
            updatePlayerRating();
        } else if (command.equals("View Player Win Rate")) {
            viewPlayerWinRate();
        } else if (command.equals("View Performance Curve")) {
            viewPerformanceCurve();
        } else if (command.equals("Record New Game")) {
            recordNewGame();
        } else if (command.equals("Overview Games")) {
            overviewGames();
        } else if (command.equals("View Game")) {
            viewGame();
        } else if (command.equals("Delete Game")) {
            deleteGame();
        } else if (command.equals("Filter Games")) {
            filterGames();
        } else if (command.equals("Save")) {
            saveData();
        } else if (command.equals("Load")) {
            loadData();
        } else if (command.equals("Quit")) {
            printEventLog();
            System.exit(0);
        } else {
            outputArea.setText("Unknown Command");
        }
    }

    private void printEventLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n");
        }
    }

    /*
     * MODIFIES: playerList
     * EFFECTS: adds unique player with given non-empty name with default rating
     */
    private void addPlayer() {
        try {
            String playerName = JOptionPane.showInputDialog(this, "Enter player name:");
            if (playerName.isEmpty()) {
                throw new IllegalArgumentException("Player name cannot be empty.");
            }

            Player existingPlayer = playerList.findPlayer(playerName);
            if (existingPlayer != null) {
                throw new IllegalArgumentException("Player with this name already exists.");
            }

            Player newPlayer = new Player(playerName);
            playerList.addPlayer(newPlayer);
            outputArea.setText("Player " + playerName + " added successfully with default rating 1250.");
        } catch (IllegalArgumentException e) {
            outputArea.setText(e.getMessage());
        }
    }

    /*
     * MODIFIES: playerList
     * EFFECTS: removes player with given name if such player exists
     */
    private void removePlayer() {
        try {
            String playerName = JOptionPane.showInputDialog(this, "Enter player name to remove:");
            if (playerName.isEmpty()) {
                throw new IllegalArgumentException("Player name cannot be empty.");
            }
            Player playerToRemove = playerList.findPlayer(playerName);
            if (playerToRemove == null) {
                throw new IllegalArgumentException("Player not found.");
            }
            playerList.removePlayer(playerName);
            outputArea.setText("Player " + playerName + " removed successfully.");
        } catch (IllegalArgumentException e) {
            outputArea.setText(e.getMessage());
        }
    }

    /*
     * EFFECTS: prints the rating, win rate, past games, and rating history of the
     * specified player
     */
    private void viewPlayerDetails() {
        try {
            String playerName = JOptionPane.showInputDialog(this, "Enter player name to view details:");
            if (playerName.isEmpty()) {
                throw new IllegalArgumentException("Player name cannot be empty.");
            }
            Player player = playerList.findPlayer(playerName);
            if (player == null) {
                throw new IllegalArgumentException("Player not found.");
            }
            String playerDetails = "Name: " + player.getName() + "\n"
                    + "Rating: " + player.getRating() + "\n"
                    + "Win Rate: " + gameHistory.getPlayerWinRate(playerName) + "\n"
                    + "Games Played: " + gameHistory.filterByPlayer(playerName, gameHistory.getGameHistory()).size()
                    + "\n"
                    + "Rating History: " + gameHistory.getRatingHistory(playerName).toString();
            outputArea.setText(playerDetails);
        } catch (IllegalArgumentException e) {
            outputArea.setText(e.getMessage());
        }
    }

    /*
     * EFFECTS: prints a list of all players with their name and current rating
     */
    private void viewPlayerList() {
        String playerListString = "Player List:\n";
        for (Player player : playerList.getPlayers()) {
            playerListString += "Name: " + player.getName() + ", Rating: " + player.getRating() + "\n";
        }
        outputArea.setText(playerListString);
    }

    /*
     * MODIFIES: playerList
     * EFFECTS: changes the rating of player with given name to given rating if such
     * player exists
     */
    private void updatePlayerRating() {
        String playerName = JOptionPane.showInputDialog(this, "Enter player name:");
        Player player = playerList.findPlayer(playerName);
        if (player == null) {
            outputArea.setText("Player not found.");
        }
        String newRatingString = JOptionPane.showInputDialog(this, "Enter new rating:");
        try {
            int newRating = Integer.parseInt(newRatingString);
            player.setRating(newRating);
            outputArea.setText("Player " + playerName + "'s rating updated to " + newRating + ".");
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid rating.");
        }
    }

    /*
     * EFFECTS: prints the win rate of player with given name if such player exists
     */
    private void viewPlayerWinRate() {
        try {
            String playerName = JOptionPane.showInputDialog(this, "Enter player name:");
            if (playerName.isEmpty()) {
                throw new IllegalArgumentException("Player name cannot be empty.");
            }
            Player player = playerList.findPlayer(playerName);
            if (player == null) {
                throw new IllegalArgumentException("Player not found.");
            }
            String winRate = gameHistory.getPlayerWinRate(playerName);
            outputArea.setText("Player " + playerName + "'s win rate: " + winRate);
        } catch (IllegalArgumentException e) {
            outputArea.setText(e.getMessage());
        } catch (Exception e) {
            outputArea.setText("An unexpected error occurred: " + e.getMessage());
        }
    }

    /*
     * EFFECTS: prints the rating history of player with given name if such player
     * exists
     */
    private void viewPerformanceCurve() {
        try {
            String playerName = JOptionPane.showInputDialog(this, "Enter player name:");
            if (playerName.isEmpty()) {
                throw new IllegalArgumentException("Player name cannot be empty.");
            }
            Player player = playerList.findPlayer(playerName);
            if (player == null) {
                throw new IllegalArgumentException("Player not found.");
            }
            ArrayList<Integer> ratings = gameHistory.getRatingHistory(playerName);
            if (ratings.isEmpty()) {
                throw new IllegalArgumentException("No rating history available for this player.");
            }
            PerformanceCurve performanceCurve = new PerformanceCurve(ratings);
            JFrame frame = new JFrame("Performance Curve for " + playerName);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(performanceCurve);
            frame.setSize(800, 600);
            frame.setVisible(true);
        } catch (IllegalArgumentException e) {
            outputArea.setText(e.getMessage());
        }
    }

    /*
     * EFFECTS: records a new game with players, date, results, moves, and
     * annotations
     */
    private void recordNewGame() {
        Game newGame = initializeNewGame();
        GameBoardUI gameBoardUI = new GameBoardUI(GameHistory.getInstance(), newGame);
        JFrame frame = new JFrame("New Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(gameBoardUI);
        frame.pack();
        frame.setVisible(true);
    }

    /*
     * EFFECTS: makes a new game with specified players, date and result
     */
    private Game initializeNewGame() {
        try {
            String date = getDate("Enter game date (yyyy-mm-dd):");
            Player blackPlayer = getPlayer("Enter black player name:");
            Player whitePlayer = getPlayer("Enter white player name:");
            String winner = getWinner("Enter winner (b for black, w for white, d for draw):");

            Game newGame = new Game(date, blackPlayer, whitePlayer, winner);
            return newGame;
        } catch (IllegalArgumentException e) {
            outputArea.setText(e.getMessage());
        }
        return null;
    }

    /*
     * EFFECTS: handles user input of date
     */
    private String getDate(String message) {
        String date = JOptionPane.showInputDialog(this, message);
        if (date.isEmpty()) {
            throw new IllegalArgumentException("Date cannot be empty.");
        }
        if (!date.matches(Game.REGEX_DATE)) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-mm-dd.");
        }
        return date;
    }

    /*
     * EFFECTS: handles user input of player
     */
    private Player getPlayer(String message) {
        String playerName = JOptionPane.showInputDialog(this, message);
        Player player = playerList.findPlayer(playerName);
        if (player == null) {
            throw new IllegalArgumentException(playerName + " does not exist.");
        }
        return player;
    }

    /*
     * EFFECTS: handles user input of winner
     */
    private String getWinner(String message) {
        String winner = JOptionPane.showInputDialog(this, message);
        if (!winner.equals("b") && !winner.equals("w") && !winner.equals("d")) {
            throw new IllegalArgumentException("Invalid winner. Use 'b', 'w', or 'd'.");
        }
        return winner;
    }

    /*
     * EFFECTS: prints game ID, date, players, and winner of all games
     */
    private void overviewGames() {
        outputArea.setText("Overview of all games:\n");
        for (Game game : gameHistory.getGameHistory()) {
            outputArea.append("Game ID: " + game.getGameId() + " Date: " + game.getDate()
                    + " Black Player: " + game.getBlackPlayer().getName()
                    + " White Player: " + game.getWhitePlayer().getName()
                    + " Winner: " + game.getWinner() + "\n");
        }
    }

    /*
     * EFFECTS: prints date, players, winner, moves, and annotations of the game
     * with given ID
     */
    private void viewGame() {
        String idString = JOptionPane.showInputDialog(this, "Enter Game ID:");
        try {
            int id = Integer.parseInt(idString);
            Game game = gameHistory.getGameById(id);
            if (game != null) {
                outputArea.setText("Date: " + game.getDate()
                        + " Black Player: " + game.getBlackPlayer().getName()
                        + " (" + game.getBlackRating()
                        + ") White Player: " + game.getWhitePlayer().getName()
                        + " (" + game.getWhiteRating()
                        + ") Winner: " + game.getWinner()
                        + "\nMoves: " + game.getMoves()
                        + "\nAnnotation: " + game.getAnnotation());
            } else {
                outputArea.setText("Game not found.");
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid Game ID.");
        }
    }

    /*
     * EFFECTS: deletes the game with given ID
     */
    private void deleteGame() {
        String idString = JOptionPane.showInputDialog(this, "Enter Game ID:");
        try {
            int id = Integer.parseInt(idString);
            gameHistory.deleteGame(id);
            outputArea.setText("Game deleted successfully.");
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid Game ID.");
        }
    }

    /*
     * EFFECTS: filter game history with the given specifications
     */
    private void filterGames() {
        String errorMessage = null;
        String category = JOptionPane.showInputDialog(this, "Enter player/date/result:");
        ArrayList<Game> filteredList = new ArrayList<>();

        if (category.equals("player")) {
            filteredList = filterByPlayer();
        } else if (category.equals("date")) {
            try {
                filteredList = filterByDate();
            } catch (IllegalArgumentException ex) {
                errorMessage = ex.getMessage();
            }
        } else if (category.equals("result")) {
            try {
                filteredList = filterByResult();
            } catch (IllegalArgumentException ex) {
                errorMessage = ex.getMessage();
            }
        } else {
            filteredList = gameHistory.getGameHistory();
        }

        displayFilteringResult(errorMessage, filteredList);
    }

    /*
     * EFFECTS: display filtered games or the error message if an error occurs
     */
    private void displayFilteringResult(String errorMessage, ArrayList<Game> filteredList) {
        if (errorMessage != null) {
            outputArea.setText(errorMessage);
        } else {
            displayFilteredGames(filteredList);
        }
    }

    /*
     * EFFECTS: filter game history with the specified player
     */
    private ArrayList<Game> filterByPlayer() {
        String playerName = JOptionPane.showInputDialog(this, "Enter player name:");
        return gameHistory.filterByPlayer(playerName, gameHistory.getGameHistory());
    }

    /*
     * EFFECTS: filter game history with the specified date
     */
    private ArrayList<Game> filterByDate() {
        String date = JOptionPane.showInputDialog(this, "Enter date in yyyy-mm-dd format:");
        return gameHistory.filterByDate(date, gameHistory.getGameHistory());
    }

    /*
     * EFFECTS: filter game history with the specified result
     */
    private ArrayList<Game> filterByResult() {
        String player = JOptionPane.showInputDialog(this, "Enter player name:");
        String result = JOptionPane.showInputDialog(this, "Enter result (w/l/d):");
        return gameHistory.filterByResult(result, player, gameHistory.getGameHistory());
    }

    /*
     * EFFECTS: prints game ID, date, players, and winner of all filtered games
     */
    private void displayFilteredGames(ArrayList<Game> filteredList) {
        outputArea.setText("Filtered Games:\n");
        for (Game game : filteredList) {
            outputArea.append("Game ID: " + game.getGameId() + " Date: " + game.getDate()
                    + " Black Player: " + game.getBlackPlayer().getName()
                    + " White Player: " + game.getWhitePlayer().getName()
                    + " Winner: " + game.getWinner() + "\n");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: saves the state of the application by writing to the specified json
     * file
     */
    private void saveData() {
        try {
            gameJsonWriter.open();
            gameJsonWriter.writeGameHistory(GameHistory.getInstance());
            gameJsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + GAMEHISTORY_JSON_STORE);
        }
        try {
            playerJsonWriter.open();
            playerJsonWriter.writePlayerList(PlayerList.getInstance());
            playerJsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + PLAYERLIST_JSON_STORE);
        }
        outputArea.setText("Data saved successfully.");
    }

    /*
     * MODIFIES: this
     * EFFECTS: load the state of the application by reading the specified json file
     */
    private void loadData() {
        try {
            gameJsonReader.readGameHistory();
            System.out.println("Loaded game history from " + GAMEHISTORY_JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + GAMEHISTORY_JSON_STORE);
        }
        try {
            playerJsonReader.readPlayerList();
            System.out.println("Loaded player list from " + PLAYERLIST_JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + PLAYERLIST_JSON_STORE);
        }
        outputArea.setText("Data loaded successfully.");
    }
}
