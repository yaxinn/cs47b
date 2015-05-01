	import java.io.*;

	public class Position {
		private String name;

		public Position (int r, int c) {
			name = r + " " + c;
		}

		@Override
		public boolean equals ( Object o) {
			Position p = (Position) o;
			return name.equals(p.name);
		}

		public String toString () {
			return name;
		}

		@Override
		public int hashCode () {
			return name.hashCode();
		}

		public static void main (String [ ] args) {
			Position p = new Position (1, 1);
			Position p2 = new Position (1, 1);

			System.out.println ( p + " equals " + p2 + ": " + p.equals(p2));
		}
	}