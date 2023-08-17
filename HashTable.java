//-----------------------------------------------------------------
//Assignment 3
//Written by: Belal Abu-Thuraia 40209178 and Arwa Alsibaai 40207467
//-----------------------------------------------------------------

package a3;

import java.util.Random;

public class HashTable <T>{
	
	// hashtable attributes
	private Entry [] hasharray;
	private int size=1000;
	private Entry header = new Entry();
	private int count;
	
	// Constructor
	public HashTable() 
	{
		// Setting size of array
		hasharray = new Entry[this.size];
		
		header.before = header.after = header;
		// Initialize every element in array 
		for(int i = 0; i<hasharray.length; i++)
			hasharray[i]= new Entry();
	}
	
	// returns hash value
	private int getHash(int key) {
		return Integer.parseInt(Integer.toString(key).substring(0,3));
	}
	
	// add the entry before, to maintain reverse chronological order
	private void addBefore(Entry header, Entry current)
	{
		current.after = header;
		current.before = header.before;
		current.before.after = current;
		current.after.before= current;
	}
	
	public boolean put(int key, Object value)
	{
		int index= getHash(key);
		//Save value of array at hash index
		Entry array_value = hasharray[index];
		//Create new entry
		Entry new_value = new Entry(key, value);
		while(array_value.next != null)
		{
			if (key == array_value.next.getKey())
			return false;
			array_value = array_value.next;
		}
		//Update chain
		new_value.next = array_value.next;
		array_value.next= new_value;
		
		//Link with predecessor
		addBefore(header, new_value);
		count++;
		return true;
	}
	
	public void remove(int key)
	{
		int index= getHash(key);
		//Save value of array at hash index
		Entry array_value = hasharray[index];
		Entry previous = null;
		while(array_value!= null) {
			if(array_value.getKey()==key)
			{
				//Unlink node 
				array_value.after.before= array_value.before;
				array_value.before.after = array_value.after;
				previous.next = array_value.next;
				array_value.next = null;
				//Get entry and set to null 
				array_value.key = 0;
				array_value.value = null;
				break;
			}
			count--;
			
			//Next entry
			previous = array_value;
			array_value= array_value.next;
		}
	}
	
	public Entry getPrev(int key) 
	{
		Entry before = null;
		int index = getHash(key);
		Entry array_value= hasharray[index];
		/*if there are multiple values there, traverse
		through linked list*/
		while(array_value!= null) {
			if(array_value.getKey()==key)
			{
				//Get entry and break out of linked list
				before = array_value.after;
				break;
			}
			
			//Next entry
			array_value= array_value.next;
		}
		return before;
	}
	
	public Entry getNext(int key) 
	{
		Entry after = null;
		int index = getHash(key);
		Entry array_value= hasharray[index];
		/*if there are multiple values there, traverse
		through linked list*/
		while(array_value!= null) {
			
			if(array_value.getKey()==key)
			{
				//Get value and break out of linked list
				after = array_value.before;
				break;
			}
			
			//Next entry
			array_value= array_value.next;
			
		}
		return after;
	}
	
	public Object get(int key) 
	{
		Object value = null;
		int index= getHash(key);
		//Get value from index
		Entry array_value= hasharray[index];
		/*if there are multiple values there, traverse
		through linked list*/
		while(array_value!= null) {
			
			if(array_value.getKey()==key)
			{
				//Get value and break out of linked list
				value = array_value.getValue();
				break;
			}
			
			//Next entry
			array_value= array_value.next;
			
		}
		return value;
	}
	
	public int rangeKey(int key1, int key2)
	{
		// if they are the same key, the range is 1
		if(key1==key2)
			return 1;
		
		// Search for key1
		int index = getHash(key1);
		Entry current = hasharray[index];
		int count = 1;
		boolean found = false;
		while (current != null)
		{
			if(current.getKey() == key1)
				break;
			current = current.next;
		}
		
		Entry current1 = current; // this is the entry of key1
		if(current1 == null) // if key1 is not found, current1 will be null
			throw new RuntimeException("Keys do not exist");
		
		// Search for key2 going forward
		while(!found)
		{
			// when current1 holds key2, it means it was found
			if(current1.getKey()==key2)
			{
				found = true;
				break;
			}
			// until key2 is found, keep going to the next entry
			else
			{
				current1 = current1.after;
				count++; // increment count to keep track of how many keys we looked at
			}
			
			if(current1.getKey() == 0) // this means we reached the last entry so key2 is not after key1
			{
				count = 2; // reset count
				break;
			}
		}
		
		// if still not found, search for key2 going backwards
		while(!found)
		{
			// when before current holds key2, it means it was found
			if(current.before.getKey()== key2)
			{
				found = true;
				break;
			}
			// until key2 is found, keep going to the previous entry
			else
			{
				current = current.before;
				count++; // increment count to keep track of how many keys we looked at
			}
			
			// if key2 is not after nor before key1, it does not exist
			if(current.before.getKey() == 0) // this means we reached the first entry so key2 is not before key1
			{
				throw new RuntimeException("Keys do not exist");
			}
		}
		return count;
	}
	
	public void generate()
	{
		Random ran = new Random(); // create a random object
		// generate a random 8-digit number
		int n = 10000000 + ran.nextInt(90000000);
		
		boolean x = put(n, null); // try to insert the key into the hash table
		if (x==false) // if the key already exists, generate a new key
			generate();
	}
	
	public int[] keyArray() 
	{
		// create a new array of size n, where n is the number of keys
		int [] key_array = new int[count];
		int index = 0;
		// iterate through the entire hasharray
		for(int i = 0; i < 1000; i++) {
			// for each value at an index, check the entire chain and add the keys into the array
			Entry array_value = hasharray[i];
			while (array_value!=null) {
				if (array_value.getKey() == 0) { // this means there is no key at this index
					array_value= array_value.next;
					continue;
				}
				key_array[index] = array_value.getKey();
				index++;
				// go to the next key in the chain
				array_value = array_value.next;
			}
		}
		// merge-sort the array of keys before returning it
		mergeSort(key_array, 0, key_array.length - 1);
		return key_array;
	}
	
	private void mergeSort(int[] array, int left, int right) 
	{
		if (left < right) { // if the array size is more than 1

			// divide the array into two subarrays
			int half = (left + right) / 2;
			mergeSort(array, left, half);
			mergeSort(array, half + 1, right);

			// merge the sorted subarrays
			merge(array, left, half, right);
		}
	}
	  
	private void merge(int array[], int p, int q, int r) 
	{
		int n1 = q - p + 1; // the size of first half
		int n2 = r - q; // the size of the second half
		
		// fill the two subarrays
	 	int first[] = new int[n1];
		int second[] = new int[n2];
		for (int i = 0; i < n1; i++)
			first[i] = array[p + i];
		for (int j = 0; j < n2; j++)
			second[j] = array[q + 1 + j];
		
		// keep track of the indices of the arrays
		int i = 0; // index of the first subarray
		int j = 0; // index of the second subarray
		int k = p; // index of the main array
		
		while (i < n1 && j < n2) { // while both subarrays are not empty
			if (first[i] <= second[j]) {
				array[k] = first[i];
				i++;
			} 
			else {
				array[k] = second[j];
				j++;
			}
			k++;
		}
		
		// if either one of the subarrays is still not empty
		while (i < n1) {
			array[k] = first[i];
			i++;
			k++;
		}
		
		while (j < n2) {
			array[k] = second[j];
			j++;
			k++;
		}
	}
	
}