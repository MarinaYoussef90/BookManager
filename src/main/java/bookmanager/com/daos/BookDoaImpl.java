package bookmanager.com.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmanager.com.entities.Book;
import bookmanager.com.managers.DatabaseManager;

public class BookDoaImpl implements BookDao {

	DatabaseManager databaseManager = new DatabaseManager();

	public List<Book> findAll() {
		Connection conn = databaseManager.getConnection();
		Statement stmt = null;
		List<Book> books = new ArrayList<Book>();
		try {
			stmt = conn.createStatement();
			String sql = "select * from BOOKS";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				// Retrieve by column name
				Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
						rs.getString("description"));
				books.add(book);
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
		}
		return books;
	}

	public boolean update(Book book) {
		Connection conn = databaseManager.getConnection();
		PreparedStatement prepStatement = null;

		try {
			prepStatement = conn
					.prepareStatement("UPDATE BOOKS SET title=?1 , author =?2, description =?3 WHERE id =?4");

			prepStatement.setString(1, book.getTitle());
			prepStatement.setString(2, book.getAuthor());
			prepStatement.setString(3, book.getDescription());
			prepStatement.setInt(4, book.getId());

			prepStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (prepStatement != null)
					prepStatement.close();
			} catch (SQLException se2) {
			}

		}
		return true;
	}

	public int add(Book book) {
		Connection conn = databaseManager.getConnection();
		PreparedStatement prepStatement = null;
		 int generatedKey = 0;
		try {
			String sql = "INSERT INTO BOOKS (title, author, description) VALUES (?, ?, ?)";
			prepStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			prepStatement.setString(1, book.getTitle());
			prepStatement.setString(2, book.getAuthor());
			prepStatement.setString(3, book.getDescription());

			prepStatement.executeUpdate();
			
			ResultSet rs = prepStatement.getGeneratedKeys();
			
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (prepStatement != null)
					prepStatement.close();
			} catch (SQLException se2) {
			}

		}
		return generatedKey;
	}

	public List<Book> search(String text) {
		Connection conn = databaseManager.getConnection();
		PreparedStatement prepStatement = null;
		List<Book> books = new ArrayList<Book>();
		try {
			
			prepStatement = conn.prepareStatement("select * from BOOKS WHERE TITLE LIKE?1 OR AUTHOR LIKE?1");
			prepStatement.setString(1, "%" + text + "%");
			ResultSet rs = prepStatement.executeQuery();

			while (rs.next()) {
				// Retrieve by column name
				Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
						rs.getString("description"));
				books.add(book);
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (prepStatement != null)
					prepStatement.close();
			} catch (SQLException se2) {
			}
		}
		return books;
	}

}
