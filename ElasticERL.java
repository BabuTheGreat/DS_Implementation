//-----------------------------------------------------------------
//Assignment 3
//Written by: Belal Abu-Thuraia 40209178 and Arwa Alsibaai 40207467
//-----------------------------------------------------------------

package a3;

public class ElasticERL {
	
	// the attributes of ElasticERL:
	private HashTable <Integer> hm;
	private AVL <Integer> tree;
	private ArrayList list;
	private int size = 0;

	// Method to ensure key is 8 digits
	public static int countDig(int n)  
	{  
		int count = 0;  
		while(n != 0)  
		{  
			// removing the last digit of the number n  
			n = n / 10;  
			// increasing count by 1  
			count = count + 1;  
		}  
		return count;  
	} 
	
	public void SetEINThreshold (int size)
	{
		this.size = size; // set the size variable
		if (size > 500000 || size < 100) // if the size is out of range
		{
			this.size = 0; // reset the size
			throw new RuntimeException("Threshold of ElasticERL out of range");
		}
		else if(size > 9999) // if the size is large
		{
			tree = new AVL<Integer>();
		}
		else if(size > 999) // if the size is medium
		{
			hm = new HashTable<Integer>();
		}
		else // if the size is small
		{
			list = new ArrayList();
		}
	}
	
	public void generate() {
		if(size == 0) // this means the threshold has not been set
		{
			throw new RuntimeException("You must set the size of the threshold");
		}
		else if(size > 9999) // if the size is large
		{
			tree.generate();
		}
		else if(size > 999) // if the size is medium
		{
			hm.generate();
		}
		else // if the size is small
		{
			list.generate();
		}
	}
	
	public void allKeys() {
		if(size == 0) // this means the threshold has not been set
		{
			throw new RuntimeException("You must set the size of the threshold");
		}
		else if(size > 9999) // if the size is large
		{
			tree.inorder();
			System.out.println();
		}
		else if(size > 999) // if the size is medium
		{
			// get the sorted array and iterate through it to print its elements
			int[] keys = hm.keyArray();
			for (int i = 0; i < keys.length; i++) {
				System.out.print(keys[i] + " ");
			}
			System.out.println();
		}
		else // if the size is small
		{
			// get the sorted array and iterate through it to print its elements
			int[] keys = list.keyArray();
			for (int i = 0; i < keys.length; i++) {
				System.out.print(keys[i] + " ");
			}
			System.out.println();
		}
	}
	
	public void add(int key, Object value) {
		if(size == 0) // this means the threshold has not been set
		{
			throw new RuntimeException("You must set the size of the threshold");
		}
		else if(size > 9999) // if the size is large
		{
			if(countDig(key)==8) // check that the key is 8-digits
				tree.insert(key, value);
		}
		else if(size > 999) // if the size is medium
		{
			if(countDig(key)==8) // check that the key is 8-digits
				hm.put(key, value);
		}
		else // if the size is small
		{
			if(countDig(key)==8) // check that the key is 8-digits
				list.add(key, value);
		}
	}
	
	public void remove(int key) {
		if(size == 0) // this means the threshold has not been set
		{
			throw new RuntimeException("You must set the size of the threshold");
		}
		else if(size > 9999) // if the size is large
		{
			tree.remove(key);
		}
		else if(size > 999) // if the size is medium
		{
			hm.remove(key);
		}
		else // if the size is small
		{
			list.remove(key);
		}
	}
	
	public Object getValues(int key) {
		if(size == 0) // this means the threshold has not been set
		{
			throw new RuntimeException("You must set the size of the threshold");
		}
		else if(size > 9999) // if the size is large
		{
			return tree.get(key);
		}
		else if(size > 999) // if the size is medium
		{
			return hm.get(key);
		}
		else // if the size is small
		{
			return list.get(key);
		}
	}
	
	public int nextKey(int key) {
		if(size == 0) // this means the threshold has not been set
		{
			throw new RuntimeException("You must set the size of the threshold");
		}
		else if(size > 9999) // if the size is large
		{
			return tree.getNext(key).getKey();
		}
		else if(size > 999) // if the size is medium
		{
			return hm.getNext(key).getKey();
		}
		else // if the size is small
		{
			return list.getNext(key).getKey();
		}
	}

	public int prevKey(int key) {
		if(size == 0) // this means the threshold has not been set
		{
			throw new RuntimeException("You must set the size of the threshold");
		}
		else if(size > 9999) // if the size is large
		{
			return tree.getPrev(key).getKey();
		}
		else if(size > 999) // if the size is medium
		{
			return hm.getPrev(key).getKey();
		}
		else // if the size is small
		{
			return list.getPrev(key).getKey();
		}
	}
	
	public int rangeKey(int key1, int key2) {
		if(size == 0) // this means the threshold has not been set
		{
			throw new RuntimeException("You must set the size of the threshold");
		}
		else if(size > 9999) // if the size is large
		{
			return tree.rangeKey(key1, key2);
		}
		else if(size > 999) // if the size is medium
		{
			return hm.rangeKey(key1, key2);
		}
		else // if the size is small
		{
			return list.rangeKey(key1, key2);
		}
	}
	
}
