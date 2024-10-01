# Project Proposal: Go Board and Game Log Application

## Overview
The application proposed is a Go board and a game log where the game information and the player's data will be stored and analyzed. It will record game histories, provide an evaluation of the win rates, and generate an analysis of the player's performance. The application's goal is to become a one-stop solution for Go players to monitor their performance and study game histories. 

## Target Audience
* Go players
* Go clubs and organizations
* Go instructors and coaches

## Personal Interest
This project is of particular interest to me because I am a passionate Go player. In my opinion, Go games recorded and analyzed efficiently can greatly improve the learning process and aid one in search of mistakes. 

## User Stories

* As a user, I want to be able to record Go games.
* As a user, I want to be able to add games to game history.
* As a user, I want to be able to see my win rate.
* As a user, I want to be able to delete recorded games.
* As a user, I want to be able to view my past games.
* As a user, I want to be able to record player statistics (name, rank, etc.).
* As a user, I want to be able to filter the game logs by date, opponent, and result.
* As a user, I want to be able to annotate moves.
* As a user, I want to see my performance curve.

#### Data Persistence

* As a user, I want to have the option to save my current game state and log to a file, so that I can keep my progress.
* As a user, I want to be able to reload my game state and log from a file, so that I can continue my analysis and gameplay from a previous session.

## Phase 3 - Instruction for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by adding players to player list by the "add player" button or adding games to game history by clicking the "record new game" button.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by
  - clicking the "overview games" button to see all games and the detail of a specific game by clicking the "view game" button.
  - clicking the "delete game" button to remove a specific game from the game history.
  - clicking the "filter games" button to see the subset of games in the game history satisfying the specified requirement.
  - clicking the "view player list" button to see all players and the detail of a specific player by clicking the "view player details" button.
  - clicking the "remove player" button to remove a specific player from the player list.
- You can locate my visual component by 
  - clicking the "view player performance curve" button to view a plot of the player's past ratings.
  - clicking the "record new game" button for a graphical user interface for entering moves in the game.
- You can save the state of my application by clicking the "save" button.
- You can reload the state of my application by clicking the "load" button.

## Phase 4: Task 2

Thu Aug 08 15:22:38 PDT 2024
Player a added to player list

Thu Aug 08 15:23:09 PDT 2024
Player b added to player list

Thu Aug 08 15:23:30 PDT 2024
Game #1 added to game history

Thu Aug 08 15:23:49 PDT 2024
Player c added to player list

Thu Aug 08 15:24:03 PDT 2024
Game #2 added to game history

Thu Aug 08 15:24:12 PDT 2024
Game #1 deleted from game history

Thu Aug 08 15:24:22 PDT 2024
Player c with all associated games deleted

Thu Aug 08 15:24:28 PDT 2024
App data saved

## Phase 4: Task 3

Here are some improvements I would've made if I had more time or knew better:
* I would implement the observer pattern for GameBoardUI. As such, I can reduce coupling by removing the game logic related code from the GameBoardUI class, and make a seperate Rule class to handle the game logic.
* I would try to remove the association from Game to Player, as the game only needs to know a small fraction of information of players, ans exposing a lot of extra information can potentially cause problems.
* Instead of representing the board by a 2D array, it would be better represented by a list of cells. As such, operations like highlighting stones can be achieved.

## References
* The persistence-related code references the code from JsonSerializationDemo provided by the CPSC 210 course team.
* The Event class and the Eventlog class are provided by the CPSC210 course team.














