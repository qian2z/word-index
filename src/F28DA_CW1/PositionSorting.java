package F28DA_CW1;

import java.util.Comparator;

/**
 * A helper class that I implemented to sort the position list for each word
 * firstly, sort according to the fileName, secondly, sort according to the
 * lineNumber
 **/

public class PositionSorting implements Comparator<IPosition> {

	@Override
	public int compare(IPosition pos1, IPosition pos2) {

		/**
		 * Mahrsee, R. (2021) Comparator Interface in Java with Examples. Available at:
		 * https://www.geeksforgeeks.org/comparator-interface-java/ (Accessed: 17
		 * February 2022).
		 */
		int fileNameCompare = pos1.getFileName().compareTo(pos2.getFileName());

		int lineCompare = 0;
		int line1 = pos1.getLine();
		int line2 = pos2.getLine();
		if (line1 == line2) {
			lineCompare = 0;
		} else if (line1 > line2) {
			lineCompare = 1;
		} else {
			lineCompare = -1;
		}

		// 2nd level comparison
		return (fileNameCompare == 0) ? lineCompare : fileNameCompare;

	}

}
