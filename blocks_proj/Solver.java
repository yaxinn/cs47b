import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class Solver {
	/* variables for program */
	private String op = "-ooption";
	private int DEBUGGING = -1;

	/* variables */
	private int tray_length;
	private int tray_width;
	private Content init_config;
	private ArrayList <Block> goal_config;

	private HashSet seen_config;

	public Solver () {
		goal_config = new ArrayList<Block> ();

		/* create a new hashtable */
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

		else return;

		puzzle.readFiles (fn_init, fn_goal);

		System.out.println(" - initial configuration");
		puzzle.init_config.prtContent ();

		System.out.println(" - goal configuration\n");
		for (Block b: puzzle.goal_config)
			System.out.println("\t| " + b + " |");
		System.out.println ();

		/* generate the game tree from the initial Content */
		System.out.println ("Goal Reachable: " + puzzle.tryMove (puzzle.init_config));

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
			// System.out.println (line);
			/* implement to parse the representation of block */
			tok = line.split(" ");
			init_config.addBlock ( new Block (Integer.parseInt(tok[0]), Integer.parseInt(tok[1]), 
					Integer.parseInt(tok[2]), Integer.parseInt(tok[3])) );
		}

		/* configure the goal Content */
		while ( (line = goal_reader.readLine()) != null ) {
			// System.out.println (line);
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
			System.out.println ( "Goal Reachable: True\n_______________");
			// System.out.println (" ** goal reached!! **");
			// s.prtContent ();
			return true;
		}

		if ( seen_config.contains (s)) {
			return false;
		}
		seen_config.add (s);

		int direction;
		HashSet<Position> empty = s.calcEmpty ();

		for (Block b: s.config) {
			for ( direction = 1; direction < 5; direction++) {
				if ( b.movable(empty, direction, tray_length, tray_width) && tryMove (s.genNext (b, direction))) {
					System.out.println ( " Moving Block(" + b + ") to " + (
											direction == 1? "East": direction == 2? "South": 
											direction == 3? "West": direction == 4? "North": "ERROR"));
					return true;
				}
			}
		}
		return false;
	}

	private static void prtOptions () {
		System.out.println ("-ooption: List all available debugging options.");
	}

	// private void debugPrint (String s, int option) {
	// 	if (DEBUGGING) {
	// 		System.out.println(s);
	// 	}
	// }

	class Position {
		public String name;

		public Position (int r, int c) {
			name = r + " " + c;
		}

		@Override
		public boolean equals ( Object o) {
			Position p = (Position) o;
			return name.equals(p.name);
		}

		@Override
		public int hashCode () {
			return name.hashCode();
		}
	}

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

		@Override
		public boolean equals (Object o) {
			Block b = (Block) o;
			return len == b.len && width == b.width && row == b.row && col == b.col;
		}

		@Override
		public int hashCode ( ) {
			return toString().hashCode();
		}

		public void prtBlock () {
			// if (DEBUGGING == 0) 
				System.out.println ("\t" + this);
		}

		public boolean movable ( HashSet<Position> empty, int direction, int tray_length, int tray_width) {
			int r, c;
			switch (direction) {
				/* East */
				case 1: if ( (c = col + width) >=  tray_width )
							return false;
						for (r = row; r < row + len; r++) {
							if (!empty.contains (new Position (r, c)))
								return false;
						}
						break;
				/* South */
				case 2: if ((r = row + len) >= tray_length )
							return false;
						for (c = col; c < col + width; c++) {
							if (!empty.contains (new Position (r, c)))
								return false;
						}
						break;
				/* West */
				case 3: if ((c = col - 1) < 0)
							return false;
						for (r = row; r < row + len; r++) {
							if (!empty.contains (new Position (r, c)))
								return false;
						}
						break;

				/* North */
				case 4: if ((r = row - 1) < 0)
							return false;
						for (c = col; c < col + width; c++) {
							if (!empty.contains (new Position (r, c)))
								return false;
						}
						break;
				default: System.exit (1);
			}
			return true;
		}
	}

	class Content {
		public HashSet<Block> config;
		public HashSet<Position> empty;

		private boolean [] mark;
		private int len;
		private int width;

		public Content ( int l, int w) {
			config = new HashSet<Block> ();
			empty = new HashSet<Position> ();
			mark = new boolean [l * w];
			len = l;
			width = w;
		}

		@Override
		public int hashCode () {
			int code = 0;
			for (Block b: config)
				code += b.toString().hashCode();
			return code;
		}

		@Override
		public String toString () {
			String s = "";

			for (Block b: config) 
				s = s + "\n" + b;

			return s;
		}

		@Override
		public boolean equals (Object o) {
			Content c = (Content) o;
			for (Block b: c.config) {
				if (! config.contains (b))
					return false;
			}
			return true;
		}

		public void addBlock (Block b) {
			config.add ( b );
			// System.out.println ("\t" + b);

			// update empty positions;
			for (int i = b.row; i < b.row + b.len; i++)
				for (int j = b.col; j < b.col + b.width; j++)
					mark[i*width + j] = true;
		}

		public Content genNext (Block mv, int direction) {
			Content c = new Content (len, width);
			Block moved;

			for (Block b: config) {
				if (b.equals (mv)) {
					switch (direction) {
						case 1: moved = new Block (b.len, b.width, b.row, b.col+1);
								c.addBlock (moved);
							break;
						case 2: moved = new Block (b.len, b.width, b.row+1, b.col);
								c.addBlock (moved);
							break;
						case 3: moved = new Block (b.len, b.width, b.row, b.col-1);
								c.addBlock (moved);
							break;
						case 4: moved = new Block (b.len, b.width, b.row-1, b.col);
								c.addBlock (moved);
							break;
						default: System.out.println ("No SUCH DIRECTION!");
							System.exit (1);
					}
				}
				else c.addBlock (b);
			}
			if (DEBUGGING == 2)
				System.out.println ( " Moving " + mv + " to " + (
									direction == 1? "East": direction == 2? "South": 
									direction == 3? "West": direction == 4? "North": "ERROR"));

			c.calcEmpty();
			return c;
		}

		public HashSet<Position> calcEmpty () {
			for (int i = 0; i < len * width; i++ ) {
				if (mark[i] == false) {
					empty.add (new Position (i / width, i % width));
				}
			}

			// debug
			// for (Position p: empty)
			// 	System.out.println (p);
			return empty;
		}

		public boolean checkGoal (ArrayList<Block> goal) {
			boolean win = true;

			// if (DEBUGGING == 0) 
				// System.out.println ("the following block(s) do not satisfy.");
			for (Block b: goal) {
				if ( !config.contains (b) ) {
					// b.prtBlock ();
					win = false;
				}
			}
			return win;
		}

		public void prtContent () {
			System.out.println ();
			for (Block b: config)
				System.out.println ("\t| " + b + " |");
			System.out.println ();
		}

	}
}