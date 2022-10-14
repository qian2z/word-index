package F28DA_CW1;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/** Main class for the Word Index program */
public class WordIndex {

	static final File textFilesFolder = new File("TextFiles");
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
			wordPossMap = new HashWordMap();

			// reading the content of the command file
			while (commandReader.hasNextWord()) {
				// getting the next command
				String command = commandReader.nextWord().getWord();

				switch (command) {
				case "addall":
					assert (textFilesFolder.isDirectory());
					File[] listOfFiles = textFilesFolder.listFiles(txtFilenameFilter);
					Arrays.sort(listOfFiles);
					int countAddAll = 0;
					int countFile = 0;
					for (File textFile : listOfFiles) {
						WordTxtReader wordReader = new WordTxtReader(textFile);
						while (wordReader.hasNextWord()) {
							WordPosition wordPos = wordReader.nextWord();
							String wordAdd = wordPos.getWord().toLowerCase();
							String fileAdd = wordPos.getFileName().toLowerCase();
							int lineAdd = wordPos.getLine();
							// adding word to the map
							wordPossMap.addPos(wordAdd, new WordPosition(fileAdd, lineAdd, wordAdd));
							countAddAll++;
						}
						countFile++;
					}
					System.out.println("addAll(): ");
					System.out.println(countAddAll + " entries have been indexed from " + countFile + " files");
					System.out.println();
					break;

				case "add":
					File textFile = new File(textFilesFolder, commandReader.nextWord().getWord() + ".txt");
					WordTxtReader wordReader1 = new WordTxtReader(textFile);
					int countAdd = 0;
					while (wordReader1.hasNextWord()) {
						WordPosition word = wordReader1.nextWord();
						// adding word to the map
						wordPossMap.addPos(word.getWord(), word);
						countAdd++;
					}
					System.out.println("addFile(): ");
					System.out
							.println(countAdd + " entries have been indexed from file \"" + textFile.getName() + "\"");
					System.out.println();
					break;

				case "search":
					int nb = Integer.parseInt(commandReader.nextWord().getWord());
					String word = commandReader.nextWord().getWord();
					// search for word entry in map
					// a linked list to store each file name
					LinkedList<String> fileNameList = new LinkedList<String>();
					// a linked list to store the entry (positionList-occurrence pair)
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
							// create a entry variable that store the positionList in each file of the word
							// with the number of occurrence of the word in the each file
							OccurrenceEntry entry = new OccurrenceEntry(ipoList, occurrence);
							// add the entry into the list
							occurList.add(entry);
						}
						// sort the list from lowest occurrence to highest occurrence
						occurList.sort(new OccurrenceSorting());

						// print output
						System.out.println("search " + nb + " " + word + "(): ");
						System.out.println("The word \"" + word + "\" occurs " + i + " times in " + fileNameList.size()
								+ " files");
						// print the nb top most occurrence file
						for (int c = occurList.size() - 1; c >= occurList.size() - nb; c--) {
							OccurrenceEntry e = occurList.get(c);
							LinkedList<IPosition> ipoList = e.getKey();
							System.out.println(e.getValue() + " times in " + ipoList.get(0).getFileName());
							System.out.print("( lines ");
							// print all the lines that contain the searched word in the file
							for (int d = 0; d < ipoList.size(); d++) {
								WordPosition wp = (WordPosition) ipoList.get(d);
								System.out.print(wp.getLine() + " ");
							}
							System.out.print(")");
							System.out.println("\n");
						}
					} catch (WordException e) {
						System.err.println("not found");
					}
					System.out.println();
					break;

				case "remove":
					File textFileToRemove = new File(textFilesFolder, commandReader.nextWord().getWord() + ".txt");
					// remove word-positions
					WordTxtReader wordReaderRemove = new WordTxtReader(textFileToRemove);
					int countRemove = 0;
					while (wordReaderRemove.hasNextWord()) {
						WordPosition wordRemove = wordReaderRemove.nextWord();
						String wordToRemove = wordRemove.getWord();
						String fileName = wordRemove.getFileName();
						int line = wordRemove.getLine();
						WordPosition wp = new WordPosition(fileName, line, wordToRemove);
						// removing word from the map
						try {
							wordPossMap.removePos(wordToRemove, wp);
							countRemove++;
						} catch (WordException e) {
							System.err.print("");
						}
					}
					System.out.println("remove(): ");
					System.out.println(countRemove + " entries have been removed from file \""
							+ textFileToRemove.getName() + "\"");
					System.out.println();
					break;

				case "overview":
					// print overview
					LinkedList<String> totalFileList = new LinkedList<String>();
					LinkedList<String> totalPosList = new LinkedList<String>();
					Iterator<String> ite1 = wordPossMap.words();
					LinkedList<String> wordList = new LinkedList<String>();
					while (ite1.hasNext()) {
						String str = ite1.next();
						wordList.add(str);
						try {
							Iterator<IPosition> ite2 = wordPossMap.positions(str);
							while (ite2.hasNext()) {
								IPosition ip = ite2.next();
								String fileName = ip.getFileName();
								String posText = "" + ip.getFileName() + ip.getLine();
								if (!totalFileList.contains(fileName)) {
									totalFileList.add(fileName);
								}
								if (!totalPosList.contains(posText)) {
									totalPosList.add(posText);
								}
							}
						} catch (WordException e) {
							e.printStackTrace();
						}
					}
					System.out.println("overview(): ");
					System.out.println("number of words: " + wordList.size());
					System.out.println("number of positions: " + totalPosList.size());
					System.out.println("number of files: " + totalFileList.size());
					System.out.println();
					break;

				default:
					break;
				}

			}

		} catch (IOException e) { // catch exceptions caused by file input/output errors
			System.err.println("Check your file name");
			System.exit(1);
		}
	}
}
