//-----------------------------------------------------------------
//Assignment 3
//Written by: Belal Abu-Thuraia 40209178 and Arwa Alsibaai 40207467
//-----------------------------------------------------------------

package a3;

public class Entry {
	
	protected Entry after, before; // keep track of previous and next entries
	protected int key;
	protected Object value;
	protected Entry next; // used for chaining
	
	// Constructor that initializes the key and value of an entry
	public Entry(int key, Object value)
	{
		after = null;
		before = null;
		next = null;
		this.key = key;
		this.value = value;
	}
	
	// this will only be used to initialize the hash array
	// only the next entry and key are initialized
	public Entry()
	{
		next = null;
		key = 0;
	}
	
	// key accessor method
	public int getKey()
	{
		return key;
	}
	
	// value accessor method
	public Object getValue()
	{
		return value;
	}
	
}
