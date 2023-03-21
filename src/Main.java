import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);
	static String databaseName;
	static String databaseUsername;
	static String databasePass;

	public static void main(String[] args) {
		API.APICountries();
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
			System.out.println("3) Print Universities");
			System.out.println("4) API / Database");
			System.out.println("5) Search");
			System.out.println("*********************");
			System.out.print("Enter Number:  ");
			String option = sc.next();

			switch (option) {
			case "1":
				API.APICountries();
				break;
			}

		}
	}
}
