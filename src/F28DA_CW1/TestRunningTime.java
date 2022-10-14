package F28DA_CW1;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class TestRunningTime {

	static final File textFilesFolder = new File("TextFiles_Shakespeare");
	static final FileFilter commandFileFilter = (File file) -> file.getParent() == null;
	static final FilenameFilter txtFilenameFilter = (File dir, String filename) -> filename.endsWith(".txt");

	public static void main(String[] argv) {

		if (argv.length != 1) {
			System.err.println("Usage: WordIndex commands.txt");
			System.exit(1);
		}

		try {
			File commandFile = new File(argv[0]);
			if (commandFile.getParent() != null) {
				System.err.println("Use a command file in current directory");
				System.exit(1);
			}

			// creating a command reader from a file
			WordTxtReader commandReader = new WordTxtReader(commandFile);

			// initialize map
			IWordMap wordPossMap;
			wordPossMap = new ListWordMap();

			// reading the content of the command file
			while (commandReader.hasNextWord()) {
				// getting the next command
				String command = commandReader.nextWord().getWord();

				switch (command) {
				case "addall":
					assert (textFilesFolder.isDirectory());
					File[] listOfFiles = textFilesFolder.listFiles(txtFilenameFilter);
					Arrays.sort(listOfFiles);
					int countFile = 0;
					int index = 0;
					long[] timeArr = new long[listOfFiles.length];
					String[] fileArr = new String[listOfFiles.length];
					int[] wordInsertedArr = new int[listOfFiles.length];
					for (File textFile : listOfFiles) {
						fileArr[index] = textFile.getName();
						WordTxtReader wordReader = new WordTxtReader(textFile);
						long startTime1 = System.currentTimeMillis();
						while (wordReader.hasNextWord()) {
							WordPosition wordPos = wordReader.nextWord();
							// adding word to the map
							wordPossMap.addPos(wordPos.getWord(), wordPos);
						}
						long endTime1 = System.currentTimeMillis();
						long runningTime1 = endTime1 - startTime1;
						timeArr[index] = runningTime1;
						wordInsertedArr[index] = wordPossMap.numberOfEntries();
						countFile++;
						index++;
					}

					for (int i = 0; i < fileArr.length; i++) {
						if (i == 0) {
							System.out.println(
									fileArr[i] + ": " + (wordInsertedArr[i]) + " words, running time: " + timeArr[i]);
						} else {
							System.out.println(fileArr[i] + ": " + (wordInsertedArr[i] - wordInsertedArr[i - 1])
									+ " words inserted, running time: " + timeArr[i] + " ms");
						}
					}

					System.out.println();
					System.out.println("addAll(): ");
					System.out.println(
							wordPossMap.numberOfEntries() + " entries have been indexed from " + countFile + " files");
					System.out.println();
					break;

				case "add":
					File textFile = new File(textFilesFolder, commandReader.nextWord().getWord() + ".txt");
					WordTxtReader wordReader = new WordTxtReader(textFile);
					int countAdd = 0;
					long startTime2 = System.currentTimeMillis();
					while (wordReader.hasNextWord()) {
						WordPosition word = wordReader.nextWord();
						// adding word to the map
						wordPossMap.addPos(word.getWord(), word);
						countAdd++;
					}
					long endTime2 = System.currentTimeMillis();
					long runningTime2 = endTime2 - startTime2;
					System.out.println("addFile(): ");
					System.out
							.println(countAdd + " entries have been indexed from file \"" + textFile.getName() + "\"");
					System.out.println(
							wordPossMap.numberOfEntries() + " words inserted, running time: " + runningTime2 + " ms");
					System.out.println();
					break;

				case "search":
					long startTime3 = System.currentTimeMillis();
					int nb = Integer.parseInt(commandReader.nextWord().getWord());
					String word = commandReader.nextWord().getWord();
					// search for word entry in map
					LinkedList<String> fileNameList = new LinkedList<String>();
					LinkedList<OccurrenceEntry> occurList = new LinkedList<OccurrenceEntry>();
					try {
						Iterator<IPosition> poss = wordPossMap.positions(word);
						int i = 0;
						while (poss.hasNext()) {
							String str1 = poss.next().getFileName();
							i++;
							if (!fileNameList.contains(str1)) {
								fileNameList.add(str1);
							}
						}

						for (int a = 0; a < fileNameList.size(); a++) {
							LinkedList<IPosition> ipoList = new LinkedList<IPosition>();
							int occurrence = 0;
							String compareStr = fileNameList.get(a);
							Iterator<IPosition> poss2 = wordPossMap.positions(word);
							while (poss2.hasNext()) {
								IPosition ipo = poss2.next();
								String str2 = ipo.getFileName();
								if (compareStr.equalsIgnoreCase(str2)) {
									ipoList.add(ipo);
									occurrence++;
								}
							}
							OccurrenceEntry entry = new OccurrenceEntry(ipoList, occurrence);
							occurList.add(entry);
						}
						occurList.sort(new OccurrenceSorting());

						// print output
						System.out.println("search " + nb + " " + word + "(): ");
						System.out.println("The word \"" + word + "\" occurs " + i + " times in " + fileNameList.size()
								+ " files");
						for (int c = occurList.size() - 1; c >= occurList.size() - nb; c--) {
							OccurrenceEntry e = occurList.get(c);
							LinkedList<IPosition> ipoList = e.getKey();
							System.out.println(e.getValue() + " times in " + ipoList.get(0).getFileName());
							System.out.print("( lines ");
							for (int d = 0; d < ipoList.size(); d++) {
								WordPosition wp = (WordPosition) ipoList.get(d);
								System.out.print(wp.getLine() + " ");
							}
							System.out.print(")");
							System.out.println("\n");
						}
						long endTime3 = System.currentTimeMillis();
						long runningTime3 = endTime3 - startTime3;
						System.out.println("Running Time: " + runningTime3);
					} catch (WordException e) {
						System.err.println("not found");
					}
					System.out.println();
					break;
				}

			}
		} catch (IOException e) { // catch exceptions caused by file input/output errors
			System.err.println("Check your file name");
			System.exit(1);
		}

	}
}
