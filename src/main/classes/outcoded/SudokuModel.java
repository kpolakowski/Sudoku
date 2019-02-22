package outcoded;

import java.util.Random;

public class SudokuModel {
    
    private int[][] board;
    private int level;
    private final Random random;
    
    public SudokuModel(){
        random = new Random();
    }
    
    public void initBoard(){
        this.board = new int[9][9];
        for(int i=0;i<this.board.length;i++)
            for(int j=0;j<this.board.length;j++)
                this.board[i][j] = (i*3 + i/3 + j) % 9 +1;
        shuffleBoard();
    }
    
    
    public void loadCustomBoard(int[][] board){
        this.board = board;
    }
    
    public int[][] getBoard(){
        return this.board;
    }
    public void setBoard(int[][] board){
        this.board = board;
    }
    
    public void setLevel(int level){
        this.level = level;
    }
    public int getLevel(){
        return this.level;
    }
    
    private boolean isCompletedColumnsRows(int cr){
        int sumc = 0, sumr = 0;
        for(int i = 0; i < this.board.length; i++){
            sumc+=this.board[i][cr];
            sumr+=this.board[cr][i];
        }
        return sumc==45 && sumr==45;
    }
    private boolean isCompletedSquare(int square){
        int sum = 0;
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                sum+=this.board[square / 3 * 3 + i][square % 3 *3 + j];
        return sum==45;
    }
    public boolean isSolved(){
        boolean result = true;
        for(int i = 0; i < this.board.length; i++){
            if(!isCompletedColumnsRows(i) | !isCompletedSquare(i)) 
                result = false;
        }
        return result;
    }
    
    private void shuffleBoard(){
        for(int j = 0; j < 2;j++)
            for(int i = 0; i < 2; i++ ){
                int x = random.nextInt(3);
                int y = 0;
                do {
                    y = random.nextInt(3);
                } while(x==y);
                swapColumns(i * 3 + x, i * 3 + y);
                swapRows(i * 3 + x, i * 3 + y);
                swapBigRows(x,y);
                swapBigColumns(x, y);
                swapValues();
                
            }
        hideFields(this.level);
    }
    
    private void swapColumns(int c1, int c2){
        for(int i=0; i<this.board.length; i++){
            int temp = this.board[i][c1];
            this.board[i][c1] = this.board[i][c2];
            this.board[i][c2] = temp;
        }
    }
    private void swapRows(int r1, int r2){
        for(int i=0; i<this.board.length; i++){
            int temp = this.board[r1][i];
            this.board[r1][i] = this.board[r2][i];
            this.board[r2][i] = temp;
        }
    }
    
    private void swapBigColumns(int c1, int c2){
        int x = random.nextInt(3);
        int y = 0;
        do {
            y = random.nextInt(3);
        } while(x==y);
        for(int j = 0; j<3;j++){
            swapColumns(x*3+j, y*3+j);
        }
    }
    
    private void swapBigRows(int r1, int r2){
        int x = random.nextInt(3);
        int y = 0;
        do {
            y = random.nextInt(3);
        } while(x==y);
        for(int j = 0; j<3;j++){
            swapRows(x*3+j, y*3+j);
        }
    }
    
    private void swapValues(){
            int x = random.nextInt(8)+1;
            int y = 0;
            do {
                y = random.nextInt(8)+1;
            } while(x==y);
 
            for(int i = 0; i<this.board.length; i++)
                for(int j = 0; j<this.board.length; j++){
                    if(this.board[i][j]==x)this.board[i][j] = y; else
                    if(this.board[i][j]==y)this.board[i][j] = x;
                }
    }
    
    private void hideFields(int n){
        //for each square remove 6 elements
        for(int i = 0; i < 9; i++)
                for( int k = 0; k < n; k++){
                    int x = 0;
                    int y = 0;
                    do {
                        x = random.nextInt(3) + (i/3) * 3;
                        y = random.nextInt(3) + (i%3) * 3;
                    } while(this.board[x][y]==0);
                    this.board[x][y] = 0;
                }
    }
    
    public boolean isValid(){
        boolean norepeat=true;
        for(int i = 0; i < this.board.length; i++)
            for(int j = 0; j < this.board.length; j++){
                if(this.board[i][j]!=0 && checkSudoku(i, j, this.board[i][j], this.board)>3)
                    norepeat=false;
            }
        return norepeat;
        
    }
    
    public int checkSudoku(int row, int column, int number, int[][] matrix){
        int count = 0;
        
        //check rows and columns
        for(int i = 0; i < matrix.length; i++){
            if(matrix[row][i]==number) count++;
            if(matrix[i][column]==number) count++;
        }
        
        row = row - row%3;
        column = column - column%3;
       
        //check 3x3 squares
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if(matrix[row+i][column+j]==number) count++;
        
        return count;
    }
    

    
}
