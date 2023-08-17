//-----------------------------------------------------------------
//Assignment 3
//Written by: Belal Abu-Thuraia 40209178 and Arwa Alsibaai 40207467
//-----------------------------------------------------------------

package a3;

public class Node {
	
	// the attributes of a node of a tree
	protected int key;
	protected Object value;
	protected int height;
	protected Node left;
	protected Node right;
	protected Node next, previous; // to keep track of the chronological order
	
	// Constructor that sets the key and value of a node
	public Node(int key, Object value)
	{
		this.key = key;
		this.value = value;
		height = 1;
	}
	
	public Node() // default constructor 
	{
		this.left = null;
		this.right = null;
	}

	// key accessor method
	public int getKey()
	{
		return key;
	}
	
	// printing the node
	public String toString()
	{
		return ""+ this.key;
	}
	
}
