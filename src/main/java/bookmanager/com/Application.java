package bookmanager.com;

import java.util.Scanner;

import bookmanager.com.managers.BookManager;
import bookmanager.com.managers.DatabaseManager;

public class Application {

	static BookManager bookManager = new BookManager();
	static DatabaseManager databaseManager = new DatabaseManager();
	
	public static void main(String[] args) {

		databaseManager.initializeDB();
		int actionId = 0;
		Scanner input = new Scanner(System.in);
		while (actionId != 5) {
			bookManager.printMenu();
			actionId = input.nextInt();
			switch (actionId) {
			case 1:
				bookManager.viewAll();
				break;
			case 2:
				bookManager.addBook();
				break;
			case 3:
				bookManager.editBook();
				break;
			case 4:
				bookManager.search();
				break;
			case 5:
				System.out.println("Library saved.");
				break;
			}
		}
		input.close();
		databaseManager.finalizeDB();
	}

}
