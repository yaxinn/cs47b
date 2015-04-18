import java.util.*;
import java.lang.Integer;

public class Heap {

	private final boolean DEBUGGING = true;
	
	private int mySize;
	private node root;
	private node pregnant;

	/* constructor: initializes the size to zero */
	public Heap ( ) {
		mySize = 0;
		root = null;
		pregnant = null;

		if (DEBUGGING) {
			display ();
			// check ();
		}
	}

	/* Return true exactly when the heap is empty. */
	public boolean isEmpty () {
		return mySize == 0;
	}

	/* return the top of the heap without removing it */
	public int top () throws NoSuchElementException {
		if ( isEmpty () ) {
			throw new NoSuchElementException ("attempt to access top of empty heap");
		}
		else {
			return root.getValue ();
		}
	}

	/* remove the top of the heap */
	public void remove () throws NoSuchElementException {
		if (isEmpty ()) {
			throw new NoSuchElementException ("attempt to access top of empty heap");
		}

		mySize--;
		bubbleDown (root);
		findPregnant();

		if (DEBUGGING) {
			display ( );
		}
	}

	private void bubbleDown (node root ) {

		if (mySize == 0) {
			root = null;
			return;
		}

		node next = root;
		while (next != null) {

			if (next.getLeft() == null && next.getRight() == null) {
				if (pregnant == null) {
					root = null;
					return;
				}
				else if (next.getParent() == pregnant && pregnant.getRight () == next){
					pregnant.setRight(null);
					return;
				}
				else if (next.getParent() == pregnant && pregnant.getLeft () == next) {
					pregnant.setLeft(pregnant.getRight());
					pregnant.setRight(null); 
					return;
				}
				else if (next.getParent() != pregnant ) {
					if (pregnant.getRight () != null) {
						next.setValue(pregnant.getRight().getValue());
						pregnant.setRight (null);
						return;
					}
					else {
						next.setValue(pregnant.getLeft().getValue());
						pregnant.setLeft (null);
						return;
					}
				}
			}
			else if (next.getRight() == null && next.getLeft() != null) {
				next.setValue (next.getLeft().getValue());
				next.setLeft(null);
				return;
			}
			else if (next.getLeft().getValue () > next.getRight().getValue() ) {
				next.setValue (next.getLeft().getValue ());
				next = next.getLeft();
			}
			else if (next.getLeft().getValue () <= next.getRight().getValue() ) {
				next.setValue (next.getRight().getValue ());
				next = next.getRight();
			}
		}

	}

	/* add a new value to the heap */
	public void add (int newValue ) {

		if (root == null) {
			root = new node (newValue);
			mySize++;
		}
		else {
			node newNode = new node (newValue);

			mySize ++;
			if (pregnant == null ) {
				pregnant = root;
				pregnant.setLeft (newNode);
			}
			else if (pregnant.getRight () == null) {
				pregnant.setRight (newNode);
			}
			else if (pregnant.getRight () != null ) {
				findPregnant ();
				pregnant.setLeft (newNode);
			}
			newNode.setParent (pregnant);
			bubbleUp (newNode);
		}

		if (DEBUGGING) {
			display ( );
		}
	}

	private void bubbleUp (node newNode ) {
		int v = newNode.getValue ();
		node p = newNode.getParent();
		int pv = p.getValue();
		node cur = newNode;

		while (v > pv) {
			cur.setValue(pv);
			cur = p;
			if (p != root) {
				p = p.getParent();
				pv = p.getValue();
			}
			else break;
		}
		cur.setValue (v);
	}

	private void findPregnant ( ) {
		String bin = Integer.toBinaryString( mySize ); 
		int len = bin.length () - 1;
		pregnant = root;

		for (int i=1; i<len; i++) {
			if (bin.charAt (i) == '0') 
				pregnant = pregnant.getLeft ();
			else 
				pregnant = pregnant.getRight ();
		}
	}

	public void display () {	
		System.out.print("\nHeap (" + mySize + " element(s) )");

		if (isEmpty()) {
			System.out.println("\nEmpty Heap");
			return;
		}
		LinkedList<node> queue = new LinkedList<node>();
		queue.add(root);
		int cnt = 1, i, level = 1;
		i = cnt;
		while (!queue.isEmpty()) {
			node current = queue.remove();
			if (i == 0) {
				cnt *= 2;
				i = cnt;
			}
			if (i == cnt) {
				System.out.print("\n level " +level+": " + current.getValue() + " ");
				i--;
				level++;
			}
			else {
				i--;
				System.out.print(" " + current.getValue() + " ");
			}
			if (current.getLeft() != null) {
				queue.add(current.getLeft());
			}
			if (current.getRight() != null)
				queue.add(current.getRight());
		}
		System.out.println ();
	}


	public static void main (String [ ] args) {
		Heap h = new Heap ();

		// /* add and remove 9 elems */
		// for (int i = 1; i < 10; i++) {
		// 	h.add(i);
		// }
		// System.out.print("\nREMOVE");
		// for (int i = 1; i < 10; i++) {
		// 	h.remove ();
		// }
		
		/* add and remove 9 random elements */
		Random r = new Random ( );
		int i;

		/* filled the heap with 8 random elements */
		for (i = 0; i < 6; i++) {
			int newElement = r.nextInt();
			newElement = newElement < 0? (-newElement)%10: newElement%10; 
			h.add (newElement);
		}
		/* add one more to fill the bottom level */
		h.add (14);

		/* add one more to create a new level */
		h.add (9);

		/* remove 2 times */
		h.remove ();
		h.remove ();

		/* add 2 more again */
		h.add (8);
		h.add (100);

		/* remove 2 times */
		h.remove ();
		h.remove ();

		System.out.print("\nREMOVE ALL");
		for ( i = 0; i < 6; i++) {
			System.out.print("\ntop value: " + h.top() + ", after remove");
			h.remove();
		}
	}


/* helper class */
	public class node {
		private int value;
		private node parent;
		private node left;
		private node right;

		public node (int value ) {
			this.value = value;
			this.parent = null;
			this.left = null;
			this.right = null;
		}
		public node (int value, node parent, node left, node right) {
			this.value = value;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}

		public int getValue ( ) {
			return this.value;
		}

		public void setValue (int value ) {
			this.value = value;
		}

		public node getParent ( ) {
			if (this.parent != null)
				return this.parent;
			return null;
		}

		public void setParent ( node parent ) {
			this.parent = parent;
		}

		public node getLeft ( ) {
			return this.left;
		}

		public void setLeft ( node left ) {
			this.left = left;
		}

		public node getRight ( ) {
			return this.right;
		}  

		public void setRight ( node right ) {
			this.right = right;
		}

	}

}
