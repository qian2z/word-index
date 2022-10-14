package F28DA_CW1;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class HashWordMapTest {

	// Add your own tests, for example to test the method hashCode from HashWordMap

	private HashWordMap map;

	@Before
	public void setup() {
		map = new HashWordMap(0.5f);
	}

	@Test
	public void addPos1() {
		// add two different words with different positions
		String str1 = "word1";
		String str2 = "word2";
		map.addPos(str1, new WordPosition("test1.txt", 4, str1));
		map.addPos(str2, new WordPosition("test2.txt", 5, str2));
		assertEquals(2, map.numberOfEntries());
		// check the position for word1
		try {
			Iterator<IPosition> possOut1 = map.positions(str1);
			assertTrue(possOut1.hasNext());
			assertEquals(new WordPosition("test1.txt", 4, str1), possOut1.next());
		} catch (WordException e) {
			fail();
		}
		// check the position for word2
		try {
			Iterator<IPosition> possOut2 = map.positions(str2);
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
		map.addPos(str1, new WordPosition("test1.txt", 4, str1));
		map.addPos(str1, new WordPosition("test2.txt", 5, str1));
		assertEquals(2, map.numberOfEntries());
		// check the position for word1
		try {
			Iterator<IPosition> possOut1 = map.positions(str1);
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
		map.addPos(str1, new WordPosition("test1.txt", 5, str1));
		map.addPos(str2, new WordPosition("test1.txt", 5, str2));
		assertEquals(2, map.numberOfEntries());
		// check the position for word1
		try {
			Iterator<IPosition> possOut1 = map.positions(str1);
			assertTrue(possOut1.hasNext());
			assertEquals(new WordPosition("test1.txt", 5, str1), possOut1.next());
		} catch (WordException e) {
			fail();
		}
		// check the position for word2
		try {
			Iterator<IPosition> possOut2 = map.positions(str2);
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
		map.addPos(str1, new WordPosition("test2.txt", 5, str1));
		map.addPos(str1, new WordPosition("test2.txt", 5, str1));
		assertEquals(1, map.numberOfEntries());
		// check the position for word1
		try {
			Iterator<IPosition> possOut1 = map.positions(str1);
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
		map.addPos("", new WordPosition("test2.txt", 5, ""));
		assertEquals(0, map.numberOfEntries());
		map.addPos(" ", new WordPosition("test2.txt", 5, " "));
		assertEquals(0, map.numberOfEntries());
	}

	@Test(expected = WordException.class)
	public void removeWord1() throws WordException {
		// remove a word from an empty map
		map.removeWord("test1");
	}

	@Test(expected = WordException.class)
	public void removeWord2() throws WordException {
		// add two different words with different positions
		map.addPos("word1", new WordPosition("test1.txt", 4, "word1"));
		map.addPos("word2", new WordPosition("test2.txt", 5, "word2"));
		assertEquals(2, map.numberOfEntries());
		// remove the word that not present in the map
		map.removeWord("word3");
	}

	@Test
	public void removeWord3() throws WordException {
		// add two different words with different positions
		map.addPos("word1", new WordPosition("test1.txt", 4, "word1"));
		map.addPos("word2", new WordPosition("test2.txt", 5, "word2"));
		assertEquals(2, map.numberOfEntries());
		map.removeWord("word1");
		assertEquals(1, map.numberOfEntries());
		map.removeWord("word2");
		assertEquals(0, map.numberOfEntries());
	}

	@Test(expected = WordException.class)
	public void removePos1() throws WordException {
		// remove a position from an empty map
		map.removePos("word1", new WordPosition("test1.txt", 4, "word1"));
	}

	@Test(expected = WordException.class)
	public void removePos2() throws WordException {
		// add two different words with different positions
		map.addPos("word1", new WordPosition("test1.txt", 4, "word1"));
		map.addPos("word2", new WordPosition("test2.txt", 5, "word2"));
		assertEquals(2, map.numberOfEntries());
		// remove the word that not present in the map
		map.removePos("word3", new WordPosition("test1.txt", 4, "word1"));
	}

	@Test(expected = WordException.class)
	public void removePos3() throws WordException {
		// add two different words with different positions
		map.addPos("word1", new WordPosition("test1.txt", 4, "word1"));
		map.addPos("word2", new WordPosition("test2.txt", 5, "word2"));
		assertEquals(2, map.numberOfEntries());
		// remove the word with the position that is not associated in the map
		map.removePos("word1", new WordPosition("test1.txt", 5, "word1"));
	}

	@Test
	public void removePos4() throws WordException {
		// add two different words with different positions
		map.addPos("word1", new WordPosition("test1.txt", 4, "word1"));
		map.addPos("word2", new WordPosition("test2.txt", 5, "word2"));
		assertEquals(2, map.numberOfEntries());
		map.removePos("word1", new WordPosition("test1.txt", 4, "word1"));
		assertEquals(1, map.numberOfEntries());
		map.removePos("word2", new WordPosition("test2.txt", 5, "word2"));
		assertEquals(0, map.numberOfEntries());
	}

	@Test
	public void removePos5() throws WordException {
		// add the same word with different positions
		map.addPos("word1", new WordPosition("test1.txt", 4, "word1"));
		map.addPos("word1", new WordPosition("test2.txt", 5, "word1"));
		assertEquals(2, map.numberOfEntries());
		// check the initial total number of position for word1 in the map
		Iterator<IPosition> posList1 = map.positions("word1");
		int count = 0;
		while (posList1.hasNext()) {
			posList1.next();
			count++;
		}
		assertEquals(2, count);
		// remove one of the position
		map.removePos("word1", new WordPosition("test1.txt", 4, "word1"));
		// check the number of position left for word1 in the map
		Iterator<IPosition> posList2 = map.positions("word1");
		assertTrue(posList2.hasNext());
		assertEquals(new WordPosition("test2.txt", 5, "word1"), posList2.next());
		// remove the another position
		map.removePos("word1", new WordPosition("test2.txt", 5, "word1"));
		// after removing the last position for word1, there's no any position for word1
		// in the map, word1 will be removed as well in the map
		assertFalse(posList2.hasNext());
		assertEquals(0, map.numberOfEntries());
	}

	@Test
	public void testHashCode() {
		String str1 = "zoo";
		WordPosition wp1 = new WordPosition("file1", 4, str1);
		int hashCode = map.hashCode(str1);
		map.addPos(str1, wp1);
		WordEntry[] table = map.getMap();
		assertEquals(table[hashCode].getKey(), str1);
	}

	/** Test 1: add an entry and number of entries is 1 */
	@Test
	public void test1() {
		float maxLF = 0.5f;
		HashWordMap h = new HashWordMap(maxLF);
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		h.addPos(word, pos);
		assertTrue(h.numberOfEntries() == 1);
	}

	/** Test 2: add and find an entry */
	@Test
	public void test2() {
		float maxLF = 0.5f;
		HashWordMap h = new HashWordMap(maxLF);
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		h.addPos(word, pos);
		Iterator<IPosition> possOut;
		try {
			possOut = h.positions(word);
			IPosition posOut = possOut.next();
			assertTrue(posOut.getFileName().equals(file) && posOut.getLine() == line);
		} catch (WordException e) {
			fail();
		}
	}

	/** Test 3: look for an inexistent key */
	@Test
	public void test3() {
		float maxLF = 0.5f;
		HashWordMap h = new HashWordMap(maxLF);
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		h.addPos(word, pos);
		try {
			h.positions(word);
		} catch (WordException e) {
			assertTrue(true);
		}
	}

	/** Test 4: try to delete a nonexistent entry. Should throw an exception. */
	@Test
	public void test4() {
		float maxLF = 0.5f;
		HashWordMap h = new HashWordMap(maxLF);
		String word = "abc";
		String file = "f1";
		int line = 2;
		WordPosition pos = new WordPosition(file, line, word);
		h.addPos(word, pos);
		try {
			h.removeWord("other");
			// fail();
		} catch (WordException e) {
			assertTrue(true);
		}
	}

	/** Test 5: delete an actual entry. Should not throw an exception. */
	@Test
	public void test5() {
		try {
			float maxLF = 0.5f;
			HashWordMap h = new HashWordMap(maxLF);
			String word1 = "abc";
			String word2 = "bcd";
			String file = "f1";
			int line = 2;
			WordPosition pos1 = new WordPosition(file, line, word1);
			WordPosition pos2 = new WordPosition(file, line, word2);
			h.addPos(word1, pos1);
			h.addPos(word2, pos2);
			h.removePos(word2, pos2);
			assertTrue(h.numberOfEntries() == 1);
			Iterator<IPosition> possOut = h.positions(word1);
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
		float maxLF = 0.5f;
		HashWordMap h = new HashWordMap(maxLF);
		String word;
		int line;
		String file;
		WordPosition pos;
		for (int k = 0; k < 200; k++) {
			word = "w" + k;
			line = k + 1;
			file = "f" + k;
			pos = new WordPosition(file, line, word);
			h.addPos(word, pos);
		}
		assertEquals(h.numberOfEntries(), 200);
		for (int k = 0; k < 200; ++k) {
			word = "w" + k;
			try {
				Iterator<IPosition> poss = h.positions(word);
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
			float maxLF = 0.5f;
			HashWordMap h = new HashWordMap(maxLF);
			String word;
			int line;
			String file;
			WordPosition pos;
			for (int k = 0; k < 200; ++k) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				h.addPos(word, pos);
			}

			assertEquals(h.numberOfEntries(), 200);
			for (int k = 0; k < 200; ++k) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				h.removePos(word, pos);
			}

			assertEquals(h.numberOfEntries(), 0);
			for (int k = 0; k < 200; ++k) {
				word = "w" + k;
				try {
					h.positions(word);
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
		float maxLF = 0.5f;
		HashWordMap h = new HashWordMap(maxLF);
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
				h.addPos(word, pos);
			}

			for (int k = 10; k < 30; k++) {
				word = "w" + k;
				line = k + 1;
				file = "f" + k;
				pos = new WordPosition(file, line, word);
				h.removePos(word, pos);
			}
		} catch (WordException e) {
			fail();
		}
		Iterator<String> it = h.words();
		int count = 0;

		while (it.hasNext()) {
			count++;
			it.next();
		}
		assertEquals(h.numberOfEntries(), 80);
		assertEquals(count, 80);
	}

	@Test
	public void signatureTest() {
		try {
			IWordMap map = new HashWordMap(0.5f);
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
