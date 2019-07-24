package bookmanager.com.daos;

import java.util.List;

import bookmanager.com.entities.Book;

public interface BookDao {

	public List<Book> findAll();

	public boolean update(Book book);

	public int add(Book book);

	public List<Book> search(String text);

}
