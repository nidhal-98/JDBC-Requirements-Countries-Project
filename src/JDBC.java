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
					+ "        currencies_name VARCHAR(255),\r\n"
					+ "        currencies_symbol VARCHAR(10),\r\n"
					+ "        idd_root VARCHAR(10),\r\n"
					+ "        idd_suffixes VARCHAR(255),\r\n"
					+ "        capital VARCHAR(255),\r\n"
					+ "        alt_spellings VARCHAR(255),\r\n"
					+ "        region VARCHAR(255),\r\n"
					+ "        subregion VARCHAR(255),\r\n"
					+ "        languages_key VARCHAR(10),\r\n"
					+ "        languages_value VARCHAR(255)\r\n"
					+ "    );\r\n"
					+ "END;\r\n"
					+ "INSERT INTO Countries (name_common, name_official, tld, cca2, ccn3, cca3, cioc, independent, status, un_member, currencies_name, currencies_symbol, idd_root, idd_suffixes, capital, alt_spellings, region, subregion, languages_key, languages_value)\r\n"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\r\n"
					+ "";

			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, API.countries.get(0).name.common);
			statement.setString(2, API.countries.get(0).name.official);
			statement.setString(3, API.countries.get(0).tld[0]);
			statement.setString(4, API.countries.get(0).cca2);
			statement.setInt(5, API.countries.get(0).ccn3);
			statement.setString(6, API.countries.get(0).cca3);
			statement.setString(7, API.countries.get(0).cioc);
			statement.setBoolean(8, API.countries.get(0).independent);
			statement.setString(9, API.countries.get(0).status);
			statement.setBoolean(10, API.countries.get(0).unMember);
			
			String key = null;
			String name = null;
			String symbol = null;
			if (API.countries.get(0).currencies != null && !API.countries.get(0).currencies.isEmpty()) {
				for (Entry<String, Currencies> entry : API.countries.get(0).currencies.entrySet()) {
					key = entry.getKey();
					Currencies value = entry.getValue();
					name = value.name;
					symbol = value.symbol;

				}
			}
			
			statement.setString(11, name);
			statement.setString(12, symbol);
			statement.setString(13, API.countries.get(0).idd.root);
			statement.setString(14, API.countries.get(0).idd.suffixes[0]);
			statement.setString(15, API.countries.get(0).capital[0]);
			statement.setString(16, API.countries.get(0).altSpellings[0]);
			statement.setString(17, API.countries.get(0).region);
			statement.setString(18, API.countries.get(0).subregion);
			
			String firstKey = null;
			String firstValue = null;
			if (API.countries.get(0).languages != null && !API.countries.get(0).languages.isEmpty()) {
				for (Entry<String, String> entry : API.countries.get(0).languages.entrySet()) {
					firstKey = entry.getKey();
					firstValue = entry.getValue();
				}
			}
			statement.setString(19, firstKey);
			statement.setString(20, firstValue);
			
			statement.executeUpdate();


			// Close the PreparedStatement object
			statement.close();

			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

}
