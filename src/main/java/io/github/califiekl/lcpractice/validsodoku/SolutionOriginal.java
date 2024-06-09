package io.github.califiekl.lcpractice.validsodoku;

public class SolutionOriginal implements Solution{
    @Override
    public boolean isValidSudoku(char[][] board) {
        int[][] rows=new int[9][9];
        int[][] columns=new int[9][9];
        int[][] squares=new int[9][9];

        for(int i=0;i<9;++i){
            for(int j=0;j<9;++j){
                if(board[i][j]=='.') continue;
                int curr = (int)board[i][j]-48;

                if(rows[i][curr-1]!=0) return false;
                else rows[i][curr-1]=curr;

                if(columns[j][curr-1]!=0) return false;
                else columns[j][curr-1]=curr;

                int squareInd = getSquareInd(i,j);
                if(squares[squareInd][curr-1]!=0) return false;
                else squares[squareInd][curr-1]=curr;
            }
        }
        return true;
    }
    private int getSquareInd(int i, int j){
        return 3*(i/3)+(j/3);
    }
    
}
