package tictactoe.models;

import java.util.Scanner;

public class Player {
    private char symbol;
    private String name;
    private int id;
    private PlayerType playerType;
    private Scanner scanner;

    public Player(char symbol, String name, int id, PlayerType playerType) {
        this.symbol = symbol;
        this.name = name;
        this.id = id;
        this.playerType = playerType;
        scanner = new Scanner(System.in);
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Cell makeMove(Board board) {
        System.out.println(this.name+", your turn now");
        System.out.println("Enter row, col");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        while (!validateMove(row, col, board)) {
            System.out.println(this.name+" ,You made a invalid move. Please try again");
            System.out.println("Enter row, col");
            row = scanner.nextInt();
            col = scanner.nextInt();
        }
        Cell cell = board.getBoard().get(row).get(col);
        cell.setPlayer(this);
        cell.setCellState(CellState.FILLED);
        return cell;
    }

    private boolean validateMove(int row, int col, Board board) {
        if (row>=board.getDimension()|| row<0){
            return false;
        }
        if (col>=board.getDimension() || col<0){
            return false;
        }
        if (!CellState.EMPTY.equals(board.getBoard().get(row).get(col).getCellState())){
            return false;
        }
        return true;
    }
}
