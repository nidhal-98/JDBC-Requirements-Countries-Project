import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map.Entry;

public class JDBC {

	public static void backup() {

		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";" + "encrypt=true;"
				+ "trustServerCertificate=true";

		String user = /* "sa" */ Main.databaseUsername;
		String pass = /* "root" */ Main.databasePass;

		Connection con = null;
		try {

			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, user, pass);

			String sql = "BACKUP DATABASE " + Main.databaseName + "\r\n"
					+ "TO DISK = 'C:\\Users\\Lenovo\\eclipse-workspace\\CountriesAPI\\" + Main.databaseName + ".bak'\r\n"
					+ "WITH DESCRIPTION = 'Full Backup for" + Main.databaseName + " Database'";
			
	
			PreparedStatement statement = con.prepareStatement(sql);

			statement.executeUpdate();

			statement.close();
			con.close();
			
			System.out.println("Backup Successfully :)");
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}
	
	/*public void removeTable() {
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
	}*/
	
	public static void countriesTable() {

		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";" + "encrypt=true;"
				+ "trustServerCertificate=true";

		String user = /* "sa" */ Main.databaseUsername;
		String pass = /* "root" */ Main.databasePass;

		Connection con = null;
		try {

			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, user, pass);

			String sql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Countries')\r\n"
					+ "BEGIN\r\n"
					+ "    CREATE TABLE Countries (\r\n"
					+ "        id INT NOT NULL PRIMARY KEY IDENTITY,\r\n"
					+ "        name_common VARCHAR(255),\r\n"
					+ "        name_official VARCHAR(255),\r\n"
					+ "        tld VARCHAR(255),\r\n"
					+ "        cca2 VARCHAR(2),\r\n"
					+ "        ccn3 VARCHAR(3),\r\n"
					+ "        cca3 VARCHAR(3),\r\n"
					+ "        cioc VARCHAR(3),\r\n"
					+ "        independent TINYINT,\r\n"
					+ "        status VARCHAR(255),\r\n"
					+ "        un_member TINYINT,\r\n"
					+ "        idd_root VARCHAR(10),\r\n"
					+ "        idd_suffixes VARCHAR(255),\r\n"
					+ "        capital VARCHAR(255),\r\n"
					+ "        alt_spellings VARCHAR(255),\r\n"
					+ "        region VARCHAR(255),\r\n"
					+ "        subregion VARCHAR(255)\r\n"
					+ "    );\r\n"
					+ "END;\r\n"
					+ "INSERT INTO Countries (name_common, name_official, tld, cca2, ccn3, cca3, cioc, independent, status, un_member, idd_root, idd_suffixes, capital, alt_spellings, region, subregion)\r\n"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\r\n";
					
					String insertLangSql =  "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Languages')\r\n"
					+ "BEGIN\r\n"
					+ "    CREATE TABLE Languages (\r\n"
					+ "        country_Name VARCHAR(255),\r\n"
					+ "        Language_Key VARCHAR(255),\r\n"
					+ "        Language_Value VARCHAR(255)\r\n"
					+ "    );\r\n"
					+ "END;\r\n"
					+ "INSERT INTO Languages (country_Name, Language_Key, Language_Value)\r\n"
	        		+ "VALUES (?, ?, ?);\r\n";
					
					String insertCurrSql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Currencies')\r\n"
					+ "BEGIN\r\n"
					+ "    CREATE TABLE Currencies (\r\n"
					+ "        country_Name VARCHAR(255),\r\n"
					+ "        Currencies_Key VARCHAR(255),\r\n"
					+ "        Name VARCHAR(255),\r\n"
					+ "        Symbol VARCHAR(255)\r\n"
					+ "    );\r\n"
					+ "END;"
					+ "INSERT INTO Currencies (country_Name, Currencies_Key, Name, Symbol)\r\n"
	        		+ "VALUES (?, ?, ?, ?);\r\n";

			


			PreparedStatement statement = con.prepareStatement(sql);
	        PreparedStatement langStatement = con.prepareStatement(insertLangSql);
	        PreparedStatement currenciesStatement = con.prepareStatement(insertCurrSql);


			
			statement.setString(1, API.countries.get(API.countries.size()-1).name.common);
			statement.setString(2, API.countries.get(API.countries.size()-1).name.official);
			
			String tldCombined = "";
			for(int i =0; i<API.countries.get(API.countries.size()-1).tld.length; i++) {
				tldCombined = tldCombined + " - " + API.countries.get(API.countries.size()-1).tld[i];
			}
			
			statement.setString(3, tldCombined);
			statement.setString(4, API.countries.get(API.countries.size()-1).cca2);
			statement.setInt(5, API.countries.get(API.countries.size()-1).ccn3);
			statement.setString(6, API.countries.get(API.countries.size()-1).cca3);
			statement.setString(7, API.countries.get(API.countries.size()-1).cioc);
			statement.setBoolean(8, API.countries.get(API.countries.size()-1).independent);
			statement.setString(9, API.countries.get(API.countries.size()-1).status);
			statement.setBoolean(10, API.countries.get(API.countries.size()-1).unMember);
			
			String key = null;
			String name = null;
			String symbol = null;
			
			if (API.countries.get(API.countries.size()-1).currencies != null && !API.countries.get(API.countries.size()-1).currencies.isEmpty()) {
				for (Entry<String, Currencies> entry : API.countries.get(API.countries.size()-1).currencies.entrySet()) {
					key = entry.getKey();
					Currencies value = entry.getValue();
					name = value.name;
					symbol = value.symbol;
			        currenciesStatement.setString(1, API.countries.get(API.countries.size()-1).name.common);
			        currenciesStatement.setString(2, key);
			        currenciesStatement.setString(3, name);
			        currenciesStatement.setString(4, symbol);
			        currenciesStatement.executeUpdate();
				}
				
			}
			

			statement.setString(11, API.countries.get(API.countries.size()-1).idd.root);
			
			
			String suffixesCombined = "";
			for(int i =0; i<API.countries.get(API.countries.size()-1).idd.suffixes.length; i++) {
				suffixesCombined = suffixesCombined + " - " + API.countries.get(API.countries.size()-1).idd.suffixes[i];
			}
			statement.setString(12, suffixesCombined);
			
			String capitalCombined = "";
			for(int i =0; i<API.countries.get(API.countries.size()-1).capital.length; i++) {
				capitalCombined = capitalCombined + " - " + API.countries.get(API.countries.size()-1).capital[i];
			}
			statement.setString(13, capitalCombined);
			
			String altSpellingsCombined = "";
			for(int i =0; i<API.countries.get(API.countries.size()-1).altSpellings.length; i++) {
				altSpellingsCombined = altSpellingsCombined + " - " + API.countries.get(API.countries.size()-1).altSpellings[i];
			}
			statement.setString(14, altSpellingsCombined);
			
			statement.setString(15, API.countries.get(API.countries.size()-1).region);
			statement.setString(16, API.countries.get(API.countries.size()-1).subregion);
			

			if (API.countries.get(API.countries.size()-1).languages != null && !API.countries.get(API.countries.size()-1).languages.isEmpty()) {
			    for (Entry<String, String> entry : API.countries.get(API.countries.size()-1).languages.entrySet()) {
			        String langKey = entry.getKey();
			        String langValue = entry.getValue();
			        langStatement.setString(1, API.countries.get(API.countries.size()-1).name.common);
			        langStatement.setString(2, langKey);
			        langStatement.setString(3, langValue);
			        langStatement.executeUpdate();
			    }
			}
			
			

			
			statement.executeUpdate();

			// Close the PreparedStatement object
			statement.close();
			currenciesStatement.close();
			langStatement.close();

			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

}
