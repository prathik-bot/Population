import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *	Population - To implement different sorting methods and organize a large set of data.
 *
 *	Requires FileUtils and Prompt classes.
 *
 *	@author	Prathik Kumar
 *	@since	December 6, 2023
 */
public class Population {
	
	// List of cities
	private List<City> cities;
	
	// US data file
	private final String DATA_FILE = "usPopData2017.txt";

	public Population(){
		cities = new ArrayList<City>();
	}
	public static void main(String[] args){
		Population pl = new Population();
		pl.run();
	}

	/**
	 *
	 * Run method that is called from main to execute all criteria.
	 *
	 */
	public void run(){
		getCities(); 		//Populate cities
		printIntroduction(); //Print introduction
		System.out.println(cities.size() + " cities in database\n");
		int choice = 0;
		while (choice !=9) {
			printMenu();
			choice = Prompt.getInt("Enter Selection");
			switch(choice){
				case 1:
					leastPop50Cities(); //Selection Sort
					break;
				case 2:
					mostPop50Cities(); //Merge Sort
					break;
				case 3:
					first50Cities(); //Insertion Sort
					break;
				case 4:
					last50Cities(); //merge sort
					break;
				case 5:
					// insert 5th case
					break;
				case 6:
					// insert 6th case
					break;
				case 9:
					System.out.println("Thanks for using Population!");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid Selection. Please try again!");
					break;
			}
		}
	}


	/**
	 * Get Cities from file and populate in array list
	 */
	public void getCities(){
		Scanner sc = FileUtils.openToRead(DATA_FILE);
		sc.useDelimiter("[\t\n]");
		while(sc.hasNext()){
			cities.add(new City(sc.next(), sc.next(), sc.next(), sc.nextInt()));
		}
	}
	/**	Prints the introduction to Population */
	public void printIntroduction() {
		System.out.println("   ___                  _       _   _");
		System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
		System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
		System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
		System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
		System.out.println("           |_|");
		System.out.println();
	}


	/**	Print out the choices for population sorting */
	public void printMenu() {
		System.out.println("1. Fifty least populous cities in USA (Selection Sort)");
		System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
		System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
		System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
		System.out.println("5. Fifty most populous cities in named state");
		System.out.println("6. All cities matching a name sorted by population");
		System.out.println("9. Quit");
	}

	/**
	 * Helper method for swapping two indicies of a List
	 *
	 * @param original		The list where the indicies will be swapped
	 * @param first			The first index
	 * @param second		The second index
	 */
	private void swap(List<City> original, int first, int second) {
		City temp = original.get(first);
		original.set(first, original.get(second));
		original.set(second, temp);
	}
	/**
	 * Fifty least populous cities in USA using Selection Sort
	 */
	public void leastPop50Cities(){

		//performing sort
		long startMillisec = System.currentTimeMillis();
		for (int outer = 0; outer < cities.size()-1; outer++) {
			int max = outer;
			for (int inner = outer+1; inner < cities.size(); inner++) {
				if (cities.get(inner).compareTo(cities.get(max)) < 0)
					max = inner;
			}
			swap(cities, outer, max);
		}
		long endMillisec = System.currentTimeMillis();

		//printing output
		System.out.println("\nFifty least populous cities");
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		for (int i = 0; i < 50; i++){
			System.out.printf("%4s %s\n", (i+1 + ":"), cities.get(i));
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");

	}

	/**
	 * Fifty most populous cities in the USA using merge sort
	 */
	public void mostPop50Cities(){

		//performing sort
		long startMillisec = System.currentTimeMillis();
		mergeSort(cities, 0, cities.size()-1, false);
		long endMillisec = System.currentTimeMillis();

		//printing output
		System.out.println("\nFifty most populous cities");
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		for (int i = 0; i < 50; i++){
			System.out.printf("%4s %s\n", (i+1 + ":"), cities.get(i));
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");
	}
	/**
	 * Merge sort method
	 *
	 * @param arrToSort	The array to sort
	 * @param from		The from index
	 * @param to		The ending/to index
	 * @param isNames	boolean to sort by name or not
	 */
	private void mergeSort(List<City> arrToSort, int from, int to, boolean isNames)
	{
		if (from >= to) //array contains just a single element
			return;

		int midIdx = from + (to - from) / 2; //middle index
		mergeSort(arrToSort, from, midIdx, isNames); //Divide the left half recursively
		mergeSort(arrToSort, midIdx + 1, to, isNames); //Divide the right half recursively

		merge(arrToSort, from, midIdx, to, isNames); //merge the left and right half
	}

	/**
	 * Merge method of the merge sort algorithm
	 *
	 * @param arrToSort	The array to sort
	 * @param from		The from index
	 * @param middle	The middle index
	 * @param to		The to index
	 * @param isNames	Whether or not you are sorting by name
	 */
	private void merge(List<City> arrToSort, int from, int middle, int to, boolean isNames)
	{
		List<City> leftArr = new ArrayList<City>();
		List<City> rightArr = new ArrayList<City>();

		//Initializing the left and right arrays
		for(int i=0; i<middle - from + 1; i++)
			leftArr.add(arrToSort.get(from + i));

		for(int i=0; i<to - middle; i++)
			rightArr.add(arrToSort.get(middle + i + 1));

		//merging the left and right arrays into a single sorted array
		int leftArrIdx = 0, rightArrIdx = 0, sortedArrIdx = from;
		while((leftArrIdx < leftArr.size()) && (rightArrIdx < rightArr.size()))
		{
			if (isNames){
				if(leftArr.get(leftArrIdx).compareNames(rightArr.get(rightArrIdx)) > 0)
				{
					arrToSort.set(sortedArrIdx, leftArr.get(leftArrIdx));
					leftArrIdx += 1;
				}
				else
				{
					arrToSort.set(sortedArrIdx, rightArr.get(rightArrIdx));
					rightArrIdx += 1;
				}
				sortedArrIdx += 1;
			} else {
				if(leftArr.get(leftArrIdx).compareTo(rightArr.get(rightArrIdx)) > 0)
				{
					arrToSort.set(sortedArrIdx, leftArr.get(leftArrIdx));
					leftArrIdx += 1;
				}
				else
				{
					arrToSort.set(sortedArrIdx, rightArr.get(rightArrIdx));
					rightArrIdx += 1;
				}
				sortedArrIdx += 1;
			}

		}

		//Adding the rest of the elements of left array if present
		while(leftArrIdx < leftArr.size())
		{
			arrToSort.set(sortedArrIdx, leftArr.get(leftArrIdx));
			leftArrIdx += 1;
			sortedArrIdx += 1;
		}

		//Adding the rest of the elements of right array if present
		while(rightArrIdx < rightArr.size())
		{
			arrToSort.set(sortedArrIdx, rightArr.get(rightArrIdx));
			rightArrIdx += 1;
			sortedArrIdx += 1;
		}
	}

	/**
	 * First fifty cities sorted by name using Insertion Sort
	 */
	public void first50Cities(){
		//performing sort
		long startMillisec = System.currentTimeMillis();
		for (int outer = 1; outer < cities.size(); outer++) {
			City temp = cities.get(outer);
			int inner = outer;
			while (inner > 0 && temp.compareNames(cities.get(inner-1)) < 0){
				cities.set(inner, cities.get(inner-1));
				inner--;
			}
			cities.set(inner, temp);
		}
		long endMillisec = System.currentTimeMillis();

		//printing output
		System.out.println("\nFifty cities sorted by name");
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		for (int i = 0; i < 50; i++){
			System.out.printf("%4s %s\n", (i+1 + ":"), cities.get(i));
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");
	}
	/**
	 * Last fifty cities sorted by name descending using merge sort
	 */
	public void last50Cities(){

		//performing sort
		long startMillisec = System.currentTimeMillis();
		mergeSort(cities, 0, cities.size()-1, true);
		long endMillisec = System.currentTimeMillis();

		//printing output
		System.out.println("\nFifty cities sorted by names descending");
		System.out.printf("%4s %-22s %-22s %-12s %12s\n", "", "State", "City", "Type", "Population");
		for (int i = 0; i < 50; i++){
			System.out.printf("%4s %s\n", (i+1 + ":"), cities.get(i));
		}
		System.out.println("\n");
		System.out.println("Elapsed Time: " + (endMillisec - startMillisec) + " milliseconds\n\n");
	}
	
}
