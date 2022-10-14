package F28DA_CW1;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class ListWordMapTest {

	// Add your own tests

	private ListWordMap l;

	@Before
	public void setup() {
		l = new ListWordMap();
	}

	@Test
	public void addPos1() {
		// add two different words with different positions
		String str1 = "word1";
		String str2 = "word2";
		l.addPos(str1, new WordPosition("test1.txt", 4, str1));
		l.addPos(str2, new WordPosition("test2.txt", 5, str2));
		assertEquals(2, l.numberOfEntries());
		// check the position for word1
		try {
			Iterator<IPosition> possOut1 = l.positions(str1);
			assertTrue(possOut1.hasNext());
			assertEquals(new WordPosition("test1.txt", 4, str1), possOut1.next());
		} catch (WordException e) {
			fail();
		}
		// check the position for word2
		try {
			Iterator<IPosition> possOut2 = l.positions(str2);
			assertTrue(possOut2.hasNext());
			assertEquals(new WordPosition("test2.txt", 5, str2), possOut2.next());
		} catch (WordException e) {
			fail();
		}
	}

	@Test
	public void addPos2() {
		// add the same word with two different positions
		String str1 = "word1";
		l.addPos(str1, new WordPosition("test1.txt", 4, str1));
		l.addPos(str1, new WordPosition("test2.txt", 5, str1));
		assertEquals(2, l.numberOfEntries());
		// check the position for word1
		try {
			Iterator<IPosition> possOut1 = l.positions(str1);
			// first position
			assertTrue(possOut1.hasNext());
			assertEquals(new WordPosition("test1.txt", 4, str1), possOut1.next());
			// second position
			assertTrue(possOut1.hasNext());
			assertEquals(new WordPosition("test2.txt", 5, str1), possOut1.next());
		} catch (WordException e) {
			fail();
		}
	}

	@Test
	public void addPos3() {
		// add the two different words with the same position
		String str1 = "word1";
		String str2 = "word2";
		l.addPos(str1, new WordPosition("test1.txt", 5, str1));
		l.addPos(str2, new WordPosition("test1.txt", 5, str2));
		assertEquals(2, l.numberOfEntries());
		// check the position for word1
		try {
			Iterator<IPosition> possOut1 = l.positions(str1);
			assertTrue(possOut1.hasNext());
			assertEquals(new WordPosition("test1.txt", 5, str1), possOut1.next());
		} catch (WordException e) {
			fail();
		}
		// check the position for word2
		try {
			Iterator<IPosition> possOut2 = l.positions(str2);
			assertTrue(possOut2.hasNext());
			assertEquals(new WordPosition("test1.txt", 5, str2), possOut2.next());
		} catch (WordException e) {
			fail();
		}
	}

	@Test
	public void addPos4() {
		// add the same word with the same position
		String str1 = "word1";
		l.addPos(str1, new WordPosition("test2.txt", 5, str1));
		l.addPos(str1, new WordPosition("test2.txt", 5, str1));
		assertEquals(1, l.numberOfEntries());
		// check the position for word1
		try {
			Iterator<IPosition> possOut1 = l.positions(str1);
			// first position
			assertTrue(possOut1.hasNext());
			assertEquals(new WordPosition("test2.txt", 5, str1), possOut1.next());
			// no second position
			assertFalse(possOut1.hasNext());
		} catch (WordException e) {
			fail();
		}
	}

	@Test
	public void addPos5() {
		// add an empty string
		l.addPos("", new WordPosition("test2.txt", 5, ""));
		assertEquals(0, l.numberOfEntries());
		l.addPos(" ", new WordPosition("test2.txt", 5, " "));
		assertEquals(0, l.numberOfEntries());
	}

	@Test(expected = WordException.class)
	public void removeWord1() throws WordException {
		// remove a word from an empty map
		l.removeWord("test1");
	}

	@Test(expected = WordException.class)
	public void removeWord2() throws WordException {
		// add two different words with different positions
		l.addPos("word1", new WordPosition("test1.txt", 4, "word1"));
		l.addPos("word2", new WordPosition("test2.txt", 5, "word2"));
		assertEquals(2, l.numberOfEntries());
		// remove the word that not present in the map
		l.removeWord("word3");
	}

	@Test
	public void removeWord3() throws WordException {
		// add two different words with different positions
		l.addPos("word1", new WordPosition("test1.txt", 4, "word1"));
		l.addPos("word2", new WordPosition("test2.txt", 5, "word2"));
		assertEquals(2, l.numberOfEntries());
		l.removeWord("word1");
		assertEquals(1, l.numberOfEntries());
		l.removeWord("word2");
		assertEquals(0, l.numberOfEntries());
	}

	@Test(expected = WordException.class)
	public void removePos1() throws WordException {
		// remove a position from an empty map
		l.removePos("word1", new WordPosition("test1.txt", 4, "word1"));
	}

	@Test(expected = WordException.class)
	public void removePos2() throws WordException {
		// add two different words with different positions
		l.addPos("word1", new WordPosition("test1.txt", 4, "word1"));
		l.addPos("word2", new WordPosition("test2.txt", 5, "word2"));
		assertEquals(2, l.numberOfEntries());
		// remove the word that not present in the map
		l.removePos("word3", new WordPosition("test1.txt", 4, "word1"));
	}

	@Test(expected = WordException.class)
	public void removePos3() throws WordException {
		// add two different words with different positions
		l.addPos("word1", new WordPosition("test1.txt", 4, "word1"));
		l.addPos("word2", new WordPosition("test2.txt", 5, "word2"));
		assertEquals(2, l.numberOfEntries());
		// remove the word with the position that is not associated in the map
		l.removePos("word1", new WordPosition("test1.txt", 5, "word1"));
	}

	@Test
	public void removePos4() throws WordException {
		// add two different words with different positions
		l.addPos("word1", new WordPosition("test1.txt", 4, "word1"));
		l.addPos("word2", new WordPosition("test2.txt", 5, "word2"));
		assertEquals(2, l.numberOfEntries());
		l.removePos("word1", new WordPosition("test1.txt", 4, "word1"));
		assertEquals(1, l.numberOfEntries());
		l.removePos("word2", new WordPosition("test2.txt", 5, "word2"));
		assertEquals(0, l.numberOfEntries());
	}

	@Test
	public void removePos5() throws WordException {
		// add the same word with different positions
		l.addPos("word1", new WordPosition("test1.txt", 4, "word1"));
		l.addPos("word1", new WordPosition("test2.txt", 5, "word1"));
		assertEquals(2, l.numberOfEntries());
		// check the initial total number of position for word1 in the map
		Iterator<IPosition> posList1 = l.positions("word1");
		int count = 0;
		while (posList1.hasNext()) {
			posList1.next();
			count++;
		}
		assertEquals(2, count);
		// remove one of the position
		l.removePos("word1", new WordPosition("test1.txt", 4, "word1"));
		// check the number of position left for word1 in the map
		Iterator<IPosition> posList2 = l.positions("word1");
		assertTrue(posList2.hasNext());
		assertEquals(new WordPosition("test2.txt", 5, "word1"), posList2.next());
		// remove the another position
		l.removePos("word1", new WordPosition("test2.txt", 5, "word2"));
		// after removing the last position for word1, there's no any position for word1
		// in the map, word1 will be removed as well in the map
		assertFalse(posList2.hasNext());
		assertEquals(0, l.numberOfEntries());
	}

	/** Test 1: add an entry and number of entries is 1 */
	@Test
	public void test1() {
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		l.addPos(word, pos);
		assertTrue(l.numberOfEntries() == 1);
	}

	/** Test 2: add and find an entry */
	@Test
	public void test2() {
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		l.addPos(word, pos);
		Iterator<IPosition> possOut;
		try {
			possOut = l.positions(word);
			IPosition posOut = possOut.next();
			assertTrue(posOut.getFileName().equals(file) && posOut.getLine() == line);
		} catch (WordException e) {
			fail();
		}
	}

	/** Test 3: look for an inexistent key */
	@Test
	public void test3() {
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		l.addPos(word, pos);
		try {
			l.positions(word);
		} catch (WordException e) {
			assertTrue(true);
		}
	}

	/** Test 4: try to delete a nonexistent entry. Should throw an exception. */
	@Test
	public void test4() {
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		l.addPos(word, pos);
		try {
			l.removeWord("other");
			// fail();
		} catch (WordException e) {
			assertTrue(true);
		}
	}

	/** Test 5: delete an actual entry. Should not throw an exception. */
	@Test
	public void test5() {
		try {
			String word1 = "abc";
			String word2 = "bcd";
			String file = "f1";
			int line = 2;
			WordPosition pos1 = new WordPosition(file, line, word1);
			WordPosition pos2 = new WordPosition(file, line, word2);
			l.addPos(word1, pos1);
			l.addPos(word2, pos2);
			l.removePos(word2, pos2);
			assertTrue(l.numberOfEntries() == 1);
			Iterator<IPosition> possOut = l.positions(word1);
			assertTrue(possOut.hasNext());
			assertEquals(possOut.next(), pos1);
		} catch (WordException e) {
			fail();
		}
	}

	/**
	 * Test 6: insert 200 different values into the Map and check that all these
	 * values are in the Map
	 */
	@Test
	public void test6() {
		String word;
		int line;
		String file;
		WordPosition pos;
		for (int k = 0; k < 200; k++) {
			word = "w" + k;
			line = k + 1;
			file = "f" + k;
			pos = new WordPosition(file, line, word);
			l.addPos(word, pos);
		}
		assertEquals(l.numberOfEntries(), 200);
		for (int k = 0; k < 200; ++k) {
			word = "w" + k;
			try {
				Iterator<IPosition> poss = l.positions(word);
				assertTrue(poss.hasNext());
			} catch (WordException e) {
				fail();
			}

		}
	}

	/** Test 7: Delete a lot of entries from the Map */
	@Test
	public void test7() {
		try {
			String word;
			int line;
			String file;
			WordPosition pos;
			for (int k = 0; k < 200; ++k) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				l.addPos(word, pos);
			}
			assertEquals(l.numberOfEntries(), 200);
			for (int k = 0; k < 200; ++k) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				l.removePos(word, pos);
			}
			assertEquals(l.numberOfEntries(), 0);
			for (int k = 0; k < 200; ++k) {
				word = "w" + k;
				try {
					l.positions(word);
					fail();
				} catch (WordException e) {
				}
			}
		} catch (WordException e) {
			fail();
		}
	}

	/** Test 8: Get iterator over all keys */
	@Test
	public void test8() {
		String word;
		int line;
		String file;
		WordPosition pos;
		try {
			for (int k = 0; k < 100; k++) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				l.addPos(word, pos);
			}

			for (int k = 10; k < 30; k++) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				l.removePos(word, pos);
			}
		} catch (WordException e) {
			fail();
		}
		Iterator<String> it = l.words();
		int count = 0;

		while (it.hasNext()) {
			count++;
			it.next();
		}
		assertEquals(l.numberOfEntries(), 80);
		assertEquals(count, 80);
	}

	@Test
	public void signatureTest() {
		try {
			IWordMap map = new ListWordMap();
			String word1 = "test1";
			String word2 = "test2";
			IPosition pos1 = new WordPosition("test.txt", 4, word1);
			IPosition pos2 = new WordPosition("test.txt", 5, word2);
			map.addPos(word1, pos1);
			map.addPos(word2, pos2);
			map.words();
			map.positions(word1);
			map.numberOfEntries();
			map.removePos(word1, pos1);
			map.removeWord(word2);
		} catch (Exception e) {
			fail("Signature of solution does not conform");
		}
	}

}
