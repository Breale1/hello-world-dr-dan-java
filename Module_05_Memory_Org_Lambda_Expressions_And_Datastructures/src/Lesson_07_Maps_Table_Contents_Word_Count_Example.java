/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Hello World with Dr. Dan - A Complete Introduction to Programming from Java to C++ (Code and Course � Dan Grissom)
//
// Additional Lesson Resources from Dr. Dan:
//		High-Quality Video Tutorials: www.helloDrDan.com
//		Free Commented Code: https://github.com/DanGrissom/hello-world-dr-dan-java
//
// In this lesson you will learn:
//		1) Complex usage of the Map data structure
//			a) Creating a Table of Contents
//				i) Sorting a Map by key (using Collections.sort() to sort the map's key set)
//			b) Creating a Word Frequency Table
//				i) Sorting a Map by value, (using Collections.sort(), a custom Comparator and a simple Class to
//					connect the map-key pair)
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import models.WordCountPair;

public class Lesson_07_Maps_Table_Contents_Word_Count_Example {

	public static void main(String[] args) {

		// Simple welcome statements printed to screen
		System.out.println("Program Objective: Learn to use Maps in complex scenarios.");
		System.out.println("===========================================================================");

		// Initialize Scanner to read in from user
		Scanner scan = new Scanner(System.in);

		// Initial welcome and prompt for filename
		System.out.println("Welcome to the file analyzer.");
		System.out.print("Please enter the file you would like to analyze: ");
		String fileName = scan.next();

		// Prompt for option
		System.out.println("Please select an option by typing the number of the option:");
		System.out.println("\t1. Print Table of Contents (ToC)");
		System.out.println("\t2. Print words with top word count");
		int option = scan.nextInt();

		if (option == 1)
		{
			// Declare Map and obtain instance from method call
			Map<String, ArrayList<Integer>> toc = getTableOfContents(fileName);
			printOrderedTableOfContents(toc);
		}
		else if (option == 2)
		{
			// Declare Map and obtain instance from method call
			Map<String, Integer> wordCount = getWordCount(fileName);

			// Ask the user how many of the most frequent words they'd like to print and print them
			System.out.println("How many words would you like to print from the most frequent words?");
			int numWords = scan.nextInt();
			printTopWordCounts(numWords, wordCount);
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// This method takes in a filename, parses it and returns a table of contents
	// (i.e., a mapping of words to page numbers corresponding words appear on in
	// the input file).
	//		Parameters:
	//			filename - A string of the filename to open and convert to a ToC
	//		Returns:
	//			A Map which maps words (Strings) to an ArrayList of page numbers
	////////////////////////////////////////////////////////////////////////////////
	private static Map<String, ArrayList<Integer>> getTableOfContents(String filename)
	{
		// Keep track of the current page while search through the file
		int currentPg = 0;

		// TODO 1: Create the Map that you will eventually return and call it toc (for Table of Contents)
		Map<String, ArrayList<Integer>> toc = new HashMap<String, ArrayList<Integer>>();

		// Init file input objects
		FileInputStream fis = null;
		Scanner scan = null;

		try
		{
			fis = new FileInputStream(filename);
			scan = new Scanner(fis);

			// Keep reading words from file...
			while (scan.hasNext())
			{
				// TODO 2: Read in next word and update it so that we ignore the following
				// symbols: . , ; : ( ) ! ? (HINT: Replace these symbols with "", i.e., NOTHING!)
				// We do this b/c "today." is the same word as "today" to a table of contents
				// Also, force all words to lowercase ("The" is same as "the")
				String word = scan.next().replaceAll("[.,;:()!?]", "").toLowerCase();

				// If we find the sequence of characters denoting the page number
				if (word.equals("%%%%"))
				{
					// TODO 3: Read in pg number and clear off the closing "%%%%" sequence
					currentPg = scan.nextInt();
					scan.next(); // flush the closing %%%%
				}
				else if (Character.isAlphabetic(word.charAt(0)))  // Make sure to only print words that begin with a letter
				{	
					// TODO 4: If the word is found in the toc map (replace "true" with your logic)
					if(toc.containsKey(word))
					{
						// TODO 5: Get the current list of pages for this word and
						// add the current page IF NOT ALREADY in the ArrayList
						ArrayList<Integer> pages = toc.get(word);
						if (!pages.contains(currentPg))
							pages.add(currentPg);
					}
					else // ...if word not already in toc map...
					{
						// TODO 6: Create a new list, add the current page to your list, and PUT
						// the list in the toc map with the key as the word
						ArrayList<Integer> pages = new ArrayList<Integer>();
						pages.add(currentPg);
						toc.put(word, pages);
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR: " + e.getMessage());
		}
		finally
		{
			// Close file
			try
			{
				if (scan != null) scan.close();
				if (fis != null) fis.close();
			}
			catch(Exception e)
			{
				System.out.println("ERROR: " + e.getMessage());
			}

		}

		return toc;
	}

	////////////////////////////////////////////////////////////////////////////////
	// This method prints the table of contents in alphabetical order followed by
	// page numbers each word appears on (separated by commas).
	//		Parameters:
	//			toc - A Map representing the Table of Contents
	//					(mapping String->ArrayList of page numbers)
	//		Returns:
	//			void (nothing)
	////////////////////////////////////////////////////////////////////////////////
	private static void printOrderedTableOfContents(Map<String, ArrayList<Integer>> toc)
	{
		// TODO 7: Get Unique words in a Set (get the key set) and call the set uniqueWords
		Set<String> uniqueWords = toc.keySet();

		// It is not easy to sort a map (it wasn't designed for this). We really just need to sort
		// the keys. Once the keys are sorted, we can then iterate through the sorted keys and
		// use the map to obtain the value associated with the key.

		// TODO 8: Create a new ArrayList called orderedWords and add all
		// of the words from the map to it. Then, sort it using 
		// the Collections.sort() method.
		ArrayList<String> orderedWords = new ArrayList<String>(uniqueWords);
		Collections.sort(orderedWords);


		// TODO 9: Now that the words (keys) from the maps are sorted alphabetically within
		// orderedWords, cycle through each word and obtain the matching value from
		// the map (an ArrayList of ints). Print out each word and page numbers like:
		//		topology:	2, 3, 5, 8,
		for (String word : orderedWords) {
			System.out.print(word + ": ");
			for (int pg : toc.get(word))
				System.out.print(pg + ", ");
			System.out.println();
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	// This method takes in a filename, parses it and returns a mapping of words to
	// their frequency (count) in the input file.
	//		Parameters:
	//			filename - A string of the filename to open and convert to a word count
	//		Returns:
	//			A Map which maps words (Strings) to a frequency count (int)
	////////////////////////////////////////////////////////////////////////////////
	private static Map<String, Integer> getWordCount(String filename)
	{
		// TODO 10: Create the Map that you will eventually return and call it wordCount
		Map<String, Integer> wordCount = new HashMap<String, Integer>();
		int currentPg = 0;

		// Init file input objects
		FileInputStream fis = null;
		Scanner scan = null;

		try
		{
			fis = new FileInputStream(filename);
			scan = new Scanner(fis);

			// Keep reading words from file...
			while (scan.hasNext())
			{
				// TODO 11 (Same as TODO 2): Read in next word and update it so that we ignore the following
				// symbols: . , ; : ( ) ! ?  (HINT: Replace these symbols with "", i.e., NOTHING!)
				// We do this b/c "today." is the same word as "today" to a table of contents
				// Also, force all words to lowercase ("The" is same as "the")
				String word = scan.next().replaceAll("[.,;:()!?]", "").toLowerCase();

				// If we find the sequence of characters denoting the page number
				if (word.equals("%%%%"))
				{
					// TODO 12 (Same as TODO 3): Read in pg number and clear off the closing "%%%%" sequence
					currentPg = scan.nextInt();
					scan.next();
				}
				else if (Character.isAlphabetic(word.charAt(0))) // Make sure to only print words that begin with a letter
				{	
					// TODO 13 (Similar to TODO 4): If the word is found in the wordCount map (replace "true" with your logic)
					if(wordCount.containsKey(word))
					{
						// TODO 14: Get the current word count for this word and
						// increment it
						int count = wordCount.get(word) + 1;
						wordCount.put(word, count);
					}
					else // ...if word not already in the wordCount map
					{
						// TODO 15: PUT the word in the wordCount map with a count (value) of 1 (first appearance)
						wordCount.put(word, 1);
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR: " + e.getMessage());
		}
		finally
		{
			// Close file
			try
			{
				if (scan != null) scan.close();
				if (fis != null) fis.close();
			}
			catch(Exception e)
			{
				System.out.println("ERROR: " + e.getMessage());
			}
		}

		return wordCount;
	}

	////////////////////////////////////////////////////////////////////////////////
	// This method prints the corresponding number of most-frequently-used words in wordCount. 
	//		Parameters:
	//			numWords - An int representing how many word counts to print
	//			wordCount - A Map which maps words (Strings) to a frequency count (int)
	//		Returns:
	//			void (nothing)
	////////////////////////////////////////////////////////////////////////////////
	
	private static void printTopWordCounts(int numWords, Map<String, Integer> wordCount)
	{
		// Like earlier, it is hard to sort things in a map. We want to sort based on the
		// values, which is even harder (because we cannot easily re-associate values with
		// the key b/c the values can be repeated in the map; for example, there may be 
		// numerous words that appear exactly 5 times).

		// TODO 15: Create an ArrayList of WordCountPairs* called sortedWordCount. For every word in the
		// map, you should create a new WordCountPair object with the word and count and add
		// it to the sortedWordCount ArrayList.
		//
		// *NOTE: WordCountPair is a custom class we created to encapsulate the entire key-value
		// pair (that is, the word-count pair).
		ArrayList<WordCountPair> sortedWordCount = new ArrayList<WordCountPair>();
		for (String word : wordCount.keySet())
			sortedWordCount.add( new WordCountPair(word, wordCount.get(word)) );

		// TODO 16: Once we have the ArrayList of WordCountPairs, we must use a custom Comparator. This is 
		// a VERY VALUABLE thing to learn as it allows for more interesting/complex sortings. You
		// can find a solution that points you in the right direction here (THE TOP ANSWER):
		// SOURCE: http://stackoverflow.com/questions/29920027/how-can-i-sort-a-list-of-pairstring-integer
		//
		// NOTE: We are sorting sortedWordCount (not words), which contains WordCountPair objects (not
		// Pair<String, Integer> objects.
		//
		// NOTE2: Inside the compare method, you'll need some code that compares the word counts of each
		// WordCountPair object and returns an integer (think about String method compareTo() which we saw
		// earlier this school year).
		Collections.sort(sortedWordCount, new Comparator<WordCountPair>() {
			public int compare(WordCountPair wcp1, WordCountPair wcp2) {
				return wcp2.getCount() - wcp1.getCount();
			}
		});


		// TODO 17: Now, using the numWords parameter, print out word counts for the numWords most-frequent
		// words HINT: You are just iterating through the first numWords words in sortedWordCount
		// and printing in the following format:
		// 		pcb: 76 times
		int wordsToPrint = Math.min(numWords, sortedWordCount.size());
		for (int i = 0; i < wordsToPrint; i++) {
			WordCountPair wcp = sortedWordCount.get(i);
			System.out.println(wcp.getWord() + ": " + wcp.getCount() + " times");
		}
	}
}
