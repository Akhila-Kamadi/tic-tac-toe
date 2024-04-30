package tictactoe.strategies.winnigstrategies;

import tictactoe.models.Board;
import tictactoe.models.Move;

import java.util.HashMap;
import java.util.Map;

public class RowWinningStrategy implements WinningStrategy{

    Map<Integer, Map<Character, Integer>> map = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        char symbol = move.getPlayer().getSymbol();
        if (!map.containsKey(row)){
            map.put(row, new HashMap<>());
        }
        Map<Character,Integer> rowMap = map.get(row);
        if (!rowMap.containsKey(symbol)) {
            rowMap.put(symbol,0);
        }
        rowMap.put(symbol,rowMap.get(symbol)+1);
        if (rowMap.get(symbol).equals(board.getDimension())){
            return true;
        }
        return false;
    }

    @Override
    public void undo(Board board, Move lastMove) {
        int row = lastMove.getCell().getRow();
        char symbol = lastMove.getPlayer().getSymbol();
        Map<Character, Integer> rowMap = map.get(row);
        rowMap.put(symbol, rowMap.get(symbol)-1);
    }
}
