import java.util.*;
import java.util.HashSet;

public class Block {

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


		public static void main (String [ ] args) {
			Block b = new Block (1, 1, 0, 0);
			Block c = new Block (1, 1, 0, 0);
			HashSet<Position> empty = new HashSet<Position> ();
			empty.add (new Position (0, 1));

			b.prtBlock ();
			c.prtBlock ();

			System.out.println ("equals: " + b.equals(c));
			System.out.println ("movable East: " + b.movable (empty, 1, 1, 2));
			System.out.println ("movable South: " + b.movable (empty, 2, 1, 2));
			System.out.println ("movable West: " + b.movable (empty, 3, 1, 2));
			System.out.println ("movable North: " + b.movable (empty, 4, 1, 2));
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
				default: System.out.println ("ERROR ");
						 System.exit (1);
			}
			return true;
		}
}