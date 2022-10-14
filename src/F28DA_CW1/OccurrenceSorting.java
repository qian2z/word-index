package F28DA_CW1;

import java.util.Comparator;

/**
 * A helper class that I implemented to sort the occurrence of the specific word
 * in each file the most occurrence in a file will be at the end of the linked
 * list will retrieve the top most N file in a descending order later
 **/

public class OccurrenceSorting implements Comparator<OccurrenceEntry> {

	@Override
	public int compare(OccurrenceEntry o1, OccurrenceEntry o2) {
		return o1.getValue().compareTo(o2.getValue());
	}

}
