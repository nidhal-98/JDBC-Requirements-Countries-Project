import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);
	static String databaseName;
	static String databaseUsername;
	static String databasePass;
	
	static String countryEntrerd;

	static String tableName1;

	public static void main(String[] args) {
		//API.APICountries();
		boolean menue = true;
		System.out.print("Enter Database Name:      ");
		databaseName = sc.next();
		System.out.print("Enter Database Username:  ");
		databaseUsername = sc.next();
		System.out.print("Enter Database Password:  ");
		databasePass = sc.next();
		while (menue) {
			System.out.println("\n1) backup");
			System.out.println("2) Removing Table");
			System.out.println("3) Print Countries");
			System.out.println("4) Search");
			System.out.println("*********************");
			System.out.print("Enter Number:  ");
			String option = sc.next();

			switch (option) {
			case "1":
				JDBC.backup();
				break;
				
			case "2":
				try {
					String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";"
							+ "encrypt=true;" + "trustServerCertificate=true";
					String username = Main.databaseUsername;
					String password = Main.databasePass;
					Connection conn = DriverManager.getConnection(url, username, password);

					DatabaseMetaData metadata = conn.getMetaData();
					String[] types = { "TABLE" };
					ResultSet resultSet = metadata.getTables(null, null, "%", types);

					while (resultSet.next()) {
						String tableName = resultSet.getString("TABLE_NAME");
						if (!tableName.equalsIgnoreCase("trace_xe_action_map")
								&& !tableName.equalsIgnoreCase("trace_xe_event_map")) {
							JDBC.table.add(tableName);
						}
					}

					// Close the connection
					conn.close();
				} catch (Exception ex) {
				}
				if(JDBC.table.isEmpty()) {
					System.out.println("There is no Tables");
				}
				else {
					JDBC.printTables();
					System.out.print("\nEnter Table Name:  ");
					tableName1 = sc.next();
					JDBC.deleteTable();
					JDBC.table.remove(tableName1);
				}
				break;
			
			case "3":
				API.APICountries();
				break;
				
			case "4":
				API.APICountriesName();
				for(String countryName: API.countriesList) {
					System.out.println(countryName);
				}
				System.out.print("If the country has two word, WRITE it like ");			
				System.err.println("'United+States'");
				System.out.print("Enter name of country: ");
				String countryName = sc.next();
				for(int i=0; i<API.countries.size(); i++) {
					if(API.countries.get(i).name.official.equalsIgnoreCase(countryName)) {
		                System.out.println("     Country Name - Official: " + API.countries.get(i).name.official);
		                System.out.println("     Country Name - Common: " + API.countries.get(i).name.common);
		                System.out.println("     tld: ");
		                for(int j=0; j<API.countries.get(i).tld.length; j++) {                	
		                	 System.out.print("      " + API.countries.get(i).tld[j] + ", ");
		                }
		                System.out.println("     CCA2: " + API.countries.get(i).cca2);
		                System.out.println("     CCN3: " + API.countries.get(i).ccn3);
		                System.out.println("     CCA3: " + API.countries.get(i).cca3);
		                System.out.println("     CIOC: " + API.countries.get(i).cioc);
		                System.out.println("     Independent: " + API.countries.get(i).independent);
		                System.out.println("     Status: " + API.countries.get(i).status);
		                System.out.println("     unMember: " + API.countries.get(i).unMember);
		                System.out.println("     Currencies: ");
		                System.out.println("\n -------------------------------------------------------------------------------------------");
					}
				}
				break;
				
			}

		}
	}
}
