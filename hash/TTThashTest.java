import java.io.*;
// import java.util.*;
import java.util.Hashtable;

public class TTThashTest {

    public static void main (String [ ] args) {

        Hashtable table = new Hashtable<TTTboard, Integer> ();

        long startTime = System.currentTimeMillis ( );
        for (int k=0; k<19683; k++) {
            TTTboard b = new TTTboard (k);
            table.put (b, new Integer (k));
        }
        long finishTime = System.currentTimeMillis ( );
        System.out.println ("Time to insert all Tic-tac-toe boards = "
            + (finishTime-startTime));
    }
}

