import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

public class Solver {
	/* variables for program */
	private String op = "-ooption";
	static int DEBUGGING = 0;

	/* variables */
	private int tray_length;
	private int tray_width;
	private Content init_config;
	private ArrayList <Block> goal_config;

	private HashSet<Content> seen_config;

	public Solver () {
		goal_config = new ArrayList<Block> ();
		seen_config = new HashSet <Content>();
	}


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
			fn_init = args[1];
			fn_goal = args[2];

			if (args[0].equals (puzzle.op)) {
				prtOptions ();
				return;
			}
			else if (args[0].equals ("-o1"))
				DEBUGGING = 1;
			else if (args[0].equals ("-o2")) 
				DEBUGGING = 2;
			else {
				System.out.println ("NO SUCH OPTION! ");
				System.exit (1);
			}
		}

		else if (argc == 2) {
			fn_init = args[0];
			fn_goal = args[1];
		}

		else return;

		puzzle.readFiles (fn_init, fn_goal);

		System.out.println(" - initial configuration");
		puzzle.init_config.prtContent ();

		System.out.println(" - goal configuration\n");
		for (Block b: puzzle.goal_config)
			System.out.println("\t" + b);
		System.out.println ();

		/* generate the game tree from the initial Content */
		System.out.println ("Goal Reached " + puzzle.tryMove (puzzle.init_config));

		return;
	}

	private void readFiles (String init, String goal) throws Exception {
		BufferedReader init_reader = new BufferedReader (new FileReader (init));
		BufferedReader goal_reader = new BufferedReader (new FileReader (goal));

		String line;
		String tok[];

		line = init_reader.readLine();
		tok = line.split(" ");

		/* set length and width */
		tray_length = Integer.parseInt(tok[0]);
		tray_width = Integer.parseInt(tok[1]);

		System.out.println("_______________\nTray Size: " + tray_length + " x " + tray_width);
		
		/* configure the initial Content */
		init_config = new Content (tray_length, tray_width);
		while ( (line = init_reader.readLine()) != null ) {
			/* implement to parse the representation of block */
			tok = line.split(" ");
			init_config.addBlock ( new Block (Integer.parseInt(tok[0]), Integer.parseInt(tok[1]), 
					Integer.parseInt(tok[2]), Integer.parseInt(tok[3])) );
		}

		/* configure the goal Content */
		while ( (line = goal_reader.readLine()) != null ) {
			/* implement to parse the representation of block */
			tok = line.split(" ");
			goal_config.add ( new Block (Integer.parseInt(tok[0]), Integer.parseInt(tok[1]), 
								Integer.parseInt(tok[2]), Integer.parseInt(tok[3])) );
		}

		init_reader.close();
		goal_reader.close();
	}

	public boolean tryMove (Content s) {

		if ( s.checkGoal (goal_config) ) {
			System.out.println ( "Goal Reachable: ture\n_______________");

			/* debug print */
			if (DEBUGGING == 2) {
				System.out.println ("Final configuration");
				s.prtContent ();
			}

			return true;
		}

		if ( seen_config.contains (s)) {
			return false;
		}
		seen_config.add (s);

		int direction;
		HashSet<Position> empty = s.calcEmpty ();

		if (empty.isEmpty()) {
			return false;
		}

		for (Block b: s.config) {
			for ( direction = 1; direction < 5; direction++) {
				Content c;
				if ( b.movable(empty, direction, tray_length, tray_width) && tryMove (c=(s.genNext (b, direction)))) {
					System.out.println ( " - Moved Block(" + b + ") to " + (
											direction == 1? "East": direction == 2? "South": 
											direction == 3? "West": direction == 4? "North": "ERROR"));
					if (DEBUGGING == 1) {
						Iterator i1 = s.config.iterator(), i2 = c.config.iterator();
						while (i1.hasNext ())
							System.out.println ("\t" + i1.next() + "			" + i2.next ());
					}
					return true;
				}
			}
		}
		return false;
	}

	private static void prtOptions () {
		System.out.println ("-ooption: List all available debugging options.");
		System.out.println (" =>  -o1: list all contents after move ");
		System.out.println (" =>  -o2: print the goal content ");
	}
}