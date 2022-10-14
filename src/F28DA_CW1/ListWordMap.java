package F28DA_CW1;

import java.util.Iterator;
import java.util.LinkedList;

public class ListWordMap implements IWordMap {

	private LinkedList<WordEntry> map;
	private LinkedList<String> wordList;
	private int entries;

	// constructor for the class
	public ListWordMap() {
		map = new LinkedList<WordEntry>();
		wordList = new LinkedList<String>();
	}

	@Override
	// adds a new position to an entry of the map, creates the entry if the input
	// word is not already present in the map
	public void addPos(String word, IPosition pos) {
		/**
		 * Gurung, B. (2013) How to check whether a string contains at least one
		 * alphabet in java? Available at:
		 * https://stackoverflow.com/questions/14278170/how-to-check-whether-a-string-contains-at-least-one-alphabet-in-java
		 * (Accessed: 17 February 2022).
		 */
		if (word.length() > 0 && word.matches(".*[a-zA-Z]+.*")) {
			if (!(contains(word))) {
				wordList.add(word);
				WordEntry wordEntry = new WordEntry(word);
				LinkedList<IPosition> posList = new LinkedList<IPosition>();
				posList.add(new WordPosition(pos.getFileName(), pos.getLine(), word));
				wordEntry.setValue(posList);
				map.add(wordEntry);
				entries++;

			} else {
				int index = wordList.indexOf(word);
				WordEntry wordEntry = map.get(index);
				LinkedList<IPosition> posList = wordEntry.getValue();
				WordPosition wp = new WordPosition(pos.getFileName(), pos.getLine(), word);
				if (!posList.contains(wp)) {
					posList.add(wp);
					wordEntry.setValue(posList);
					entries++;
				}
			}
		}
	}

	@Override
	// removes the entry for the word from the map, throw exception if the word is
	// not present in the map
	public void removeWord(String word) throws WordException {
		if (word.length() > 0 && word.matches(".*[a-zA-Z]+.*")) {
			if (!contains(word)) {
				throw new WordException("The word " + word + " is not available in the map.");
			} else {
				int index = wordList.indexOf(word);
				LinkedList<IPosition> posList = map.get(index).getValue();
				int size = posList.size();
				entries -= size;
				wordList.remove(index);
				map.remove(index);
			}
		}
	}

	@Override
	// removes the position from the map for the input word, throw exception if the
	// word is not present in the map or if word is not associated to the given
	// position
	public void removePos(String word, IPosition pos) throws WordException {
		if (word.length() > 0 && word.matches(".*[a-zA-Z]+.*")) {
			if (!contains(word)) {
				throw new WordException("The word " + word + " is not available in the map.");
			} else {
				int index = wordList.indexOf(word);
				WordEntry wordEntry = map.get(index);
				LinkedList<IPosition> posList = wordEntry.getValue();
				WordPosition wp = new WordPosition(pos.getFileName(), pos.getLine(), word);
				if (!posList.contains(wp)) {
					throw new WordException("The position " + pos.getFileName() + ", " + pos.getLine() + " for word "
							+ word + " is not available in the map.");
				} else {
					int inx = posList.indexOf(wp);
					posList.remove(inx);
					entries--;
					// when there are no any positions for the word, will remove the word from the
					// map as well
					if (posList.size() == 0) {
						removeWord(word);
					} else {
						wordEntry.setValue(posList);
					}
				}
			}
		}
	}

	@Override
	// return an Iterator over all words in the map
	public Iterator<String> words() {
		Iterator<String> ite = wordList.iterator();
		return ite;
	}

	@Override
	// return an Iterator over all positions of word, throw exception if word is not
	// present in the map
	public Iterator<IPosition> positions(String word) throws WordException {
		if (!contains(word)) {
			throw new WordException("The word " + word + " is not available in the map.");
		} else {
			int index = wordList.indexOf(word);
			WordEntry wordEntry = map.get(index);
			LinkedList<IPosition> posList = wordEntry.getValue();
			posList.sort(new PositionSorting());
			Iterator<IPosition> ite = posList.iterator();
			return ite;
		}
	}

	@Override
	// return the number of entries stored in the map
	public int numberOfEntries() {
		return entries;
	}

	// "contains" method that I implement myself with a O(n^2) to check whether the
	// input word is already present in the map
	private boolean contains(String str) {
		for (int i = 0; i < wordList.size(); i++) {
			String compare = wordList.get(i);
			if (compare.equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "" + map;
	}

}
