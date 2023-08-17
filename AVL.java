//-----------------------------------------------------------------
//Assignment 3
//Written by: Belal Abu-Thuraia 40209178 and Arwa Alsibaai 40207467
//-----------------------------------------------------------------

package a3;

import java.util.Random;

public class AVL<T> {
	
	// AVL tree attributes
	private Node root;
	private Node header = new Node();
	private boolean linked = false;
	
	// Constructor 
	public AVL()
	{
		header.previous = header.next = header;
	}
	
	// Returns the height of specified node
	private int height(Node n)
	{
		if(n == null)
			return 0;
		else
			return n.height;
	}
	
	// Used to update height 
	private int max(int a, int b) {
        return (a > b) ? a : b;
    }
	
	// Perform when tree is left heavy
	private Node rightRotate(Node y)
	{
		// Set node variables
		Node x = y.left; 
		Node T2;
		if(x == null)
			T2 = x;
		else
			T2 = x.right;
		
		// Perform rotations
		if(x == null)
			x = y;
		else
			x.right = y; 
		y.left = T2;
		
		// Update heights
		y.height = max(height(y.left), height(y.right)) + 1;
		x.height = max(height(x.left), height(x.right)) + 1;
		
		// Return new root
		return x;
	}
	
	// Perform when tree is right heavy
	private Node leftRotate(Node x)
	{
		// Set node variables
		Node y = x.right;
		Node T2;
		if(y == null)
			T2 = y;
		else
			T2 = y.left;
		
		// Perform rotations
		if(y == null)
			y = x;
		else
			y.left = x;
		x.right = T2;
		
		// Update heights
		y.height = max(height(y.left), height(y.right)) + 1;
		x.height = max(height(x.left), height(x.right)) + 1;
		
		// Return new root
		return y;
		
	}
	
	// add the entry before, to maintain reverse chronological order
	private void addBefore(Node header, Node current)
	{
		current.next = header;
		current.previous = header.previous;
		current.previous.next = current;
		current.next.previous = current;
	}
	
	// Getting balance factor of node
	private int getBalance(Node n)
	{
		if(n == null)
			return 0;
		else
			return height(n.left) - height(n.right);			
	}
	
	// Insertion override
	public Node insert(int key, Object value)
	{
		// Return actual insertion method
		root = insert(root, key, value);
		return root;
	}
	
	// Insetion 
	private Node insert (Node root, int key, Object value)
	{
		// If tree is empty 
		if(root == null)
		{
			Node n = new Node(key, value);
			addBefore(header, n);
			return n;
		}
		
		// Recursively call left subtree if key is less than root
		if(key < root.key)
			root.left = insert(root.left, key, value);
			
		// Recursively call right subtree if key is greater than root
		else if(key > root.key) 
			root.right = insert(root.right, key, value);

		// No duplicates
		else {
			return root;
		}

		// Update height 
		root.height = max(height(root.left), height (root.right)) + 1;
		
		// Get balance factor and see if tree requires any rotations
		int balance = getBalance(root);
		
		/* Since there are two cases for single rotations and 
		two for double rotations, we must take care of 4 different scenarios.
		 
		 Left left		Right right		Left right		Right left
		 (Single)		(Single)		(Double)		(Double)
		 	x				x				x				x
		   /			   	 \			   /				 \
		  y				  	  y			  y					  y
		 /					   \		   \				 /
		z						z       	z				z
		 */
		
		// Left left
		if (balance > 1 && key < root.left.key)
			return rightRotate(root);
		
		// Right right
		if (balance < -1 && key > root.right.key)
			return leftRotate(root);
		
		// Left right
		if (balance > 1 && key > root.left.key)
		{
			root.left = leftRotate(root.left);
			return rightRotate(root);
		}
		
		// Right left
		if(balance < -1 && key < root.right.key)
		{
			root.right = rightRotate(root.right);
			return leftRotate(root);
		}
		
		return root;	
	}
	
	// Override inorder
	public void inorder()
	{	
		inorder(root);
	}
	
	// Prints out the inorder traversal
	private void inorder(Node root)
	{
		if (root != null)
		{	
			inorder(root.left);
			System.out.print(root + " ");
			inorder(root.right);
		}
	}
	
	// Returns the previous node (chrnologically reversed)
	public Node getPrev(int key) {
		
		// Search for node and return the next data type
		Node current = root;
		while (current != null)
		{
			if (current.key == key)
				break;
			if(key > current.key)
				current = current.right;
			else
				current = current.left;
		}
		
		return current.next;
		
	}
	
	// Returns the next node (chrnologically reversed)
	public Node getNext(int key) {
		// Search for node and return the previous data type
		Node current = root;
		while (current != null)
		{
			if (current.key == key)
				break;
			if(key>current.key)
				current = current.right;
			else
				current = current.left;
		}
		return current.previous;	
	}
	
	// Returns the value of key
	public Object get(int key)
	{
		Object value = null;
		Node current = root;
		
		// Search for node and return value
		while (current != null)
		{
			if (current.key == key)
				break;
			if(key>current.key)
				current = current.right;
			else
				current = current.left;
		}
		
		value = current.value;
		return value;
	}
	
	// Return smallest node in tree
	private Node getMin(Node node)
	{
        Node current = node; 
        
        // loop down to find the leftmost leaf 
        while (current.left != null) 
        	current = current.left; 
  
        return current; 
	}
	
	// Return largest node in tree
	Node getMax(Node node)
	{
		Node current = node;
		while (current.right != null)
			current = current.right;
		return current;
	}
	
	// Deletion Override 
	public Node remove(int key)
	{
		return remove(root, key);
	}
	
	// Deletion 
	private Node remove(Node root, int key)
	{
		// If tree is empty
		if (root == null)
		{
			return root;
		}
		
		// Recursively call left subtree if key is less than root
		else if(key < root.key)
		{
			root.left = remove(root.left, key);
		}
		
		// Recursively call right subtree if key is greater than root
		else if (key > root.key)
		{
			//System.out.println(root.right+ "potato");
			root.right = remove(root.right, key);
			
		}
		
		// Once key is found
		else
		{
			if(!linked) {
			root.previous.next=root.next;
			root.next.previous=root.previous;
			linked = true;
			}
			// If key only has one child or no child
			if((root.left == null) || (root.right == null))
			{
				Node temp = null;
				if(temp == root.left)
					temp = root.right;
				else
					temp = root.left;
				
				// No child case
				if(temp==null)
				{
					temp = root;
					root=null;
				}
				
				// One child
				else {
					root = temp;
				}
			}
			// Two children
			else
			{
				// Get successor of inorder sequence of key
				Node temp = getMin(root.right);
				root.key = temp.key;
				root.right = remove(root.right, temp.key);
			}
		}
		
		// If tree only had one node
		if(root == null)
			return root;
		
		// Update height of node
		root.height = max(height(root.left), height(root.right)) + 1;
		
		// Balance tree
		int balance = getBalance(root);
		
		/* Since there are two cases for single rotations and 
		two for double rotations, we must take care of 4 different scenarios.
		 
		 Left left		Right right		Left right		Right left
		 (Single)		(Single)		(Double)		(Double)
		 	x				x				x				x
		   /			   	 \			   /				 \
		  y				  	  y			  y					  y
		 /					   \		   \				 /
		z						z       	z				z
		 */
		
		// Left left
		if(balance > 1 && getBalance(root.left) >= 0)
			return rightRotate(root);
		
		// Right right
		if(balance < -1 && getBalance(root.right) <= 0)
			return leftRotate(root);
		
		// Left right
		if(balance > 1 && getBalance(root.left) < 0)
		{	
			root.left = leftRotate(root.left); 
			return rightRotate(root);
		}
		
		// Right left
		if(balance < -1 && getBalance (root.right) > 0)
		{
			root.right = rightRotate(root.right); 
			return leftRotate(root);
		}
		return root;
	}
	
	// Preorder Override
	public void preOrder()
	{
		preOrder(root);
	}
	
	// Prints out the preorder traversal of tree
	private void preOrder(Node node) 
	    { 
	        if (node != null) 
	        { 
	            System.out.print(node.key + " "); 
	            preOrder(node.left); 
	            preOrder(node.right); 
	        } 
	    } 
	
	// Return range, chronologically, between two keys
	public int rangeKey(int key1, int key2) {
		
		// If the keys are the same the range is 1
		if(key1==key2)
			return 1;
		Node current = root;
		int count = 1;
		boolean found = false;
		
		// Find node of first key
		while(current != null)
		{
			if (current.key == key1)
				break;
			else if(current.key<key1)
				current = current.right;
			else 
				current = current.left;
		}
		Node current1 = current;
		
		if(current1 == null)
			throw new RuntimeException("Keys do not exist");
		
		// Keep searching node's next value until it equals the second key given from method call
		while (!found)
		{
			// Break if found
			if(current1.key == key2)
			{
				found = true;
				break;
			}
			
			// Assign next node
			else
			{
				current1 = current1.next;
				count++;
			}
			
			//If you reach end of chronological sequence, then reset variables and break
			if(current1.key == 0)
			{
				count = 2;
				break;
			}
		}
		
		// Keep searching node's previous value until it equals the second key given from method call
		while(!found)
		{
			// Break if found
			if (current.previous.key == key2)
			{
				found = true;
				break;
			}
			// Assign next node
			else
			{
				current = current.previous;
				count++;
			}
			// If you reach end of chronological sequence, then throw exception since one of the keys does not exist
			if (current.previous.key == 0)
			{
				throw new RuntimeException("Keys do not exist");
			}
		}
		return count;
	}
	
	// Return boolean if node exist in tree
	private boolean keyExist(Node node, int key)
	{
	    if (node == null)
	        return false;
	 
	    if (node.key == key)
	        return true;
	 
	    // then recur on left subtree /
	    boolean res1 = keyExist(node.left, key);
	   
	    // node found, no need to look further
	    if(res1) return true;
	 
	    // node is not found in left,
	    // so recur on right subtree /
	    boolean res2 = keyExist(node.right, key);
	 
	    return res2;
	}
	
	// Generate random 8 digit values 
	public void generate()
	{
		Random ran = new Random();
		int n;
		while(true) {
			n = 10000000 + ran.nextInt(90000000); // generate a random 8-digit number
			// Only insert if the key is unique
			if(!keyExist(root, n))
			{
				insert(n, null);
				break;
			}
		}
	}
	
}