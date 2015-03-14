import java.util.*;

public class AmoebaFamily {

	private class Amoeba {

		public String myName;			// amoeba’s name
		public Amoeba myParent;			// amoeba’s parent
		public Vector myChildren;		// amoeba’s children
		
		public Amoeba (String name, Amoeba parent) {
			myName = name;
			myParent = parent;
			myChildren = new Vector ( );
		}
		
		public String toString ( ) {
			return myName;
		}
		
		public Amoeba parent ( ) {
			return myParent;
		}

		// Add an amoeba with the given name as this amoeba’s youngest child.		
		public void addChild (String childName) {
			Amoeba child = new Amoeba (childName, this);
			myChildren.addElement (child);
		}
	}	

	private Amoeba myHead = null;
	
	public AmoebaFamily (String name) {
		myHead = new Amoeba (name, null);
	}
	
	// Add a new amoeba named childName as the youngest child
	// of the amoeba named parentName.
	// Precondition: the amoeba family contains an amoeba named parentName.
	public void addChild (String parentName, String childName) {
		// You supply this code for exercise 1.
		_addChild(myHead, parentName, childName);
	}

	private boolean _addChild(Amoeba parent, String parentName, String childName){
		boolean addSuccess = false;
		Iterator iter;

		if (parent != null) {
			if (parent.myName.equals(parentName)){
				parent.addChild(childName);
				addSuccess = true;
			}
			else if (!parent.myChildren.isEmpty()) {
				iter = parent.myChildren.iterator();
				while (iter.hasNext()){
					addSuccess = _addChild((Amoeba)iter.next(), parentName, childName);
				}
			}
		}
		return addSuccess;
	}
	
	// Print the names of all amoebas in the family.
	// Names should appear in preorder, with children's names
	// printed oldest first.
	// Members of the family constructed with the main program above
	// should be printed in the following sequence:
	//	Amos McCoy, mom/dad, me, Mike, Bart, Lisa, Homer, Marge,
	//	Bill, Hilary, Fred, Wilma
	public void print ( ) {
		// You supply this code for exercise 2.
		_print(myHead);
	}

	private void _print(Amoeba parent) {
		Iterator iter;

		if (parent != null){ // check if parent is null to prevent unexpected errors
			System.out.println(parent.myName); // print the name if parent is not null
			if (!parent.myChildren.isEmpty()) {
				iter = parent.myChildren.iterator();
				while (iter.hasNext()){
					_print((Amoeba)iter.next());
				}
			}
		}
	}
	
	public static void main (String [ ] args) {
		AmoebaFamily family = new AmoebaFamily ("Amos McCoy");
		family.addChild ("Amos McCoy", "mom/dad");
		family.addChild ("mom/dad", "me");
		family.addChild ("mom/dad", "Fred");
		family.addChild ("mom/dad", "Wilma");
		family.addChild ("me", "Mike");
		family.addChild ("me", "Homer");
		family.addChild ("me", "Marge");
		family.addChild ("Mike", "Bart");
		family.addChild ("Mike", "Lisa");
		family.addChild ("Marge", "Bill");
		family.addChild ("Marge", "Hilary");
		System.out.println ("Here's the family:");
		family.print ( );
		System.out.println ("");
		System.out.println ("Here it is again!");

		System.out.println ("Preorder:");
		AmoebaEnumerationS enumS = family.new AmoebaEnumerationS ( );
		while (enumS.hasMoreElements ( )) {
			System.out.println (((Amoeba) enumS.nextElement ( )));
		}
		System.out.println ("\nBreadth first:");
		AmoebaEnumerationQ enumQ = family.new AmoebaEnumerationQ ( );
		while (enumQ.hasMoreElements ( )) {
			System.out.println (((Amoeba) enumQ.nextElement ( )));
		}
	}
	
	public class AmoebaEnumerationS implements Enumeration {
		// Amoebas in the family are enumerated in preorder,
		// with children enumerated oldest first.
		// Members of the family constructed with the main program above
		// should be enumerated in the following sequence:
		//	Amos McCoy, mom/dad, me, Mike, Bart, Lisa, Homer, Marge,
		//	Bill, Hilary, Fred, Wilma
		// Complete enumeration of a family of n amoebas should take
		// O(n) operations.
		
		// You supply the details of this class for exercises 3 and 4.
		Stack<Amoeba> stack;

		public AmoebaEnumerationS () {
			stack = new Stack<Amoeba>();
			stack.push(myHead);
		}

		public boolean hasMoreElements() {
			if (stack.empty()) return false;
			else return true;
		}

		public Amoeba nextElement() {
			Amoeba current = stack.pop();

			if (!current.myChildren.isEmpty()) {
				for (int i = current.myChildren.size(); i > 0; i--){
					stack.push((Amoeba)current.myChildren.get(i-1));
				}
			}
			return current;
		}
	}

	public class AmoebaEnumerationQ implements Enumeration {
		// Amoebas in the family are enumerated in preorder,
		// with children enumerated oldest first.
		// Members of the family constructed with the main program above
		// should be enumerated in the following sequence:
		//	Amos McCoy, mom/dad, me, Mike, Bart, Lisa, Homer, Marge,
		//	Bill, Hilary, Fred, Wilma
		// Complete enumeration of a family of n amoebas should take
		// O(n) operations.
		
		// You supply the details of this class for exercises 3 and 4.
		LinkedList<Amoeba> queue;

		public AmoebaEnumerationQ () {
			queue = new LinkedList<Amoeba>();
			queue.add(myHead);
		}

		public boolean hasMoreElements() {
			if (queue.isEmpty()) return false;
			else return true;
		}

		public Amoeba nextElement() {
			Amoeba current = queue.poll();

			if (!current.myChildren.isEmpty()) {
				for (int i = 0; i < current.myChildren.size(); i++){
					queue.add((Amoeba)current.myChildren.get(i));
				}
			}
			return current;
		}
	}

}	
