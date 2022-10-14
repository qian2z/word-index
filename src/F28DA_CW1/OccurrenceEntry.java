package F28DA_CW1;

import java.util.LinkedList;

/**
 * A helper class that I implemented to create an entry object that store
 * position list in each file as key and the occurrence as value
 **/

public class OccurrenceEntry implements java.util.Map.Entry<LinkedList<IPosition>, Integer> {

	private LinkedList<IPosition> posList;
	private int occurrence;

	public OccurrenceEntry(LinkedList<IPosition> name, int occur) {
		this.posList = name;
		this.occurrence = occur;
	}

	@Override
	public LinkedList<IPosition> getKey() {
		return posList;
	}

	@Override
	public Integer getValue() {
		return occurrence;
	}

	@Override
	public Integer setValue(Integer value) {
		return occurrence = value;
	}

	public String toString() {
		return posList + " " + occurrence;
	}

}
