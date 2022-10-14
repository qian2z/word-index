package F28DA_CW1;

import java.util.LinkedList;

/**
 * A helper class that I implemented to create an entry object that store a new
 * word as key and its position list (all positions that the word appear in all
 * the indexed file) as value
 **/

public class WordEntry implements java.util.Map.Entry<String, LinkedList<IPosition>> {

	private String word;
	private LinkedList<IPosition> posList;

	public WordEntry(String key) {
		this.word = key;
		this.posList = new LinkedList<IPosition>();
	}

	public WordEntry(String key, LinkedList<IPosition> value) {
		this.word = key;
		this.posList = value;
	}

//	public WordEntry(String key, WordEntry nextNode) {
//		this.word = key;
//		this.posList = new LinkedList<IPosition>();
//		this.next = nextNode;
//	}
//	
//	public WordEntry(String key, LinkedList<IPosition> value, WordEntry nextNode) {
//		this.word = key;
//		this.posList = value;
//		this.next = nextNode;
//	}

	@Override
	public String getKey() {
		return word;
	}

	@Override
	public LinkedList<IPosition> getValue() {
		return posList;
	}

//	public WordEntry getNext() {
//		return next;
//	}

	@Override
	public LinkedList<IPosition> setValue(LinkedList<IPosition> value) {
		return posList = value;
	}

//	public void setNext(WordEntry nextNode) {
//		this.next = nextNode;
//	}

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof WordEntry) {
			WordEntry we = (WordEntry) o;
			boolean sameKey = this.getKey().equals(we.getKey());
			boolean sameValue = this.getValue() == we.getValue();
//			boolean sameNext = this.getNext().equals(we.getNext());
			return sameKey && sameValue;
		} else {
			return false;
		}
	}

	public String toString() {
		return word + " " + posList;
	}

}
