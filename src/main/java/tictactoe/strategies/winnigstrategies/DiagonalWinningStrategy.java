package tictactoe.strategies.winnigstrategies;

import tictactoe.models.Board;
import tictactoe.models.Move;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements WinningStrategy{
    Map<Character, Integer> leftDiagonalMap = new HashMap<>();
    Map<Character, Integer> rightDiagonalMap = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        char symbol = move.getPlayer().getSymbol();
        if (row == col){
            leftDiagonalMap.put(symbol, leftDiagonalMap.getOrDefault(symbol, 0)+1);
            if (leftDiagonalMap.get(symbol).equals(board.getDimension())){
                return true;
            }
        }
        if (row+col == board.getDimension()-1){
            rightDiagonalMap.put(symbol,rightDiagonalMap.getOrDefault(symbol,0)+1);
            if (rightDiagonalMap.get(symbol).equals(board.getDimension())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void undo(Board board, Move lastMove) {
        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getCol();
        char symbol = lastMove.getPlayer().getSymbol();
        if (row == col){
            leftDiagonalMap.put(symbol, leftDiagonalMap.get(symbol)-1);
        }
        if (row + col == board.getDimension()-1){
            rightDiagonalMap.put(symbol,rightDiagonalMap.get(symbol)-1);
        }
    }


}
