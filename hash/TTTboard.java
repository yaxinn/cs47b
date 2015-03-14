import java.io.*;

public class TTTboard {

    private StringBuffer myBoard;

    // Initialize a blank Tic-tac-toe board.
    public TTTboard ( ) {
        myBoard = new StringBuffer ("         ");
    }
    
    // Initialize a Tic-tac-toe board from the given value
    // by interpreting the value in base 3 and representing
    // a 0 as a blank, a 1 as an X, and a 2 as an O.
    public TTTboard (int base3) {
	myBoard = new StringBuffer ("         ");
        for (int k=8; k>=0; k--) {
            int digit = base3 % 3;
            switch (digit) {
            case 0:
                myBoard.setCharAt (k, BLANK); 
                break;
            case 1: 
                myBoard.setCharAt (k, 'X'); 
                break;
            case 2:
                myBoard.setCharAt (k, 'O'); 
                break;
            }
            base3 = base3/3;
        }
    }
    
    final char BLANK = ' ';
    
    // Handle a player's move.
    // If player1isMoving, the move is 'x', otherwise it's 'o'.
    // row and column are 1-based, not zero-based.
    // If the chosen move isn't blank, print an error message
    // and don't do anything.
    public void makeMove (boolean player1isMoving, int row, int col) {
        int index = stringIndex (row, col);
        if (myBoard.charAt(index) == BLANK) {
            System.err.println ("Can't move to " + row + "," + col
                + "; move already made.");
        } else if (player1isMoving) {
            myBoard.setCharAt (index, 'X');
        } else {
            myBoard.setCharAt (index, 'O');
        }
    }
    
    // Determine if the move just made was a winner, that is,
    // created three in a row of whoever moved.
    public boolean wins (int row, int col) {
        switch (row) {
        case 1:
            switch (col) {
            case 1: return allMatch (0, 1, 2) || allMatch (0, 3, 6) || allMatch (0, 4, 8);
            case 2: return allMatch (0, 1, 2) || allMatch (1, 4, 7);
            case 3: return allMatch (0, 1, 2) || allMatch (2, 5, 8) || allMatch (2, 4, 6);
            }
        case 2:
            switch (col) {
            case 1: return allMatch (3, 4, 5) || allMatch (0, 3, 6);
            case 2: return allMatch (3, 4, 5) || allMatch (1, 4, 7)
                || allMatch (0, 4, 8) || allMatch (2, 4, 6);
            case 3: return allMatch (3, 4, 5) || allMatch (2, 5, 8);
            }
        case 3:
            switch (col) {
            case 1: return allMatch (6, 7, 8) || allMatch (0, 3, 6) || allMatch (2, 4, 6);
            case 2: return allMatch (6, 7, 8) || allMatch (1, 4, 7);
            case 3: return allMatch (6, 7, 8) || allMatch (2, 5, 8) || allMatch (0, 4, 8);
            }
        }
		return false;
    }
        
    // Return the translation of people coordinates (row between 1 and 3,
    // col between 1 and 3) to internal board position.
    private static int stringIndex (int row, int col) {
        return (row-1)*3 + (col-1);
    }
    
    // a, b, and c represent the internal coordinates of the elements
    // of a row, column, or diagonal of the board, at least one of which
    // is nonblank.  Return true if they all match.
    private boolean allMatch (int a, int b, int c) {
        return myBoard.charAt (a) == myBoard.charAt (b)
            && myBoard.charAt (b) == myBoard.charAt (c);
    }
    
    public void print ( ) {
        for (int r=1; r<=3; r++) {
            for (int c=1; c<=3; c++) {
                System.out.print (myBoard.charAt (stringIndex(r,c)));
            }
            System.out.println ("");
        }
    }

    /* hashCode function

    /* convert the board to a String and then use the String hashCode function */
    /* about 98 */
    public int hashCode () {
        return myBoard.toString().hashCode();
    }

    /* interpret the Tic-tac-toe board as a nine-digit base 3 numeral and return 
    the corresponding integer as the hash value*/
    /* about 121 */
    // public int hashCode(){
    //     int code = 0;
    //     int base;
    //     for (int i = 0; i < myBoard.length(); i++){
    //         base = (int) Math.pow(3.0, (double)i); // this is the base for each char in the index
    //         if (myBoard.charAt(i)=='X'){
    //             code += base;
    //         }else if(myBoard.charAt(i)=='O'){
    //             code += 2*base;
    //         }
    //     }
    //     return code;
    // }
}
