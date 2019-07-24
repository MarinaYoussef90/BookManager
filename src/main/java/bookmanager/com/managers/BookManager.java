package bookmanager.com.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import bookmanager.com.daos.BookDao;
import bookmanager.com.daos.BookDoaImpl;
import bookmanager.com.entities.Book;

public class BookManager {

	DatabaseManager databaseManager = new DatabaseManager();
	BookDao bookDao = new BookDoaImpl();

	public void printMenu() {
		System.out.println("==== Book Manager ====");
		System.out.println("1) View all books");
		System.out.println("2) Add a book");
		System.out.println("3) Edit a book");
		System.out.println("4) Search for a book");
		System.out.println("5) Save and exit");
		System.out.print("Choose [1-5]: ");
	}

	public void viewAll() {
		// Get List of Books
		List<Book> bookList = new ArrayList<Book>();
		bookList = bookDao.findAll();
		if (bookList.size() <= 0) {
			System.out.println("There is no books found");
			return;
		}
		String bookId = "";
		boolean showListagain = true;
		do {
			// print short list
			System.out.println("==== View Books ====");
			printShortList(bookList);
			Scanner input = new Scanner(System.in);

			System.out.println("To view details enter the book ID, to return press <Enter>");
			System.out.print("Book Id: ");

			// read customer input
			bookId = input.nextLine();

			// if the value in not integer return
			if (bookId != null && !bookId.equals("")) {

				try {
					int id = Integer.parseInt(bookId);
					Book book = getBookByIdFromList(bookList, id);
					if (book == null)
						System.out.println("Please, insert correct book id.");
					else
						printBookDetails(book);
				} catch (NumberFormatException exception) {
					System.out.println("Please, insert correct book id.");
				}
				
			} else {
				showListagain = false;
			}

		} while (showListagain);

	}

	public void addBook() {
		System.out.println("==== Add Book ====");
		Scanner input = new Scanner(System.in);
		System.out.print("Title: ");
		String title = input.nextLine();

		System.out.print("Author: ");
		String author = input.nextLine();

		System.out.print("Description: ");
		String description = input.nextLine();

		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		book.setDescription(description);

		int bookid = bookDao.add(book);
		System.out.println("Book [" + bookid + "] Saved");
		
	}

	public void editBook() {
		// Get List of Books
		List<Book> bookList = new ArrayList<Book>();
		bookList = bookDao.findAll();
		if (bookList.size() <= 0) {
			System.out.println("There is no books found");
			return;
		}

		String bookId = "";
		boolean showListagain = true;
		do {
			// print short list
			System.out.println("==== Edit a Book ====");
			printShortList(bookList);
			Scanner input = new Scanner(System.in);

			System.out.println("Enter the book ID of the book you want to edit; to return press <Enter>.");
			System.out.print("Book Id: ");

			// read customer input
			bookId = input.nextLine();

			// if the value in not integer return
			if (bookId != null && !bookId.equals("")) {

				try {
					int id = Integer.parseInt(bookId) - 1;
					if (updateEntity(bookList.get(id))) {
						System.out.println("Book saved.");
					} else {
						System.out.println("Book not saved.");
					}

				} catch (NumberFormatException exception) {
					System.out.println("Please, insert correct book id.");
				}
				
			} else {
				showListagain = false;
			}

		} while (showListagain);

	}

	public void search() {
		// TODO Auto-generated method stub
		System.out.println("==== Search ====");
		System.out.println("Type in one or more keywords to search for");
		Scanner input = new Scanner(System.in);
		System.out.print("Search: ");
		String keyword = input.nextLine();
		List<Book> bookList = new ArrayList<Book>();
		bookList = bookDao.search(keyword);
		if (bookList.size() <= 0) {
			System.out.println("There is no books found");
			return;
		}

		System.out.println(
				"The following books matched your query. Enter the book ID to see more details, or <Enter> to return.");

		String bookId = "";
		boolean showListagain = true;

		do {
			// print short list
			printShortList(bookList);

			System.out.println("To view details enter the book ID, to return press <Enter>");
			System.out.print("Book Id: ");

			// read customer input
			bookId = input.nextLine();

			// if the value in not integer return
			if (bookId != null && !bookId.equals("")) {

				try {
					int id = Integer.parseInt(bookId);
					Book book = getBookByIdFromList(bookList, id);
					if (book == null)
						System.out.println("Please, insert correct book id.");
					else
						printBookDetails(book);
				} catch (NumberFormatException exception) {
					System.out.println("Please, insert correct book id.");
				}
				
			} else {
				showListagain = false;
			}

		} while (showListagain);

	}

	private Book getBookByIdFromList(List<Book> bookList, int id) {
		Optional<Book> optional = bookList.stream().filter(b -> b.getId() == id).findFirst();
		Book book = (optional.isPresent()) ? optional.get() : null;
		return book;
	}

	private void printShortList(List<Book> bookList) {

		for (Book book : bookList) {
			System.out.println("[" + book.getId() + "] " + book.getTitle());
		}
	}

	private void printBookDetails(Book book) {
		System.out.println("ID: " + book.getId());
		System.out.println("Title: " + book.getTitle());
		System.out.println("Author: " + book.getAuthor());
		System.out.println("Description: " + book.getDescription());
	}

	private boolean updateEntity(Book book) {
		Scanner input = new Scanner(System.in);
		System.out.print("Title [ " + book.getTitle() + " ]: ");
		String title = input.nextLine();
		System.out.print("Author [ " + book.getAuthor() + " ]: ");
		String author = input.nextLine();
		System.out.print("Description [ " + book.getDescription() + " ]: ");
		String description = input.nextLine();

		if (!title.isEmpty())
			book.setTitle(title);
		if (!author.isEmpty())
			book.setAuthor(author);
		if (!description.isEmpty())
			book.setDescription(description);
		boolean updated = bookDao.update(book);
		
		return updated;
	}
}
