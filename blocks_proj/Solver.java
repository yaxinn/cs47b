import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class Solver {
	/* variables for program */
	private String op = "-ooption";
	private int DEBUGGING = 0;

	/* variables */
	private int tray_length;
	private int tray_width;
	private state init_config;
	private ArrayList <Block> goal_config;

	public Solver () {
		goal_config = new ArrayList<Block> ();
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
		String tok[];

		line = init_reader.readLine();
		sc = new Scanner(line).useDelimiter(" ");

	// set length and width
		tray_length = sc.nextInt();
		tray_width = sc.nextInt();

		System.out.println( tray_length + " x " + tray_width);
		System.out.println(" initial configuration ");
		
		init_config = new state (tray_length, tray_width);

		while ( (line = init_reader.readLine()) != null ) {
			// System.out.println (line);
			/* implement to parse the representation of block */
			tok = line.split(" ");
			init_config.addBlock ( new Block (Integer.parseInt(tok[0]), Integer.parseInt(tok[1]), 
					Integer.parseInt(tok[2]), Integer.parseInt(tok[3])) );
		}

		System.out.println("goal configuration ");

		while ( (line = goal_reader.readLine()) != null ) {
			// System.out.println (line);
			/* implement to parse the representation of block */
			tok = line.split(" ");
			goal_config.add ( new Block (Integer.parseInt(tok[0]), Integer.parseInt(tok[1]), 
								Integer.parseInt(tok[2]), Integer.parseInt(tok[3])) );
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

	class Block {

		public int len;
		public int width;
		public int row;
		public int col;

		public Block (int l, int w, int r, int c ){
			len = l;
			width = w;
			row = r;
			col = c;
		}

		public String toString () {
			return len + " " + width + " " + row + " " + col;
		}

		public boolean equals (Block b) {
			return len == b.len && width == b.width && row == b.row && col == b.col;
		}
	}

	class state {
		public HashSet<Block> config;
		public ArrayList<Block> empty;
		public int num_empty;

		private boolean [] mark;
		private int len;
		private int width;

		public state ( int l, int w) {
			config = new HashSet<Block> ();
			empty = new ArrayList<Block> ();
			mark = new boolean [l * w];
			len = l;
			width = w;
		}

		public void addBlock (Block b) {
			config.add ( b);
			System.out.println (b);

			// update empty positions;
			for (int i = b.row; i < b.row + b.len; i++)
				for (int j = b.col; j < b.col + b.width; j++)
					mark[i*width + j] = true;
		}

		public ArrayList<Block> getEmptyBlocks () {
			for (int i = 0; i < len * width; i++ ) {
				if (mark[i] == false) 
					empty.add (new Block (1, 1, i / width, i % width));

			}
			return empty;
		}

	}
}