import java.io.*;
// import java.util.*;

public class HashTest {

    static BufferedReader reader (String fileName) throws Exception {
        File wordFile;
        FileInputStream wordFileStream;
        InputStreamReader wordsIn; 
        
        wordFile = new File (fileName);
        wordFileStream = new FileInputStream (wordFile);
        wordsIn = new InputStreamReader (wordFileStream);
        return new BufferedReader (wordsIn);
    }

	// Arguments are the file of words and the table size.
    public static void main (String [ ] args) throws Exception {
        if (args.length != 2) {
            System.err.println ("Wrong number of arguments.");
            System.exit (1);
        }
        BufferedReader wordReader;
        int tableSize = Integer.parseInt (args[1]);
        Hashtable table = new Hashtable (tableSize);
        String word;

		// Read all the words into the hash table.
        int wordCount = 0;
        wordReader = reader (args[0]);
        do {
            try {
                word = wordReader.readLine ();
            } catch (Exception e) {
                System.err.println (e.getMessage());
                break;
            }
            if (word == null) {
                break;
            } else {
                wordCount++;
                table.put (word, new Integer(wordCount));
            }
        } while (true);
        
        // Now look up all the words.
        wordReader = reader (args[0]);
        long startTime = System.currentTimeMillis ( );
        do {
            try {
                word = wordReader.readLine ();
            } catch (Exception e) {
                System.err.println (e.getMessage());
                break;
            }
            if (word == null) {
                break;
            } else {
                boolean result = table.containsKey (word);
            }
        } while (true);
        long finishTime = System.currentTimeMillis ( );
        System.out.println ("Time to hash " + wordCount + " words is " 
            + (finishTime-startTime) + " milliseconds.");
        table.printStatistics ( );
    }
}


