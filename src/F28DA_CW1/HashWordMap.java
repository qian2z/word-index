package F28DA_CW1;

import java.util.Iterator;
import java.util.LinkedList;

public class HashWordMap implements IWordMap, IHashMonitor {

	private TheHashMap map;

	// constructor for the class
	public HashWordMap() {
		map = new TheHashMap();
	}

	// constructor for the class which allows to set the maximum load factor at
	// construction time
	public HashWordMap(float maxLoadFactor) {
		map = new TheHashMap(maxLoadFactor);
	}

	@Override
	// returns the maximum authorized load factor
	public float getMaxLoadFactor() {
		return map.getMaxLoad();
	}

	@Override
	// returns the current load factor
	public float getLoadFactor() {
		return map.getCurrentLoad();
	}

	@Override
	// returns an average number of probes performed by the hash table
	public float averNumProbes() {
		return (float) map.getNumOfProbes() / map.getNumOfOperations();
	}

	@Override
	// return the hash code as an integer of a given string
	public int hashCode(String s) {
		return (int) map.hashSlot(s);
	}

	@Override
	// adds a new position to an entry of the map
	public void addPos(String word, IPosition pos) {
		map.addPos(word, pos);
	}

	@Override
	// removes the entry for the word from the map, throw exception if the word is
	// not present in the map
	public void removeWord(String word) throws WordException {
		if (word.length() > 0 && word.matches(".*[a-zA-Z]+.*")) {
			if (!map.contains(word)) {
				throw new WordException("The word " + word + " is not available in the map.");
			} else {
				map.removeWord(word);
			}
		}
	}

	@Override
	// removes the position from the map for the input word, throw exception if the
	// word is not present in the map or if word is not associated to the given
	// position
	public void removePos(String word, IPosition pos) throws WordException {
		if (word.length() > 0 && word.matches(".*[a-zA-Z]+.*")) {
			if (!map.contains(word)) {
				throw new WordException("The word " + word + " is not available in the map.");
			} else {
				int entries = map.numberOfEntries();
				int index = map.find(word);
				WordEntry we = map.get(index);
				LinkedList<IPosition> posList = we.getValue();
				WordPosition wp = new WordPosition(pos.getFileName(), pos.getLine(), word);
				if (!posList.contains(wp)) {
					throw new WordException("The position " + pos.getFileName() + ", " + pos.getLine() + " for word "
							+ word + " is not available in the map.");
				} else {
					int inx = posList.indexOf(wp);
					posList.remove(inx);
					entries--;
					map.setEntries(entries);
					if (posList.size() == 0) {
						removeWord(word);
					} else {
						we.setValue(posList);
					}
				}
			}
		}
	}

	@Override
	// return an Iterator over all words in the map
	public Iterator<String> words() {
		LinkedList<String> wordList = map.getWordList();
		Iterator<String> ite = wordList.iterator();
		return ite;
	}

	@Override
	// return an Iterator over all positions of word, throw exception if word is not
	// present in the map
	public Iterator<IPosition> positions(String word) throws WordException {
		if (!map.contains(word)) {
			throw new WordException("The word " + word + " is not available in the map.");
		} else {
			int index = map.find(word);
			WordEntry we = map.get(index);
			LinkedList<IPosition> posList = we.getValue();
			posList.sort(new PositionSorting());
			Iterator<IPosition> ite = posList.iterator();
			return ite;
		}
	}

	@Override
	// return the number of entries stored in the map
	public int numberOfEntries() {
		return map.numberOfEntries();
	}

	public void print() {
		map.print();
	}

	public WordEntry[] getMap() {
		return map.getMap();
	}

	public String toString() {
		return "" + map;
	}
	
	public int find(String s) {
		return map.find(s);
	}

}
