import java.util.HashMap;

public class Countries {
	Name name = new Name();
	String [] tld;
	String cca2;
	int ccn3;
	String cca3;
	String cioc;
	boolean independent;
	String status;
	boolean unMember;
	HashMap<String, Currencies> currencies;
	Idd idd = new Idd();
	String [] capital;
	String [] altSpellings;
	String region;
	String subregion;
	HashMap<String, String> languages;
	HashMap<String, Translations> translations;
	double [] latlng;
	boolean landlocked;
	String[] borders;
	double area;
	HashMap<String, Demonyms> demonyms;
	String flag;
	Map maps = new Map(); 
	long population;
	String fifa;
	Car car = new Car();
	String [] timezones;
	String [] continents;
	Flag flags = new Flag();
	CoatOfArms coatOfArms = new CoatOfArms();
	String startOfWeek;
	CapitalInfo capitalInfo = new CapitalInfo();
	PostalCode postalCode = new PostalCode();
}
