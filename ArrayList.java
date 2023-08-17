//-----------------------------------------------------------------
//Assignment 3
//Written by: Belal Abu-Thuraia 40209178 and Arwa Alsibaai 40207467
//-----------------------------------------------------------------

package a3;

import java.util.Random;

public class ArrayList {
	
	// arraylist attributes
	private Entry [] array;
	private int size; // keep track of the number of elements in the arraylist
	private int capacity;
	private Entry header = new Entry();
	
	// Constructor
	public ArrayList()
	{
		capacity = 100; // we start with a capacity of 100 because that is the lowest possible ElasticERL size
		this.array = new Entry [capacity];
		size = 0;
		header.before = header.after = header;
	}
	
	// Increase the capacity of the array if needed
	public void increaseCapacity()
	{
		capacity = array.length * 2; // double the size of the array
		Entry [] new_array = new Entry [capacity]; // create a new array with double the size
		for (int i = 0; i < this.size; i++)
			new_array[i] = array[i]; // move each entry from the old array to the new array
		array = new_array; // assign the new array to the arraylist variable
	}
	
	// Method to find the index of a key
	private int find(int key)
	{
		int scan = 0; // this will be the index of the key
		boolean found = false;
		if(size != 0)
		{
			while(!found && scan < size) // iterate through the array till the key is found
			{
				if(array[scan].getKey() == key)
				{
					found = true;
					return scan;
				}
				scan++;
			}
		}
		return -1; // if the key does not exist
	}
	
	public void add(int key, Object value)
	{
		if(!contains(key)) { // if the key exists
			if(size==capacity) // if we have reached the maximum capacity, increase the capacity of the array
			{
				increaseCapacity();
			}
			Entry n = new Entry (key, value);
			array[size] = n; // insert the new entry at the end
			addBefore(header, array[size]); // update the reverse chronological order
			size++; // increment the number of elements
		}
		
	}
	
	// add the entry before, to maintain reverse chronological order
	private void addBefore(Entry header, Entry current)
	{
		current.after = header;
		current.before = header.before;
		current.before.after = current;
		current.after.before= current;
	}
	
	public void remove(int key)
	{
		int index = find(key); // get the index of the key
		if(index == -1) // if the key does not exist
		{
			throw new RuntimeException("Key not found");
		}
		// fix the chronological order without the removed element
		array[index].after.before = array[index].before;
		array[index].before.after= array[index].after;
		size--; // update the number of elements in the arraylist
		// move each element back one slot from the index to the end of the array
		for (int scan = index; scan<size; scan++)	
			array[scan] = array[scan+1];
		array[size]=null; // the last space is set to null
	}
	
	public Entry getPrev(int key)
	{
		return array[find(key)].after; // get the key of the entry after the entry at the index of the key
	}
	
	public Entry getNext(int key)
	{
		return array[find(key)].before; // get the key of the entry before the entry at the index of the key
	}
	
	public Object get(int key)
	{
		return array[find(key)].getValue(); // get the value of the entry at the index of the key
	}
	
	public boolean contains(int key)
	{
		return (find(key) != -1); // the arraylist contains the key if find key does not return -1
	}
	
	// Method that returns an array of all the keys in chronological order
	private int [] keys()
	{
		int [] arr = new int[size]; // create a new array of size n, where n is the number of elements 
		// add the keys of all the entries in the arraylist into this array
		for (int i = 0; i<size; i++)
			arr[i]=array[i].getKey();
		return arr;
	}

	public void generate()
	{
		Random ran = new Random(); // crate a random object
		int n = 10000000 + ran.nextInt(90000000); // generate an 8-digit number
		if(!contains(n)) // if the new key does not exist, add it to the arraylist
			add(n, null);
		else // otherwise, generate a key again
			generate();
	}
	
	public int rangeKey(int key1, int key2)
	{
		// if the keys are the same key, the range is 1
		if(key1==key2)
			return 1;
		
		int index = find(key1); // get the index of key1
		int copy = index;
		if(!contains(key1) || !contains(key2)) // check that the arraylist contains both keys
			throw new RuntimeException("Keys are invalid");
		
		int [] arr = keys(); // get an array of the keys (they are in chronological order)
		int count = 1; // start with count 1
		boolean found = false;
		// move forward in the array looking for key2
		while (!found)
		{
			if(copy == size-1) // if the index is the last index in the array, break
			{
				break;
			}
			if(arr[copy] == key2) // if we find key2
			{
				found = true;
				break;
			}
			else // increment the count and index till key2 is found or we reach the end of the array
			{
				count++; // increment count to keep track of how many keys we looked at
				copy++;
			}
			if(copy == size-1) // when we reach the last index
			{
				if(arr[copy] == key2) // check whether the last index is key2
				{
					found = true;
					break;
				}
				count=1; // if the last index is not key2, reset the count and leave the loop
				break;
			}
		}
		// if key2 was not found going forward, look for it going backwards
		while (!found)
		{
			if(arr[index] == key2) // if we find key2
			{
				found = true;
				break;
			}
			else // increment count and decrement index till we find key2 or we reach the end of the array
			{
				count++; // increment count to keep track of how many keys we looked at
				index--;
			}
		}
		return count;
	}

	// Method that returns a sorted array of keys
	public int [] keyArray()
	{
		int [] arr = keys(); // get the array of keys
		mergeSort(arr, 0, arr.length - 1); // merge-sort the array of keys
		return arr;
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
