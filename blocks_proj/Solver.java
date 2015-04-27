import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;


public class Solver {
	/* variables for program */
	private String op = "-ooption";
	private int DEBUGGING = 0;

	/* variables */
	private int tray_length;
	private int tray_width;
	private ArrayList <Block> init_config;
	private ArrayList <Block> goal_config;

	public Solver ()


	public static void main (String [] args) throws Exception {
		int argc = args.length;
		Solver puzzle = new Solver ();
		String fn_init = null, fn_goal = null;
		// private Block block();

		if (argc != 2 && argc != 3 ) {
			System.err.println ("Wrong number of arguments.");
			System.exit (1);
		}

		else if (argc == 3) {
			if (args[0].equals (puzzle.op)) {
				prtOptions ();
				return;
			}
			else {
				fn_init = args[1];
				fn_goal = args[2];
				// DEBUGGING = ?;
			}
		}

		else if (argc == 2) {
			fn_init = args[0];
			fn_goal = args[1];
		}

		puzzle.readFiles (fn_init, fn_goal);
		return;
	}

	private void readFiles (String init, String goal) throws Exception {
		BufferedReader init_reader = new BufferedReader (new FileReader (init));
		BufferedReader goal_reader = new BufferedReader (new FileReader (goal));

		String line;
		Scanner sc;

		line = init_reader.readLine();
		sc = new Scanner(line).useDelimiter(" ");
		tray = new Tray (sc.nextInt(), sc.nextInt());
		System.out.println( tray.getLength () + " x " + tray.getWidth());

		System.out.println("initial configuration ");

		while ( (line = init_reader.readLine()) != null ) {
			System.out.println (line);
			/* implement to parse the representation of block */
		}

		System.out.println("goal configuration ");

		while ( (line = goal_reader.readLine()) != null ) {
			System.out.println (line);
			/* implement to parse the representation of block */
		}

		init_reader.close();
		goal_reader.close();

		return;
	}

	private static void prtOptions () {
		System.out.println ("-ooption: List all available debugging options.");
	}

	// private void debugPrint (String s, int option) {
	// 	if (DEBUGGING) {
	// 		System.out.println(s);
	// 	}
	// }
}