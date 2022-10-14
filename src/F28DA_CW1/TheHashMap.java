package F28DA_CW1;

import java.util.LinkedList;

/**
 * Implementation of hash table in double hashing
 **/

/**
 * GeeksforGeeks (2021) Java Program to Implement Hash Tables with Double
 * Hashing. Available at:
 * https://www.geeksforgeeks.org/java-program-to-implement-hash-tables-with-double-hashing/
 * (Accessed: 26 February 2022).
 */
public class TheHashMap {

	private int HASH_TABLE_SIZE;
	private int size;
	private WordEntry[] table;
	private int primeSize;
	private float maxLoad;
	private int numOfProbes;
	private int numOfOperations;
	private int entries;

	public TheHashMap() {
		// initialize variables
		size = 0;
		entries = 0;
		// default initial size of hash table = 13
		HASH_TABLE_SIZE = 13;
		table = new WordEntry[HASH_TABLE_SIZE];
		for (int i = 0; i < HASH_TABLE_SIZE; i++) {
			table[i] = null;
		}
		primeSize = getPrime();
		// default maximum load factor = 0.5
		maxLoad = 0.5f;
	}

	public TheHashMap(int tableSize) {
		// initialize variables
		size = 0;
		entries = 0;
		HASH_TABLE_SIZE = tableSize;
		table = new WordEntry[HASH_TABLE_SIZE];

		for (int i = 0; i < HASH_TABLE_SIZE; i++) {
			table[i] = null;
		}
		primeSize = getPrime();
		// default maximum load factor = 0.5
		maxLoad = 0.5f;
	}

	public TheHashMap(float maxLF) {
		// initialize variables
		size = 0;
		entries = 0;
		// default initial size of hash table = 13;
		HASH_TABLE_SIZE = 13;
		table = new WordEntry[HASH_TABLE_SIZE];
		for (int i = 0; i < HASH_TABLE_SIZE; i++) {
			table[i] = null;
		}
		primeSize = getPrime();
		maxLoad = maxLF;
	}

	// get the prime number which less than the current size of hash table
	// for the use of hash functions
	public int getPrime() {
		for (int i = HASH_TABLE_SIZE - 1; i >= 1; i--) {
			int count = 0;
			for (int j = 2; j * j <= i; j++) {
				if (i % j == 0) {
					count++;
				}
			}
			if (count == 0) {
				return i;
			}
		}
		return 3;
	}

	// check whether the number is a prime number
	private boolean isPrime(int n) {
		int temp;
		if (n == 1 || n == 0) {
			return false;
		} else {
			for (int i = 2; i <= (n / 2); i++) {
				temp = n % i;
				if (temp == 0) {
					return false;
				}
			}
			return true;
		}
	}

	public float getMaxLoad() {
		return maxLoad;
	}

	public float getCurrentLoad() {
		return (float) size / (float) HASH_TABLE_SIZE;
	}

	public int getSize() {
		return size;
	}

	public int getHashTableSize() {
		return HASH_TABLE_SIZE;
	}

	public int numberOfEntries() {
		return entries;
	}

	public void setEntries(int ent) {
		this.entries = ent;
	}

	public int getNumOfProbes() {
		return numOfProbes;
	}

	public int getNumOfOperations() {
		return numOfOperations;
	}

	public WordEntry[] getMap() {
		return table;
	}

	// check whether the input word is already present in the map
	public boolean contains(String word) {
		numOfOperations++;
		for (int i = 0; i < HASH_TABLE_SIZE; i++) {
			if (table[i] != null) {
				String compare = table[i].getKey();
				if (compare.equalsIgnoreCase(word)) {
					return true;
				}
			}
		}
		return false;
	}

	// get the list that only contains each distinct word in the map
	public LinkedList<String> getWordList() {
		LinkedList<String> wordList = new LinkedList<String>();
		for (int i = 0; i < HASH_TABLE_SIZE; i++) {
			if (table[i] != null) {
				wordList.add(table[i].getKey());
			}
		}
		return wordList;
	}

	// get the index of the indexed word in the map
	public int find(String word) {
		numOfOperations++;
		int hash1 = hashFunc1(word);
		int hash2 = hashFunc2(word);
		int index = -1;
		boolean isTrue = false;
		while (!isTrue) {
			if (table[hash1] != null) {
				String compare = table[hash1].getKey();
				if (compare.equalsIgnoreCase(word)) {
					index = hash1;
					isTrue = true;
				}
			}
			hash1 += hash2;
			hash1 %= HASH_TABLE_SIZE;
			numOfProbes++;
		}
		return index;
	}

	// get the WordEntry with the input index in the map
	public WordEntry get(int index) {
		return table[index];
	}

	// adds a new position to an entry of the map, creates the entry if the input
	// word is not already present in the map
	public void addPos(String word, IPosition pos) {
		if (word.length() > 0 && word.matches(".*[a-zA-Z]+.*")) {
			numOfOperations++;
			
			if (contains(word)) {
				int index = find(word);
				WordEntry we = table[index];
				LinkedList<IPosition> posList = we.getValue();
				if (!posList.contains(pos)) {
					posList.add(pos);
					table[index] = new WordEntry(word, posList);
					entries++;
				}
			} else {
				int hashSlot = hashSlot(word);
				WordEntry we = new WordEntry(word);
				LinkedList<IPosition> posList = we.getValue();
				posList.add(pos);
				table[hashSlot] = new WordEntry(word, posList);
				size++;
				entries++;
			}
		}

		if (getCurrentLoad() >= maxLoad) {
			resize();
		}
	}

	// reinsert all the entries in the previous map into the new map (size increased
	// map)
	private void reinsert(String word, LinkedList<IPosition> posList) {
		int hash1 = hashFunc1(word);
		int hash2 = hashFunc2(word);
		while (table[hash1] != null) {
			numOfProbes++;
			hash1 += hash2;
			hash1 %= HASH_TABLE_SIZE;
		}
		table[hash1] = new WordEntry(word, posList);
		size++;
	}

	/**
	 * Algorithms and Data Structures (no date) Hash table. Dynamic resizing.
	 * Available at:
	 * https://www.algolist.net/Data_structures/Hash_table/Dynamic_resizing
	 * (Accessed: 28 February 2022).
	 */
	// resize the current map when the current load factor is larger than the
	// maximum allowed load factor
	private void resize() {
		int tableSize = 2 * table.length;
		while (!isPrime(tableSize)) {
			tableSize++;
		}
		HASH_TABLE_SIZE = tableSize;
		WordEntry[] oldTable = table;
		table = new WordEntry[tableSize];
		size = 0;
		for (int i = 0; i < oldTable.length; i++) {
			if (oldTable[i] != null) {
				reinsert(oldTable[i].getKey(), oldTable[i].getValue());
			}
		}
	}

	// remove the input word with the positions that indexed to the word from the
	// map
	public void removeWord(String word) {
		numOfOperations++;
		int index = find(word);
		WordEntry we = table[index];
		LinkedList<IPosition> posList = we.getValue();
		int numOfPos = posList.size();
		entries -= numOfPos;
		table[index] = null;
	}

	/**
	 * GeeksforGeeks (2021) String hashing using Polynomial rolling hash function.
	 * Available at:
	 * https://www.geeksforgeeks.org/string-hashing-using-polynomial-rolling-hash-function/
	 * (Accessed: 27 February 2022).
	 */
	// hash code for string input
	private long hashCode(String s) {
		// P and M
		int p = 31;
		int m = (int) (1e9 + 9);
		long power_of_p = 1;
		long hash_val = 0;

		// Loop to calculate the hash value
		// by iterating over the elements of String
		for (int i = 0; i < s.length(); i++) {
			hash_val = (hash_val + (s.charAt(i) - 'a' + 1) * power_of_p) % m;
			power_of_p = (power_of_p * p) % m;
		}
		return hash_val;
	}

	// first hash function for the double hashing map
	private int hashFunc1(String str) {
		int hashValue1 = (int) hashCode(str);
		hashValue1 %= HASH_TABLE_SIZE;
		if (hashValue1 < 0) {
			hashValue1 += HASH_TABLE_SIZE;
		}
		return hashValue1;
	}

	// second hash function for the double hashing map
	private int hashFunc2(String str) {
		int hashValue2 = (int) hashCode(str);
		hashValue2 %= HASH_TABLE_SIZE;
		if (hashValue2 < 0) {
			hashValue2 += HASH_TABLE_SIZE;
		}
		return primeSize - hashValue2 % primeSize;
	}

	// get the index slot that is empty in the map using hash functions
	public int hashSlot(String str) {
		int hash1 = hashFunc1(str);
		int hash2 = hashFunc2(str);
		while (table[hash1] != null) {
			numOfProbes++;
			hash1 += hash2;
			hash1 %= HASH_TABLE_SIZE;
		}
		return hash1;
	}

	// print the map
	public void print() {
		for (int i = 0; i < HASH_TABLE_SIZE; i++) {
			if (table[i] != null) {
				System.out.println(i + ": " + table[i].getKey() + " " + table[i].getValue());
			} else if (table[i] == null) {
				System.out.println(i + ": null");
			}
		}
	}

	public String toString() {
		String print = "";
		for (int i = 0; i < HASH_TABLE_SIZE; i++) {
			if (table[i] != null) {
				print += i + ": " + table[i].getKey() + " " + table[i].getValue() + "\n";
			} else if (table[i] == null) {
				print += i + ": null\n";
			}
		}
		return print;
	}
}
