/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author	Prathik Kumar
 *	@since	December 6, 2023
 */
public class City implements Comparable<City> {
	
	// fields
	private String name; // city name
	private String state; // state name
	private String designation; // designation
	private int population; // population number
	// constructor
	public City(String state, String name, String designation, int population){
		this.name = name;
		this.state = state;
		this.designation = designation;
		this.population = population;
	}

	/**	Compare two cities populations
	 *	@param city		the other City to compare
	 *	@return				the following value:
	 *		If populations are different, then returns (this.population - city.population)
	 *		else if states are different, then returns (this.state - city.state)
	 *		else returns (this.name - city.name)
	 */
	public int compareTo(City city){
		if (this.population != city.population)
			return this.population - city.population;
		else if (this.state.compareTo(city.state) != 0)
			return this.state.compareTo(city.state);
		else
			return this.name.compareTo(city.name);
	}

	/**	Equal city name and state name
	 *	@param city		the other City to compare
	 *	@return				true if city name and state name equal; false otherwise
	 */
	public int compareNames(City city){
		int comparison = this.name.compareTo(city.name);
		if (comparison != 0){
			return comparison;
		}else if (this.population != city.population){
			return city.population - this.population;
		}else {
			return this.state.compareTo(city.state);
		}
	}
	
	/**	Accessor methods */
	public String getName() {
		return name;
	}
	public String getState(){
		return state;
	}
	public String getDesignation() {
		return designation;
	}
	public int getPopulation() {
		return population;
	}

	/**	toString */
	@Override
	public String toString() {
		return String.format("%-22s %-22s %-12s %,12d", state, name, designation,
						population);
	}


}
