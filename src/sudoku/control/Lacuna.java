package sudoku.control;

import java.util.ArrayList;
import java.util.List;

public class Lacuna {
    private int row, col;
    List<Integer> val;

    public Lacuna(int row, int col) {
        this.row = row;
        this.col = col;
        this.val = new ArrayList<>();
    }
    
    public void addValue(int val) {
       this.val.add(val);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public List<Integer> getVal() {
        return val;
    }

    @Override
    public String toString() {
        String str = "";
        for(Integer i : val)
            str += i + " ";
        
        return str + "row: " + row + " col: " + col;
    }
}