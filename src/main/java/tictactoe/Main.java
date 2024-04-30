package tictactoe;

import tictactoe.controllers.GameController;
import tictactoe.exceptions.DuplicateSymbolException;
import tictactoe.exceptions.MoreThanOneBotException;
import tictactoe.exceptions.PlayersCountMismatchException;
import tictactoe.models.*;
import tictactoe.strategies.winnigstrategies.ColumnWinningStrategy;
import tictactoe.strategies.winnigstrategies.DiagonalWinningStrategy;
import tictactoe.strategies.winnigstrategies.RowWinningStrategy;
import tictactoe.strategies.winnigstrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws DuplicateSymbolException, PlayersCountMismatchException, MoreThanOneBotException {
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);

        int dimension = 3;
        List<Player> players = new ArrayList<>();
        List<WinningStrategy> winningStrategies = new ArrayList<>();

        players.add(new Player('x', "Akhii", 1, PlayerType.HUMAN));
        players.add(new Bot('o', "Abhii", 1, PlayerType.BOT, BotDifficultyLevel.EASY));

        winningStrategies.add(new RowWinningStrategy());
        winningStrategies.add(new ColumnWinningStrategy());
        winningStrategies.add(new DiagonalWinningStrategy());

        Game game = gameController.startGame(dimension, players, winningStrategies);

        while (game.getGameStatus().equals(GameState.IN_PROGRESS)) {
            gameController.printBoard(game);
            System.out.println("Does anyone want to undo? (y/n)");
            String undo = scanner.next();
            if (undo.equalsIgnoreCase("y")){
                gameController.undo(game);
                continue;
            }
            gameController.makeMove(game);
        }

        gameController.printBoard(game);
        if (game.getGameStatus().equals(GameState.SUCCESS)){
            System.out.println(game.getWinner().getName()+", Congrats! you won the game... :)");
        }
        if (game.getGameStatus().equals(GameState.DRAW)){
            System.out.println("Match tied :|");
        }
    }
}
