import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

import com.google.gson.Gson;

public class API {

	static ArrayList<String> countrySet = new ArrayList<String>();

	public static void APICountries() {
		String apiUrl = "https://restcountries.com/v3.1/all";
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			StringBuilder json = new StringBuilder();

			while ((output = br.readLine()) != null) {
				json.append(output);
			}

			conn.disconnect();

			Gson gson = new Gson();
			Countries countriesArr[] = gson.fromJson(json.toString(), Countries[].class);

			// Use myObj for further processing
			for (int i = 0; i < countriesArr.length; i++) {
				System.out.println("NAME:");
				Countries newCountry = countriesArr[i];

				System.out.println("  Common:   " + newCountry.name.common);
				System.out.println("  Official: " + newCountry.name.official);
				
				System.out.println("  NativeName:");
				if (newCountry.name.nativeName != null && !newCountry.name.nativeName.isEmpty()) {
					for (Entry<String, NativeName> entry : newCountry.name.nativeName.entrySet()) {
						String key = entry.getKey();
						NativeName value = entry.getValue();
						System.out.println("  *" + key + "*");
						System.out.println("    " + "Official: " + value.official);
						System.out.println("    " + "Common:   " + value.common);

					}
				}

				if (newCountry.tld != null) {
					for (String tld : newCountry.tld) {
						System.out.println("  Tld:  " + tld);
					}
				}
				
				System.out.println("  Cca2: " + newCountry.cca2);
				System.out.println("  Ccn3: " + newCountry.ccn3);
				System.out.println("  Cca3: " + newCountry.cca3);
				System.out.println("  Cioc: " + newCountry.cioc);
				System.out.println("  Independent: " + newCountry.independent);
				System.out.println("  Status: " + newCountry.status);
				System.out.println("  UnMember: " + newCountry.unMember);
				
				System.out.println("  Currencies:");
				if (newCountry.currencies != null && !newCountry.currencies.isEmpty()) {
					for (Entry<String, Currencies> entry : newCountry.currencies.entrySet()) {
						String key = entry.getKey();
						Currencies value = entry.getValue();
						System.out.println("  *" + key + "*");
						System.out.println("    " + "Name: " + value.name);
						System.out.println("    " + "Symbol:   " + value.symbol);

					}
				}
				
				System.out.println("  Idd: " );
				System.out.println("    " + "Root: " + newCountry.idd.root);
				if (newCountry.idd.suffixes != null) {
					for (String idd : newCountry.idd.suffixes) {
						System.out.println("    " + "Suffixes: " + idd);
					}
				}
				
				if (newCountry.capital != null) {
					for (String capital : newCountry.capital) {
						System.out.println("  Capital: " + capital);
					}
				}
				
				if (newCountry.altSpellings != null) {
					System.out.println("  AltSpellings: ");
					for (String altSpellings : newCountry.altSpellings) {
						System.out.println("   " + altSpellings);
					}
				}
				
				System.out.println("  Region: " + newCountry.region);
				System.out.println("  SubRegion: " + newCountry.subregion);
				
				System.out.println("  Languages:");
				if (newCountry.languages != null && !newCountry.languages.isEmpty()) {
					for (Entry<String, String> entry : newCountry.languages.entrySet()) {
						String key = entry.getKey();
						String value = entry.getValue();
						System.out.println("  *" + key + "*");
						System.out.println("    " + value);
					}
				}
				
				System.out.println("  Translations:");
				if (newCountry.translations != null && !newCountry.translations.isEmpty()) {
					for (Entry<String, Translations> entry : newCountry.translations.entrySet()) {
						String key = entry.getKey();
						Translations value = entry.getValue();
						System.out.println("  *" + key + "*");
						System.out.println("    " + "Official: " + value.official);
						System.out.println("    " + "Common:   " + value.common);
					}
				}
				
				if (newCountry.latlng != null) {
					System.out.println("  Latlng: ");
					for (double latlng : newCountry.latlng) {
						System.out.println("    " + latlng);
					}
				}
				
				System.out.println("  Landlocked: " + newCountry.landlocked);
				
				if (newCountry.borders != null) {
					System.out.println("  Borders: ");
					for (String borders : newCountry.borders) {
						System.out.println("    " + borders);
					}
				}

				System.out.println("  Area: " + newCountry.area);

				System.out.println("  Demonyms:");
				if (newCountry.demonyms != null && !newCountry.demonyms.isEmpty()) {
					for (Entry<String, Demonyms> entry : newCountry.demonyms.entrySet()) {
						String key = entry.getKey();
						Demonyms value = entry.getValue();
						System.out.println("  *" + key + "*");
						System.out.println("    " + "f: " + value.f);
						System.out.println("    " + "m: " + value.m);
					}
				}
				
				System.out.println("  Flag: " + newCountry.flag);
				
				System.out.println("  Map: ");
				System.out.println("    googleMaps:       " + newCountry.maps.googleMaps);
				System.out.println("    openStreetMaps:   " + newCountry.maps.openStreetMaps);

				System.out.println("  Population: " + newCountry.population);
				
				System.out.println("  fifa: " + newCountry.fifa);
				
				if (newCountry.car.signs != null) {
					System.out.println("  Car: ");
					System.out.println("    Signs: ");
					for (String sign : newCountry.car.signs) {
						System.out.println("      "+ sign);
					}
					System.out.println("    side: ");
					System.out.println("      "+ newCountry.car.side);
				}
				
				if (newCountry.timezones != null) {
					System.out.println("  Timezones: ");
					for (String timezones : newCountry.timezones) {
						System.out.println("    " + timezones);
					}
				}
				
				if (newCountry.continents != null) {
					System.out.println("  Continents: ");
					for (String continents : newCountry.continents) {
						System.out.println("    " + continents);
					}
				}
				
				System.out.println("  Flags: ");
				System.out.println("    png:   " + newCountry.flags.png);
				System.out.println("    svg:   " + newCountry.flags.svg);
				System.out.println("    alt:   " + newCountry.flags.alt);
				
				System.out.println("  CoatOfArms: ");
				System.out.println("    png:   " + newCountry.coatOfArms.png);
				System.out.println("    svg:   " + newCountry.coatOfArms.svg);				
				
				System.out.println("  StartOfWeek: " + newCountry.startOfWeek);
				
				System.out.println("  CapitalInfo: " + newCountry.startOfWeek);
				if (newCountry.capitalInfo.latlng != null) {
					System.out.println("   Latlng: ");
					for (double latlng : newCountry.capitalInfo.latlng) {
						System.out.println("     " + latlng);
					}
				}
				
				System.out.println("  PostalCode: ");
				System.out.println("    Format:   " + newCountry.postalCode.format);
				System.out.println("    Regex:   " + newCountry.postalCode.regex);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
