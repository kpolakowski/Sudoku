package outcoded;

import java.util.ArrayList;
import java.util.Arrays;

public class SudokuSolver {
    
    private final SudokuModel m;
    private int steps;
    public SudokuSolver(SudokuModel m) {
        this.m = m;
        steps = 0;
    }
    
    
    public boolean Solve(int[][] board){
        this.steps++;
        ArrayList<ArrayList> checking_range = new ArrayList();
        boolean break_condition = false;
        
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board.length; j++)
                if(board[i][j]==0){
                    break_condition=true;
                    ArrayList temp2 = new ArrayList();
                    for(int k = 0; k < 10; k++)
                        if( checkSudoku(i, j, k, board) ) 
                            temp2.add(k);
                    checking_range.add( new ArrayList( Arrays.asList( i*9+j,temp2.size() ) ) );
                }
        
        if(!break_condition){
            this.m.setBoard(board);
            System.out.printf("Sudoku solved in %d steps \n",this.steps);
            this.steps=0;
            return true;
        }
        
        int mrs = (int) checking_range.get(0).get(0);
        int low =(int) checking_range.get(0).get(1);
        
        for(int i = 0; i < checking_range.size(); i++){
            if( (int) checking_range.get(i).get(1)< low){
                low = (int)checking_range.get(i).get(1);
                mrs = (int)checking_range.get(i).get(0);
            } 
        }
        int row = mrs/9;
        int column = mrs%9; 
        
        for(int i = 0; i < 10; i++){
            if(checkSudoku(row, column, i, board)){
                board[row][column]=i;
                if(Solve(board)){
                    return true;
                }  
                board[row][column]=0;
            }
        }
        return false;
    }
    
    public boolean checkSudoku(int row, int column, int number, int[][] matrix){
        boolean check = true;
        
        //check rows and columns
        for(int i = 0; i < matrix.length; i++){
            if(matrix[row][i]==number) check=false;
            if(matrix[i][column]==number) check=false;
        }
        
        row = row - row%3;
        column = column - column%3;
       
        //check 3x3 squares
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if(matrix[row+i][column+j]==number) check=false;
        return check;
    }



}
