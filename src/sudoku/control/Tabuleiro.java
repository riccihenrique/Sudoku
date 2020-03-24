package sudoku.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Tabuleiro {
    private int[][] board;
    private boolean terminou = false;
    
    public Tabuleiro(int[][] mat) {
        
        if(mat != null) 
            board = mat;
        else {
            int random = (int)(Math.random() * 10) % 4;
            switch(random) {
                case 0:
                    board1();
                    break;
                case 1:
                    board2();
                    break;
                case 2:
                    board3();
                    break;
                case 3:
                    board4();
                    break;
            }
        }
    }
    
    public List<Integer> getSubMatriz(int row, int col)
    {
        int index_rowIni, index_rowEnd, index_colIni, index_colEnd;
        boolean flag = true;
        
        if(row == 0) { // 3 primeiras linhas
            index_rowIni = 0;
            index_rowEnd = 3;
        }
        else if(row == 1) { // 3 linhas do meio
            index_rowIni = 3;
            index_rowEnd = 6;
        }
        else { // 3 linhas do ultimo
            index_rowIni = 6;
            index_rowEnd = 9;
        }
        
        if(col == 0) { // 3 primeiras colunas
            index_colIni = 0;
            index_colEnd = 3;
        }
        else if(col == 1) { // 3 colunas do meio
            index_colIni = 3;
            index_colEnd = 6;
        }
        else { // 3 colunas do fim
            index_colIni = 6;
            index_colEnd = 9;
        }
        
        int a, b;
        a=b=0;
        List<Integer> mAux = new ArrayList<>();
        
        for(; index_rowIni < index_rowEnd; index_rowIni++) 
        {
            for(int j = index_colIni; j < index_colEnd; j++) 
            {
                mAux.add(board[index_rowIni][j]);
                b++;
            }    
            b = 0;
            a++;
        }
                
        return mAux;
    }
    
    public boolean resolve() {
        List<Lacuna> l = getValNotExist();
        
        resolve_recursivo(l, 0);        
        return terminou;
    }
    
    public void show() { // Exibe o boarduleiro
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
                switch(j) {
                    case 2:
                    case 5:
                            System.out.print("| ");
                            break;
                }
            }
                
            System.out.println("");
            switch(i) {
                case 2:
                case 5:
                    System.out.println("---------------------");
                    break;
            }
        }
        System.out.println("");
    }
    
    public Lacuna validateTab() {
        for(int i = 0; i < 9; i++) 
            for(int j = 0; j < 9; j++)
                if(board[i][j] != 0 && !(checkRow(i, j, board[i][j]) && checkColumn(i, j, board[i][j]) && checkLocalMat(i, j, board[i][j])))
                    return new Lacuna(i, j);
        
        return null;
    } 
    
    public int[][] getBoard() {
        return this.board;
    }

    public void setBoard(int[][] mat) {
        this.board = mat;
    }
    
    private void resolve_recursivo(List<Lacuna> l, int index) {
        boolean mudou = false;
        if(index < l.size()) {
            for(int i = 0; i < l.get(index).getVal().size(); i++) {
                int val = l.get(index).getVal().get(i);
                int row = l.get(index).getRow();
                int col = l.get(index).getCol();

                if(checkRow(row, col, val) && checkColumn(row, col, val) && checkLocalMat(row, col, val)) {
                    mudou = true;
                    board[row][col] = val;
                    //show();
                    resolve_recursivo(l, index + 1);
                }
                
                if(mudou && !terminou) 
                    board[row][col] = 0;
            }            
        }
        else
            terminou = true;
    }
    
    private void board1() { // Tabuleiro facil
        int[][] mat = { {5, 0, 1, 8, 9, 0, 2, 7, 6},
                        {0, 4, 0, 0, 2, 7, 0, 0, 0},
                        {8, 0, 7, 1, 0, 3, 0, 0, 0},
                        {0, 9, 6, 0, 0, 0, 0, 5, 1},
                        {0, 0, 8, 0, 0, 5, 0, 0, 0},
                        {0, 5, 0, 9, 1, 6, 4, 8, 0},
                        {0, 0, 2, 7, 3, 8, 0, 1, 4},
                        {1, 0, 0, 2, 0, 0, 0 ,6, 7},
                        {0, 0, 0, 0, 5, 0, 0, 2, 0}};
        board = mat;               
    }
    
    private void board2() { //Tabuleiro Medio
        int[][] mat = { {0, 2, 0, 0, 5, 0, 8, 0, 0},
                        {1, 0, 0, 0, 0, 0, 0, 9, 4},
                        {0, 0, 0, 8, 1, 9, 2, 0, 0},
                        {0, 0, 5, 0, 0, 1, 4, 0, 0},
                        {0, 0, 3, 0, 7, 0, 0, 5, 0},
                        {0, 0, 0, 2, 4, 5, 6, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 2, 0},
                        {0, 3, 0, 0, 0, 6, 5, 8, 9},
                        {9, 0, 0, 0, 8, 3, 0, 0, 0}};
        board = mat;               
    }
    
    private void board3() { //Tabuleiro dificil
        int[][] mat = { {2, 0, 6, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 2, 8, 1, 0, 0, 0},
                        {4, 0, 0, 0, 0, 0, 3, 0, 1},
                        {0, 0, 0, 6, 0, 0, 7, 5, 0},
                        {9, 3, 5, 0, 0, 0, 0, 0, 0},
                        {0, 0, 2, 8, 3, 0, 0, 4, 0},
                        {3, 0, 0, 0, 0, 7, 6, 0, 5},
                        {0, 0, 0, 0, 1, 3, 0, 0, 0},
                        {1, 5, 9, 0, 0, 0, 0, 0, 0}};
        board = mat;               
    }
    
    private void board4() { //Tabuleiro muito dificil
        int[][] mat = { {0, 1, 2, 7, 0, 0, 0, 0, 0},
                        {0, 7, 0, 6, 0, 8, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 8, 3, 0},
                        {0, 0, 8, 0, 0, 7, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 3, 5, 0},
                        {0, 0, 6, 0, 0, 4, 0, 0, 0},
                        {2, 4, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 5, 0, 0, 1, 6},
                        {1, 0, 0, 0, 8, 0, 9, 0, 0}};
        board = mat;               
    }
    
    private boolean checkColumn(int row, int col, int val) { //Verrifica se o valor ja nao existe na naquela coluna
        boolean flag = true;
        for(int i = 0; i < 9; i++)
            if(i != row && board[i][col] == val) {                
                flag = false;
                break;
            }
        return flag;
    }
    
    private boolean checkRow(int row, int col, int val) { //Verifica se o valor ja nao existe naquela linha
        boolean flag = true;
        for(int i = 0; i < 9; i++)
            if(i != col && board[row][i] == val) {                
                flag = false;
                break;
            }
        return flag;
    }
    
    private boolean checkLocalMat(int row, int col, int val) { // Verifica qual das matrizes 3x3 o elemento se encontra e se ja nao existe um elemento nela
        int index_rowIni, index_rowEnd, index_colIni, index_colEnd;
        boolean flag = true;
        
        if(row <= 2) { // 3 primeiras linhas
            index_rowIni = 0;
            index_rowEnd = 3;
        }
        else if(row <= 5) { // 3 linhas do meio
            index_rowIni = 3;
            index_rowEnd = 6;
        }
        else { // 3 linhas do ultimo
            index_rowIni = 6;
            index_rowEnd = 9;
        }
        
        if(col <= 2) { // 3 primeiras colunas
            index_colIni = 0;
            index_colEnd = 3;
        }
        else if(col <= 5) { // 3 colunas do meio
            index_colIni = 3;
            index_colEnd = 6;
        }
        else { // 3 colunas do fim
            index_colIni = 6;
            index_colEnd = 9;
        }
        
        
        for(; index_rowIni < index_rowEnd; index_rowIni++) 
            for(int j = index_colIni; j < index_colEnd; j++) 
                if(index_rowIni != row && j != col && board[index_rowIni][j] == val) {
                    flag = false;
                    break;
                }
        
        return flag;
    }
    
    private List<Lacuna> getValNotExist() { // Obtem as lacunas que estao vazias e os possveis valores aceitos por cada uma
        List<Lacuna> l = new ArrayList<>();
        Lacuna lacuna;
        for(int i = 0; i < 9; i++) 
            for(int j = 0; j < 9; j++) // Varre a matriz completa
                if(board[i][j] == 0) { // 0 == vazio
                    lacuna = new Lacuna(i, j);
                    
                    for(int k = 1; k <= 9; k++) // testa todos os possiveis valores para cada lacuna
                        if(checkRow(i, j, k) && checkColumn(i, j, k) && checkLocalMat(i, j, k))                         
                            lacuna.addValue(k);
                    
                    l.add(lacuna);
                }
        
         /* EURISTICA */
        
        Collections.sort(l, new SortLacunas()); // Ordenas os elementos pela quantidade de valores possiveis
        return l;
    }
}

class SortLacunas implements Comparator<Lacuna> { // Comparator para ordenar
    @Override
    public int compare(Lacuna a, Lacuna b) { 
        return a.getVal().size() - b.getVal().size(); 
    } 
} 