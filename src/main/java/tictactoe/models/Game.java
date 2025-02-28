package tictactoe.models;

import tictactoe.exceptions.DuplicateSymbolException;
import tictactoe.exceptions.MoreThanOneBotException;
import tictactoe.exceptions.PlayersCountMismatchException;
import tictactoe.strategies.winnigstrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private GameState gameState;
    private Player winner;
    private int nextPlayerIndex;

    private List<WinningStrategy> winningStrategies;

    private Game(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.board = new Board(dimension);
        this.players = players;
        this.winningStrategies = winningStrategies;
        this.gameState = GameState.IN_PROGRESS;
        this.nextPlayerIndex = 0;
        this.moves = new ArrayList<>();
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public void printBoard() {
        board.printBoard();
    }

    public void makeMove() {
        Player player = players.get(nextPlayerIndex);
        Cell cell = player.makeMove(board);
        Move move = new Move(cell,player);
        moves.add(move);
        if (checkWinner(board,move)){
            gameState = GameState.SUCCESS;
            winner = player;
            return;
        }
        if (moves.size() == board.getDimension()* board.getDimension()){
            gameState = GameState.DRAW;
            return;
        }
        nextPlayerIndex++;
        nextPlayerIndex = nextPlayerIndex % players.size();
    }

    private boolean checkWinner(Board board, Move move) {
        for (WinningStrategy winningStrategy: winningStrategies){
            if (winningStrategy.checkWinner(board, move)) {
                return true;
            }
        }
        return false;
    }

    public void undo() {
        if (moves.isEmpty()){
            System.out.println("No moves to undo");
            return;
        }

        Move lastMove = moves.get(moves.size()-1);
        moves.remove(lastMove);

        Cell cell = lastMove.getCell();
        cell.setCellState(CellState.EMPTY);
        cell.setPlayer(null);

        for (WinningStrategy winningStrategy: winningStrategies){
            winningStrategy.undo(board,lastMove);
        }

        if (nextPlayerIndex != 0){
            nextPlayerIndex--;
        }
        else {
            nextPlayerIndex = players.size()-1;
        }
    }

    public static class Builder{
        int dimension;
        List<Player> players;
        List<WinningStrategy> winningStrategies;

        private Builder(){
            dimension = 0;
            players = new ArrayList<>();
            winningStrategies = new ArrayList<>();
        }

        public Game build() throws MoreThanOneBotException, DuplicateSymbolException, PlayersCountMismatchException {
            /*
            1. Validate bot count <= 1
            2.ValidateUniqueSymbolForPlayers
            3.ValidateDimensionsAndPlayerCount
             */
            validateBotCount();
            validateUniqueSymbolForPlayers();
            validateDimensionsAndPlayerCount();
            return new Game(dimension,players,winningStrategies);
        }

        private void validateDimensionsAndPlayerCount() throws PlayersCountMismatchException {
            if (players.size() != dimension-1){
                throw new PlayersCountMismatchException();
            }
        }

        private void validateUniqueSymbolForPlayers() throws DuplicateSymbolException {
            Set<Character> symbols = new HashSet<>();
            for (Player player: players){
                if (symbols.contains(player.getSymbol())){
                    throw new DuplicateSymbolException();
                }
                else {
                    symbols.add(player.getSymbol());
                }
            }
        }

        private void validateBotCount() throws MoreThanOneBotException {
            int botCount = 0;
            for (Player player: players){
                if (PlayerType.BOT.equals(player.getPlayerType())){
                    botCount++;
                }
            }
            if (botCount>1) {
                throw new MoreThanOneBotException();
            }
        }

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public GameState getGameStatus() {
        return gameState;
    }

    public void setGameStatus(GameState gameState) {
        this.gameState = gameState;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }
}
